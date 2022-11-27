package solutions.aoc2017.day05

import utils.Resources

fun main() {
    val inputLines = Resources.getLines(2017, 5)
    println("part1 = " + part1(inputLines.map { str -> str.toInt() }.toMutableList()))
    println("part2 = " + part2(inputLines.map { str -> str.toInt() }.toMutableList()))
}

fun part1(input: MutableList<Int>): Int {
    return getResult(input, 0, 0) { 1 }
}

fun part2(input: MutableList<Int>): Int {
    return getResult(input, 0, 0) { x -> if (x >= 3) -1 else 1 }
}

tailrec fun getResult(list: MutableList<Int>, index: Int, counter: Int, updater: (Int) -> (Int)): Int =
    if (index >= list.size) {
        counter
    } else {
        val updated = update(index, list, updater)
        getResult(updated.second, updated.first, counter + 1, updater)
    }

fun update(nextIndex: Int, list: MutableList<Int>, updater: (Int) -> (Int)): Pair<Int, MutableList<Int>> {
    val jump = list[nextIndex]
    list[nextIndex] = jump + updater(jump)
    return Pair(nextIndex + jump, list)
}