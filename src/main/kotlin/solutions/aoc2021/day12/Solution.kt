package solutions.aoc2021.day12

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part2(inputLines: List<String>): Any {
    val map = inputLines.map { line ->
        val (l, r) = line.split("-")
        l to r
    }

    val allResults = mutableListOf<MutableList<String>>()
    fun find(current: String, visited: MutableList<String>, visitedAlreadyTwice: String) {

        visited.add(current)
        if (current == "end") {
            allResults.add(visited)
            return
        }

        val toVisit = map.filter { x -> x.first == current }.map { it.second }.toMutableList()
        toVisit.addAll(map.filter { x -> x.second == current }.map { it.first }.toMutableList())

        toVisit.forEach {
            if (it != "start" && (it.uppercase() == it)) {
                find(it, visited.toMutableList(), visitedAlreadyTwice)
            } else if (it != "start" && (it.lowercase() == it)) {
                val alreadyVisited = visited.count { x -> x == it }
                if (alreadyVisited == 0) {
                    find(it, visited.toMutableList(), visitedAlreadyTwice)
                } else if (alreadyVisited == 1) {
                    if (visitedAlreadyTwice == "none") {
                        find(it, visited.toMutableList(), it)
                    }
                }
            }
        }
    }
    find("start", mutableListOf(), "none")
    return allResults.size
}

fun part1(inputLines: List<String>): Int {

    val map = inputLines.map { line ->
        val (l, r) = line.split("-")
        l to r
    }

    val allResults = mutableListOf<MutableList<String>>()
    fun find(current: String, visited: MutableList<String>) {

        if (!visited.contains(current)) {
            visited.add(current)
        }
        if (current == "end") {
            allResults.add(visited)
            return
        }
        val toVisit = map.filter { x -> x.first == current }.map { it.second }.toMutableList()
        toVisit.addAll(map.filter { x -> x.second == current }.map { it.first }.toMutableList())
        toVisit.forEach {
            if (it != "start" && (it.uppercase() == it || (it.lowercase() == it && !visited.contains(it)))) {
                find(it, visited.toMutableList())
            }
        }
    }
    find("start", mutableListOf())
    return allResults.size
}

