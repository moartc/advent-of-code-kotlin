package solutions.aoc2021.day15

import utils.Resources
import utils.algorithms.withoutDiagonal
import java.util.*

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines =
//        Resources.getLinesExample(2021, day)
        Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part2(inputLines: List<String>): Any {
    val map = inputLines.map { singleLine -> singleLine.map { l -> l.digitToInt() } }

    val n = map.size
    val m = map[0].size

    val expanded: List<List<Int>> = List(n * 5) { i ->
        List(m * 5) { j ->
            val value = map[i % n][j % m] + i / n + j / m
            (value - 1) % 9 + 1
        }
    }
    return bestPath(expanded).log()
}

fun part1(inputLines: List<String>): Int {


    val map = inputLines.map { singleLine -> singleLine.map { l -> l.digitToInt() } }
    return bestPath(map).log()
}


fun bestPath(grid: List<List<Int>>): Int {
    val maxY = grid.size
    val maxX = grid[0].size
    val minCost = Array(maxY) { IntArray(maxX) { Int.MAX_VALUE } }
    val pq = PriorityQueue<Triple<Int, Int, Int>>(compareBy { x -> x.third })

    minCost[0][0] = 0
    pq.add(Triple(0, 0, 0))

    while (pq.isNotEmpty()) {
        val triple = pq.poll()
        if (triple.first == maxY - 1 && triple.second == maxX - 1) {
            return triple.third
        }
        for (dir in withoutDiagonal) {
            val ny = triple.first + dir.first
            val nx = triple.second + dir.second
            if (ny in 0 until maxY && nx in 0 until maxX) {
                val newCost = triple.third + grid[ny][nx]
                if (newCost < minCost[ny][nx]) {
                    minCost[ny][nx] = newCost
                    pq.add(Triple(ny, nx, newCost))
                }
            }
        }
    }
    return -1
}

private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }