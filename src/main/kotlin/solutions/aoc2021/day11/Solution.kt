package solutions.aoc2021.day11

import utils.Resources
import utils.algorithms.withDiagonal
import utils.grid.Point

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part2(inputLines: List<String>): Any {

    fun secondStep(g: MutableList<MutableList<Int>>): Boolean {

        val listOfFlashed = mutableListOf<Pair<Int, Int>>()
        var checkFlash = true
        while (checkFlash) {
            checkFlash = false
            for (y in 0..g.lastIndex) {
                for (x in 0..g[0].lastIndex) {
                    val v = g[y][x]
                    if (v > 9 && !listOfFlashed.contains(y to x)) {
                        listOfFlashed.add(y to x)
                        checkFlash = true
                        flash(g, y, x)
                    }
                }
            }
        }
        listOfFlashed.forEach { p ->
            g[p.first][p.second] = 0
        }
        return listOfFlashed.size == g.size * g[0].size
    }

    val grid = inputLines.map { it.map { x -> x.digitToInt() }.toMutableList() }.toMutableList()
    repeat(999999) { round ->
        firstStep(grid)
        val allFlashed = secondStep(grid)
        if (allFlashed) {
            return round + 1 // I am counting from 0
        }
    }
    return -1
}

fun part1(inputLines: List<String>): Int {

    var flashCounter = 0
    fun secondStep(g: MutableList<MutableList<Int>>) {
        val listOfFlashed = mutableListOf<Pair<Int, Int>>()
        var checkFlash = true
        while (checkFlash) {
            checkFlash = false
            for (y in 0..g.lastIndex) {
                for (x in 0..g[0].lastIndex) {
                    val v = g[y][x]
                    if (v > 9 && !listOfFlashed.contains(y to x)) {
                        listOfFlashed.add(y to x)
                        flashCounter++
                        checkFlash = true
                        flash(g, y, x)
                    }
                }
            }
        }
        listOfFlashed.forEach { p ->
            g[p.first][p.second] = 0
        }
    }

    val grid = inputLines.map { it.map { x -> x.digitToInt() }.toMutableList() }.toMutableList()
    repeat(100) {
        firstStep(grid)
        secondStep(grid)
    }
    return flashCounter
}

fun firstStep(g: MutableList<MutableList<Int>>) {
    for (y in 0..g.lastIndex) {
        for (x in 0..g[0].lastIndex) {
            g[y][x] = g[y][x] + 1
        }
    }
}

fun flash(g: MutableList<MutableList<Int>>, y: Int, x: Int) {
    for (pair in withDiagonal) {
        val newY = pair.first + y
        val newX = pair.second + x
        val p = Point(newY, newX)
        if (p.isInRange(g)) {
            g[newY][newX] = g[newY][newX] + 1
        }
    }
}
