package solutions.aoc2016.day16

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Any {
    return solve(inputLines[0], 272)
}

fun part2(inputLines: List<String>): String {
    return solve(inputLines[0], 35651584)
}

fun solve(input: String, size: Int): String {

    val input = input.map { x -> x == '1' }
    val test = makeAtLeastLong(input, size)
    val shortened = test.take(size)
    return calcChecksum(shortened).map { x -> if (!x) '0' else '1' }.joinToString("")
}


fun makeAtLeastLong(inp: List<Boolean>, size: Int): MutableList<Boolean> {
    val finalInp = inp.toMutableList()
    while (true) {
        if (finalInp.size >= size) {
            return finalInp
        } else {
            val secondPart = finalInp.reversed().map { x -> !x }
            finalInp.add(false)
            finalInp.addAll(secondPart)
        }
    }
}

fun calcChecksum(str: List<Boolean>): List<Boolean> {

    val res = mutableListOf<Boolean>()
    var i = 0
    while (i <= str.lastIndex - 1) {
        res.add(str[i] == str[i + 1])
        i += 2
    }
    return if (res.size % 2 == 0) {
        calcChecksum(res)
    } else {
        res
    }
}