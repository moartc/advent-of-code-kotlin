package solutions.aoc2021.day13

import utils.Resources
import utils.collections.extensions.splitOnEmpty
import utils.grid.printGridFromPairsXY

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val (fp, sp) = inputLines.splitOnEmpty()

    val points = fp.map { l ->
        val (f, s) = l.split(",")
        f.toInt() to s.toInt()
    }.toMutableList()

    val firstFold = sp.first().split(" ").last()
    val (line, value) = firstFold.split("=")
    return fold(line.first(), value.toInt(), points).toSet().size
}

fun part2(inputLines: List<String>): Any {
    val (fp, sp) = inputLines.splitOnEmpty()

    val points = fp.map { l ->
        val (f, s) = l.split(",")
        f.toInt() to s.toInt()
    }.toMutableList()

    var pointsToChange = points.toMutableList()
    sp.forEach {
        val i = it.split(" ").last()
        val (line, value) = i.split("=")
        pointsToChange = fold(line.first(), value.toInt(), pointsToChange).toSet().toMutableList()
    }
    printGridFromPairsXY(pointsToChange)
    /*
    X X X X . . X X . . X . . X . X . . X . X X X . . X X X X . . X X . . X X X .
    X . . . . X . . X . X . . X . X . X . . X . . X . X . . . . X . . X . X . . X
    X X X . . X . . X . X X X X . X X . . . X . . X . X X X . . X . . . . X . . X
    X . . . . X X X X . X . . X . X . X . . X X X . . X . . . . X . . . . X X X .
    X . . . . X . . X . X . . X . X . X . . X . X . . X . . . . X . . X . X . . .
    X X X X . X . . X . X . . X . X . . X . X . . X . X X X X . . X X . . X . . .
     */
    return "EAHKRECP"
}


fun fold(c: Char, v: Int, points: MutableList<Pair<Int, Int>>): MutableList<Pair<Int, Int>> {
    val newMap = mutableListOf<Pair<Int, Int>>()
    if (c == 'y') {
        for (point in points) {
            val (x, y) = point
            if (y < v) {
                newMap.add(point)
            } else if (y > v) {
                newMap.add(x to v - (y - v))
            }
        }
    } else { // x
        for (point in points) {
            val (x, y) = point
            if (x < v) {
                newMap.add(point)
            } else if (x > v) {
                newMap.add(v - (x - v) to y)
            }
        }
    }
    return newMap
}