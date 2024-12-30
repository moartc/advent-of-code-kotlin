package utils.collections.extensions

import utils.grid.Point


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

fun <T> List<List<T>>.get(point: Point): T {
    return this[point.y][point.x]
}