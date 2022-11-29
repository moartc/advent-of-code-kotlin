package solutions.aoc2017.day08

import utils.Resources

fun main() {
    val inputLines = Resources.getLines(2017, 8)
    val instructions = parseInput(inputLines)
    println("part1 = ${part1(instructions)}")
    println("part2 = ${part2(instructions)}")
}

fun part1(instructions: List<Instruction>): Int {
    return instructions.fold(mutableMapOf<String, Int>()) { acc, instruction ->
        val current = acc.getOrDefault(instruction.register, 0)
        if (isConditionTrue(acc, instruction.condition)) {
            if (instruction.lineOp == "inc") {
                acc[instruction.register] = current + instruction.lineVal
            } else {
                acc[instruction.register] = current - instruction.lineVal
            }
        }
        return@fold acc
    }.maxBy { it.value }.value
}

fun part2(instructions: List<Instruction>): Int {
    return instructions.fold(Pair(mutableMapOf<String, Int>(), 0)) { acc, instruction ->
        val current = acc.first.getOrDefault(instruction.register, 0)
        if (isConditionTrue(acc.first, instruction.condition)) {
            if (instruction.lineOp == "inc") {
                acc.first[instruction.register] = current + instruction.lineVal
            } else {
                acc.first[instruction.register] = current - instruction.lineVal
            }
        }
        return@fold Pair(acc.first, acc.first.maxBy { it.value }.value.coerceAtLeast(acc.second))
    }.second
}

fun parseInput(inputLines: List<String>): List<Instruction> {
    return inputLines.map { line -> Regex("""(\w+) (\S+) (-?[0-9]+) if (\w+) (.*) (-?[0-9]+)""").matchEntire(line)!!.groups }
        .map {
            Instruction(
                it[1]!!.value,
                it[2]!!.value,
                it[3]!!.value.toInt(),
                Condition(it[4]!!.value, it[5]!!.value, it[6]!!.value.toInt())
            )
        }
}

fun isConditionTrue(register: Map<String, Int>, cond: Condition): Boolean {
    val regValue = register.getOrDefault(cond.reg, 0)
    return when (cond.op) {
        "<" -> regValue < cond.value
        ">" -> regValue > cond.value
        "<=" -> regValue <= cond.value
        ">=" -> regValue >= cond.value
        "==" -> regValue == cond.value
        else -> regValue != cond.value
    }
}

data class Condition(val reg: String, val op: String, val value: Int)
data class Instruction(val register: String, val lineOp: String, val lineVal: Int, val condition: Condition)