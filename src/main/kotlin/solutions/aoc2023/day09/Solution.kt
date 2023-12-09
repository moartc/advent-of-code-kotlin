package solutions.aoc2023.day09

import utils.Resources
import utils.getInts

fun main() {

    val inputLine =
        Resources.getLines(2023, 9)
//        Resources.getLinesExample(2023, 9)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}


fun nextElemPart2(list: List<Int>): Int {
    var listLoc = mutableListOf<MutableList<Int>>()
    listLoc.add(list.toMutableList())
    while (!listLoc.last().all { x -> x == 0 }) { // while not all 0
        val lastVals = listLoc.last()

        listLoc.add(mutableListOf())
        for (i in 0..<lastVals.size - 1) { // count new diffs
            listLoc.last().add(lastVals[i + 1] - lastVals[i])
        }
    }

    var diff = 0
    for (idx in listLoc.lastIndex downTo 0) {
        diff = listLoc[idx].first() - diff
        diff.log("ne diff")
    }
    return diff
}

fun part2(input: List<String>): Int {

    input.forEach { it.log("p1") }

    val flatMap = input.map { it.split(" ").flatMap { it.getInts() } }

    val sum = flatMap.sumOf {
        nextElemPart2(it)
    }
    sum.log("answ")

    return sum
}


fun part1(input: List<String>): Int {

    input.forEach { it.log("p1") }

    val flatMap = input.map { it.split(" ").flatMap { it.getInts() } }

    var sum = flatMap.sumOf {
        val nextElem = nextElem(it)
        nextElem.log("nexxt")
        nextElem
    }

    sum.log("answ")

    return sum
}

fun nextElem(list: List<Int>): Int {
    var listLoc = mutableListOf<MutableList<Int>>()
    listLoc.add(list.toMutableList())
    while (!listLoc.last().all { x -> x == 0 }) { // while not all 0
        val lastVals = listLoc.last()

        listLoc.add(mutableListOf())
        for (i in 0..<lastVals.size - 1) { // count new diffs
            listLoc.last().add(lastVals[i + 1] - lastVals[i])
        }
    }

    var diff = 0
    var value = 0
    for (idx in listLoc.lastIndex downTo 0) {
        diff = listLoc[idx].last() + diff
        diff.log("ne diff")
    }
    return diff
}

private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



