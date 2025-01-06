package solutions.aoc2017.day24

import utils.Resources
import utils.grid.Point

fun main() {
    val input = Resources.getLines(2017, 24)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {

    val map = input.map { it.split("/") }.map { Point(it[0].toInt(), it[1].toInt()) }
    val startingPoints = map.filter { x -> x.y == 0 || x.x == 0 }

    fun dfs(current: Point, valueToMatch: Int, visited: MutableSet<Point> = mutableSetOf()): Int {
        visited.add(current)
        val toVisit = map.filter { x -> !visited.contains(x) && (x.y == valueToMatch || x.x == valueToMatch) }

        if (toVisit.isEmpty()) {
            return visited.sumOf { x -> x.y + x.x }
        }
        return toVisit.maxOf {
            val newVisited = visited.toMutableSet()
            val valueToMatch = if (it.y == valueToMatch) it.x else it.y
            dfs(it, valueToMatch, newVisited)
        }
    }

    return startingPoints.maxOf { point ->
        val valueToMatch = if (point.y == 0) point.x else point.y
        dfs(point, valueToMatch)
    }
}

fun part2(input: List<String>): Int {

    val map = input.map { it.split("/") }.map { Point(it[0].toInt(), it[1].toInt()) }
    val startingPoints = map.filter { x -> x.y == 0 || x.x == 0 }

    fun dfs(current: Point, valueToMatch: Int, visited: MutableSet<Point> = mutableSetOf()): Pair<Int, Int> {
        visited.add(current)
        val toVisit = map.filter { x -> !visited.contains(x) && (x.y == valueToMatch || x.x == valueToMatch) }

        if (toVisit.isEmpty()) {
            return visited.size to visited.sumOf { p -> p.y + p.x }
        }
        val all = toVisit.map {
            val newVisited = visited.toMutableSet()
            val valueToMatch = if (it.y == valueToMatch) it.x else it.y
            dfs(it, valueToMatch, newVisited)
        }
        return all.maxWith(compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second })
    }

    var longest = -1
    var best = -1
    startingPoints.forEach {
        val point = it
        val valueToMatch = if (point.y == 0) point.x else point.y
        val r = dfs(point, valueToMatch)
        if (r.first > longest) {
            longest = r.first
            best = r.second
        }
    }
    return best
}


