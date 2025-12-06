package solutions.aoc2025.day06

import utils.Resources
import utils.collections.extensions.product
import utils.collections.extensions.transpose
import utils.parser.getLongs

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val operations = inputLines[inputLines.lastIndex].filter { !it.isWhitespace() }.toCharArray()
    return inputLines
        .dropLast(1)
        .map { x -> x.getLongs() }
        .transpose()
        .withIndex().sumOf { (i, longs) ->
            if (operations[i] == '*') {
                longs.product()
            } else {
                longs.sum()
            }
        }
}

fun part2(inputLines: List<String>): Long {

    val sb = StringBuilder()
    val maxLen = inputLines.maxOf { it.length }
    val collectedLongs = mutableListOf<Long>()
    var totalSum = 0L
    for (x in maxLen - 1 downTo 0) {
        for ((y, string) in inputLines.withIndex()) {
            if (x < string.length && string[x].isDigit()) {
                sb.append(string[x])
            } else if (y == inputLines.lastIndex) {
                val longStr = sb.toString()
                if (longStr.isNotEmpty()) {
                    collectedLongs.add(longStr.toLong())
                }
                if (x < string.length && string[x] == '*') {
                    totalSum += collectedLongs.product()
                    collectedLongs.clear()
                } else if (x < string.length && string[x] == '+') {
                    totalSum += collectedLongs.sum()
                    collectedLongs.clear()
                }
                sb.clear()
            }
        }
    }
    return totalSum
}




