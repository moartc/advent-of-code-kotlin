package solutions.aoc2025.day05

import utils.Resources
import utils.collections.extensions.splitOn

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val (rangeStrings, ingredientStrings) = inputLines.splitOn { it.isEmpty() }
    val ranges = rangeStrings
        .map { str -> str.split("-") }
        .map { split -> split[0].toLong() to split[1].toLong() }
    val ingredients = ingredientStrings.map { i -> i.toLong() }

    return ingredients.count { i -> ranges.any { r -> r.first <= i && r.second >= i } }
}

fun part2(inputLines: List<String>): Long {

    val (rangeStrings, _) = inputLines.splitOn { it.isEmpty() }
    val ranges = rangeStrings
        .map { str -> str.split("-") }
        .map { split -> split[0].toLong() to split[1].toLong() }

    val sortedRanges = ranges.sortedBy { it.first }
    val prevStart = sortedRanges[0].first
    var prevEnd = sortedRanges[0].second
    var total = prevEnd - prevStart + 1
    for (range in sortedRanges.drop(1)) {
        val (l, r) = range
        if (l > prevEnd) {
            total += r - l + 1
            prevEnd = r
        } else if (r > prevEnd) {
            total += r - prevEnd
            prevEnd = r
        }
    }
    return total
}





