package solutions.aoc2022.day03

import utils.Resources

fun main() {
    val inputLines = Resources.getLines(2022, 3)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(input: List<String>): Int {
    return input.sumOf { s ->
        val leftSet = s.substring(0, s.length / 2).toCharArray().toMutableSet()
        leftSet.retainAll(s.substring(s.length / 2).toCharArray().toSet())
        val diff = leftSet.iterator().next()
        if (diff.isLowerCase()) diff.code - 96 else diff.code - 38
    }
}

fun part2(input: List<String>): Int {
    return input.chunked(3).sumOf {
        val res = it[0].toCharArray().toMutableSet()
        res.retainAll(it[1].toCharArray().toSet())
        res.retainAll(it[2].toCharArray().toSet())
        val diff = res.iterator().next()
        if (diff.isLowerCase()) diff.code - 96 else diff.code - 38
    }
}