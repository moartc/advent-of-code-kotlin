package solutions.aoc2021.day07

import utils.Resources
import kotlin.math.abs
import kotlin.math.min

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val ints = inputLines[0].split(",").map { it.toInt() }
    return (ints.min()..ints.max()).minOf { i ->
        ints.sumOf { toCheck -> abs(i - toCheck) }
    }
}

fun part2(inputLines: List<String>): Any {

    val ints = inputLines[0].split(",").map { it.toInt() }
    var cost = Int.MAX_VALUE
    for (i in ints.min()..ints.max()) {
        var currSum = 0
        for (int in ints) {
            var curr = 1
            repeat(abs(int - i)) {
                currSum += (curr++)
            }
        }
        cost = min(cost, currSum)
    }
    return cost
}


