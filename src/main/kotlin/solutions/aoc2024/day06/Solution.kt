package solutions.aoc2024.day06

import utils.grid.Direction
import utils.Resources
import utils.collections.extensions.findPosition


fun main() {

    val inputLines = Resources.getLines(2024, 6)

    println("part1 = ${part1(inputLines)} ")
    println("part2 = ${part2(inputLines)} ")
}

fun part1(inputLines: List<String>): Int {

    val map = inputLines.map { it.replace('^', '.') }
    var (y, x) = inputLines.findPosition('^')

    var dir = Direction.NORTH
    var steps = 0
    val visitedPositions = mutableSetOf<Pair<Int, Int>>()

    while (true) {
        visitedPositions.add(Pair(y, x))
        val newY = y + dir.y
        val newX = x + dir.x

        if (newY !in map.indices || newX !in map[0].indices) {
            break
        }
        if (map[newY][newX] == '.') {
            y = newY
            x = newX
            steps++
        } else if (map[newY][newX] == '#') {
            dir = dir.turnClockwise()
        } else {
            break
        }
    }
    return visitedPositions.size
}

fun part2(inputLines: List<String>): Int {

    val map = inputLines.map { it.replace('^', '.') }

    fun isEndlessLoop(map: List<CharArray>, startY: Int, startX: Int): Boolean {
        var y = startY
        var x = startX
        var direction = Direction.NORTH
        val visitedPositions = mutableSetOf<Triple<Int, Int, Direction>>()

        while (true) {
            // with direction to not detect the same point after turning right
            if (!visitedPositions.add(Triple(y, x, direction))) {
                return true
            }
            val nextX = y + direction.y
            val nextY = x + direction.x

            if (nextX !in map.indices || nextY !in map[0].indices) {
                break
            }
            if (map[nextX][nextY] == '.') {
                y = nextX
                x = nextY
            } else if (map[nextX][nextY] == '#') {
                direction = direction.turnClockwise()
            } else {
                break
            }
        }
        return false
    }
    val (y, x) = inputLines.findPosition('^')
    var ctr = 0
    val mapToEdit = map.map { it.toCharArray() }
    for (i in map.indices) {
        c@ for (j in map[i].indices) {
            if (map[i][j] == '.') {
                mapToEdit[i][j] = '#'
                val isEndless = isEndlessLoop(mapToEdit, y, x)
                // replace back
                mapToEdit[i][j] = '.'
                if (isEndless) {
                    ctr++
                    continue@c
                }
            }
        }
    }
    return ctr

}
