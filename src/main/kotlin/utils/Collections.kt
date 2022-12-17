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

class CircularList<out T>(private val list: List<T>) : List<T> by list {

    override fun get(index: Int): T =
        list[index.safely()]

    private fun Int.safely(): Int =
        if (this < 0) (this % size + size) % size
        else this % size

}