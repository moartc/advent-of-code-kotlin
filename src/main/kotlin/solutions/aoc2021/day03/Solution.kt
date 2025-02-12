package solutions.aoc2021.day03

import utils.Resources
import kotlin.math.ceil

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val resString = (0..<inputLines[0].length).map { i ->
        val countOnes = inputLines.count { x -> x[i].digitToInt() == 1 }
        if (countOnes > inputLines.size / 2) '1' else '0'
    }.joinToString("")
    return resString.toInt(2) * resString.map { if (it == '1') '0' else '1' }.joinToString("").toInt(2)
}

fun part2(inputLines: List<String>): Any {

    fun calc(input: List<String>, keepMostCommon: Boolean): Int {
        var inputToPrecess = input.map { it.map { i -> i.digitToInt() } }
        for (i in 0..<input[0].length) {
            val countForPos = inputToPrecess.count { x -> x[i] == 1 }
            val is1MostCommon = countForPos >= ceil(inputToPrecess.size / 2.0).toInt()
            if (is1MostCommon xor keepMostCommon) {
                inputToPrecess = inputToPrecess.filter { x -> x[i] == 1 }
            } else {
                inputToPrecess = inputToPrecess.filter { x -> x[i] == 0 }
            }
            if (inputToPrecess.size == 1) {
                return inputToPrecess[0].joinToString("").toInt(2)
            }
        }
        return -1
    }
    return calc(inputLines, true) * calc(inputLines, false)
}




