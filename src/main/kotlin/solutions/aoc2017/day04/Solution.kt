package solutions.aoc2017.day04

import utils.Resources

fun main() {

    val inputLine = Resources.getLines(2017, 4)
    println("part1 = " + solve(inputLine, ::isValid))
    println("part2 = " + solve(inputLine, ::isValid2))
}

fun isValid(line: String): Boolean {
    val split = line.split(" ".toRegex())
    return split.size == split.toSet().size
}

fun isValid2(line: String): Boolean {
    val split = line.split(" ".toRegex())
    val sorted = split.map { single -> single.toCharArray().apply { sort() }.joinToString() }.toSet()
    return split.size == sorted.size
}

fun solve(input: List<String>, predicate: (String) -> Boolean): Int {
    return input.count { line -> predicate(line) }
}


