package utils.collections.extensions


fun <T> List<List<T>>.deepCopyMutable(): MutableList<MutableList<T>> = this.map { it.toMutableList() }.toMutableList()
fun <T> List<List<T>>.deepCopyImmutable(): List<List<T>> = this.map { it.toList() }.toList()

fun List<Int>.product() = this.reduce { acc, number -> acc * number }
fun List<Long>.product() = this.reduce { acc, number -> acc * number }

fun <T> List<List<T>>.intersection(): List<T> {
    return this.reduce { acc, ts -> acc.intersect(ts.toSet()).toList() }
}

fun <T> List<T>.shiftLeft(n: Int) = drop(n) + take(n)
fun <T> List<T>.shiftRight(n: Int) = takeLast(n) + dropLast(n)

fun <T> List<List<T>>.transpose(): List<List<T>> {
    val result = (first().indices).map { mutableListOf<T>() }.toMutableList()
    forEach { list -> result.zip(list).forEach { it.first.add(it.second) } }
    return result
}

fun List<String>.findPosition(char: Char): Pair<Int, Int> {
    return this.mapIndexedNotNull { y, str ->
        str.indexOf(char).takeIf { it != -1 }?.let { y to it }
    }.first()
}

fun List<String>.findAllPositions(char: Char): List<Pair<Int, Int>> = this.flatMapIndexed { y, row ->
    row.mapIndexedNotNull { x, value ->
        if (value == char) Pair(y, x) else null
    }
}

fun <T> List<T>.toFrequencyMap(): MutableMap<T, Long> {
    return this.groupingBy { it }.fold(0L) { acc, _ -> acc + 1 }.toMutableMap()
}

fun <T> List<T>.containsSublist(sublist: List<T>): Boolean {
    for (i in 0..this.size - sublist.size) {
        if (this.slice(i until i + sublist.size) == sublist) {
            return true
        }
    }
    return false
}

/**
 * Returns all unique pairs from the list where order doesn't matter (combinations)
 */
fun <T> List<T>.allPairs(): Sequence<Pair<T, T>> = sequence {
    for (i in indices) {
        for (j in i + 1 until size) {
            yield(this@allPairs[i] to this@allPairs[j])
        }
    }
}

/**
 * Returns all unique pairs as a list
 */
fun <T> List<T>.allUnorderedPairs(): List<Pair<T, T>> =
    flatMapIndexed { i, a -> drop(i + 1).map { b -> a to b } }

/**
 * Returns the top k elements
 */
fun <T : Comparable<T>> List<T>.topK(k: Int, descending: Boolean = true): List<T> {
    return if (descending) sortedDescending().take(k)
    else sorted().take(k)
}

/**
 * Returns the product of the top k elements selected by the given selector
 */
fun <T> Iterable<T>.productOfTopK(k: Int, selector: (T) -> Long): Long {
    return this.map(selector)
        .sortedDescending()
        .take(k)
        .reduce(Long::times)
}

/**
 * Returns the pair with the minimum distance according to the distance function
 */
fun <T> List<T>.closestPair(distance: (T, T) -> Double): Pair<T, T>? {
    return allPairs()
        .minByOrNull { (a, b) -> distance(a, b) }
        ?.let { it.first to it.second }
}

/**
 * Returns the k pairs with the smallest distances
 */
fun <T> List<T>.kNearestPairs(k: Int, distance: (T, T) -> Double): List<Pair<T, T>> {
    return allPairs()
        .sortedBy { (a, b) -> distance(a, b) }
        .take(k)
        .toList()
}