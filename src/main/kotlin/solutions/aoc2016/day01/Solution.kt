package solutions.aoc2016.day01

import utils.Resources
import utils.grid.Direction
import utils.grid.Point
import utils.grid.allPointsBetween
import kotlin.math.absoluteValue

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    var pos = Point(0, 0)
    var dir = Direction.UP
    inputLines[0].split(", ").forEach {
        val d = it.take(1)
        val dist = it.drop(1).toInt()

        dir = if (d == "L") {
            dir.turnCounterClockwise()
        } else {
            dir.turnClockwise()
        }
        pos = Point(pos.y + (dir.y * dist), pos.x + (dir.x * dist))
    }

    return pos.y.absoluteValue + pos.x.absoluteValue
}

fun part2(inputLines: List<String>): Int {
    var pos = Point(0, 0)
    var dir = Direction.UP
    val visited = mutableSetOf<Point>()
    inputLines[0].split(", ").forEach {
        val d = it.take(1)
        val dist = it.drop(1).toInt()

        dir = if (d == "L") {
            dir.turnCounterClockwise()
        } else {
            dir.turnClockwise()
        }
        val beforeMove = pos
        pos = Point(pos.y + (dir.y * dist), pos.x + (dir.x * dist))
        allPointsBetween(beforeMove, pos).drop(1).forEach { p ->
            if (!visited.add(p)) {
                return p.y.absoluteValue + p.x.absoluteValue
            }
        }
    }
    return -1
}

