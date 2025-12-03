package solutions.aoc2025.day03

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    return inputLines.sumOf { line ->
        line.toCharArray().map { x -> x.digitToInt() }.let { r ->
            r.indices.flatMap { i ->
                (i + 1..r.lastIndex).map { j -> r[i] * 10 + r[j] }
            }.max()
        }
    }
}

fun part2(inputLines: List<String>): Long {

    fun best12(str: String): String {
        var start = 0
        val sb = StringBuilder()
        repeat(12) {
            var best = str[start]
            var bestIdx = start
            for (i in start + 1..str.lastIndex - 12 + it + 1) {
                if (str[i] > best) {
                    best = str[i]
                    bestIdx = i
                }
            }
            sb.append(best)
            start = bestIdx + 1
        }
        return sb.toString()
    }
    return inputLines.sumOf { line -> best12(line).toLong() }
}
