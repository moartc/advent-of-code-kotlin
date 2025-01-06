package solutions.aoc2017.day25

import utils.Resources
import utils.collections.extensions.splitOnEmpty
import utils.parser.getInt

fun main() {
    val input = Resources.getLines(2017, 25)
    println("part1 = ${part1(input)}")
}

data class State(
    val valueToWriteFor0: Int, val moveSlotsIf0: Int, val nextStateIf0: Char,
    val valueToWriteFor1: Int, val moveSlotsIf1: Int, val nextStateIf1: Char
)

fun part1(input: List<String>): Int {

    val split = input.splitOnEmpty()
    val startingState = split[0][0].takeLast(2).dropLast(1)[0]
    val numberOfSteps = split[0][1].getInt()!!

    val states = split.drop(1).associate {
        val stateId = it[0].takeLast(2).dropLast(1)[0]
        val valueToWriteFor0 = it[2].takeLast(2).dropLast(1).toInt()
        val whereMove = it[3].split(" ").last()
        val moveSlotsIf0 = if (whereMove == "right.") 1 else -1
        val nextStateIf0 = it[4].takeLast(2).dropLast(1)[0]
        val valueToWriteFor1 = it[6].takeLast(2).dropLast(1).toInt()
        val whereMove2 = it[7].split(" ").last()
        val moveSlotsIf1 = if (whereMove2 == "right.") 1 else -1
        val nextStateIf1 = it[8].takeLast(2).dropLast(1)[0]
        stateId to State(valueToWriteFor0, moveSlotsIf0, nextStateIf0, valueToWriteFor1, moveSlotsIf1, nextStateIf1)
    }

    val slots = mutableMapOf<Int, Int>()
    fun doStep(currentSlot: Int, currentState: State): Pair<Int, Char> {
        val currentValue = slots.getOrDefault(currentSlot, 0)
        if (currentValue == 0) {
            slots[currentSlot] = currentState.valueToWriteFor0
            return currentState.moveSlotsIf0 to currentState.nextStateIf0
        } else {
            slots[currentSlot] = currentState.valueToWriteFor1
            return currentState.moveSlotsIf1 to currentState.nextStateIf1
        }
    }

    var currentState = startingState
    var currentSlot = 0
    repeat(numberOfSteps) {
        val stateToUse = states[currentState]!!
        val res = doStep(currentSlot, stateToUse)
        currentSlot += res.first
        currentState = res.second
    }

    return slots.count { x -> x.value == 1 }
}
