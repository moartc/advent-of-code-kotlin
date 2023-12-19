package solutions.aoc2023.day04

import utils.Resources
import utils.parser.getInts

fun main() {

    val inputLine = Resources.getLines(2023, 4)
    println("part1 = ${part1(inputLine) == 23235}")
    println("part2 = ${part2(inputLine) == 5920640}")
}

fun part1(input: List<String>): Int {

    return input.sumOf {
        val colonSplit = it.split(":")
        val barSplit = colonSplit[1].split("|")
        val winning = barSplit[0].getInts()
        val myList = barSplit[1].getInts()
        val wins = winning.intersect(myList).size
        if (wins == 0) 0 else 1 shl (wins - 1)
    }
}

fun part2(input: List<String>): Int {

    val lines = input.map {
        val colonSplit = it.split(":")
        val barSplit = colonSplit[1].split("|")
        val winning = barSplit[0].getInts()
        val myList = barSplit[1].getInts()
        Line(1, winning, myList)
    }.toMutableList()

    lines.forEachIndexed { index, line ->
        val wins = line.winning.intersect(line.myLis).size
        for (toAdd in 1..wins) {
            if (index + toAdd < lines.size) {
                lines[index + toAdd].copies += line.copies
            }
        }
    }
    return lines.sumOf { it.copies }
}

data class Line(var copies: Int, val winning: List<Int>, val myLis: List<Int>)



