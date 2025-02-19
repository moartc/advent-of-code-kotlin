package solutions.aoc2021.day09

import utils.Resources
import utils.algorithms.withoutDiagonal
import java.util.*

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    fun getNeighbour(y: Int, x: Int): Int {

        if (y < 0 || y > inputLines.lastIndex) {
            return 10
        } else if (x < 0 || x > inputLines[0].lastIndex) {
            return 10
        } else {
            return inputLines[y][x].digitToInt()
        }

    }

    var ctr = 0
    for ((y, s) in inputLines.withIndex()) {
        for ((x, c) in s.withIndex()) {

            val value = c.digitToInt()

            val u = getNeighbour(y - 1, x)
            val d = getNeighbour(y + 1, x)
            val l = getNeighbour(y, x - 1)
            val r = getNeighbour(y, x + 1)
            if (value < u && value < d && value < l && value < r) {
                ctr += (value + 1)
            }
        }
    }
    return ctr
}

fun part2(inputLines: List<String>): Any {

    fun calculate(
        grid: List<List<Int>>,
        possibleMoves: Array<Pair<Int, Int>>,
        start: Pair<Int, Int>,
    ): Int {

        if (grid[start.first][start.second] == 9) {
            return -1
        }

        fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
            return !(nextPos.first < 0 || nextPos.first > grid.lastIndex || nextPos.second < 0 || nextPos.second > grid[0].lastIndex)
        }

        fun canVisit(remove: Pair<Int, Int>, nextPos: Pair<Int, Int>): Boolean {
            if (grid[nextPos.first][nextPos.second] == 9) {
                return false
            }
            return grid[remove.first][remove.second] < grid[nextPos.first][nextPos.second]
        }

        val queue = LinkedList<Pair<Int, Int>>()
        queue.add(start)
        val visited = mutableListOf<Pair<Int, Int>>()
        visited.add(start)
        var current = 1
        while (!queue.isEmpty()) {
            val remove = queue.remove()
            for (move in possibleMoves) {
                val nextY = remove.first + move.first
                val nextX = remove.second + move.second
                val nextPos = nextY to nextX
                if (isPosValid(nextPos) && canVisit(remove, nextPos) && !visited.contains(nextPos)) {
                    visited.add(nextY to nextX)
                    current++
                    queue.add(nextY to nextX)
                }
            }
        }
        return current
    }

    val allRes = mutableListOf<Int>()
    val grid = inputLines.map { it.toCharArray().map { it.digitToInt() } }

    for ((y, s) in inputLines.withIndex()) {
        for ((x, _) in s.withIndex()) {
            allRes += calculate(grid, withoutDiagonal, y to x)

        }
    }
    val (f, s, t) = allRes.sortedDescending().take(3)
    return f * s * t
}


