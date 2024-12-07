package solutions.aoc2024.day07

import utils.Resources
import utils.parser.getLongs


fun main() {

    val inputLines = Resources.getLines(2024, 7)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun parseInp(inputLines: List<String>): List<Pair<Long, List<Long>>> {
    return inputLines.map {
        val f = it.split(": ")
        f[0].getLongs()[0] to f[1].getLongs()
    }
}

fun canMakeRes(expected: Long, index: Int, current: Long, numbers: List<Long>, isPart2: Boolean): Boolean {
    if (index == numbers.size) {
        return current == expected
    }
    val next = numbers[index]
    return canMakeRes(expected, index + 1, current + next, numbers, isPart2) ||
            canMakeRes(expected, index + 1, current * next, numbers, isPart2) ||
            (isPart2 && canMakeRes(expected, index + 1, current.toString().plus(next).toLong(), numbers, isPart2))
}

fun part1(inputLines: List<String>): Long {

    val expToList = parseInp(inputLines)
    return expToList.filter { pair -> canMakeRes(pair.first, 1, pair.second[0], pair.second, false) }.sumOf { it.first }
}

fun part2(inputLines: List<String>): Long {

    val expToList = parseInp(inputLines)
    return expToList.filter { pair -> canMakeRes(pair.first, 1, pair.second[0], pair.second, true) }.sumOf { it.first }
}