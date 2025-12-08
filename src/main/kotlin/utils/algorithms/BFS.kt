package utils.algorithms

import java.util.*

val withDiagonal = arrayOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1)
val withoutDiagonal = arrayOf(0 to -1, -1 to 0, 1 to 0, 0 to 1)

// Add these common movement patterns
val DIRECTIONS_4 = arrayOf(0 to -1, -1 to 0, 1 to 0, 0 to 1)  // alias
val DIRECTIONS_8 = arrayOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1)  // alias

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
        return nextPos.first in 0..sizeY && nextPos.second in 0..sizeX
    }

    val queue = LinkedList<Pair<Int, Int>>()
    queue.add(start)
    val bestDistance = Array(grid.size) { IntArray(grid[0].size) { Int.MAX_VALUE } }
    bestDistance[start.first][start.second] = 0

    while (queue.isNotEmpty()) {
        val current = queue.remove()
        val currentDistance = bestDistance[current.first][current.second]

        for (move in possibleMoves) {
            val nextPos = current.first + move.first to current.second + move.second

            if (isPosValid(nextPos) &&
                bestDistance[nextPos.first][nextPos.second] > currentDistance + 1 &&
                canVisit(current, nextPos)) {

                if (nextPos == end) {
                    return currentDistance + 1
                }

                queue.add(nextPos)
                bestDistance[nextPos.first][nextPos.second] = currentDistance + 1
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
        return nextPos.first in 0..sizeY && nextPos.second in 0..sizeX
    }

    fun collectShortestPath(
        last: Pair<Int, Int>,
        prevNodeForShortestPath: Map<Pair<Int, Int>, Pair<Int, Int>>
    ): List<Pair<Int, Int>> {
        val path = mutableListOf(last)
        var current = prevNodeForShortestPath[last]
        while (current != null) {
            path.add(current)
            current = prevNodeForShortestPath[current]
        }
        return path.reversed()
    }

    val queue = LinkedList<Pair<Int, Int>>()
    queue.add(start)

    val bestDistance = Array(grid.size) { IntArray(grid[0].size) { Int.MAX_VALUE } }
    bestDistance[start.first][start.second] = 0

    val prevNodeForShortestPath = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

    while (queue.isNotEmpty()) {
        val current = queue.remove()
        val currentDistance = bestDistance[current.first][current.second]

        for (move in possibleMoves) {
            val nextPos = current.first + move.first to current.second + move.second

            if (isPosValid(nextPos) &&
                bestDistance[nextPos.first][nextPos.second] > currentDistance + 1 &&
                canVisit(current, nextPos)) {

                prevNodeForShortestPath[nextPos] = current

                if (nextPos == end) {
                    return (currentDistance + 1) to collectShortestPath(nextPos, prevNodeForShortestPath)
                }

                queue.add(nextPos)
                bestDistance[nextPos.first][nextPos.second] = currentDistance + 1
            }
        }
    }
    return -1 to emptyList()
}

// Add a generic BFS that finds all reachable cells with distances
fun <T> bfsAllReachable(
    grid: List<List<T>>,
    possibleMoves: Array<Pair<Int, Int>>,
    start: Pair<Int, Int>,
    canVisit: (currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>) -> Boolean
): Map<Pair<Int, Int>, Int> {

    val sizeY = grid.size - 1
    val sizeX = grid[0].size - 1

    fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
        return nextPos.first in 0..sizeY && nextPos.second in 0..sizeX
    }

    val queue = LinkedList<Pair<Int, Int>>()
    queue.add(start)
    val distances = mutableMapOf(start to 0)

    while (queue.isNotEmpty()) {
        val current = queue.remove()
        val currentDistance = distances[current]!!

        for (move in possibleMoves) {
            val nextPos = current.first + move.first to current.second + move.second

            if (isPosValid(nextPos) &&
                nextPos !in distances &&
                canVisit(current, nextPos)) {

                distances[nextPos] = currentDistance + 1
                queue.add(nextPos)
            }
        }
    }

    return distances
}

// More flexible BFS that works with any node type
fun <T> bfsGeneric(
    start: T,
    isGoal: (T) -> Boolean,
    getNeighbors: (T) -> List<T>
): Pair<Int, List<T>>? {

    val queue = LinkedList<T>()
    queue.add(start)

    val distances = mutableMapOf(start to 0)
    val previous = mutableMapOf<T, T>()

    while (queue.isNotEmpty()) {
        val current = queue.remove()

        if (isGoal(current)) {
            val path = mutableListOf(current)
            var node = previous[current]
            while (node != null) {
                path.add(node)
                node = previous[node]
            }
            return distances[current]!! to path.reversed()
        }

        val currentDistance = distances[current]!!

        for (neighbor in getNeighbors(current)) {
            if (neighbor !in distances) {
                distances[neighbor] = currentDistance + 1
                previous[neighbor] = current
                queue.add(neighbor)
            }
        }
    }

    return null
}

/**
 * Assumes no dead ends
 * Created for day 10 2023
 */
fun <T> bfsPathFromPointToPoint(
    grid: List<List<T>>,
    possibleMoves: Array<Pair<Int, Int>>,
    start: Pair<Int, Int>,
    end: Pair<Int, Int>,
    canVisit: (currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>) -> Boolean
): Set<Pair<Int, Int>> {

    val sizeY = grid.size - 1
    val sizeX = grid[0].size - 1

    fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
        return nextPos.first in 0..sizeY && nextPos.second in 0..sizeX
    }

    fun getNext(current: Pair<Int, Int>) = possibleMoves
        .map { move -> current.first + move.first to current.second + move.second }
        .filter { n -> isPosValid(n) && canVisit(current, n) }

    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(start)

    while (true) {
        val current = visited.last()
        val possibleNextMoves = getNext(current)
        val nonEndMove = possibleNextMoves.firstOrNull { x -> x != end && x !in visited }

        when {
            nonEndMove != null -> visited.add(nonEndMove)
            end in possibleNextMoves -> return visited
            else -> error("Cannot find path from $start to $end")
        }
    }
}