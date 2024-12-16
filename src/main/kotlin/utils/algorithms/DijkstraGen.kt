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
            if (dist[v] == null || newDistance < dist[v]!!) {
                dist[v] = newDistance
            }
            queue.add(v to dist[v]!!)
        }
    }
    return dist
}

// as above (copy - paste) but with an additional track for a previous node - it stores all for the best result
// needed for 16/2024
fun <T> dijkstraGenWithPathHistory(
    src: T,
    distProvider: (T, T) -> Int,
    getNext: (T) -> List<T>
): Pair<MutableMap<T, Int>, MutableMap<T, MutableList<T>>> {

    val queue = PriorityQueue { p0: Pair<T, Int>, p1: Pair<T, Int> -> p0.second - p1.second }
    val visited = mutableSetOf<T>()
    val dist: MutableMap<T, Int> = mutableMapOf()
    val previous = mutableMapOf<T, MutableList<T>>() // Map to track the previous node

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
            if (dist[v] == null || newDistance < dist[v]!!) {
                dist[v] = newDistance
                previous.computeIfAbsent(v) { mutableListOf() }.add(u)
            } else if (dist[v] == null || newDistance == dist[v]!!) {
                previous.computeIfAbsent(v) { mutableListOf() }.add(u)
            }
            queue.add(v to dist[v]!!)
        }
    }
    return dist to previous
}
