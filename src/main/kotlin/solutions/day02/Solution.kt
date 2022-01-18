package solutions.day02

import utils.Resources

fun main() {

    val inputLines = Resources.getLines(2)
    println("part1 = " + part1(inputLines))
    println("part2 = " + part2(inputLines))
}

fun part1(lines: List<String>): Int {
    return lines.map {
        val totals = getPairs(it.split("x").map(String::toInt)).map { p -> 2 * p.first * p.second }
        totals.sum() + totals.minOrNull()!!.div(2)
    }.sum()
}

fun part2(lines: List<String>): Int {
    return lines.map {
        val sizes = it.split("x").map(String::toInt)
        sizes.reduce(Int::times).plus(getPairs(sizes).minOf { p -> 2 * p.first + 2 * p.second })
    }.sum()
}


fun getPairs(ints: List<Int>): List<Pair<Int, Int>> {
    return ints.withIndex().flatMap { (i1, e1) ->
        ints.withIndex().filter { (i2, _) -> i1 < i2 }
            .map { (_, e2) -> Pair(e1, e2) }
    }
}