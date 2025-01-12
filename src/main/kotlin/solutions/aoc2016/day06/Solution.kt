package solutions.aoc2016.day06

import utils.Resources
import utils.collections.extensions.toFrequencyMap

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): String {

    return (0..inputLines[0].lastIndex)
        .map { idx ->
            inputLines.map { it[idx] }.toFrequencyMap().entries.maxByOrNull { it.value }!!.key
        }
        .joinToString("")
}

fun part2(inputLines: List<String>): String {
    return (0..inputLines[0].lastIndex)
        .map { idx ->
            inputLines.map { it[idx] }.toFrequencyMap().entries.minByOrNull { it.value }!!.key
        }
        .joinToString("")
}