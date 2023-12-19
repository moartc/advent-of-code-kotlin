package solutions.aoc2022.day05

import utils.*
import utils.collections.extensions.deepCopyMutable
import utils.collections.extensions.transpose
import utils.parser.getChars
import utils.parser.getInts

fun main() {
    val inputLines = Resources.getLines(2022, 5)

    val map = inputLines.takeWhile { it.isNotBlank() }
        .dropLast(1)
        .mapNotNull { it.getChars(1, 4) }.reversed()
        .transpose()
        .map { it.dropLastWhile { i -> i == null } } as MutableList<MutableList<Char>>

    val moves = inputLines.dropWhile { it.isNotBlank() }.drop(1)

    println("part1 = ${solve(map.deepCopyMutable(), moves, true)}")
    println("part2 = ${solve(map.deepCopyMutable(), moves, false)}")
}

fun solve(map: MutableList<MutableList<Char>>, input: List<String>, rev: Boolean): String {
    input.forEach {
        val (move, from, to) = it.getInts()
        move(map, move, from - 1, to - 1, rev)
    }
    return map.map { it.last() }.joinToString("")
}

fun move(map: MutableList<MutableList<Char>>, num: Int, from: Int, to: Int, rev: Boolean) {
    val toMove = map[from].takeLast(num)
    val newFrom = map[from].take(map[from].size - num)
    map[from] = newFrom.toMutableList()
    map[to].addAll(if (rev) toMove.reversed() else toMove)
}
