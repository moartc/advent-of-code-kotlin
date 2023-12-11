package utils

fun <T> List<List<T>>.transpose(): List<List<T>> {
    val result = (first().indices).map { mutableListOf<T>() }.toMutableList()
    forEach { list -> result.zip(list).forEach { it.first.add(it.second) } }
    return result
}

fun <T> List<List<T>>.deepCopyMutable(): MutableList<MutableList<T>> = this.map { it.toMutableList() }.toMutableList()
fun <T> List<List<T>>.deepCopyImmutable(): List<List<T>> = this.map { it.toList() }.toList()

fun List<Int>.product() = this.reduce { acc, number -> acc * number }
fun List<Long>.product() = this.reduce { acc, number -> acc * number }

fun <T> List<List<T>>.intersection(): List<T> {
    return this.reduce { acc, ts -> acc.intersect(ts.toSet()).toList() }
}

inline fun <T> Iterable<T>.splitOn(predicate: (T) -> Boolean): List<List<T>> {
    val toRet = mutableListOf<MutableList<T>>()
    var newList = mutableListOf<T>()
    this.forEach {
        if (predicate(it)) {
            toRet += newList
            newList = mutableListOf()
        } else {
            newList += it
        }
    }
    toRet += newList
    return toRet
}

fun Iterable<String>.splitOnEmpty(): List<List<String>> = this.splitOn { it.isEmpty() }

fun <T> allCombinations(l1: List<T>, l2: List<T>): List<Pair<T, T>> {
    return l1.flatMap { i -> l2.map { j -> i to j } }
}

fun <T> allUniqueCombinations(list: List<T>): List<Pair<T, T>> {
    return list.indices.flatMap { i ->
        list.indices.minus(0..i).map { j -> (list[i] to list[j]) }
    }
}

class CircularList<out T>(private val list: List<T>) : List<T> by list {

    override fun get(index: Int): T =
        list[index.safely()]

    private fun Int.safely(): Int =
        if (this < 0) (this % size + size) % size
        else this % size
}

fun <T> Array<T>.rotateLeft(n: Int) = drop(n) + take(n)
fun <T> Array<T>.rotateRight(n: Int) = takeLast(n) + dropLast(n)

fun <T> Collection<T>.firstIndexed(predicate: (T) -> Boolean): Pair<Int, T> {
    return asSequence().mapIndexed(::Pair).first { (_, v) -> predicate(v) }
}

fun <T> Collection<T>.firstIndexedOrNull(predicate: (T) -> Boolean): Pair<Int, T>? {
    return asSequence().mapIndexed(::Pair).firstOrNull { (_, v) -> predicate(v) }
}

inline fun <T> Iterable<T>.findIndexed(predicate: (T) -> Boolean): Pair<Int, T>? {
    forEachIndexed { i, e -> if (predicate(e)) return i to e }
    return null
}

fun CharSequence.firstIndexedOrNull(predicate: (Char) -> Boolean): Pair<Int, Char>? {
    forEachIndexed { index, c ->
        if (predicate(c)) {
            return index to c
        }
    }
    return null
}

fun CharSequence.lastIndexedOrNull(predicate: (Char) -> Boolean): Pair<Int, Char>? {
    reversed()
        .forEachIndexed { index, c ->
            if (predicate(c)) {
                return length - index - 1 to c
            }
        }
    return null
}