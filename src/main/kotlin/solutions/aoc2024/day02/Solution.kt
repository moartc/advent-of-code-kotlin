package solutions.aoc2024.day02

import utils.Resources
import utils.parser.getInts
import kotlin.math.absoluteValue


fun main() {

    val inputLines = Resources.getLines(2024, 2)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part2(inputLines: List<String>): Int {

    var safe = 0
    ex@ for (s in inputLines) {
        val ints = s.getInts()

        for ((idx, _) in ints.withIndex()) {
            val removed = ints.toMutableList()
            removed.removeAt(idx)
            if (isCorrect(removed)) {
                safe++
                continue@ex
            }
        }
    }
    return safe
}

fun part1(inputLines: List<String>): Int {

    return inputLines.count { isCorrect(it.getInts()) }
}

fun isCorrect(list: List<Int>): Boolean {
    val differences = list.zipWithNext().map { (a, b) -> b - a }
    return (differences.all { it < 0 } || differences.all { it > 0 }) && differences.all { it.absoluteValue in 1..3 }
}
