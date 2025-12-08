import utils.algorithms.withoutDiagonal
import java.util.PriorityQueue


fun <T> dijkstra(
    src: T,
    distProvider: (T, T) -> Int,
    getNext: (T) -> List<T>
): Map<T, Int> {
    val queue = PriorityQueue<Pair<T, Int>>(compareBy { it.second })
    val visited = mutableSetOf<T>()
    val dist = mutableMapOf<T, Int>()

    queue.add(src to 0)
    dist[src] = 0

    while (queue.isNotEmpty()) {
        val (u, currentDist) = queue.remove()

        if (u in visited) continue
        visited.add(u)

        for (v in getNext(u)) {
            val edgeDistance = distProvider(u, v)
            val newDistance = currentDist + edgeDistance

            if (v !in dist || newDistance < dist[v]!!) {
                dist[v] = newDistance
                queue.add(v to newDistance)
            }
        }
    }

    return dist
}

/**
 * Dijkstra with path tracking - stores ALL nodes that lead to the best distance.
 * for finding all shortest paths
 *
 * @param src The starting node
 * @param distProvider Function that returns the edge weight between two nodes
 * @param getNext Function that returns all neighbors of a node
 * @return Pair of (distance map, predecessor map with all nodes that achieve best distance)
 */
fun <T> dijkstraWithPathHistory(
    src: T,
    distProvider: (T, T) -> Int,
    getNext: (T) -> List<T>
): Pair<Map<T, Int>, Map<T, List<T>>> {
    val queue = PriorityQueue<Pair<T, Int>>(compareBy { it.second })
    val visited = mutableSetOf<T>()
    val dist = mutableMapOf<T, Int>()
    val previous = mutableMapOf<T, MutableList<T>>()

    queue.add(src to 0)
    dist[src] = 0

    while (queue.isNotEmpty()) {
        val (u, currentDist) = queue.remove()

        if (u in visited) continue
        visited.add(u)

        for (v in getNext(u)) {
            val edgeDistance = distProvider(u, v)
            val newDistance = currentDist + edgeDistance

            when {
                v !in dist || newDistance < dist[v]!! -> {
                    // Found a better path
                    dist[v] = newDistance
                    previous[v] = mutableListOf(u)
                    queue.add(v to newDistance)
                }

                newDistance == dist[v]!! -> {
                    // Found an equally good path
                    previous.getOrPut(v) { mutableListOf() }.add(u)
                }
            }
        }
    }

    return dist to previous
}

/**
 * Reconstructs a single shortest path from src to target.
 * Works with the path history from dijkstraWithPathHistory.
 */
fun <T> reconstructPath(target: T, previous: Map<T, List<T>>): List<T>? {
    if (target !in previous) return null

    val path = mutableListOf(target)
    var current = target

    while (current in previous && previous[current]!!.isNotEmpty()) {
        current = previous[current]!!.first() // Take first predecessor
        path.add(current)
    }

    return path.reversed()
}

/**
 * Reconstructs ALL shortest paths from src to target.
 * Works with the path history from dijkstraWithPathHistory.
 */
fun <T> reconstructAllPaths(target: T, previous: Map<T, List<T>>): List<List<T>> {
    if (target !in previous) return listOf(listOf(target))

    fun buildPaths(node: T): List<List<T>> {
        if (node !in previous || previous[node]!!.isEmpty()) {
            return listOf(listOf(node))
        }

        val allPaths = mutableListOf<List<T>>()
        for (pred in previous[node]!!) {
            for (path in buildPaths(pred)) {
                allPaths.add(path + node)
            }
        }
        return allPaths
    }

    return buildPaths(target)
}

/**
 * Finds all nodes that are part of ANY shortest path from src to target.
 * for "how many tiles are part of any shortest path".
 */
fun <T> allNodesInShortestPaths(target: T, previous: Map<T, List<T>>): Set<T> {
    val nodesInPath = mutableSetOf<T>()
    val queue = ArrayDeque<T>()
    queue.add(target)
    nodesInPath.add(target)

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        previous[current]?.forEach { pred ->
            if (pred !in nodesInPath) {
                nodesInPath.add(pred)
                queue.add(pred)
            }
        }
    }

    return nodesInPath
}

/**
 * Grid-specific Dijkstra for 2D grids with integer costs.
 * Convenience wrapper around dijkstra() for common grid problems.
 */
fun dijkstraGrid(
    grid: List<List<Int>>,
    start: Pair<Int, Int>,
    end: Pair<Int, Int>,
    directions: Array<Pair<Int, Int>> = withoutDiagonal
): Int? {
    val result = dijkstra(
        src = start,
        distProvider = { _, next -> grid[next.first][next.second] },
        getNext = { (y, x) ->
            directions
                .map { (dy, dx) -> y + dy to x + dx }
                .filter { (ny, nx) ->
                    ny in grid.indices && nx in grid[0].indices
                }
        }
    )

    return result[end]
}