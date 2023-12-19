package solutions.aoc2023.day10

import utils.Resources
import utils.algorithms.bfsPathFromPointToPoint
import utils.algorithms.isPointInPolygon
import utils.algorithms.withoutDiagonal


fun main() {

    val inputLine = Resources.getLines(2023, 10)

    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}


fun part1(input: List<String>): Int {

    val map = input.map { it.toList() }
    val start = input.indices.flatMap { y -> input.indices.map { x -> y to x } }.first { p -> map[p.first][p.second] == 'S' }

    val bfsPathFromPointToPoint = bfsPathFromPointToPoint(map, withoutDiagonal, start, start) { curr, next -> canVisitLoc(curr, next, map) }
    return bfsPathFromPointToPoint.size / 2
}


fun part2(input: List<String>): Int {

    val map = input.map { it.toList() }
    val start = input.indices.flatMap { y -> input.indices.map { x -> y to x } }.first { p -> map[p.first][p.second] == 'S' }

    val bestFound = bfsPathFromPointToPoint(map, withoutDiagonal, start, start) { curr, next -> canVisitLoc(curr, next, map) }.toList()
    val allPossiblePoints = (0..input.lastIndex).flatMap { y -> (0..input[0].lastIndex).map { x -> y to x } }
    val notVisited = allPossiblePoints.filterNot { bestFound.contains(it) }
    return notVisited.count { (y, x) -> isPointInPolygon(y to x, bestFound) }
}

fun canVisitLoc(currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>, grid: List<List<Char>>): Boolean {
    val nextChar = grid[nextPos.first][nextPos.second]
    val currChar = grid[currentPos.first][currentPos.second]
    if (currChar in "S|F7" && nextChar in "|LJS" && down(currentPos, nextPos)) {
        return true
    } else if (currChar in "S|LJ" && nextChar in "|F7S" && up(currentPos, nextPos)) {
        return true
    } else if (currChar in "S-7J" && nextChar in "-FLS" && left(currentPos, nextPos)) {
        return true
    } else if (currChar in "S-FL" && nextChar in "-7JS" && right(currentPos, nextPos)) {
        return true
    }
    return false
}

fun up(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
    val (cY, cX) = p1
    val (nY, nX) = p2
    return cY - 1 == nY && cX == nX
}

fun down(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
    val (cY, cX) = p1
    val (nY, nX) = p2
    return cY + 1 == nY && cX == nX
}

fun left(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
    val (cY, cX) = p1
    val (nY, nX) = p2
    return cY == nY && cX - 1 == nX
}

fun right(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
    val (cY, cX) = p1
    val (nY, nX) = p2
    return cY == nY && cX + 1 == nX
}