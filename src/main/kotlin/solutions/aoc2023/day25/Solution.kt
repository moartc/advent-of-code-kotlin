package solutions.aoc2023.day25

import org.jgrapht.Graph
import org.jgrapht.alg.flow.GusfieldGomoryHuCutTree
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import utils.Resources


fun main() {

    val inputLine = Resources.getLines(2023, 25)
    println("part1 = ${part1(inputLine)}")
}

fun part1(input: List<String>): Int {

    val graph: Graph<String, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)

    input.forEach {
        val split = it.split(": ")
        val left = split[0]
        val rightSplit = split[1].split(" ").toMutableList()

        graph.addVertex(left)
        rightSplit.forEach {
            graph.addVertex(it)
            graph.addEdge(left, it)
        }
    }

    val gusfieldGomoryHuCutTree = GusfieldGomoryHuCutTree(graph)
    gusfieldGomoryHuCutTree.calculateMinCut()
    val sourcePartition = gusfieldGomoryHuCutTree.sourcePartition
    val oneSide = graph.vertexSet().size - sourcePartition.size
    return oneSide * sourcePartition.size
}
