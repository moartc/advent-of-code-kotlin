package solutions.aoc2021.day05

import utils.Resources
import utils.grid.Point
import utils.grid.allPointsInLineBetween

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val ctr = mutableMapOf<Point, Int>()
    for (line in inputLines) {
        val (left, right) = line.split(" -> ")
        val (fy, fx) = left.split(",").map { it.toInt() }
        val (sy, sx) = right.split(",").map { it.toInt() }

        if (fy == sy || fx == sx) {
            val p1 = Point(fy, fx)
            val p2 = Point(sy, sx)
            allPointsInLineBetween(p1, p2).forEach {
                ctr.computeIfAbsent(it) { 0 }
                ctr[it] = ctr[it]!! + 1
            }
        }
    }
    return ctr.filter { x -> x.value >= 2 }.size
}

fun part2(inputLines: List<String>): Any {

    val ctr = mutableMapOf<Point, Int>()

    for (line in inputLines) {

        val (left, right) = line.split(" -> ")
        val (fy, fx) = left.split(",").map { it.toInt() }
        val (sy, sx) = right.split(",").map { it.toInt() }
        val p1 = Point(fy, fx)
        val p2 = Point(sy, sx)

        allPointsInLineBetween(p1, p2).forEach {
            ctr.computeIfAbsent(it) { 0 }
            ctr[it] = ctr[it]!! + 1
        }
    }
    return ctr.filter { x -> x.value >= 2 }.size
}

