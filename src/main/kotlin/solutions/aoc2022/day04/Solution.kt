package solutions.aoc2022.day04

import utils.Resources

fun main() {
    val inputLines = Resources.getLines(2022, 4)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(input: List<String>): Int {
    return ranges(input).count { (r1, r2) -> (r1.contains(r2.first) && r1.contains(r2.last) || r2.contains(r1.first) && r2.contains(r1.last)) }
}

fun part2(input: List<String>): Int {
    return ranges(input).count { (r1, r2) -> r1.contains(r2.first) || r1.contains(r2.last) || r2.contains(r1.first) || r2.contains(r1.last) }
}

fun ranges(list: List<String>): List<Pair<IntRange, IntRange>> = list.map {
    val pairs = it.split(",")
    val left = pairs[0].split("-")
    val right = pairs[1].split("-")
    Pair((left[0].toInt()..left[1].toInt()), (right[0].toInt()..right[1].toInt()))
}

