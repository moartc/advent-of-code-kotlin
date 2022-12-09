package solutions.aoc2022.day09

import utils.Resources
import kotlin.math.abs

fun main() {
    val input = Resources.getLines(2022, 9)

    val (part1, part2) = solve(input)
    println("part1 = $part1")
    println("part2 = $part2")
}

fun solve(input: List<String>): Pair<Int, Int> {

    val moves = input.map { it.split(" ") }.flatMap { it[0].repeat(it[1].toInt()).toList() }
    var startPos = listOf(Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0))
    val allPosPart1 = mutableSetOf<Pair<Int, Int>>()
    val allPosPart2 = mutableSetOf<Pair<Int, Int>>()
    allPosPart1.add(startPos[1])
    allPosPart2.add(startPos[9])
    moves.forEach {
        startPos = move(it, startPos)
        allPosPart1.add(startPos[1])
        allPosPart2.add((startPos[9]))
    }
    return allPosPart1.size to allPosPart2.size
}

fun move(dir: Char, currentPos: List<Pair<Int, Int>>): MutableList<Pair<Int, Int>> {

    val posH = currentPos[0]

    val head = when (dir) {
        'R' -> Pair(posH.first, posH.second + 1)
        'L' -> Pair(posH.first, posH.second - 1)
        'U' -> Pair(posH.first + 1, posH.second)
        else -> Pair(posH.first - 1, posH.second)
    }
    val newPositions = mutableListOf<Pair<Int, Int>>()
    newPositions.add(head)

    currentPos.subList(1, 10).forEachIndexed { index, pair ->
        val prev = newPositions[index]
        val yDist = prev.first - pair.first
        val xDist = prev.second - pair.second

        val newPos = if (abs(yDist) == 2 || abs(xDist) == 2) {
            if (abs(yDist) == 2 && abs(xDist) == 2) { // diag move
                if (yDist > 0 && xDist > 0) {
                    prev.first - 1 to prev.second - 1
                } else if (yDist < 0 && xDist > 0) {
                    prev.first + 1 to prev.second - 1
                } else if (yDist > 0 && xDist < 0) {
                    prev.first - 1 to prev.second + 1
                } else { // left down
                    prev.first + 1 to prev.second + 1
                }
            } else {
                if (abs(yDist) == 2) {
                    if (yDist > 0) { // up
                        prev.first - 1 to prev.second
                    } else { // down
                        prev.first + 1 to prev.second
                    }
                } else { // x > 2
                    if (xDist > 0) { // right
                        prev.first to prev.second - 1
                    } else { // down
                        prev.first to prev.second + 1
                    }
                }
            }
        } else {
            pair.first to pair.second
        }
        newPositions.add(newPos)
    }
    return newPositions
}
