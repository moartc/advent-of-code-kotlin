package utils.algorithms

import java.util.*


fun <T> dijkstraGen(src: T, distProvider: (T, T) -> Int, getNext: (T) -> List<T>): MutableMap<T, Int> {

    val queue = PriorityQueue { p0: Pair<T, Int>, p1: Pair<T, Int> -> p0.second - p1.second }
    val visited = mutableSetOf<T>()
    val dist: MutableMap<T, Int> = mutableMapOf()

    queue.add(src to 0)
    dist[src] = 0
    while (!queue.isEmpty()) {
        val u = queue.remove().first
        if (visited.contains(u)) {
            continue
        }
        visited.add(u)

        // previous content of eNeigbour
        var edgeDistance: Int
        var newDistance: Int
        for (v in getNext(u)) {
            edgeDistance = distProvider(u, v)
            newDistance = dist[u]!! + edgeDistance
            if (dist[v] == null || newDistance < dist[v]!!)
                dist[v] = newDistance

            queue.add(v to dist[v]!!)
        }
    }
    return dist
}
