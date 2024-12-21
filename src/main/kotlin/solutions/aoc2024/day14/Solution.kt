package solutions.aoc2024.day14

import utils.Resources
import utils.collections.extensions.containsSublist
import utils.parser.getLongs
import utils.grid.printGridFromPoints

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

// 101 x 103
fun main() {

    val inputLines = Resources.getLines(2024, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val robots = inputLines.map {
        val longs = it.getLongs()
        Robot(longs[0], longs[1], longs[2], longs[3])
    }

    val gridWidth = 101
    val gridHeight = 103
    val moves = 100

    repeat(moves) {
        robots.forEach { robot ->
            robot.move(1, gridWidth, gridHeight)
        }
    }

    val midX = gridWidth / 2
    val midY = gridHeight / 2
    val counters = mutableListOf(0, 0, 0, 0)
    robots.forEach { (x, y) ->
        when {
            x < midX && y < midY -> counters[0]++
            x >= midX + 1 && y < midY -> counters[1]++
            x < midX && y >= midY + 1 -> counters[2]++
            x >= midX + 1 && y >= midY + 1 -> counters[3]++
        }
    }
    return counters.fold(1) { acc, i -> acc * i }

}

fun part2(inputLines: List<String>): Int {

    val gridWidth = 101
    val gridHeight = 103
    val maxMoves = 10000000

    val listOfRobots = inputLines.map {
        val longs = it.getLongs()
        Robot(longs[0], longs[1], longs[2], longs[3])
    }

    fun containsConsecutivePointsInRow(points: List<Pair<Int, Int>>, consecutiveCount: Int): Boolean {
        val groupedByY = points.groupBy { it.second }
        for (entry in groupedByY) {
            val listOfDiffs = entry.value.map { p -> p.first }.sorted().zipWithNext { a, b -> b - a }.toList()
            if (listOfDiffs.containsSublist(List(consecutiveCount - 1) { 1 })) {
                return true
            }
        }
        return false
    }

    repeat(maxMoves) { time ->
        listOfRobots.forEach { robot ->
            robot.move(1, gridWidth, gridHeight)
        }
        val currentPoints = listOfRobots.map { p -> p.x.toInt() to p.y.toInt() }
        // 10 as a 'final' value - the Christmas tree is already visible for 8.
        if (containsConsecutivePointsInRow(currentPoints, 10)) {
            printGridFromPoints(currentPoints)
            return time + 1
        }
    }
    // shouldn't happen
    return -1
}

/*
here is the Christmas tree xD
. X . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
. . . X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X . . .
. . . X . . . . . . . . . . . . . . . . . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . . . . . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . . . . . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . . . . . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . . X . . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . X X X . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . X X X X X . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . X X X X X X X . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . X X X X X X X X X . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . X X X X X . . . . . . . . . . . . X . . .
X . . X . . . . . . . . . . . X X X X X X X . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . X X X X X X X X X . . . . . . . . . . X . . .
. . . X . . . . . . . . . X X X X X X X X X X X . . . . . . . . . X . . .
. . . X . . . . . . . . X X X X X X X X X X X X X . . . . . . . . X . . .
. . . X . . . . . . . . . . X X X X X X X X X . . . . . . . . . . X . . .
. . . X . . . . . . . . . X X X X X X X X X X X . . . . . . . . . X . . .
. . . X . . . . . . . . X X X X X X X X X X X X X . . . . . . . . X . . .
. . . X . . . . . . . X X X X X X X X X X X X X X X . . . . . . . X . . .
X . . X . . . . . . X X X X X X X X X X X X X X X X X . . . . . . X . . .
. . . X . . . . . . . . X X X X X X X X X X X X X . . . . . . . . X . . .
. . . X . . . . . . . X X X X X X X X X X X X X X X . . . . . . . X . . .
. . . X . . . . . . X X X X X X X X X X X X X X X X X . . . . . . X . . .
. . . X . . . . . X X X X X X X X X X X X X X X X X X X . . . . . X . . .
. . . X . . . . X X X X X X X X X X X X X X X X X X X X X . . . . X . . .
. . . X . . . . . . . . . . . . . X X X . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . X X X . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . X X X . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . . . . . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . . . . . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . . . . . . . . . . . . . . . . . X . . .
. . . X . . . . . . . . . . . . . . . . . . . . . . . . . . . . . X . . .
. . . X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X . . .
. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
(cut from a larger output)
 */


data class Robot(var x: Long, var y: Long, private val vx: Long, private val vy: Long) {
    fun move(seconds: Int, gridWidth: Int, gridHeight: Int) {
        x = (x + vx * seconds + gridWidth) % gridWidth
        y = (y + vy * seconds + gridHeight) % gridHeight
    }
}
