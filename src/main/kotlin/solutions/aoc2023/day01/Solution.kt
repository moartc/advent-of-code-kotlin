package solutions.aoc2023.day01

import utils.Resources

fun main() {
    val inputLine =
        Resources.getLines(2023, 1)
    println("part1 =  ${part1(inputLine)}")
    println("part2 =  ${part2(inputLine)} ")
}

fun part1(input: List<String>): Int {

    return input
        .map { it.first { c -> c.isDigit() } + "" + it.last { c -> c.isDigit() } }
        .map(String::toInt)
        .sum()
}

fun part2(input: List<String>): Int {
    val wordToDigit = mapOf(
        Pair("one", 1),
        Pair("two", 2),
        Pair("three", 3),
        Pair("four", 4),
        Pair("five", 5),
        Pair("six", 6),
        Pair("seven", 7),
        Pair("eight", 8),
        Pair("nine", 9),
    )

    return input.sumOf { line ->
        val firstWord = wordToDigit.entries.filter { x -> line.contains(x.key) }.associate { e -> line.indexOf(e.key) to e.key }
            .minByOrNull { x -> x.key }
        val lastWord = wordToDigit.entries.filter { x -> line.contains(x.key) }.associate { e -> line.lastIndexOf(e.key) to e.key }
            .maxByOrNull { x -> x.key }

        val firstDigitIndex = line.indexOfFirst { x -> x.isDigit() }
        val lastDigitIndex = line.indexOfLast { x -> x.isDigit() }

        var stringDigit = ""
        if (firstDigitIndex != -1 && (firstWord == null || firstDigitIndex < firstWord.key)) {
            stringDigit += line.first { x -> x.isDigit() }
        } else {
            stringDigit += wordToDigit[firstWord!!.value]
        }
        if (lastDigitIndex != -1 && (lastWord == null || lastDigitIndex > lastWord.key)) {
            stringDigit += line.last { x -> x.isDigit() }
        } else {
            stringDigit += wordToDigit[lastWord!!.value]
        }
        stringDigit.toInt()
    }
}