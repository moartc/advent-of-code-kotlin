package solutions.aoc2025.day09

import utils.Resources
import utils.algorithms.getEdgePoints
import utils.algorithms.isPointInPolygon
import utils.parser.getLongs
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val points = inputLines.map { i -> i.getLongs() }.map { l -> l[0] to l[1] }

    var best: Long = 0

    for (i in 0 until points.size) {
        for (j in i + 1 until points.size) {
            val (x1, y1) = points[i]
            val (x2, y2) = points[j]
            if (x1 != x2 && y1 != y2) {
                val a = abs(x1 - x2) + 1
                val b = abs(y1 - y2) + 1
                val area = a * b
                if (area > best) {
                    best = area
                }
            }
        }
    }
    return best
}

fun part2(inputLines: List<String>): Long {

    val points = inputLines.map { i -> i.getLongs() }.map { l -> l[0] to l[1] }

    val edgePoints = getEdgePoints(points)

    val cache = mutableMapOf<Pair<Long, Long>, Boolean>()

    fun cached(p: Pair<Long, Long>): Boolean {
        return cache.getOrPut(p) { p in edgePoints || isPointInPolygon(p, points) }
    }

    var best = 0L

    for (i in 0 until points.size) {
        for (j in i + 1 until points.size) {
            val (x1, y1) = points[i]
            val (x2, y2) = points[j]

            if (x1 == x2 || y1 == y2) {
                continue
            }

            val minX = min(x1, x2)
            val maxX = max(x1, x2)
            val minY = min(y1, y2)
            val maxY = max(y1, y2)

            val hasPointInside = points.any { p ->
                p.first in minX + 1..<maxX && p.second in minY + 1..<maxY
            }

            if (hasPointInside) {
                continue
            }

            var valid = true
            for (y in minY..maxY) {
                if (!cached(minX to y)) {
                    valid = false
                    break
                }
                if (!cached(maxX to y)) {
                    valid = false
                    break
                }
            }
            if (valid) {
                for (x in minX..maxX) {
                    if (!cached(x to minY)) {
                        valid = false
                        break
                    }
                    if (!cached(x to maxY)) {
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
    return best
}


