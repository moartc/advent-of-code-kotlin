package solutions.aoc2017.day10

import utils.Resources

fun main() {
    val inputLine = Resources.getLine(2017, 10)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(inputLine: String): Int {
    var list = (0..255).toMutableList()
    var idx = 0
    var skip = 0
    for (value in inputLine.split(",").map { it.trim().toInt() }) {
        list = reverseSubList(list, idx, value)
        idx = (idx + (skip + value)) % list.size
        skip += 1
    }
    return list[0] * list[1]
}

fun part2(inputLine: String): Any {
    val lengths = inputLine.map { c -> c.code }.toMutableList()
    lengths.addAll(listOf(17, 31, 73, 47, 23))
    var list = (0..255).toMutableList()
    var idx = 0
    var skip = 0
    repeat(64) {
        for (value in lengths) {
            list = reverseSubList(list, idx, value)
            idx = (idx + (skip + value)) % list.size
            skip += 1
        }
    }
    return list.chunked(16).map { x -> x.fold(0) { acc, i -> acc.xor(i) } }.joinToString("") { intToHex(it) }
}

fun reverseSubList(list: List<Int>, start: Int, size: Int): MutableList<Int> {
    val ml = list.toMutableList()
    var first = start
    var end = (start + (size - 1)) % (list.size)
    var toMove = size / 2
    while (toMove > 0) {
        ml.swap(first, end)
        first += 1
        if (first == list.size) first = 0
        end -= 1
        if (end < 0) end = list.size - 1
        toMove--
    }
    return ml
}

fun intToHex(int: Int): String {
    val hex = Integer.toHexString(int)
    return if (hex.length == 1) "0$hex" else hex
}

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}
