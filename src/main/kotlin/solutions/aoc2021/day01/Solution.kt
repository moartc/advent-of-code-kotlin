package solutions.aoc2021.day01

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines =
        Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val ints = inputLines.map { it.toInt() }
    var prev = Int.MAX_VALUE
    var ans = 0
    for (int in ints) {
        if (prev < int) {
            ans++
        }
        prev = int
    }
    return ans
}

fun part2(inputLines: List<String>): Any {

    val ints = inputLines.map { it.toInt() }
    var prev = Int.MAX_VALUE
    var ans = 0
    var index = 0
    while (index <= ints.lastIndex - 2) {
        val sum = ints[index] + ints[index + 1] + ints[index + 2]
        if (prev < sum) {
            ans++
        }
        prev = sum
        index++
    }
    return ans
}
