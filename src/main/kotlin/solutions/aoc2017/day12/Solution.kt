package solutions.aoc2017.day12

import utils.Resources

fun main() {
    val input = Resources.getLines(2017, 12)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun findP1(value: String, inpMap: Map<String, List<String>>, setToAdd: MutableSet<String>): MutableSet<String> {
    inpMap[value]!!.forEach {
        if (!setToAdd.contains(it)) {
            setToAdd.add(it)
            findP1(it, inpMap, setToAdd)
        }
    }
    return setToAdd
}

fun part1(input: List<String>): Int {
    val inpMap = input.map { it.split(" <-> ") }.associate { it[0] to it[1].split(", ") }
    return findP1("0", inpMap, mutableSetOf()).size
}

fun findGroup(value: String, inpMap: Map<String, List<String>>): MutableSet<String> {
    val setToReturn = mutableSetOf<String>()
    findP1(value, inpMap, setToReturn)
    return setToReturn
}

fun part2(input: List<String>): Int {
    val inpMap = input.map { it.split(" <-> ") }.associate { it[0] to it[1].split(", ") }
    val allFound = mutableSetOf<String>()
    var counter = 0
    while (allFound.size < inpMap.keys.size) {
        counter++
        val first = inpMap.keys.first { key -> !allFound.contains(key) }
        allFound.addAll(findGroup(first, inpMap))
    }
    return counter
}

