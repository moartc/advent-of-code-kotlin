package solutions.aoc2022.day11

import utils.Resources
import utils.parser.getInt
import utils.parser.getInts

fun main() {
    val input = Resources.getLines(2022, 11)

    println("part1 = ${solve(input, 20, true)}")
    println("part2 = ${solve(input, 10000, false)}")
}

fun parseInput(input: List<String>): List<Monkey> {
    val chunks = input.chunked(7).map { it.subList(1, 6) }
    return chunks.map {
        val first = it[0].getInts().map { v -> v.toLong() }.toMutableList()
        val operation = it[1].split(" ").takeLast(2)
        val divisibleBy = it[2].getInt()!!
        val opTrue = it[3].getInt()!!
        val opFalse = it[4].getInt()!!
        Monkey(first, operation[0].first(), operation[1], divisibleBy, opTrue, opFalse)
    }
}

fun newValue(item: Long, char: Char, value: String): Long {
    return if (char == '*') return if (value == "old") item * item else item * value.toLong()
    else if (value == "old") item + item else item + value.toLong()
}

fun solve(input: List<String>, reps: Int, divBy3: Boolean): Long {
    val monkeys = parseInput(input)
    val insp = IntArray(monkeys.size)
    val allDivisors = monkeys.map { it.divisibleBy }.reduce { acc, i -> acc * i }
    repeat(reps) {
        monkeys.forEachIndexed { index, monkey ->
            insp[index] += monkey.items.size
            monkey.items.forEach { item ->
                val valToThrow = newValue(item, monkey.opChar, monkey.opVal) % allDivisors / if (divBy3) 3 else 1
                val throwTo = if (valToThrow % monkey.divisibleBy == 0L) monkey.ifTrue else monkey.ifFalse
                monkeys[throwTo].items.add(valToThrow)
            }
            monkey.items = mutableListOf()
        }
    }
    val twoMostActiveEvent = insp.sorted().takeLast(2)
    return twoMostActiveEvent[0].toLong() * twoMostActiveEvent[1].toLong()
}

data class Monkey(var items: MutableList<Long>, val opChar: Char, val opVal: String, val divisibleBy: Int, val ifTrue: Int, val ifFalse: Int)
