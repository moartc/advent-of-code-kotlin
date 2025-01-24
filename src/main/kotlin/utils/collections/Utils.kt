package utils.collections

fun <T> allCombinations(l1: List<T>, l2: List<T>): List<Pair<T, T>> {
    return l1.flatMap { i -> l2.map { j -> i to j } }
}

fun <T> allUniqueCombinations(list: List<T>): List<Pair<T, T>> {
    val result = mutableListOf<Pair<T, T>>()
    for (i in list.indices) {
        for (j in i + 1 until list.size) {
            result.add(list[i] to list[j])
        }
    }
    return result
}

fun <T> allUniqueCombinationsOrNull(list: List<T>): MutableList<Pair<T, T>>? {
    if (list.size < 2) {
        return null
    }
    val result = mutableListOf<Pair<T, T>>()
    for (i in list.indices) {
        for (j in i + 1 until list.size) {
            result.add(list[i] to list[j])
        }
    }
    return result
}