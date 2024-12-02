package solutions.aoc2024.day01

import utils.Resources
import utils.parser.getLongs
import kotlin.math.abs


fun main() {

    val inputLines = Resources.getLines(2024, 1)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val left = mutableListOf<Long>()
    val right = mutableListOf<Long>()
    inputLines.forEach {
        val split = it.getLongs()
        left.add(split[0])
        right.add(split[1])
    }

    val ls = left.sorted()
    val rs = right.sorted()

    return ls.withIndex().sumOf { (i, v) -> abs(v - rs[i]) }
}

fun part2(inputLines: List<String>): Long {

    val leftList = mutableListOf<Long>()
    val rightList = mutableListOf<Long>()

    inputLines.forEach {
        val longs = it.getLongs()
        leftList.add(longs[0])
        rightList.add(longs[1])
    }

    val freq = rightList.groupingBy { it }.eachCount()

    return leftList.sumOf {
        val rightVal = freq.getOrDefault(it, 0)
        it * rightVal
    }
}

