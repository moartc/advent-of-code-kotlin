package solutions.aoc2025.day08

import utils.Resources
import utils.parser.getLongs
import kotlin.math.sqrt

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {
    val ints = inputLines.map { l -> l.getLongs() }
    var map = ints.associateWith { _ -> -1 }.toMutableMap()
    val pairsDist = mutableMapOf<Pair<List<Long>, List<Long>>, Double>()
    for ((i1, e1) in map.entries.withIndex()) {
        for ((i2, e2) in map.entries.withIndex()) {
            if (i2 <= i1) {
                continue
            }
            val d = dist(e1.key, e2.key)
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
    val ints = inputLines.map { l -> l.getLongs() }
    var map = ints.associateWith { _ -> -1 }

    var lastBox1: List<Long>? = null
    var lastBox2: List<Long>? = null
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
    return lastBox1!![0] * lastBox2!![0]
}

fun findClosestAndConnect(m: Map<List<Long>, Int>): Pair<MutableMap<List<Long>, Int>, Pair<List<Long>, List<Long>>> {
    var bestD = Double.MAX_VALUE
    var l1: List<Long> = emptyList()
    var l2: List<Long> = emptyList()
    val entries = m.entries
    for ((i1, e1) in entries.withIndex()) {
        for ((i2, e2) in entries.withIndex()) {
            if (i2 <= i1) {
                continue
            }
            if (e1.value == e2.value && e1.value != -1) {
                continue
            }
            val d = dist(e1.key, e2.key)
            if (bestD > d) {
                bestD = d
                l1 = e1.key
                l2 = e2.key
            }
        }
    }
    return connect(l1, l2, m) to (l1 to l2)
}

fun connect(l1: List<Long>, l2: List<Long>, map: Map<List<Long>, Int>): MutableMap<List<Long>, Int> {
    val nm = map.toMutableMap()
    val g1 = nm[l1]!!
    val g2 = nm[l2]!!
    if (g1 != -1 && g2 != -1) {
        val keysToUpdate = nm.entries.filter { it.value == g2 }.map { it.key }
        keysToUpdate.forEach { nm[it] = g1 }
    } else if (g1 == -1 && g2 == -1) {
        val maxGroup = nm.values.filter { it != -1 }.maxOrNull() ?: 0
        nm[l1] = maxGroup + 1
        nm[l2] = maxGroup + 1
    } else if (g1 == -1) {
        nm[l1] = g2
    } else {
        nm[l2] = g1
    }
    return nm
}

fun dist(a: List<Long>, b: List<Long>): Double {
    val dx = (a[0] - b[0]).toDouble()
    val dy = (a[1] - b[1]).toDouble()
    val dz = (a[2] - b[2]).toDouble()
    return sqrt(dx * dx + dy * dy + dz * dz)
}
