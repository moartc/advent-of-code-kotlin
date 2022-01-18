package solutions.day03

import utils.Resources
import java.awt.Point


fun main() {

    val inputLine = Resources.getLine(3)
    println("part1 = " + part1(inputLine))
    println("part2 = " + part2(inputLine))
}

fun part1(input: String): Int {
    return input.fold(mutableListOf(Point(0, 0))) { acc, c -> acc.apply { add(acc.last().next(c)) } }
        .toSet()
        .count()
}

fun part2(input: String): Int {
    fun foo(predicate: (Int) -> Boolean) = input.foldIndexed(mutableListOf(Point(0, 0))) { idx, acc, c ->
        if (predicate(idx)) {
            acc.apply { add(acc.last().next(c)) }
        } else acc
    }.toSet()
    return (foo { it % 2 == 0 } + foo { it % 2 != 0 }).size
}

fun Point.next(c: Char): Point {
    return when (c) {
        '>' -> Point(this.x + 1, this.y)
        '<' -> Point(this.x - 1, this.y)
        '^' -> Point(this.x, this.y + 1)
        'v' -> Point(this.x, this.y - 1)
        else -> throw Exception("Cannot find next point")
    }
}
