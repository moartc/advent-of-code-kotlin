package solutions.aoc2025.day09

import utils.Resources
import utils.algorithms.isPointInPolygon
import utils.collections.extensions.allPairs
import utils.parser.getInts
import utils.parser.getLongs
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines =
//        Resources.getLinesExample(2025, day)
        Resources.getLines(2025, day)

//    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part2(inputLines: List<String>): Long {

    val points = inputLines.map { i -> i.getInts() }.map { l -> l[0] to l[1] }
    points.log("p")

    val minx = points.minOf { p -> p.first }
    val miny = points.minOf { p -> p.second }
    val maxx = points.maxOf { p -> p.first }
    val maxy = points.maxOf { p -> p.second }

    minx.log("minx")
    maxx.log("maxx")
    miny.log("miny")
    maxy.log("maxy")

    val pairs = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until points.size) {
        for (j in i + 1 until points.size) {
            pairs.add(i to j)
        }
    }
    var best = 0
    val cache = mutableMapOf<Pair<Int, Int>, Boolean>()

    val allE = allEdgesPoints(points)

    fun cached(p: Pair<Int, Int>): Boolean {

        return cache.getOrPut(p) { p in allE || isPointInPolygon(p, points) }
    }
    for (i in 0 until points.size) {
        for (j in i + 1 until points.size) {
            println("$i and $j")
            val (x1, y1) = points[i]
            val (x2, y2) = points[j]

            if (x1 == x2 || y1 == y2) {
                continue
            }

            val minX = min(x1, x2)
            val maxX = max(x1, x2)
            val minY = min(y1, y2)
            val maxY = max(y1, y2)

            val minxp1 = minX + 1
            val maxxm1 = maxX - 1
            val minyp1 = minY + 1
            val maxym1 = maxY - 1

            val hasPointInside = points.any { p ->
                p.first in minxp1..maxxm1 && p.second in minyp1..maxym1
            }

            if (!hasPointInside) {
                var valid = true

                // check if it's not somewhere outside
                if (valid) {
                    for (y in minY..maxY) {
                        if (!cached(minX to y) || !cached(maxX to y)) {
                            valid = false
                            break
                        }
                    }
                }

                if (valid) {
                    for (x in minX..maxX) {
                        if (!cached(x to minY) || !cached(x to maxY)) {
                            valid = false
                            break
                        }
                    }
                }

                if (valid) {
                    val area = (maxX - minX + 1) * (maxY - minY + 1)
                    best = max(best, area)
                }
            }
        }
    }

    best.log("max area")
    "p1".log()

    return 12
}

fun allEdgesPoints(corners: List<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    val area = mutableSetOf<Pair<Int, Int>>()
    val n = corners.size

    for (i in 0..n - 1) {
        val start = corners[i]
        val end = corners[(i + 1) % n]

        if (start.first == end.first) {
            val x = start.first
            val minY = minOf(start.second, end.second)
            val maxY = maxOf(start.second, end.second)
            for (y in minY..maxY) {
                area.add(Pair(x, y))
            }
        } else if (start.second == end.second) {
            val y = start.second
            val minX = minOf(start.first, end.first)
            val maxX = maxOf(start.first, end.first)
            for (x in minX..maxX) {
                area.add(Pair(x, y))
            }
        }
    }

    return area
}

fun part1(inputLines: List<String>): Long {
    inputLines.log("inp")

    val points = inputLines.map { i -> i.getLongs() }.map { l -> l[0] to l[1] }
    points.log("p")


    points.allPairs().forEach { p ->

    }
    var best: Long = 0
    for (i in 0 until points.size) {
        for (j in i + 1 until points.size) {
            val (x1, y1) = points[i]
            val (x2, y2) = points[j]
            val (x3, y3) = x1 to y2
            val (x4, y4) = x1 to y1
            if (x1 != x2 && y1 != y2) {
                val a = abs(x1 - x2) + 1
                val b = abs(y1 - y2) + 1
                val area = a * b
                if (area > best)
                    best = area
                val area2 = max(x1, x2) - min(x1, x2) * max(y1, y2) - min(y1, y2)
                if (area2 > best)
                    best = area2
            }
        }
    }

    println(best)
    "p1".log()

    return 1
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }

