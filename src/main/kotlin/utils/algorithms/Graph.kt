package utils.algorithms

import utils.collections.extensions.allPairs

fun <T> minimumSpanningTree(
    points: List<T>,
    distance: (T, T) -> Double
): List<Pair<T, T>> {
    val uf = UnionFind<T>()
    points.forEach { uf.add(it) }

    return points.allPairs()
        .sortedBy { (a, b) -> distance(a, b) }
        .filter { (a, b) -> uf.union(a, b) }
        .toList()
}