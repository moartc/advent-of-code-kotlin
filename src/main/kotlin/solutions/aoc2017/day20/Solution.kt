package solutions.aoc2017.day20

import utils.Resources
import utils.parser.getLongs
import kotlin.math.abs

fun main() {
    val input = Resources.getLines(2017, 20)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {

    val parts = input.map {
        it.getLongs().toMutableList()
    }.toMutableList()

    fun upd(sp: MutableList<Long>) {
        sp[3] += sp[6]
        sp[4] += sp[7]
        sp[5] += sp[8]
        sp[0] += sp[3]
        sp[1] += sp[4]
        sp[2] += sp[5]
    }

    fun dist(a: List<Long>): Long {
        return abs(a[0]) + abs(a[1]) + abs(a[2])
    }

    fun findTheClosest(ll: List<List<Long>>): Int = ll.withIndex().minBy { (_, x) -> dist(x) }.index

    var ctr = 0
    var foundIdx = -1
    while (ctr < 1000) {
        parts.forEach { upd(it) }
        foundIdx = findTheClosest(parts)
//        println(foundIdx)
        ctr++
    }
    return foundIdx
}

fun part2(input: List<String>): Int {
    val parts = input.map {
        it.getLongs().toMutableList()
    }.toMutableList()

    fun upd(sp: MutableList<Long>) {
        sp[3] += sp[6]
        sp[4] += sp[7]
        sp[5] += sp[8]
        sp[0] += sp[3]
        sp[1] += sp[4]
        sp[2] += sp[5]
    }

    fun isCollision(a: List<Long>, b: List<Long>): Boolean {
        return a[0] == b[0] && a[1] == b[1] && a[2] == b[2]
    }

    var ctr = 0
    while (ctr < 100) {
        parts.forEach { upd(it) }
        val toRemove = mutableSetOf<Int>()
        for (i in 0..parts.lastIndex) {
            val il = parts[i]
            for (j in i + 1..parts.lastIndex) {
                val jl = parts[j]
                if (isCollision(il, jl)) {
                    toRemove.add(i)
                    toRemove.add(j)
                }
            }
        }
        if (toRemove.size > 0) {
            for ((idxDec, i) in toRemove.sorted().withIndex()) {
                parts.removeAt(i - idxDec)
            }
        }
        ctr++
    }
    return parts.size
}
