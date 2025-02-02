package solutions.aoc2016.day20

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {
    val ranges = mutableListOf<Pair<Long, Long>>()
    val blockedAddresses = inputLines.map { it.split("-") }.map { it[0].toLong() to it[1].toLong() }.sortedBy { x -> x.first }
    for (currentBlocked in blockedAddresses) {
        val (currentLeft, currentRight) = currentBlocked

        // there is one that
        val f1 = ranges.find { x -> x.first >= currentLeft && x.second <= currentRight }
        if (f1 != null) {
            ranges.remove(f1)
            ranges.add(currentBlocked)
            continue
        }
        // can extend on the left side
        val f2 = ranges.find { x -> x.first - 1 in currentLeft..currentRight }
        if (f2 != null) {
            val newRange = currentLeft to f2.second
            ranges.remove(f2)
            ranges.add(newRange)
            continue
        }
        // can extend on the right side
        val f3 = ranges.find { x -> x.second + 1 in currentLeft..currentRight }
        if (f3 != null) {
            val newRange = f3.first to currentRight
            ranges.remove(f3)
            ranges.add(newRange)
            continue
        }
        // cannot merge - just add
        ranges.add(currentBlocked)
    }
    return ranges.minByOrNull { it.first }!!.second + 1

}

fun part2(inputLines: List<String>): Int {

    val ranges = mutableListOf<Pair<Long, Long>>()
    val blockedAddresses = inputLines.map { it.split("-") }.map { it[0].toLong() to it[1].toLong() }.sortedBy { x -> x.first }

    for (currentBlocked in blockedAddresses) {
        val (currentLeft, currentRight) = currentBlocked
        // any other in the current range
        val f1 = ranges.find { existing -> existing.first >= currentLeft && existing.second <= currentRight }
        if (f1 != null) {
            ranges.remove(f1)
            ranges.add(currentBlocked)
            continue
        }
        // can extend on the left side
        val f2 =  ranges.find { x -> x.first in currentLeft..currentRight }
        if (f2 != null) {
            val newRange = currentLeft to f2.second
            ranges.remove(f2)
            ranges.add(newRange)
            continue
        }
        // can extend on the right side
        val f3 = ranges.find { x -> x.second in currentLeft..currentRight }
        if (f3 != null) {
            val newRange = f3.first to currentRight
            ranges.remove(f3)
            ranges.add(newRange)
            continue
        }
        // cannot merge - just add
        ranges.add(currentBlocked)
    }

    var i = 0L
    var ctr = 0
    while (i <= 4294967295) {
        val found = ranges.find { x -> x.first <= i && x.second >= i }
        if (found != null) {
            // it's blocked
            i = found.second + 1
        } else {
            // not blocked
            ctr++
            i++
        }
    }
    return ctr
}
