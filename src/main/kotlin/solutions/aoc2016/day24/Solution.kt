package solutions.aoc2016.day24

import utils.Resources
import utils.algorithms.bfs
import utils.algorithms.withoutDiagonal
import utils.collections.extensions.findPosition

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Int {

    val grid = inputLines.map { it.toList() }
    val toVisit = mutableListOf<Char>()
    for (chars in grid) {
        for (char in chars) {
            if (char != '.' && char != '#') {
                toVisit.add(char)
            }
        }
    }

    val allPaths = mutableMapOf<Pair<Char, Char>, Int>()
    for ((i1, c1) in toVisit.withIndex()) {
        for ((i2, c2) in toVisit.withIndex()) {
            if (i2 > i1) {
                val newStart = inputLines.findPosition(c1)
                val newEnd = inputLines.findPosition(c2)
                val dist = bfs(grid, withoutDiagonal, newStart, newEnd) { _, nextPos -> grid[nextPos.first][nextPos.second] != '#' }
                allPaths[c1 to c2] = dist
            }
        }
    }

    var bestFound = Int.MAX_VALUE
    fun findShortestPath(
        current: Char,
        visited: MutableList<Char>,
        allPaths: MutableMap<Pair<Char, Char>, Int>,
        currentDist: Int,
        totalToVisit: Int
    ) {
        visited.add(current)
        if (visited.size == totalToVisit && currentDist < bestFound) {
            bestFound = currentDist
        }
        for (entry in allPaths.entries) {
            val k = entry.key
            val v = entry.value
            if (k.first == current && !visited.contains(k.second)) {
                findShortestPath(k.second, visited.toMutableList(), allPaths, currentDist + v, totalToVisit)
            } else if (k.second == current && !visited.contains(k.first)) {
                findShortestPath(k.first, visited.toMutableList(), allPaths, currentDist + v, totalToVisit)
            }
        }
    }
    findShortestPath('0', mutableListOf(), allPaths, 0, toVisit.size)
    return bestFound
}

fun part2(inputLines: List<String>): Int {

    val grid = inputLines.map { it.toList() }
    val toVisit = mutableListOf<Char>()
    for (chars in grid) {
        for (char in chars) {
            if (char != '.' && char != '#') {
                toVisit.add(char)
            }
        }
    }
    val allPaths = mutableMapOf<Pair<Char, Char>, Int>()
    for ((i1, c1) in toVisit.withIndex()) {
        for ((i2, c2) in toVisit.withIndex()) {
            if (i2 > i1) {
                val newStart = inputLines.findPosition(c1)
                val newEnd = inputLines.findPosition(c2)
                val dist = bfs(grid, withoutDiagonal, newStart, newEnd) { _, nextPos -> grid[nextPos.first][nextPos.second] != '#' }
                allPaths[c1 to c2] = dist
            }
        }
    }

    var bestFound = Int.MAX_VALUE
    fun findShortestPath(
        current: Char,
        visited: MutableList<Char>,
        allPaths: MutableMap<Pair<Char, Char>, Int>,
        currentDist: Int,
        totalToVisit: Int
    ) {
        visited.add(current)
        if (visited.size == totalToVisit) {
            val returnTo0 =
                allPaths.entries.first { x -> x.key.first == current && x.key.second == '0' || x.key.second == current && x.key.first == '0' }.value
            if (currentDist + returnTo0 < bestFound) {
                bestFound = currentDist + returnTo0
            }
        }
        for (entry in allPaths.entries) {
            val k = entry.key
            val v = entry.value
            if (k.first == current && !visited.contains(k.second)) {
                findShortestPath(k.second, visited.toMutableList(), allPaths, currentDist + v, totalToVisit)
            } else if (k.second == current && !visited.contains(k.first)) {
                findShortestPath(k.first, visited.toMutableList(), allPaths, currentDist + v, totalToVisit)
            }
        }
    }
    findShortestPath('0', mutableListOf(), allPaths, 0, toVisit.size)
    return bestFound
}
