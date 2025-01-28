package solutions.aoc2016.day15

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val split = inputLines.map { it.split(" ") }
    val map = split.map { it[3].toInt() to it[11][0].digitToInt() }

    fun pos(p1: Pair<Int, Int>, time: Long): Long {
        val (positions, startPos) = p1
        val current = (startPos + time) % positions
        return current
    }

    var ctr = 0L
    out@ while (true) {
        var locCtr = ctr
        for (pair in map) {
            locCtr++
            val ans = pos(pair, locCtr)
            if (ans != 0L) {
                ctr++
                continue@out
            }
        }
        return ctr
    }
}

fun part2(inputLines: List<String>): Long {

    val split = inputLines.map { it.split(" ") }
    val map = split.map { it[3].toInt() to it[11][0].digitToInt() }.toMutableList()
    map.add(11 to 0)

    fun pos(p1: Pair<Int, Int>, time: Long): Long {
        val (positions, startPos) = p1
        val current = (startPos + time) % positions
        return current
    }

    var ctr = 0L
    out@ while (true) {
        var locCtr = ctr
        for (pair in map) {
            locCtr++
            val ans = pos(pair, locCtr)
            if (ans != 0L) {
                ctr++
                continue@out
            }
        }
        return ctr
    }
}

