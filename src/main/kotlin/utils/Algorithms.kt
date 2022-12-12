package utils

import java.util.*

val withDiagonal = arrayOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1)
val withoutDiagonal = arrayOf(0 to -1, -1 to 0, 1 to 0, 0 to 1)

fun <T> bfs(
    grid: List<List<T>>,
    possibleMoves: Array<Pair<Int, Int>>,
    start: Pair<Int, Int>,
    end: Pair<Int, Int>,
    canVisit: (currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>) -> Boolean
): Int {

    val sizeY = grid.size - 1
    val sizeX = grid[0].size - 1

    fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
        return !(nextPos.first < 0 || nextPos.first > sizeY || nextPos.second < 0 || nextPos.second > sizeX)
    }

    val queue = LinkedList<Pair<Int, Int>>()
    queue.add(start)
    val bestDistance = Array(grid.size) { IntArray(grid[0].size) { Int.MAX_VALUE } }
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
