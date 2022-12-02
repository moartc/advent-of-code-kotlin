package solutions.aoc2022.day02

import utils.Resources

fun main() {
    val inputLines = Resources.getLines(2022, 2)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(input: List<String>): Int {
    return input.sumOf {
        val me = it[2]
        when (it[0]) {
            'A' -> if (me == 'X') 3 else if (me == 'Y') 6 else 0
            'B' -> if (me == 'X') 0 else if (me == 'Y') 3 else 6
            else -> if (me == 'X') 6 else if (me == 'Y') 0 else 3
        } + if (me == 'X') 1 else if (me == 'Y') 2 else 3
    }
}

fun part2(input: List<String>): Int {
    return input.sumOf {
        val he = it[0]
        when (it[2]) {
            'Y' -> 3 + if (he == 'A') 1 else if (he == 'B') 2 else 3
            'X' -> 0 + if (he == 'A') 3 else if (he == 'B') 1 else 2
            else -> 6 + if (he == 'A') 2 else if (he == 'B') 3 else 1
        }
    }
}


