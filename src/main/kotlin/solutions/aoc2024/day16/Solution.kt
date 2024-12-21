package solutions.aoc2024.day16

import utils.grid.Direction
import utils.Resources
import utils.algorithms.dijkstraGenWithPathHistory
import utils.collections.extensions.findPosition
import java.util.*
import kotlin.math.abs

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Int {

    val grid = inputLines.map { it.toCharArray() }

    fun rotationCost(direction: Direction, newDir: Direction): Int {
        val diff = abs(direction.ordinal - newDir.ordinal)
        return when (diff) {
            1, 3 -> 1000
            2 -> 2000
            else -> 0
        }
    }

    fun findLowestScore(grid: List<CharArray>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {

        val scores = Array(grid.size) { IntArray(grid[0].size) { Int.MAX_VALUE } }
        val queue: Queue<Triple<Pair<Int, Int>, Direction, Int>> = LinkedList() // Holds (position, direction, score)

        queue.offer(Triple(start, Direction.EAST, 0))
        scores[start.first][start.second] = 0

        var answer = Int.MAX_VALUE

        while (queue.isNotEmpty()) {
            val (current, direction, score) = queue.poll()

            if (current == end) {
                if (score < answer) {
                    answer = score
                }
                continue
            }

            for (newDir in Direction.entries) {
                val cost = rotationCost(direction, newDir)
                val next = Pair(current.first + newDir.y, current.second + newDir.x)

                if (next.first in grid.indices && next.second in grid[0].indices && grid[next.first][next.second] != '#') {
                    val newScore = score + 1 + cost
                    if (newScore < scores[next.first][next.second]) {
                        scores[next.first][next.second] = newScore
                        queue.offer(Triple(next, newDir, newScore))
                    }
                }
            }
        }
        return answer
    }
    val (y, x) = inputLines.findPosition('S')
    val (yE, xE) = inputLines.findPosition('E')
    return findLowestScore(grid, y to x, yE to xE)
}

fun part2(inputLines: List<String>): Int {

    val grid = inputLines.map {
        it.toCharArray()
    }

    fun rotationCost(direction: Direction, newDir: Direction): Int {
        val diff = abs(direction.ordinal - newDir.ordinal)
        return when (diff) {
            1, 3 -> 1000
            2 -> 2000
            else -> 0
        }
    }

    data class Point(val pos: Pair<Int, Int>, val dir: Direction)

    fun getNext(point: Point): List<Point> {
        val nextPoints = mutableListOf<Point>()
        for (newDir in Direction.entries) {
            val newPos = Pair(point.pos.first + newDir.y, point.pos.second + newDir.x)

            if (newPos.first in grid.indices &&
                newPos.second in grid[0].indices &&
                grid[newPos.first][newPos.second] != '#'
            ) {
                nextPoints.add(Point(newPos, newDir))
            }
        }
        return nextPoints
    }

    val (y, x) = inputLines.findPosition('S')
    val start = Point(y to x, Direction.EAST)
    val dijkstraGen = dijkstraGenWithPathHistory(start, { f, s -> rotationCost(f.dir, s.dir) + 1 }, ::getNext)

    fun collectVisitedPoints(current: Point, second: MutableMap<Point, MutableList<Point>>, visited: MutableSet<Point>): MutableSet<Point> {
        visited.add(current)
        second[current]?.forEach { prev ->
            if (prev !in visited) {
                collectVisitedPoints(prev, second, visited)
            }
        }
        return visited
    }

    val (yE, xE) = inputLines.findPosition('E')
    val resultForE = dijkstraGen.first.filter { p -> p.key.pos.first == yE && p.key.pos.second == xE }.minBy { it.value }.key
    val mapOfPreviousPoints = dijkstraGen.second
    val visitedPoints = collectVisitedPoints(resultForE, mapOfPreviousPoints, mutableSetOf())

    return visitedPoints.map { i -> i.pos }.toSet().size
}
