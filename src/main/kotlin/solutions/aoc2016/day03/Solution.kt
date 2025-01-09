package solutions.aoc2016.day03

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {
    return inputLines.map { it.split(" ") }
        .map { i -> i.filter { x -> x.isNotEmpty() } }
        .map { Triple(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
        .count { isTriangle(it) }
}


fun part2(inputLines: List<String>): Int {

    val l = inputLines.map { it.split(" ") }.toList()
        .map { i -> i.filter { x -> x.isNotEmpty() } }
        .map { Triple(it[0].toInt(), it[1].toInt(), it[2].toInt()) }

    var c = 0
    for (i in 0..l.lastIndex step 3) {
        c += if (isTriangle(Triple(l[i].first, l[i + 1].first, l[i + 2].first))) 1 else 0
        c += if (isTriangle(Triple(l[i].second, l[i + 1].second, l[i + 2].second))) 1 else 0
        c += if (isTriangle(Triple(l[i].third, l[i + 1].third, l[i + 2].third))) 1 else 0
    }
    return c
}

fun isTriangle(t: Triple<Int, Int, Int>): Boolean =
    t.first + t.second > t.third && t.first + t.third > t.second && t.second + t.third > t.first
