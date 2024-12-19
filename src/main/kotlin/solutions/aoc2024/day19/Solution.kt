package solutions.aoc2024.day19

import utils.Resources
import utils.collections.extensions.splitOnEmpty

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    fun canBeMade(target: String, patterns: List<String>, index: Int): Boolean {
        if (index == target.length) {
            return true
        }
        for (pattern in patterns) {
            if (target.startsWith(pattern, index)) {
                if (canBeMade(target, patterns, index + pattern.length)) {
                    return true
                }
            }
        }
        return false
    }

    val splitOnEmpty = inputLines.splitOnEmpty()
    val patterns = splitOnEmpty[0][0].split(", ").map { it.trim() }
    val designs = splitOnEmpty[1]

    return designs.count { design -> canBeMade(design, patterns, 0) }
}

fun part2(inputLines: List<String>): Long {

    fun countWays(design: String, patterns: List<String>, cache: MutableMap<String, Long> = mutableMapOf()): Long {
        if (design.isEmpty()) {
            return 1L
        }
        if (cache.containsKey(design)) {
            return cache[design]!!
        }

        var total = 0L
        for (pattern in patterns) {
            if (design.startsWith(pattern)) {
                val remaining = design.removePrefix(pattern)
                total += countWays(remaining, patterns, cache)
            }
        }
        cache[design] = total
        return total
    }

    val splitOnEmpty = inputLines.splitOnEmpty()
    val patterns = splitOnEmpty[0][0].split(", ").map { it.trim() }
    val designs = splitOnEmpty[1]

    return designs.sumOf { countWays(it, patterns) }
}



