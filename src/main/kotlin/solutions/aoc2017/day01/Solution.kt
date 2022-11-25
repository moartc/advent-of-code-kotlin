package solutions.aoc2017.day01

import utils.Resources

fun main() {
    val inputLine = Resources.getLine(2017, 1)
    println("part1 = " + part1(inputLine))
    println("part2 = " + part2(inputLine))
}

fun part1(input: String): Int {
    return input.foldIndexed(0) { index, acc, c ->
        if (c == input[(index + 1) % input.length]) {
            acc + c.digitToInt()
        } else {
            acc
        }
    }
}

fun part2(input: String): Int {
    return input.mapIndexed { index, c ->
        val length = input.length
        val nextIdx = (index + length / 2) % length
        if (c == input[nextIdx]) {
            c.digitToInt()
        } else {
            0
        }
    }.sum()
}
