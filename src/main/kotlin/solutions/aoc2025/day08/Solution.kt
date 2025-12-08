package solutions.aoc2025.day08

import utils.Resources
import utils.grid.allPointsBetween
import utils.parser.getLongs
import utils.point.Point3D
import kotlin.math.sqrt

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines) == 97384L}")
    println("part2 = ${part2(inputLines) == 9003685096}")
}

fun part1(inputLines: List<String>): Long {
    val points = inputLines.map { l -> l.getLongs() }.map { l -> Point3D(l[0], l[1], l[2]) }
    var map = points.associateWith { _ -> -1 }.toMutableMap()
    val pairsDist = mutableMapOf<Pair<Point3D, Point3D>, Double>()

    for ((i1, e1) in map.entries.withIndex()) {
        for ((i2, e2) in map.entries.withIndex()) {
            if (i2 <= i1) {
                continue
            }
            val d = e1.key.distanceTo(e2.key)
            pairsDist[e1.key to e2.key] = d
        }
    }
    val toConnect = pairsDist.entries.sortedBy { p -> p.value }.take(1000)

    for ((p, _) in toConnect) {
        map = connect(p.first, p.second, map)
    }

    val pp = map.values.groupingBy { it }
        .eachCount().entries.filter { e -> e.key != -1 }
        .map { q -> q.value.toLong() }
        .sorted()
        .reversed()
    return pp[0] * pp[1] * pp[2]
}

fun part2(inputLines: List<String>): Long {
    val points = inputLines.map { l -> l.getLongs() }.map { l -> Point3D(l[0], l[1], l[2]) }
    var map = points.associateWith { _ -> -1 }.toMutableMap()

    var lastBox1: Point3D? = null
    var lastBox2: Point3D? = null
    var connections = 0

    while (true) {
        val connected = map.values.filter { it != -1 }.distinct()
        val unconnected = map.values.count { it == -1 }

        if (connected.size == 1 && unconnected == 0) {
            // all found
            break
        }

        val (newMap, boxes) = findClosestAndConnect(map)
        connections++
        lastBox1 = boxes.first
        lastBox2 = boxes.second
        map = newMap
    }
    return lastBox1!!.x * lastBox2!!.x
}

fun findClosestAndConnect(m: Map<Point3D, Int>): Pair<MutableMap<Point3D, Int>, Pair<Point3D, Point3D>> {
    var bestD = Double.MAX_VALUE
    var l1: Point3D? = null
    var l2: Point3D? = null
    val entries = m.entries
    for ((i1, e1) in entries.withIndex()) {
        for ((i2, e2) in entries.withIndex()) {
            if (i2 <= i1) {
                continue
            }
            if (e1.value == e2.value && e1.value != -1) {
                continue
            }
            val d = e1.key.distanceTo(e2.key)
            if (bestD > d) {
                bestD = d
                l1 = e1.key
                l2 = e2.key
            }
        }
    }
    return connect(l1!!, l2!!, m) to (l1 to l2)
}

fun connect(p1: Point3D, p2: Point3D, map: Map<Point3D, Int>): MutableMap<Point3D, Int> {
    p1 + p2
    val nm = map.toMutableMap()
    val g1 = nm[p1]!!
    val g2 = nm[p2]!!
    if (g1 != -1 && g2 != -1) {
        val keysToUpdate = nm.entries.filter { it.value == g2 }.map { it.key }
        keysToUpdate.forEach { nm[it] = g1 }
    } else if (g1 == -1 && g2 == -1) {
        val maxGroup = nm.values.filter { it != -1 }.maxOrNull() ?: 0
        nm[p1] = maxGroup + 1
        nm[p2] = maxGroup + 1
    } else if (g1 == -1) {
        nm[p1] = g2
    } else {
        nm[p2] = g1
    }
    return nm
}
