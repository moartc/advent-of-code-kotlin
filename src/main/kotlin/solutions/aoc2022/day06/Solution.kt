package solutions.aoc2022.day06

import utils.*

fun main() {
    val input = Resources.getLine(2022, 6)

    println("part1 = ${solve(input, 4)}")
    println("part2 = ${solve(input, 14)}")
}

fun solve(input: String, v: Int) = (0..input.length - v).first { idx -> input.substring(idx, idx + v).toSet().size == v } + v
