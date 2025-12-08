package utils.algorithms

class UnionFind<T> {
    private val parent = mutableMapOf<T, T>()
    private val rank = mutableMapOf<T, Int>()

    fun add(item: T) {
        if (item !in parent) {
            parent[item] = item
            rank[item] = 0
        }
    }

    fun find(item: T): T {
        if (parent[item] != item) {
            parent[item] = find(parent[item]!!) // Path compression
        }
        return parent[item]!!
    }

    fun union(a: T, b: T): Boolean {
        val rootA = find(a)
        val rootB = find(b)

        if (rootA == rootB) return false

        // Union by rank
        when {
            rank[rootA]!! < rank[rootB]!! -> parent[rootA] = rootB
            rank[rootA]!! > rank[rootB]!! -> parent[rootB] = rootA
            else -> {
                parent[rootB] = rootA
                rank[rootA] = rank[rootA]!! + 1
            }
        }
        return true
    }

    fun getGroups(): Map<T, List<T>> {
        return parent.keys.groupBy { find(it) }
    }

    fun componentCount(): Int = parent.keys.map { find(it) }.distinct().size
}