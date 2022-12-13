package utils.algorithms

import java.util.*
import kotlin.Comparator


class GFG(private val numOfVertices: Int) {

    private lateinit var adj: List<MutableList<Pair<Int, Int>>>
    val dist: IntArray = IntArray(numOfVertices)
    private val settled = mutableSetOf<Int>()
    private val queue = PriorityQueue(numOfVertices, PairComparator())

    fun dijkstra(adj: List<MutableList<Pair<Int, Int>>>, src: Int) {
        this.adj = adj
        for (i in 0 until numOfVertices) {
            dist[i] = Int.MAX_VALUE
        }
        queue.add(src to 0)
        dist[src] = 0
        while (settled.size != numOfVertices) {
            if (queue.isEmpty()) return
            val u = queue.remove().first
            if (settled.contains(u)) {
                continue
            }
            settled.add(u)
            eNeighbours(u)
        }
    }

    private fun eNeighbours(u: Int) {
        var edgeDistance: Int
        var newDistance: Int
        for (i in adj[u].indices) {
            val v = adj[u][i]
            if (!settled.contains(v.first)) {
                edgeDistance = v.second
                newDistance = dist[u] + edgeDistance
                if (newDistance < dist[v.first]) dist[v.first] = newDistance
                queue.add(v.first to dist[v.first])
            }
        }
    }
}

private fun example(arg: Array<String>) {

    val numOfVertices = 5
    val source = 0
    val nodes: MutableList<MutableList<Pair<Int, Int>>> = ArrayList()

    // Initialize list of nodes
    for (i in 0 until numOfVertices) {
        val item: MutableList<Pair<Int, Int>> = ArrayList()
        nodes.add(item)
    }

    // node -> node to cost
    nodes[0].add(1 to 9)
    nodes[0].add(2 to 6)
    nodes[0].add(3 to 5)
    nodes[0].add(4 to 3)
    nodes[2].add(1 to 2)
    nodes[2].add(3 to 4)

    val dpq = GFG(numOfVertices)
    dpq.dijkstra(nodes, source)

    // shortest path from source (0) to every other node
    for (i in dpq.dist.indices) {
        println(source.toString() + " to " + i + " is " + dpq.dist[i])
    }
}

class PairComparator : Comparator<Pair<Int, Int>> {

    override fun compare(p0: Pair<Int, Int>, p1: Pair<Int, Int>): Int {
        if (p0.second < p1.second) return -1
        return if (p0.second > p1.second) 1 else 0
    }
}