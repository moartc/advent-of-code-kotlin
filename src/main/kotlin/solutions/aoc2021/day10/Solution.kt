package solutions.aoc2021.day10

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    var res = 0
    inputLines.forEach { line ->
        val nextExpected = mutableListOf<Char>()
        for (c in line) {
            if (c == '(') {
                nextExpected.addFirst(')')
            } else if (c == '[') {
                nextExpected.addFirst(']')
            } else if (c == '{') {
                nextExpected.addFirst('}')
            } else if (c == '<') {
                nextExpected.addFirst('>')
            } else { // close
                val exp = nextExpected.removeFirst()
                if (c != exp) {
                    when (c) {
                        ')' -> res += 3
                        ']' -> res += 57
                        '}' -> res += 1197
                        '>' -> res += 25137
                    }
                }
            }
        }
    }
    return res
}

fun part2(inputLines: List<String>): Any {

    val allScores = mutableListOf<Long>()
    f@ for (line in inputLines) {
        val nextExpected = mutableListOf<Char>()
        for (c in line) {
            if (c == '(') {
                nextExpected.addFirst(')')
            } else if (c == '[') {
                nextExpected.addFirst(']')
            } else if (c == '{') {
                nextExpected.addFirst('}')
            } else if (c == '<') {
                nextExpected.addFirst('>')
            } else { // close
                val exp = nextExpected.removeFirst()
                if (c != exp && c in setOf(')', ']', '}', '>')) {
                    continue@f
                }
            }
        }
        if (nextExpected.isEmpty()) {
            continue
        }
        var res = 0L
        for (c in nextExpected) {
            res *= 5
            res += when (c) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> 0
            }
        }
        allScores.add(res)
    }
    return allScores.sorted()[(allScores.size / 2)]
}

