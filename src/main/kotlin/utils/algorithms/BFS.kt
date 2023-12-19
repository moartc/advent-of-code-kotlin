package utils.algorithms

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

fun <T> bfsWithPath(
    grid: List<List<T>>,
    possibleMoves: Array<Pair<Int, Int>>,
    start: Pair<Int, Int>,
    end: Pair<Int, Int>,
    canVisit: (currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>) -> Boolean
): Pair<Int, List<Pair<Int, Int>>> {

    val sizeY = grid.size - 1
    val sizeX = grid[0].size - 1

    fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
        return !(nextPos.first < 0 || nextPos.first > sizeY || nextPos.second < 0 || nextPos.second > sizeX)
    }

    fun collectShortestPath(last: Pair<Int, Int>, prevNodeForShortestPath: Map<Pair<Int, Int>, Pair<Int, Int>>): List<Pair<Int, Int>> {
        val toReturn = mutableListOf(last)
        var next = prevNodeForShortestPath[last]
        while (next != null) {
            toReturn.add(next)
            next = prevNodeForShortestPath[next]
        }
        return toReturn.reversed()
    }

    val queue = LinkedList<Pair<Int, Int>>()
    queue.add(start)

    val bestDistance = Array(grid.size) { IntArray(grid[0].size) { Int.MAX_VALUE } }
    bestDistance[start.first][start.second] = 0

    val prevNodeForShortestPath = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

    while (!queue.isEmpty()) {
        val remove = queue.remove()
        val currentDistance = bestDistance[remove.first][remove.second]
        for (move in possibleMoves) {
            val nextY = remove.first + move.first
            val nextX = remove.second + move.second
            val nextPos = nextY to nextX
            if (isPosValid(nextPos) && bestDistance[nextY][nextX] > currentDistance + 1 && canVisit(remove, nextPos)) {
                prevNodeForShortestPath[nextPos] = remove
                if (nextY to nextX == end) {
                    return currentDistance + 1 to collectShortestPath(nextPos, prevNodeForShortestPath)
                } else {
                    queue.add(nextY to nextX)
                    bestDistance[nextY][nextX] = (currentDistance + 1)
                }
            }
        }
    }
    return -1 to emptyList()
}

/**
 * assumes no dead ends
 * Created for day 10 2023
 */
fun <T> bfsPathFromPointToPoint(
    grid: List<List<T>>,
    possibleMoves: Array<Pair<Int, Int>>,
    start: Pair<Int, Int>,
    end: Pair<Int, Int>,
    canVisit: (currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>) -> Boolean
): MutableSet<Pair<Int, Int>> {

    val sizeY = grid.size - 1
    val sizeX = grid[0].size - 1

    fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
        return !(nextPos.first < 0 || nextPos.first > sizeY || nextPos.second < 0 || nextPos.second > sizeX)
    }

    fun getNext(current: Pair<Int, Int>) = possibleMoves
        .map { move -> current.first + move.first to current.second + move.second }
        .filter { n -> isPosValid(n) && canVisit(current, n) }

    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(start)

    while (true) {
        val current = visited.last()
        val possibleNextMoves = getNext(current)
        val nonEndMove = possibleNextMoves.firstOrNull { x -> x != end && !visited.contains(x) }
        if(nonEndMove != null) {
            visited.add(nonEndMove)
        } else if(possibleNextMoves.contains(end)){ // can only finish
            return visited
        } else {
            error("cannot find loop")
        }
    }
}