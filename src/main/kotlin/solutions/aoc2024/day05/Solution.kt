package solutions.aoc2024.day05

import utils.Resources
import utils.parser.getInts


fun main() {

    val inputLines = Resources.getLines(2024, 5)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Int {

    val pageOrdering = mutableMapOf<Int, MutableList<Int>>()
    val updates = mutableListOf<List<Int>>()

    inputLines.forEach {
        val ints = it.getInts()
        if (it.contains("|")) {
            pageOrdering.computeIfAbsent(ints[0]) { mutableListOf() }.add(ints[1])
        } else if (it.contains(",")) {
            updates.add(ints)
        }
    }

    fun isValid(f: Int, s: Int, map: Map<Int, List<Int>>) = map[s] != null && !map[s]!!.contains(f)

    var sum = 0
    ex@ for (up in updates) {
        for (pair in up.zipWithNext()) {
            if (!isValid(pair.first, pair.second, pageOrdering)) {
                continue@ex
            }
        }
        sum += up[up.size / 2]
    }
    return sum
}


fun part2(inputLines: List<String>): Int {

    val pageOrdering = mutableMapOf<Int, MutableList<Int>>()
    val updates = mutableListOf<List<Int>>()
    inputLines.forEach {
        val ints = it.getInts()
        if (it.contains("|")) {
            pageOrdering.computeIfAbsent(ints[0]) { mutableListOf() }.add(ints[1])
        } else if (it.contains(",")) {
            updates.add(ints)
        }
    }

    fun isValid(f: Int, s: Int, map: Map<Int, List<Int>>) = map[s] != null && !map[s]!!.contains(f)

    var sum = 0
    ex@ for (up in updates) {
        for (pair in up.zipWithNext()) {
            if (!isValid(pair.first, pair.second, pageOrdering)) {
                val sortedWith = up.sortedWith { a, b ->
                    when {
                        pageOrdering[a]?.contains(b)!! -> -1
                        pageOrdering[b]?.contains(a)!! -> 1
                        else -> 0
                    }
                }
                sum += sortedWith[sortedWith.size / 2]
                continue@ex
            }
        }
    }
    return sum
}
