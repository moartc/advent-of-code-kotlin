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
 * Returns the top k elements using a custom selector
 */
fun <T, R : Comparable<R>> List<T>.topKBy(k: Int, descending: Boolean = true, selector: (T) -> R): List<T> {
    return if (descending) sortedByDescending(selector).take(k)
    else sortedBy(selector).take(k)
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

/**
 * Splits list into chunks of specified size
 */
fun <T> List<T>.chunked(size: Int): List<List<T>> = chunked(size)

/**
 * Returns all permutations of the list
 * Warning: Exponential complexity - use only for small lists (n <= 10)
 */
fun <T> List<T>.permutations(): List<List<T>> {
    if (size <= 1) return listOf(this)
    val result = mutableListOf<List<T>>()
    for (i in indices) {
        val rest = this.filterIndexed { index, _ -> index != i }
        rest.permutations().forEach { perm ->
            result.add(listOf(this[i]) + perm)
        }
    }
    return result
}

/**
 * Returns all combinations of size k from the list
 */
fun <T> List<T>.combinations(k: Int): List<List<T>> {
    if (k == 0) return listOf(emptyList())
    if (k > size) return emptyList()
    if (k == size) return listOf(this)

    val result = mutableListOf<List<T>>()

    fun generate(start: Int, current: List<T>) {
        if (current.size == k) {
            result.add(current)
            return
        }
        for (i in start until size) {
            generate(i + 1, current + this[i])
        }
    }

    generate(0, emptyList())
    return result
}

/**
 * Partitions list into two groups based on predicate
 */
fun <T> List<T>.partitionIndexed(predicate: (Int, T) -> Boolean): Pair<List<T>, List<T>> {
    val first = mutableListOf<T>()
    val second = mutableListOf<T>()
    forEachIndexed { index, item ->
        if (predicate(index, item)) first.add(item)
        else second.add(item)
    }
    return first to second
}

/**
 * Sliding window over the list
 */
fun <T> List<T>.windowed(size: Int, step: Int = 1): List<List<T>> = windowed(size, step)

/**
 * Returns a list with elements rotated by n positions
 */
fun <T> List<T>.rotateLeft(n: Int): List<T> {
    val shift = n % size
    return drop(shift) + take(shift)
}

fun <T> List<T>.rotateRight(n: Int): List<T> {
    val shift = n % size
    return takeLast(shift) + dropLast(shift)
}

/**
 * Find cycle/repeating pattern in a list
 * Returns (start index, cycle length) or null if no cycle found
 */
fun <T> List<T>.findCycle(): Pair<Int, Int>? {
    for (cycleLength in 1..size / 2) {
        for (start in 0 until size - cycleLength * 2) {
            val pattern = subList(start, start + cycleLength)
            var matches = true
            var pos = start + cycleLength

            while (pos + cycleLength <= size) {
                if (subList(pos, pos + cycleLength) != pattern) {
                    matches = false
                    break
                }
                pos += cycleLength
            }

            if (matches && pos >= start + cycleLength * 2) {
                return start to cycleLength
            }
        }
    }
    return null
}

/**
 * Groups consecutive elements that satisfy the predicate
 */
fun <T> List<T>.groupConsecutive(predicate: (T, T) -> Boolean): List<List<T>> {
    if (isEmpty()) return emptyList()

    val result = mutableListOf<MutableList<T>>()
    var current = mutableListOf(first())

    for (i in 1 until size) {
        if (predicate(this[i - 1], this[i])) {
            current.add(this[i])
        } else {
            result.add(current)
            current = mutableListOf(this[i])
        }
    }
    result.add(current)

    return result
}

/**
 * Groups consecutive equal elements
 */
fun <T> List<T>.groupConsecutiveEqual(): List<List<T>> =
    groupConsecutive { a, b -> a == b }

/**
 * Returns indices where predicate is true
 */
fun <T> List<T>.indicesWhere(predicate: (T) -> Boolean): List<Int> =
    mapIndexedNotNull { index, item -> if (predicate(item)) index else null }

/**
 * Cartesian product of two lists
 */
fun <T, U> List<T>.cartesianProduct(other: List<U>): List<Pair<T, U>> =
    flatMap { a -> other.map { b -> a to b } }

/**
 * Repeat list n times
 */
fun <T> List<T>.repeat(n: Int): List<T> =
    List(n) { this }.flatten()

/**
 * Returns the element at the given index, wrapping around if out of bounds
 */
fun <T> List<T>.getWrapped(index: Int): T =
    this[index.mod(size)]

/**
 * Safe version of subList that clamps indices
 */
fun <T> List<T>.safeSubList(fromIndex: Int, toIndex: Int): List<T> {
    val from = fromIndex.coerceIn(0, size)
    val to = toIndex.coerceIn(0, size)
    return if (from >= to) emptyList() else subList(from, to)
}