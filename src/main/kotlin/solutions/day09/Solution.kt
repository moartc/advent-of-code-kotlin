package solutions.day09

import utils.Resources

fun main() {
    val inputLines = Resources.getLines(9)
    println("part1 = " + part1(inputLines))
    println("part2 = " + part2(inputLines))
}

fun findDistance(input: List<String>, min: Boolean): Int {
    val routes = input.fold(Routes()) { acc, line ->
        val groups = Regex("""^(\w+) to (\w+) = (\d+)""").matchEntire(line)!!.groups
        acc.addPair(groups[1]!!.value, groups[2]!!.value, groups[3]!!.value.toInt())
    }
    val distances = routes.cityToCityDistance.map { e -> visitCities(e.key, routes, 0, mutableListOf(), min) }
    return if (min) distances.minOrNull() ?: 0 else distances.maxOrNull() ?: 0
}

fun part1(input: List<String>) = findDistance(input, true)
fun part2(input: List<String>) = findDistance(input, false)

fun visitCities(
    start: String,
    routes: Routes,
    currentDistance: Int,
    visitedCities: MutableList<String>,
    min: Boolean
): Int {
    visitedCities.add(start)
    return if (visitedCities.size == routes.cityToCityDistance.keys.size) {
        currentDistance
    } else {
        val distances = routes.cityToCityDistance[start]!!.filter { !visitedCities.contains(it.first) }
            .map { i -> visitCities(i.first, routes, currentDistance + i.second, visitedCities.toMutableList(), min) }
        if (min) distances.minOrNull() ?: 0 else distances.maxOrNull() ?: 0
    }
}

class Routes {
    val cityToCityDistance = mutableMapOf<String, MutableList<Pair<String, Int>>>()
    fun addPair(city1: String, city2: String, distance: Int): Routes {
        cityToCityDistance.getOrPut(city1) { mutableListOf() }.add(Pair(city2, distance))
        cityToCityDistance.getOrPut(city2) { mutableListOf() }.add(Pair(city1, distance))
        return this
    }
}
