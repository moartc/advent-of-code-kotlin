package solutions.aoc2025.day06

import utils.Resources
import utils.collections.extensions.product
import utils.parser.getLongs

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val map = inputLines.dropLast(1).map { x -> x.getLongs() }
    val operations = inputLines[inputLines.lastIndex].filter { !it.isWhitespace() }.toCharArray()

    var result = 0L
    for (x in 0..map[0].lastIndex) {
        val c = operations[x]
        var singleRes = 0L
        if (c == '*') {
            singleRes = 1L
        }
        for (y in 0..map.lastIndex) {
            if (c == '*') {
                singleRes *= map[y][x]
            } else if (c == '+') {
                singleRes += map[y][x]
            }
        }
        result += singleRes
    }
    return result
}

fun part2(inputLines: List<String>): Long {

    var ctr = 0
    val sb = StringBuilder()
    val maxLen = inputLines.maxOf { it.length }
    val prevLongs = mutableListOf<Long>()
    var totalSum = 0L
    for (x in maxLen - 1 downTo 0) {
        for ((index, string) in inputLines.withIndex()) {
            if (x < string.length && string[x].isDigit()) {
                sb.append(string[x])
            } else if (index == inputLines.lastIndex) {
                if (sb.toString().isNotEmpty()) {
                    prevLongs.add(sb.toString().toLong())
                }
                if (x < string.length && string[x] == '*') {
                    totalSum += prevLongs.product()
                    ctr++
                    prevLongs.clear()
                } else if (x < string.length && string[x] == '+') {
                    totalSum += prevLongs.sum()
                    ctr++
                    prevLongs.clear()
                }
                sb.clear()
            }
        }
    }
    return totalSum
}




