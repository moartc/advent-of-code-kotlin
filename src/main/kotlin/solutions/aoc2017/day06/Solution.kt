package solutions.aoc2017.day06

import utils.Resources

fun main() {
    val inputLine = Resources.getLine(2017, 6)
    val splitted = inputLine.split("\t".toRegex()).map { it.toInt() }
    val part1 = doStep(splitted.toMutableList(), 0, mutableSetOf(splitted.toList()))
    println("part1 = ${part1.second}")
    val part2 = doStep(part1.first.toMutableList(), 0, mutableSetOf(part1.first.toList())).second
    println("part2 = $part2")
}

tailrec fun doStep(splitted: MutableList<Int>, counter: Int, set: MutableSet<List<Int>>): Pair<List<Int>, Int> {
    val maxVal = splitted.max()
    val index = splitted.indices.first { splitted[it] == maxVal }
    splitted[index] = 0
    val updated = updateList(splitted, index + 1, maxVal)
    return if (set.add(updated)) {
        doStep(updated.toMutableList(), counter + 1, set)
    } else {
        Pair(updated, (counter + 1))
    }
}

fun updateList(list: MutableList<Int>, index: Int, value: Int): List<Int> {
    return if (value == 0) {
        list
    } else {
        val idxToUpdate = if (index == list.size) 0 else index
        list[idxToUpdate] = list[idxToUpdate] + 1
        updateList(list, idxToUpdate + 1, value - 1)
    }
}

