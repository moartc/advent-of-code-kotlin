package solutions.aoc2017.day22

import utils.Resources
import utils.grid.Direction
import utils.grid.Point

fun main() {
    val input = Resources.getLines(2017, 22)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {

    val infected = mutableSetOf<Point>()
    for ((y, s) in input.withIndex()) {
        for ((x, c) in s.withIndex()) {
            if (c == '#') {
                infected.add(Point(y, x))
            }
        }
    }

    var currentPosition = Point(input.size / 2, input[0].length / 2)
    var currDir = Direction.UP
    var infCount = 0
    repeat(10000) {
        val currentNode = infected.find { x -> x == currentPosition }
        currDir = if (currentNode != null) {
            currDir.turnClockwise()
        } else {
            currDir.turnCounterClockwise()
        }

        if (currentNode == null) {
            infected.add(currentPosition)
            infCount++
        } else {
            infected.remove(currentNode)
        }
        currentPosition = Point(currentPosition.y + currDir.y, currentPosition.x + currDir.x)
    }
    return infCount
}


fun part2(input: List<String>): Int {
    // 4 - infected
    // 3 - weak
    // 2 - flagged
    // not present - clean
    val infected = mutableMapOf<Point, Int>()
    for ((y, s) in input.withIndex()) {
        for ((x, c) in s.withIndex()) {
            if (c == '#') {
                infected.put(Point(y, x), 4)
            }
        }
    }

    var currentPosition = Point(input.size / 2, input[0].length / 2)
    var currDir = Direction.UP
    var infCount = 0
    repeat(10000000) {
        val currentNode = infected.get(currentPosition)

        when (currentNode) {
            null -> {
                currDir = currDir.turnCounterClockwise()
            }

            4 -> {
                currDir = currDir.turnClockwise()
            }

            2 -> {
                currDir = currDir.turnClockwise().turnClockwise()
            }
        }

        when (currentNode) {
            null -> {
                infected.put(currentPosition, 3)
            }

            3 -> {
                infCount++
                infected.put(currentPosition, 4)
            }

            4 -> {
                infected.put(currentPosition, 2)
            }

            2 -> {
                infected.remove(currentPosition)
            }
        }
        currentPosition = Point(currentPosition.y + currDir.y, currentPosition.x + currDir.x)
    }
    return infCount
}
