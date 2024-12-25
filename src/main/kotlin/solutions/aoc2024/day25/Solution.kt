package solutions.aoc2024.day25

import utils.Resources
import utils.collections.extensions.splitOnEmpty

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)

    println("part1 = ${part1(inputLines)}")
}

fun part1(inputLines: List<String>): Int {
    val map = inputLines.splitOnEmpty()

    val locks = mutableListOf<List<Int>>()
    val keys = mutableListOf<List<Int>>()

    map.forEach { arr ->
        val columns = mutableListOf<Int>()
        for (index in arr[0].indices) {
            val count = arr.count { it[index] == '#' }
            columns.add(count - 1)
        }
        if (arr.first().all { it == '#' }) {
            locks.add(columns)
        } else {
            keys.add(columns)
        }
    }

    return keys.flatMap { k ->
        locks.map { l ->
            k.zip(l) { a, b -> a + b }
        }
    }.count { single -> single.none { x -> x > 5 } }
}
