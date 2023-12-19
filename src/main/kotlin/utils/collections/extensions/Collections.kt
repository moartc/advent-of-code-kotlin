package utils.collections.extensions


fun <T> Collection<T>.firstIndexed(predicate: (T) -> Boolean): Pair<Int, T> {
    return asSequence().mapIndexed(::Pair).first { (_, v) -> predicate(v) }
}

fun <T> Collection<T>.firstIndexedOrNull(predicate: (T) -> Boolean): Pair<Int, T>? {
    return asSequence().mapIndexed(::Pair).firstOrNull { (_, v) -> predicate(v) }
}

