package utils.collections.extensions

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

inline fun <T> Iterable<T>.findIndexed(predicate: (T) -> Boolean): Pair<Int, T>? {
    forEachIndexed { i, e -> if (predicate(e)) return i to e }
    return null
}

