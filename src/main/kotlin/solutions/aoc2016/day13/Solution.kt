package solutions.aoc2016.day13

import utils.algorithms.withoutDiagonal
import java.util.*

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val input = 1362
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(favNum: Int): Int {

    fun res(y: Int, x: Int): Int {
        return ((x * x) + 3 * x + (2 * x * y) + y + (y * y)) + favNum
    }

    fun isOpen(y: Int, x: Int): Boolean {
        return res(y, x).toString(2).count { x -> x == '1' } % 2 == 0
    }

    fun bfsNoGrid(
        possibleMoves: Array<Pair<Int, Int>>,
        start: Pair<Int, Int>,
        end: Pair<Int, Int>,
        canVisit: (currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>) -> Boolean
    ): Int {
        fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
            return !(nextPos.first < 0 || nextPos.second < 0)
        }

        val queue = LinkedList<Pair<Int, Int>>()
        queue.add(start)
        val bestDistance = Array(100) { IntArray(100) { Int.MAX_VALUE } }
        bestDistance[start.first][start.second] = 0

        while (!queue.isEmpty()) {
            val remove = queue.remove()
            val currentDistance = bestDistance[remove.first][remove.second]
            for (move in possibleMoves) {
                val nextY = remove.first + move.first
                val nextX = remove.second + move.second
                val nextPos = nextY to nextX
                if (isPosValid(nextPos) && bestDistance[nextY][nextX] > currentDistance + 1 && canVisit(remove, nextPos)) {
                    if (nextY to nextX == end) {
                        return currentDistance + 1
                    } else {
                        queue.add(nextY to nextX)
                        bestDistance[nextY][nextX] = (currentDistance + 1)
                    }
                }
            }
        }
        return -1
    }

    val start = 1 to 1
    val end = 39 to 31
    return bfsNoGrid(withoutDiagonal, start, end, { _, p2 -> isOpen(p2.first, p2.second) })
}

fun part2(favNum: Int): Int {

    fun res(y: Int, x: Int): Int {
        return ((x * x) + 3 * x + (2 * x * y) + y + (y * y)) + favNum
    }

    fun isOpen(y: Int, x: Int): Boolean {
        return res(y, x).toString(2).count { x -> x == '1' } % 2 == 0
    }

    val visited = Array(500) { IntArray(50) { Int.MAX_VALUE } }

    fun dfs(y: Int, x: Int, steps: Int) {

        if (steps > 50) {
            return
        }
        if (visited[y][x] < steps) {
            return
        }
        visited[y][x] = steps

        fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
            return !(nextPos.first < 0 || nextPos.second < 0)
        }

        for (pair in withoutDiagonal) {
            val nextY = y + pair.first
            val nextX = x + pair.second
            val nextPoint = nextY to nextX
            if (isPosValid(nextPoint) && isOpen(nextY, nextX)) {
                dfs(nextY, nextX, steps + 1)
            }
        }
    }

    dfs(1, 1, 0)

    return visited.sumOf { row -> row.count { x -> x != Int.MAX_VALUE } }
}


