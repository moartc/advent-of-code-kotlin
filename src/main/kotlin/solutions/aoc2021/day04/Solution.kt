package solutions.aoc2021.day04

import utils.Resources
import utils.collections.extensions.splitOnEmpty

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Int {

    val (allInts, allBoards) = parseIntsAndBoards(inputLines)
    val currentlyChecked = mutableSetOf<Int>()
    for (single in allInts) {
        currentlyChecked.add(single)
        for (b in allBoards) {
            val result = isWinner(b, currentlyChecked)
            if (result != -1) {
                return result * single
            }
        }
    }
    return -1
}

fun part2(inputLines: List<String>): Any {
    val (allInts, allBoards) = parseIntsAndBoards(inputLines)

    val currentlyChecked = mutableSetOf<Int>()
    val allWinners = mutableSetOf<Int>()
    for (single in allInts) {
        currentlyChecked.add(single)
        for ((boardNum, singleBoard) in allBoards.withIndex()) {
            if (!allWinners.contains(boardNum)) {
                val result = isWinner(singleBoard, currentlyChecked)
                if (result != -1) {
                    allWinners.add(boardNum)
                    if (allWinners.size == allBoards.size) {
                        val unmarked = singleBoard.sumOf { x -> x.sumOf { q -> if (!currentlyChecked.contains(q)) q else 0 } }
                        return unmarked * single
                    }
                }
            }
        }
    }
    return -1
}

fun parseIntsAndBoards(inputLines: List<String>): Pair<List<Int>, MutableList<List<List<Int>>>> {
    val lines = inputLines.splitOnEmpty()
    val first = lines[0][0]
    val allInts = first.split(",").map { it.toInt() }
    val allBoards = mutableListOf<List<List<Int>>>()
    for (i in 1..lines.lastIndex) {
        val current = lines[i]
        val board = current.map { it.split(Regex("\\s+")).filter { x -> x != "" }.map { it.toInt() } }
        allBoards.add(board)
    }

    return allInts to allBoards
}

fun isWinner(b: List<List<Int>>, allCurrent: MutableSet<Int>): Int {
    for (i in 0..<5) {
        if (b[i].all { x -> allCurrent.contains(x) }) {
            val unmarked = b.sumOf { x -> x.sumOf { q -> if (!allCurrent.contains(q)) q else 0 } }
            return unmarked
        }
    }
    ex@ for (i in 0..<5) {
        for (j in 0..<5) {
            if (!allCurrent.contains(b[j][i])) {
                continue@ex
            }
        }
        val unmarked = b.sumOf { x -> x.sumOf { q -> if (!allCurrent.contains(q)) q else 0 } }
        return unmarked
    }
    return -1
}
