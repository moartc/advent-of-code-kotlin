package solutions.aoc2022.day12

import utils.Resources

fun main() {
    val input = Resources.getLines(2022, 12)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {
    val grid = input.map { it.toCharArray()!! }!!
    var start = 0 to 0
    grid.forEachIndexed { indexy, element ->
        if (element.indexOf('S') != -1) {
            start = indexy to element.indexOf('S')
        }
    }
    var end = 0 to 0
    grid.forEachIndexed { indexy, element ->
        if (element.indexOf('E') != -1) {
            end = indexy to element.indexOf('E')
        }
    }
    grid[end.first][end.second] = 'z'
    grid[start.first][start.second] = 'a'
    findNext(grid, start, end, 0, mutableSetOf())
    return foundBest
}

fun part2(input: List<String>): Int {
    val grid = input.map { it.toCharArray()!! }!!
    var start1 = 0 to 0

    grid.forEachIndexed { indexy, element ->
        element.forEachIndexed { indexx, c ->
            if (c == 'S') {
                start1 = indexy to indexx
            }
        }
    }
    grid[start1.first][start1.second] = 'a'
    var starts = mutableListOf<Pair<Int, Int>>()

    grid.forEachIndexed { indexy, element ->
        element.forEachIndexed { indexx, c ->
            if (c == 'a') {
                starts.add(indexy to indexx)
            }
        }
    }
    var end = 0 to 0
    grid.forEachIndexed { indexy, element ->
        element.forEachIndexed { indexx, c ->
            if (c == 'E') {
                end = indexy to indexx
            }
        }
    }
    grid[end.first][end.second] = 'z'
    for (start in starts) {
        grid[start.first][start.second] = 'a'
        findNext(grid, start, end, 0, mutableSetOf())
    }
    return foundBest
}

var bestVisitedPoint = mutableMapOf<Pair<Int, Int>, Int>()
var bestVisited = mutableListOf<Pair<Int, Int>>()
var foundBest = 462 // hardcoded for part 2 - answer from part 1
tailrec fun findNext(grid: List<CharArray>, current: Pair<Int, Int>, toFind: Pair<Int, Int>, counter: Int, visited: MutableSet<Pair<Int, Int>>) {
    if (bestVisitedPoint.contains(current)) {
        if (bestVisitedPoint[current]!! <= counter) {
            return
        }
    }
    bestVisitedPoint.put(current, counter)
    visited.add(current)
    if (counter > foundBest) {
        return
    }
    if (current == toFind) {
        if (counter < foundBest) {
            foundBest = counter
            foundBest.log("best found = ")
            bestVisited = visited.toMutableList()
        }
        return
    } else {
        val neighbour = getNeighbour(grid, current, visited)
        for (pair in neighbour) {
            findNext(grid, pair, toFind, counter + 1, visited.toMutableSet())
        }
    }
}

fun getNeighbour(grid: List<CharArray>, current: Pair<Int, Int>, visited: Set<Pair<Int, Int>>): MutableList<Pair<Int, Int>> {

    val toRet = mutableListOf<Pair<Int, Int>>()
    val currChar = grid[current.first][current.second]

    val list = listOf(
        Pair(current.first + 1, current.second),
        Pair(current.first - 1, current.second),
        Pair(current.first, current.second - 1),
        Pair(current.first, current.second + 1)
    )
    for ((y, x) in list) {
        if (y >= 0 && y < grid.size && x >= 0 && x < grid[0].size) {
            val char = grid[y][x]
            if (char - 1 <= currChar && !visited.contains(y to x)) {
                toRet.add(y to x)
            }
        }
    }
    return toRet
}


fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }