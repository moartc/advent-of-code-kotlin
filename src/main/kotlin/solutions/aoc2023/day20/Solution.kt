package solutions.aoc2023.day20

import utils.Resources
import java.util.*

fun main() {

    val inputLine =
        Resources.getLines(2023, 20)
//        Resources.getLinesExample(2023, 20)
//    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

val queueToSend: Queue<String> = LinkedList<String>()
val brc = Brc(mutableListOf())
val modulesMap = mutableMapOf<String, M>()

fun part2(input: List<String>): Int {

    input.forEach {
        it.log()

        val split = it.split(" -> ")
        val sender = split[0].trim()
        val connections = split[1]!!.split(",").map { it.trim() }

        if(sender == "broadcaster") {
            brc.modules = connections.toMutableList()
        } else {
            if(sender[0] == '%') {
                val drop = sender.drop(1)
                val cm = FFM(drop, false, LinkedList(), connections.toMutableList())
                modulesMap[drop] =  cm
            } else if(sender[0] == '&') {
                val drop = sender.drop(1)
                val cm = CM(drop, mutableMapOf(), connections.toMutableList())
                modulesMap[drop] =  cm
            } else {
                error("unknown sender")
            }
        }
        val q = 12
    }

    // update map for CM

    val filter = modulesMap.values.filter { c -> c is CM }.map { it as CM }

    fun allThatSentTo(s : String): List<String> {
        return modulesMap.values.filter { m ->
            m is CM && m.modules.contains(s) || m is FFM && m.modules.contains(s)
        }.map { m -> if(m is CM) m.name else if (m is FFM) m.name else "" }
    }

    filter.forEach { cm ->
        val allThatSend = allThatSentTo(cm.name)
        allThatSend.forEach {
            cm.rememberHigh[it] = false

        }

    }
    var btPres = 0L


    fun pushOnce() {

        brc.modules.forEach {
            lowSent++
            modulesMap[it]!!.receive("broadcaster", false)
            queueToSend.add(it)
        }
        while(!queueToSend.isEmpty()) {
            val poll = queueToSend.poll()!!
            val currentSender = modulesMap[poll]
            if(currentSender is CM) {
                val toSend = currentSender.send()
                currentSender.modules.forEach {
//                    println("checking FFF ${currentSender.name} if can send to ${it}")

                    val currentReceiver = modulesMap[it]
                    if(toSend == true) highSent++ else lowSent++

                    if(it == "rx" && toSend != null && toSend == false) {
                        btPres.log("button pressed")
                        return
                    }
                    if(currentReceiver == null) {
//                        println(">>>>>>>>>>> ${currentSender.name} send ${toSend} to output")
                    } else {

                        if(toSend != null) {
//                            println(">>>>>>>>>>> ${currentSender.name} send ${toSend} to ${it}")


                            currentReceiver.receive(currentSender.name, toSend)
                            queueToSend.add(it)
                        }
                    }

                }
            } else if(currentSender is FFM) {
                val toSend = currentSender.send()

                currentSender.modules.forEach {

//                    println("checking FFF ${currentSender.name} if can send to ${it}")
                    val currentReceiver = modulesMap[it]!!
                    if(it == "rx" && toSend != null && toSend == false) {
                        btPres.log("button pressed")
                        return
                    }
                    if(toSend != null) {
                        if(toSend) highSent++ else lowSent++
//                        println(">>>>>>>>>>> ${currentSender.name} send ${toSend} to ${it}")
                        if(it == "rx" && !toSend) {
                            btPres.log("button pressed")
                            return
                        }
                        currentReceiver.receive(currentSender.name, toSend!!)
                        queueToSend.add(it)
                    }
                }
            }
        }
    }

    while(true){
        btPres++
        btPres.log("button pressed")
        pushOnce()
    }


    lowSent.log("low sent")
    highSent.log("high sent")

    val res = lowSent * highSent
    res.log("res")
    return 12
}

//fun part1(input: List<String>): Int {
//
//    input.forEach {
//        it.log()
//
//        val split = it.split(" -> ")
//        val sender = split[0].trim()
//        val connections = split[1]!!.split(",").map { it.trim() }
//
//        if(sender == "broadcaster") {
//            brc.modules = connections.toMutableList()
//        } else {
//            if(sender[0] == '%') {
//                val drop = sender.drop(1)
//                val cm = FFM(drop, false, LinkedList(), connections.toMutableList())
//                modulesMap[drop] =  cm
//            } else if(sender[0] == '&') {
//                val drop = sender.drop(1)
//                val cm = CM(drop, mutableMapOf(), connections.toMutableList())
//                modulesMap[drop] =  cm
//            } else {
//                error("unknown sender")
//            }
//        }
//        val q = 12
//    }
//
//    // update map for CM
//
//    val filter = modulesMap.values.filter { c -> c is CM }.map { it as CM }
//
//    fun allThatSentTo(s : String): List<String> {
//        return modulesMap.values.filter { m ->
//            m is CM && m.modules.contains(s) || m is FFM && m.modules.contains(s)
//        }.map { m -> if(m is CM) m.name else if (m is FFM) m.name else "" }
//    }
//
//    filter.forEach { cm ->
//        val allThatSend = allThatSentTo(cm.name)
//        allThatSend.forEach {
//            cm.rememberHigh[it] = false
//
//        }
//
//    }
//
//
//    fun pushOnce() {
//
//        brc.modules.forEach {
//            lowSent++
//            modulesMap[it]!!.receive("broadcaster", false)
//            queueToSend.add(it)
//        }
//        while(!queueToSend.isEmpty()) {
//            val poll = queueToSend.poll()!!
//            val currentSender = modulesMap[poll]
//            if(currentSender is CM) {
//                val toSend = currentSender.send()
//                currentSender.modules.forEach {
////                    println("checking FFF ${currentSender.name} if can send to ${it}")
//
//                    val currentReceiver = modulesMap[it]
//                    if(toSend == true) highSent++ else lowSent++
//
//                    if(currentReceiver == null) {
//                        println(">>>>>>>>>>> ${currentSender.name} send ${toSend} to output")
//                    } else {
//                        if(toSend != null) {
//                            println(">>>>>>>>>>> ${currentSender.name} send ${toSend} to ${it}")
//
//
//                            currentReceiver.receive(currentSender.name, toSend)
//                            queueToSend.add(it)
//                        }
//                    }
//
//                }
//            } else if(currentSender is FFM) {
//                val toSend = currentSender.send()
//
//                currentSender.modules.forEach {
//
////                    println("checking FFF ${currentSender.name} if can send to ${it}")
//                    val currentReceiver = modulesMap[it]!!
//                    if(toSend != null) {
//                        if(toSend) highSent++ else lowSent++
//                        println(">>>>>>>>>>> ${currentSender.name} send ${toSend} to ${it}")
//                        currentReceiver.receive(currentSender.name, toSend!!)
//                        queueToSend.add(it)
//                    }
//                }
//            }
//        }
//    }
//
//    repeat(1000){
//        pushOnce()
//        lowSent++ // for a button
//
//    }
//
//
//    lowSent.log("low sent")
//    highSent.log("high sent")
//
//    val res = lowSent * highSent
//    res.log("res")
//    return 12
//}

var highSent = 0
var lowSent = 0

interface M {
    fun receive(from : String, high: Boolean)
    fun send() : Boolean?
}

class Brc(var modules: MutableList<String>) { // single low to all

}
class CM(var name: String, var rememberHigh: MutableMap<String, Boolean>, var modules: MutableList<String>) : M{
    constructor() : this("", mutableMapOf(), mutableListOf()) {
    }
    override fun receive(from : String, high: Boolean) {
//        println("$name received ${high} from $from")
        rememberHigh[from] = high
//        rememberHigh.log("update for $name to")
    }

    override fun send(): Boolean? {
        if(rememberHigh.all { x -> x.value == true } ) { // if remember high for all
//            println("$name send low")
            return false // return low
        } else {
//            println("$name send high")
            return true // return high
        }
    }

}
class FFM(var name: String, var switchOn: Boolean = false, var lastRecHigh : Queue<Boolean>, var modules: MutableList<String>) : M{

    override fun receive(from : String, high: Boolean) { // switch when receive low
        lastRecHigh.add(high)
        if(high) {
//            println("$name received high, don't switch")
        } else {
//            println("$name received low switches to ${!this.switchOn}")
            this.switchOn = !this.switchOn
        }
    }

    override fun send(): Boolean? {
        val poll = lastRecHigh.poll()
        if(poll == false) {
            return switchOn
        } else {
//            println("$name send null")
            return null
        }
    }


}

private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



