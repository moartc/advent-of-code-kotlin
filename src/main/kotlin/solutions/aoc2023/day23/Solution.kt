package solutions.aoc2023.day23

import utils.Resources
import utils.algorithms.withoutDiagonal

fun main() {

    val inputLine = Resources.getLines(2023, 23)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: List<String>): Int {

    val map = input.map { it.toList() }.toList()

    fun canVisit(currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>): Boolean {
        val nextChar = map[nextPos.first][nextPos.second]
        val currChar = map[currentPos.first][currentPos.second]
        val (cy, cx) = currentPos
        val (ny, nx) = nextPos
        return if (currChar == '>') {
            cy == ny && nx == cx + 1
        } else if (currChar == '<') {
            ny == cy && nx == cx - 1
        } else if (currChar == 'v') {
            ny == cy + 1 && nx == cx
        } else if (currChar == '^') {
            ny == cy - 1 && nx == cx
        } else if (nextChar in ".^v<>") {
            true
        } else {
            false
        }
    }

    var bestFound = 0

    fun findBest(
        grid: List<List<Char>>,
        possibleMoves: Array<Pair<Int, Int>>,
        current: Pair<Int, Int>,
        end: Pair<Int, Int>,
        canVisit: (currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>) -> Boolean,
        collected: MutableList<Pair<Int, Int>>,
    ) {
        val sizeY = grid.size - 1
        val sizeX = grid[0].size - 1

        fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
            return !(nextPos.first < 0 || nextPos.first > sizeY || nextPos.second < 0 || nextPos.second > sizeX)
        }

        collected.add(current)

        for (move in possibleMoves) {
            val nextY = current.first + move.first
            val nextX = current.second + move.second
            val nextPos = nextY to nextX
            if (isPosValid(nextPos) && canVisit(current, nextPos) && !collected.contains(nextPos)) {
                if (nextY to nextX == end) {
                    if (collected.size > bestFound) {
                        bestFound = collected.size
                    }
                } else {
                    findBest(map, withoutDiagonal, nextPos, end, ::canVisit, collected.toMutableList())
                }
            }
        }
    }

    val end = map.lastIndex to map[map.lastIndex].lastIndex - 1
    findBest(map, withoutDiagonal, 0 to 1, end, ::canVisit, mutableListOf())

    return bestFound
}


fun part2(input: List<String>): Int {

    val map = input.map { it.toList() }
    val sizeY = map.size - 1
    val sizeX = map[0].size - 1
    val start = 0 to 1
    val end = map.lastIndex to map[map.lastIndex].lastIndex - 1

    fun canVisit(nextPos: Pair<Int, Int>) = map[nextPos.first][nextPos.second] in ".^v<>"

    fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
        return !(nextPos.first < 0 || nextPos.first > sizeY || nextPos.second < 0 || nextPos.second > sizeX)
    }

    val crossroads = mutableSetOf<Pair<Int, Int>>()

    map.forEachIndexed { idxY, line ->
        line.forEachIndexed { idxX, char ->
            if (char != '#') {
                val validPositions = withoutDiagonal.map { move -> idxY + move.first to idxX + move.second }
                    .filter { nextPos -> isPosValid(nextPos) && canVisit(nextPos) }
                if (validPositions.size > 2) {
                    crossroads.add(idxY to idxX)
                }
            }
        }
    }
    // plus start and end
    crossroads.add(start)
    crossroads.add(end)


    fun findConnections(current: Pair<Int, Int>, visited: MutableList<Pair<Int, Int>>, found: MutableList<Pair<Pair<Int, Int>, Int>>) {
        if (crossroads.contains(current) && visited.isNotEmpty()) {
            found.add(current to visited.size)
            return
        } else {
            visited.add(current)
            for (move in withoutDiagonal) {
                val nextY = current.first + move.first
                val nextX = current.second + move.second
                val nextPos = nextY to nextX
                if (isPosValid(nextPos) && canVisit(nextPos) && !visited.contains(nextPos)) {
                    findConnections(nextPos, visited.toMutableList(), found)
                }
            }
        }
    }

    val crossroadsConnections = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Pair<Int, Int>, Int>>>()
    for (crossroad in crossroads) {
        val result = mutableListOf<Pair<Pair<Int, Int>, Int>>()
        findConnections(crossroad, mutableListOf(), result)
        if (!crossroadsConnections.contains(crossroad)) {
            crossroadsConnections[crossroad] = ArrayList()
        }
        crossroadsConnections[crossroad] = result
    }

    fun findLongestPath(current: Pair<Int, Int>, end: Pair<Int, Int>, visited: MutableSet<Pair<Int, Int>>, currentLength: Int): Int {
        if (current == end) {
            return currentLength
        }
        visited.add(current)
        return crossroadsConnections[current]!!.maxOf { (pos, cost) ->
            if (!visited.contains(pos)) {
                findLongestPath(pos, end, visited.toMutableSet(), currentLength + cost)
            } else {
                0
            }
        }
    }
    return findLongestPath(start, end, mutableSetOf(), 0)
}
