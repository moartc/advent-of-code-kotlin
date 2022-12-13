package solutions.aoc2022.day13

import utils.Resources
import utils.parseArray
import utils.splitOnEmpty


fun main() {
    val input = Resources.getLines(2022, 13)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {

    val pairs = input.splitOnEmpty()
    return pairs.map { (f, s) -> parseArray(f) to parseArray(s) }
        .mapIndexed { index, pair -> (index + 1) to compare(pair.first, pair.second) }
        .filter { res -> res.second != 1 }.sumOf { it.first }
}

fun part2(input: List<String>): Int {

    val new2 = listOf(listOf(2))
    val new6 = listOf(listOf(6))
    val parsedInput = input.filter { it.isNotEmpty() }.map { parseArray(it) }.toMutableList()
    parsedInput.add(new2)
    parsedInput.add(new6)
    val sorted = parsedInput.sortedWith { f, s -> compare(f, s) }
    return (sorted.indexOf(new2) + 1) * (sorted.indexOf(new6) + 1)

}


fun compare(first: List<Any?>, second: List<Any?>): Int {

    val itF = first.iterator()
    val itS = second.iterator()
    while (itF.hasNext() && itS.hasNext()) {
        val f = itF.next()
        val s = itS.next()
        if (f is Int && s is Int) {
            if (f < s) return -1
            else if (s < f) return 1
        } else if (f is List<*> && s is List<*>) {
            val result = compare(f, s)
            if (result != 0) {
                return result
            }
        } else if (f is Int && s is List<*>) {
            val result = compare(listOf(f), s)
            if (result != 0) {
                return result
            }
        } else if (f is List<*> && s is Int) {
            val result = compare(f, listOf(s))
            if (result != 0) {
                return result
            }
        }
    }
    if (!itF.hasNext() && itS.hasNext()) {
        return -1
    } else if (!itS.hasNext() && itF.hasNext()) {
        return 1
    }
    return 0
}