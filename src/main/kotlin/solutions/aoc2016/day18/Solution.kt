package solutions.aoc2016.day18

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val inputLine = inputLines[0]
    return solve(inputLine, 40)
}

fun part2(inputLines: List<String>): Int {

    val inputLine = inputLines[0]
    return solve(inputLine, 400000)
}

fun solve(inputLine: String, totalNum: Int): Int {

    fun generate(str: String): String {
        var toRet = ""
        for ((i, _) in str.withIndex()) {
            val l = i - 1
            val c = i
            val r = i + 1
            val ls = if (l < 0) true else str[l] == '.'
            val cs = str[c] == '.'
            val rs = if (r > str.lastIndex) true else str[r] == '.'
            toRet += if (!ls && !cs && rs) {
                '^'
            } else if (ls && !cs && !rs) {
                '^'
            } else if (!ls && cs && rs) {
                '^'
            } else if (ls && cs && !rs) {
                '^'
            } else {
                '.'
            }
        }
        return toRet
    }

    var ctr = 0
    var curr = inputLine
    ctr += curr.count { it == '.' }
    repeat(totalNum - 1) {
        curr = generate(curr)
        ctr += curr.count { it == '.' }
    }
    return ctr
}