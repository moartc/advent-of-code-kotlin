package solutions.aoc2024.day23

import org.jgrapht.alg.clique.BronKerboschCliqueFinder
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val graph = mutableMapOf<String, MutableSet<String>>()
    inputLines.forEach {
        val (left, right) = it.split("-")
        graph.computeIfAbsent(left) { mutableSetOf() }.add(right)
        graph.computeIfAbsent(right) { mutableSetOf() }.add(left)
    }

    val triples = mutableSetOf<Set<String>>()
    graph.forEach { (left, allLeftConnections) ->
        allLeftConnections.forEach { right ->
            val neighboursOfRight = graph[right]!!
            val common = allLeftConnections.intersect((neighboursOfRight).toSet())
            common.forEach { third ->
                triples.add(setOf(left, right, third))
            }
        }
    }

    val anyStartsWithT = triples.count { triangle -> triangle.any { it.startsWith("t") } }
    return anyStartsWithT
}

fun part2(inputLines: List<String>): Any {
    // lib from 25th Dec 2024
    val graph = SimpleGraph<String, DefaultEdge>(DefaultEdge::class.java)

    inputLines.forEach {
        val (left, right) = it.split("-")
        graph.addVertex(left)
        graph.addVertex(right)
        graph.addEdge(left, right)
    }
    val bronKerboschCliqueFinder = BronKerboschCliqueFinder(graph)
    val maximumIterator = bronKerboschCliqueFinder.maximumIterator().next().toList().sorted()
    return maximumIterator.joinToString(",")
}