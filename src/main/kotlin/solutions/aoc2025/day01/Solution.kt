package solutions.aoc2025.day01

import utils.Resources
import utils.parser.getInts

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)} ")
}

fun part1(inputLines: List<String>): Int {
    var currPos = 50
    val map = inputLines.map { it[0] to it.getInts()[0] }
    var zeroCounter = 0
    for ((rot, value) in map) {
        val valueChar = if (rot == 'L') -value else value
        currPos = (currPos + valueChar) % 100
        if (currPos == 0) {
            zeroCounter++
        }
    }
    return zeroCounter
}

fun part2(inputLines: List<String>): Int {
    var currPos = 50
    val map = inputLines.map { it[0] to it.getInts()[0] }
    var zeroCounter = 0
    for ((rot, value) in map) {
        val (pos, ctr) = rot(currPos, value, rot)
        currPos = pos
        zeroCounter += ctr
    }
    return zeroCounter
}

fun rot(start: Int, move: Int, dir: Char): Pair<Int, Int> {
    var pos = start
    var ctr = 0
    repeat(move) { // just loop
        if (dir == 'R') {
            pos = if (pos == 99) 0 else pos + 1
        } else {
            pos = if (pos == 0) 99 else pos - 1
        }
        if (pos == 0) {
            ctr++
        }
    }
    return pos to ctr
}

