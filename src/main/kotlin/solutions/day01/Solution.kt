package solutions.day01

import utils.Resources


fun main() {

    val inputLine = Resources.getLine(1)
    println("solutions.day01.part1 = " + part1(inputLine))
    println("solutions.day01.part2 = " + part2(inputLine))
}

fun part1(input: String): Int {
    return input.fold(0) { acc, next -> if (next == '(') acc + 1 else acc - 1 }
}


fun part2(input: String): Int {
    return input.runningFold(0) { acc, next -> if (next == '(') acc + 1 else acc - 1 }
        .indexOfFirst { it == -1 }
}