package solutions.aoc2025.day08

import utils.Resources
import utils.algorithms.UnionFind
import utils.collections.extensions.allPairs
import utils.collections.extensions.kNearestPairs
import utils.collections.extensions.productOfTopK
import utils.parser.getLongs
import utils.point.Point3D

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {
    val points: List<Point3D> = inputLines.map { l -> l.getLongs() }.map { l -> Point3D(l[0], l[1], l[2]) }

    val uf = UnionFind<Point3D>()

    points.forEach { uf.add(it) }
    val listOfPairs = points.kNearestPairs(1000) { a, b -> a.distanceTo(b) }
    listOfPairs.forEach { (a, b) -> uf.union(a, b) }
    return uf.getGroups().values.productOfTopK(3) { it.size.toLong() }
}

fun part2(inputLines: List<String>): Long {
    val points: List<Point3D> = inputLines.map { l -> l.getLongs() }.map { l -> Point3D(l[0], l[1], l[2]) }

    val uf = UnionFind<Point3D>()

    points.forEach { p -> uf.add(p) }

    val allPairsWithDist = points.allPairs()
        .map { (a, b) -> Triple(a, b, a.distanceTo(b)) }
        .toList()

    var closestPair: Pair<Point3D, Point3D>? = null

    while (true) {
        var newPair: Pair<Point3D, Point3D>? = null
        var minDist = Double.MAX_VALUE
        for ((a, b, dist) in allPairsWithDist) {
            if (uf.find(a) == uf.find(b)) {
                continue
            }
            if (minDist > dist) {
                minDist = dist
                newPair = a to b
            }
        }
        if (newPair == null) {
            return closestPair!!.first.x * closestPair.second.x
        }
        uf.union(newPair.first, newPair.second)
        closestPair = newPair
    }
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
