package solutions.aoc2024.day18

import utils.Resources
import utils.algorithms.bfs
import utils.algorithms.withoutDiagonal
import utils.parser.getInts

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val coords = inputLines.map {
        val pair = it.getInts()
        pair[0] to pair[1]
    }
    val gridSize = 70
    val grid = List(gridSize + 1) { MutableList(gridSize + 1) { true } }

    val start = 0 to 0
    val end = gridSize to gridSize
    val cannotVisit = coords.subList(0, 1024).toSet()
    return bfs(grid, withoutDiagonal, start, end) { _, p2 -> !cannotVisit.contains(p2.first to p2.second) }

}

fun part2(inputLines: List<String>): String {

    val coords = inputLines.map {
        val pair = it.getInts()
        pair[0] to pair[1]
    }
    val gridSize = 70
    val grid = List(gridSize + 1) { MutableList(gridSize + 1) { true } }

    val start = 0 to 0
    val end = gridSize to gridSize


    (1024..coords.size).forEach { i ->
        val cannotVisit = coords.subList(0, i).toSet()
        val findShortestPath = bfs(grid, withoutDiagonal, start, end) { _, p2 -> !cannotVisit.contains(p2.first to p2.second) }

        if (findShortestPath == -1) {
            val last = cannotVisit.last()
            return "${last.first},${last.second}"
        }
    }

    return "not found"
}

