package solutions.aoc2025.day04

import utils.Resources
import utils.algorithms.withDiagonal

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Any {

    val grid = inputLines.map { x -> x.toList() }
    var ctr = 0
    for (y in 0 until grid.size) {
        for (x in 0 until grid[0].size) {
            if (grid[y][x] == '@') {
                if (count(grid, y, x) < 4) {
                    ctr++
                }
            }
        }
    }
    return ctr
}

fun part2(inputLines: List<String>): Int {

    val grid = inputLines.map { x -> x.toMutableList() }
    var ctr = 0
    do {
        var c = 0
        for (y in 0 until grid.size) {
            for (x in 0 until grid[0].size) {
                if (grid[y][x] == '@') {
                    val countNeighbors = count(grid, y, x)
                    if (countNeighbors < 4) {
                        grid[y][x] = 'x'
                        c++
                    }
                }
            }
        }
        ctr += c
    } while (c > 0)
    return ctr
}

fun count(grid: List<List<Char>>, y: Int, x: Int): Int {
    val sizeY = grid.size - 1
    val sizeX = grid[0].size - 1
    fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
        return !(nextPos.first !in 0..sizeY || nextPos.second < 0 || nextPos.second > sizeX)
    }

    var ctr = 0
    for (move in withDiagonal) {
        val next = y + move.first to x + move.second
        if (!isPosValid(next)) {
            continue
        }
        if (grid[next.first][next.second] == '@') {
            ctr++
        }
    }
    return ctr
}
