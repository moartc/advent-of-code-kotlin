package solutions.aoc2024.day08

import utils.Resources
import utils.collections.allUniqueCombinations


fun main() {

    val inputLines = Resources.getLines(2024, 8)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val inputPoints = posToChar(inputLines)
    val initialPais = pairsWithTheSameValue(inputPoints)

    fun findAntinodes(pairs: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): MutableSet<Pair<Int, Int>> {
        val result = mutableSetOf<Pair<Int, Int>>()
        pairs.forEach { (p1, p2) ->
            val dy = p2.first - p1.first
            val dx = p2.second - p1.second

            val opposite1 = Pair(p1.first - dy, p1.second - dx)
            val opposite2 = Pair(p2.first + dy, p2.second + dx)

            if (opposite1.first in 0 until inputLines.size && opposite1.second in 0 until inputLines[0].length) {
                result.add(opposite1)
            }
            if (opposite2.first in 0 until inputLines.size && opposite2.second in 0 until inputLines[0].length) {
                result.add(opposite2)
            }
        }
        return result
    }

    val antinodes = findAntinodes(initialPais)
    return antinodes.size
}

fun part2(inputLines: List<String>): Int {

    val inputPoints = posToChar(inputLines)
    val initialPais = pairsWithTheSameValue(inputPoints)

    fun findAllAntinodes(pairs: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): MutableSet<Pair<Int, Int>> {
        val result = mutableSetOf<Pair<Int, Int>>()
        for ((p1, p2) in pairs) {
            val dy = p2.first - p1.first
            val dx = p2.second - p1.second

            var newP1 = p1.first - dy to p1.second - dx
            while (newP1.first in 0 until inputLines.size && newP1.second in 0 until inputLines[0].length) {
                result.add(newP1)
                newP1 = newP1.first - dy to newP1.second - dx
            }
            var newP2 = p2.first + dy to p2.second + dx
            while (newP2.first in 0 until inputLines.size && newP2.second in 0 until inputLines[0].length) {
                result.add(newP2)
                newP2 = newP2.first + dy to newP2.second + dx
            }
        }
        return result
    }

    val allAntinodes = findAllAntinodes(initialPais)
    // add initial
    allAntinodes.addAll(inputPoints.keys)
    return allAntinodes.size
}

fun posToChar(input: List<String>): Map<Pair<Int, Int>, Char> {
    val result = mutableMapOf<Pair<Int, Int>, Char>()
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            if (char != '.') {
                result[y to x] = char
            }
        }
    }
    return result
}

fun pairsWithTheSameValue(map: Map<Pair<Int, Int>, Char>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
    val groupedByValue = map.entries.groupBy({ it.value }, { it.key })
    val result = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    groupedByValue.values.forEach { positions ->
        result.addAll(allUniqueCombinations(positions))
    }
    return result
}
