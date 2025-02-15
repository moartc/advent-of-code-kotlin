package solutions.aoc2021.day06

import utils.Resources
import utils.collections.extensions.toFrequencyMap

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Int {

    val ints = inputLines[0].split(",").map { it.toInt() }.toMutableList()
    var day = 0
    while (day < 80) {
        var toAdd = 0
        for ((index, i) in ints.withIndex()) {
            ints[index]--
            if (i == 0) {
                ints[index] = 6
                toAdd++
            }
        }
        repeat(toAdd) {
            ints.add(8)
        }
        day++
    }
    return ints.size
}


fun part2(inputLines: List<String>): Any {

    val ints = inputLines[0].split(",").map { it.toInt() }.toMutableList()
    var freq = ints.toFrequencyMap()
    repeat(256) {
        val newFreq = freq.toMutableMap()
        newFreq[0] = freq.getOrDefault(1, 0)
        newFreq[1] = freq.getOrDefault(2, 0)
        newFreq[2] = freq.getOrDefault(3, 0)
        newFreq[3] = freq.getOrDefault(4, 0)
        newFreq[4] = freq.getOrDefault(5, 0)
        newFreq[5] = freq.getOrDefault(6, 0)
        newFreq[6] = freq.getOrDefault(7, 0) + freq.getOrDefault(0, 0)
        newFreq[7] = freq.getOrDefault(8, 0)
        newFreq[8] = freq.getOrDefault(0, 0)
        freq = newFreq
    }
    return freq.values.sum()
}