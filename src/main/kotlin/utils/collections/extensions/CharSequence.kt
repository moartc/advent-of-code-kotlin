package utils.collections.extensions

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
