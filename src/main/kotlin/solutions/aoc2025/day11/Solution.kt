package solutions.aoc2025.day11

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Long {

    val graph = inputLines.associate { line ->
        val (l, r) = line.split(":")
        val neighbours = r.trim().split(" ").map { it.trim() }
        l.trim() to neighbours
    }

    fun dfs(node: String, visited: Set<String>): Long {
        if (node == "out") {
            return 1
        }
        var ctr = 0L
        graph[node]!!.forEach { neighbour ->
            if (neighbour !in visited) {
                ctr += dfs(neighbour, visited + neighbour)
            }
        }
        return ctr
    }
    return dfs("you", setOf("you"))
}

fun part2(inputLines: List<String>): Long {

    val graph = inputLines.associate { line ->
        val (l, r) = line.split(":")
        val neighbours = r.trim().split(" ").map { it.trim() }
        l.trim() to neighbours
    }

    val cache = mutableMapOf<Pair<String, Pair<Boolean, Boolean>>, Long>()

    fun dfs(node: String, visited: Set<String>, dac: Boolean, fft: Boolean): Long {
        if (node == "out") {
            return if (dac && fft) 1 else 0
        }
        var ctr = 0L

        val cacheKey = node to (dac to fft)
        if (cache.containsKey(cacheKey)) {
            return cache[cacheKey]!!
        }
        graph[node]!!.forEach { neighbour ->
            if (neighbour !in visited) {
                val newDac = dac || neighbour == "dac"
                val newFft = fft || neighbour == "fft"
                ctr += dfs(neighbour, visited + neighbour, newDac, newFft)
            }
        }
        cache[cacheKey] = ctr
        return ctr
    }
    return dfs("svr", setOf("svr"), false, false)
}