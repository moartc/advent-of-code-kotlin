package solutions.aoc2023.day01

import utils.Resources
import utils.firstIndexedOrNull
import utils.lastIndexedOrNull

fun main() {
    val inputLine = Resources.getLines(2023, 1)
    println("part1 =  ${part1(inputLine)}")
    println("part2 =  ${part2(inputLine)}")
}

fun part1(input: List<String>): Int {

    return input.sumOf { it.first { c -> c.isDigit() }.digitToInt() * 10 + it.last { c -> c.isDigit() }.digitToInt() }
}

fun part2(input: List<String>): Int {
    val wordToDigit = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    return input.sumOf { line ->

        val minBy = wordToDigit.entries.associateBy { e -> line.indexOf(e.key) }.filter { a -> a.key != -1 }.minByOrNull { it.key }
        val maxBy = wordToDigit.entries.associateBy { e -> line.lastIndexOf(e.key) }.filter { a -> a.key != -1 }.maxByOrNull { it.key }

        val firstDigitIndex = line.firstIndexedOrNull { x -> x.isDigit() }
        val lastDigitIndex = line.lastIndexedOrNull { x -> x.isDigit() }

        var stringNumber = ""
        if (firstDigitIndex != null && (minBy == null || firstDigitIndex.first < minBy.key)) {
            stringNumber += firstDigitIndex.second
        } else {
            stringNumber += minBy!!.value.value
        }
        if (lastDigitIndex != null && (maxBy == null || lastDigitIndex.first > maxBy.key)) {
            stringNumber += lastDigitIndex.second
        } else {
            stringNumber += maxBy!!.value.value
        }
        stringNumber.toInt()
    }
}