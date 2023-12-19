package utils.collections

fun <T> allCombinations(l1: List<T>, l2: List<T>): List<Pair<T, T>> {
    return l1.flatMap { i -> l2.map { j -> i to j } }
}

fun <T> allUniqueCombinations(list: List<T>): List<Pair<T, T>> {
    return list.indices.flatMap { i ->
        list.indices.minus(0..i).map { j -> (list[i] to list[j]) }
    }
}