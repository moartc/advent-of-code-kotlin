package solutions.aoc2023.day03

import utils.Resources
import utils.product

fun main() {

    val inputLines = Resources.getLines(2023, 3)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(input: List<String>): Int {

    fun isAdj(i: Int, j: Int, numLastChar: String, input: List<String>): Boolean {
        for (y in i - 1..i + 1) {
            if (y >= 0 && y < input.size) { // is y in range
                for (x in j - numLastChar.length..j + 1) {
                    if (x >= 0 && x < input[0].length) { // is x in range
                        if (y != i || (x < j - numLastChar.length + 1 || x > j)) { // doesn't overlap the number
                            if (input[y][x] != '.') {
                                return true
                            }
                        }
                    }
                }
            }
        }
        return false
    }

    var sum = 0
    for (y in input.indices) {
        val line = input[y]
        var dig = ""
        for (x in line.indices) {
            val c = line[x]
            if (c.isDigit()) {
                dig += c
                if (x == line.length - 1) { // case when the digit is the last character in the line
                    if (dig != "") { // and the number was found
                        if (isAdj(y, x, dig, input)) {
                            sum += dig.toInt()
                        }
                    }
                    dig = ""
                }
            } else {
                if (dig != "") {
                    if (isAdj(y, x - 1, dig, input)) { // x decreased to point on the last character in the number
                        sum += dig.toInt()
                    }
                }
                dig = ""
            }
        }
    }
    return sum
}


fun part2(input: List<String>): Int {

    fun idxOfAdjGear(i: Int, j: Int, dig: String, input: List<String>): Pair<Int, Int> {
        for (y in i - 1..i + 1) {
            if (y >= 0 && y < input.size) {
                for (x in j - dig.length..j + 1) {
                    if (x >= 0 && x < input[0].length) {
                        if (y != i || (x < j - dig.length + 1 || x > j)) {
                            if (input[y][x] == '*') {
                                return y to x
                            }
                        }
                    }
                }
            }
        }
        return -1 to -1
    }

    val gearToNum = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()

    for (i in input.indices) {
        val line = input[i]
        var dig = ""
        for (j in line.indices) {
            val c = line[j]
            if (c.isDigit()) {
                dig += c
                if (j == line.length - 1) { // case when the digit is the last character in the line
                    if (dig != "") { // and the number was found
                        val gerPos = idxOfAdjGear(i, j, dig, input)
                        gearToNum.getOrPut(gerPos) { mutableListOf() }.add(dig.toInt())
                    }
                    dig = ""
                }
            } else {
                if (dig != "") {
                    val gearPos = idxOfAdjGear(i, j - 1, dig, input)
                    gearToNum.getOrPut(gearPos) { mutableListOf() }.add(dig.toInt())
                }
                dig = ""
            }
        }
    }
    return gearToNum
        .filter { k -> k.key != (-1 to -1) && k.value.size > 1 }
        .values
        .sumOf { x -> x.product() }
}