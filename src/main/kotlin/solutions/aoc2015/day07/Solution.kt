package solutions.aoc2015.day07

import utils.Resources

fun main() {
    val inputLines = Resources.getLines(2015, 7)
    val part1 = part1(inputLines)
    println("part1 = $part1")
    println("part2 = " + part2(inputLines, part1))
}

fun part1(input: List<String>): UShort {
    return processCircuit(input.map { s -> createOperation(s) })["a"]!!
}

fun part2(input: List<String>, part1: UShort): UShort {
    val ops = input.map { s -> createOperation(s) }
    ops.filterIsInstance<AssignOperation>().first { it.right == "b" }.left = part1.toString()
    return processCircuit(ops)["a"]!!
}

fun processCircuit(ops: List<Operation>): MutableMap<String, UShort> {
    val valueMap: MutableMap<String, UShort> = mutableMapOf()
    generateSequence(ops) { i -> i }.takeWhile { it.any { op -> !op.isProcessed } }
        .forEach { it -> it.filter { op -> !op.isProcessed }.map { it.process(valueMap) } }
    return valueMap
}

fun createOperation(line: String): Operation {
    val split = line.split(" -> ")
    val splitLeft = split[0].split(" ")
    return when (splitLeft.size) {
        1 -> AssignOperation(splitLeft[0], split[1])
        2 -> NotOperation(splitLeft[1], split[1])
        3 -> DoubleArgOperation(splitLeft[0], splitLeft[2], splitLeft[1], split[1])
        else -> throw IllegalStateException("Cannot create operation")
    }
}

abstract class Operation(var isProcessed: Boolean) {
    abstract fun process(valueMap: MutableMap<String, UShort>)
}

class AssignOperation(var left: String, val right: String) : Operation(false) {
    override fun process(valueMap: MutableMap<String, UShort>) {
        val leftVal = left.getUShort(valueMap)
        if (leftVal != null) {
            valueMap[right] = leftVal
            isProcessed = true
        }
    }
}

class NotOperation(private val left: String, private val right: String) : Operation(false) {
    override fun process(valueMap: MutableMap<String, UShort>) {
        val leftVal = left.getUShort(valueMap)
        if (leftVal != null) {
            valueMap[right] = leftVal.inv()
            isProcessed = true
        }
    }
}

class DoubleArgOperation(
    private val first: String,
    private val second: String,
    private val op: String,
    private val right: String
) : Operation(false) {

    override fun process(valueMap: MutableMap<String, UShort>) {
        val firstVal = first.getUShort(valueMap)
        val secondVal = second.getUShort(valueMap)
        if (firstVal != null && secondVal != null) {
            valueMap[right] = processDoubleArg(firstVal, secondVal, op)
            isProcessed = true
        }
    }

    private fun processDoubleArg(firstVal: UShort, secondVal: UShort, op: String): UShort {
        return when (op) {
            "AND" -> firstVal and secondVal
            "OR" -> firstVal or secondVal
            "LSHIFT" -> (firstVal.toInt() shl secondVal.toInt()).toUShort()
            "RSHIFT" -> (firstVal.toInt() shr secondVal.toInt()).toUShort()
            else -> throw IllegalStateException("Cannot process Double Arg Op")
        }
    }
}

fun String.getUShort(valueMap: MutableMap<String, UShort>): UShort? {
    return if (this.toUShortOrNull() != null) {
        this.toUShort()
    } else {
        valueMap[this]
    }
}

