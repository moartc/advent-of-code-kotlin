package solutions.aoc2023.day09

import utils.Resources
import utils.getInts

fun main() {

    val inputLine = Resources.getLines(2023, 9)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}


fun part1(input: List<String>): Int {

    val flatMap = input.map { it.split(" ").flatMap { it.getInts() } }

    return flatMap.sumOf {
        val diffs = getListOfDiffs(it)
        diffs.foldRight(0) { y, x -> x + y.last() } as Int
    }
}

fun part2(input: List<String>): Int {

    val flatMap = input.map { it.split(" ").flatMap { it.getInts() } }

    return flatMap.sumOf {
        val diffs = getListOfDiffs(it)
        diffs.foldRight(0) { y, x -> y.first() - x } as Int
    }
}

fun getListOfDiffs(list: List<Int>): MutableList<MutableList<Int>> {
    val diffList = mutableListOf<MutableList<Int>>()
    diffList.add(list.toMutableList()) // initially add all original elements
    while (!diffList.last().all { x -> x == 0 }) { // while not all 0
        val lastVals = diffList.last()
        diffList.add(mutableListOf())
        for (i in 0..<lastVals.size - 1) { // count new diffs
            diffList.last().add(lastVals[i + 1] - lastVals[i])
        }
    }
    return diffList
}







