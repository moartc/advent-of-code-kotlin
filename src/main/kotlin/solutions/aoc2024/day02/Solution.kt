package solutions.aoc2024.day02

import utils.Resources
import utils.parser.getInts
import kotlin.math.absoluteValue


fun main() {

    val inputLines = Resources.getLines(2024, 2)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    fun isCorrect(list: List<Int>): Boolean {
        val differences = list.zipWithNext().map { (a, b) -> b - a }
        return (differences.all { it < 0 } || differences.all { it > 0 }) && differences.all { it.absoluteValue in 1..3 }
    }
    return inputLines.count { isCorrect(it.getInts()) }
}

fun part2(inputLines: List<String>): Int {

    fun isCorrectPart2(list: List<Int>, toSkip: Int): Boolean {
        val differences = list.filterIndexed { index, _ -> index != toSkip }.zipWithNext().map { (a, b) -> b - a }
        return (differences.all { it < 0 } || differences.all { it > 0 }) && differences.all { it.absoluteValue in 1..3 }
    }
    return inputLines.count {
        val ints = it.getInts()
        ints.withIndex().any { (index, _) -> isCorrectPart2(ints, index) }
    }
}
