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


