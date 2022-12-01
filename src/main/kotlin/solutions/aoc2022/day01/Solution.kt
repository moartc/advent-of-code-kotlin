package solutions.aoc2022.day01

import utils.Resources

fun main() {
    val inputLines = Resources.getLines(2022, 1)
    val (part1, part2) = solve(inputLines)
    println("part1 = $part1")
    println("part2 = $part2")
}

fun solve(input: List<String>): Pair<Int, Int> {
    var elf = 0
    val list = mutableListOf<Int>()
    input.forEach {
        if (it.isBlank()) {
            list.add(elf)
            elf = 0
        } else {
            elf += it.toInt()
        }
    }
    list.sortDescending()
    return Pair(list.first(), list.take(3).sum())
}