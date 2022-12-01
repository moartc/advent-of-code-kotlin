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
    var max = 0
    val list = mutableListOf<Int>()
    input.forEach { s ->
        if (s.isBlank()) {
            list.add(elf)
            max = elf.coerceAtLeast(max)
            elf = 0
        } else {
            elf += s.toInt()
        }
    }
    list.sortDescending()
    return Pair(max, list.take(3).sum())
}