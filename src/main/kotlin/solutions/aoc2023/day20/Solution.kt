package solutions.aoc2023.day20

import utils.Resources
import java.util.*

fun main() {

    val inputLine = Resources.getLines(2023, 20)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun parseInput(input: List<String>): Map<String, M> {
    return input.associate {
        val split = it.split(" -> ")
        val sender = split[0].trim()
        val connections = split[1].split(",").map { it.trim() }

        if (sender == "broadcaster") {
            val broadcasterM = BroadcasterM(connections)
            broadcasterM.name to broadcasterM
        } else if (sender[0] == '%') {
            val drop = sender.drop(1)
            val ffm = FlipFlopM(drop, false, LinkedList(), connections.toMutableList())
            drop to ffm
        } else if (sender[0] == '&') {
            val drop = sender.drop(1)
            val cm = ConjunctionM(drop, mutableMapOf(), connections.toMutableList())
            drop to cm
        } else {
            error("unknown sender")
        }
    }
}

fun allModulesThatSendTo(s: String, modulesMap: Map<String, M>): List<String> =
    modulesMap.values.filter { m -> m.modules.contains(s) }.map { m -> m.name }

fun initializeMemory(modulesMap: Map<String, M>) {

    // initialize memory for all senders
    modulesMap.values.filterIsInstance<ConjunctionM>().forEach { cm ->
        val allThatSend = allModulesThatSendTo(cm.name, modulesMap)
        allThatSend.forEach {
            cm.rememberHigh[it] = false
        }
    }
}

fun part1(input: List<String>): Int {
    val queueToSend: Queue<String> = LinkedList()
    val modulesMap = parseInput(input)
    initializeMemory(modulesMap)
    var highSent = 0
    var lowSent = 0

    fun pushOnce() {
        modulesMap["broadcaster"]!!.modules.forEach {
            lowSent++
            modulesMap[it]!!.receive("broadcaster", false)
            queueToSend.add(it)
        }
        while (!queueToSend.isEmpty()) {
            val poll = queueToSend.poll()!!
            val currentSender = modulesMap[poll]
            val toSend = currentSender!!.send()
            currentSender.modules.forEach {
                val currentReceiver = modulesMap[it]
                if (toSend != null) {
                    if (toSend == true) highSent++ else lowSent++
                    if (currentReceiver != null) {
                        currentReceiver.receive(currentSender.name, toSend)
                        queueToSend.add(it)
                    }
                }
            }
        }
    }
    repeat(1000) {
        lowSent++
        pushOnce()
    }
    return lowSent * highSent
}

// It may not work for other data. It assumes that the final module is 'rx' which receives the signal
// from one Conjunction module.
fun part2(input: List<String>): Long {
    val queueToSend: Queue<String> = LinkedList()
    val modulesMap = parseInput(input)
    initializeMemory(modulesMap)
    var highSent = 0
    var lowSent = 0
    var btnPressCounter = 0L
    val senderToRx = allModulesThatSendTo("rx", modulesMap)[0]
    val allThatSentTo = allModulesThatSendTo(senderToRx, modulesMap).associateWith { 0L }.toMutableMap()

    fun pushOnce(): Long {

        modulesMap["broadcaster"]!!.modules.forEach {
            lowSent++
            modulesMap[it]!!.receive("broadcaster", false)
            queueToSend.add(it)
        }
        while (!queueToSend.isEmpty()) {
            val poll = queueToSend.poll()!!
            val currentSender = modulesMap[poll]
            val toSend = currentSender!!.send()
            currentSender.modules.forEach { it ->
                val currentReceiver = modulesMap[it]
                if (toSend != null) {
                    if (toSend) highSent++ else lowSent++
                    if (currentReceiver != null) {
                        if (allThatSentTo.contains(it) && !toSend) {
                            if (allThatSentTo[it]!! == 0L) { // update cycle
                                allThatSentTo[it] = btnPressCounter
                                // check after the update whether all cycles have been found
                                if (allThatSentTo.values.all { it != 0L }) {
                                    return allThatSentTo.values.reduce { f, s -> f * s }
                                }
                            }
                        }
                        currentReceiver.receive(currentSender.name, toSend)
                        queueToSend.add(it)
                    }
                }
            }
        }
        return 0L
    }

    var result: Long
    do {
        btnPressCounter++
        result = pushOnce()
    } while (result == 0L)
    return result
}


interface M {

    val name: String
    val modules: List<String>
    fun receive(from: String, isHigh: Boolean)
    fun send(): Boolean?
}

class BroadcasterM(override val modules: List<String>) : M {

    override val name: String = "broadcaster"
    override fun receive(from: String, isHigh: Boolean) {
        // deliberately empty
    }

    override fun send(): Boolean {
        return false
    }

}

class ConjunctionM(
    override val name: String,
    var rememberHigh: MutableMap<String, Boolean>,
    override val modules: MutableList<String>
) : M {

    override fun receive(from: String, isHigh: Boolean) {
        rememberHigh[from] = isHigh
    }

    override fun send(): Boolean {
        return !rememberHigh.all { x -> x.value }
    }

}

class FlipFlopM(
    override val name: String,
    private var isSwitchOn: Boolean = false,
    private var isLastReceivedHigh: Queue<Boolean>,
    override val modules: MutableList<String>
) : M {

    override fun receive(from: String, isHigh: Boolean) {
        isLastReceivedHigh.add(isHigh)
        if (!isHigh) {
            this.isSwitchOn = !this.isSwitchOn
        }
    }

    override fun send(): Boolean? {
        val poll = isLastReceivedHigh.poll()
        return if (poll == false) {
            isSwitchOn
        } else {
            null
        }
    }
}



