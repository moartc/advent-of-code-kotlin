package solutions.aoc2023.day06

import utils.Resources
import utils.getInts

fun main() {

    val inputLine = Resources.getLines(2023, 6)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: List<String>): Int {

    val timeToDist = input[0].getInts().zip(input[1].getInts())
    return timeToDist.fold(1) { x, y -> x * getPossibleWays(y.first, y.second.toLong()) }
}

fun part2(input: List<String>): Int {

    val time = input[0].getInts().joinToString("")
    val distance = input[1].getInts().joinToString("")

    return getPossibleWays(time.toInt(), distance.toLong())
}

fun getPossibleWays(time: Int, dist: Long): Int {
    return (1..<time).count {
        val actualDist = (time - it).toBigInteger().times(it.toBigInteger())
        actualDist.compareTo(dist.toBigInteger()) == 1
    }
}
