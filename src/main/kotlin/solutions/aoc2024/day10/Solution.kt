package solutions.aoc2024.day10

import utils.Resources
import utils.algorithms.withoutDiagonal
import utils.collections.extensions.findAllPositions


fun main() {

    val inputLines = Resources.getLines(2024, 10)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Int {

    val findPositions = inputLines.findAllPositions('0')
    val grid = inputLines.map { it.toCharArray() }
    return findPositions.sumOf { pos -> path(grid, pos).map { it[it.lastIndex] }.toSet().size }
}

fun part2(inputLines: List<String>): Int {

    val findPositions = inputLines.findAllPositions('0')
    val grid = inputLines.map { it.toCharArray() }
    return findPositions.sumOf { pos -> path(grid, pos).size }
}

private fun path(
    grid: List<CharArray>,
    start: Pair<Int, Int>,
    visited: MutableSet<Pair<Int, Int>> = mutableSetOf(),
    path: MutableList<Pair<Int, Int>> = mutableListOf()
): List<List<Pair<Int, Int>>> {
    val result = mutableListOf<List<Pair<Int, Int>>>()
    val (y, x) = start
    val currentValue = grid[y][x] - '0'
    visited.add(start)
    path.add(start)
    if (currentValue == 9) {
        result.add(path)
        return result
    }
    for ((dy, dx) in withoutDiagonal) {
        val newY = y + dy
        val newX = x + dx
        val newPos = Pair(newY, newX)
        if (newY in grid.indices && newX in grid[newY].indices && newPos !in visited) {
            val nextValue = grid[newY][newX] - '0'
            if (nextValue == currentValue + 1) {
                result.addAll(path(grid, newPos, visited.toMutableSet(), path.toMutableList()))
            }
        }
    }
    return result
}