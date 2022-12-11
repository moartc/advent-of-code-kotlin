package utils

fun String.getUInts(): List<Int> = """\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toInt).toList()
fun String.getUInt(): Int? = """\d+""".toRegex().find(this)?.value?.toInt()

fun String.getInts(): List<Int> = """-?\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toInt).toList()
fun String.getInt(): Int? = """-?\d+""".toRegex().find(this)?.value?.toInt()
fun String.getChars(start: Int, interval: Int): List<Char?> = this.filterIndexed { index, _ -> index == start || (index - start) % interval == 0 }
    .map { if(it.isLetter()) it else null }.toList()


inline fun <T> Iterable<T>.splitOn(predicate: (T) -> Boolean): List<List<T>> {
    val toRet = mutableListOf<MutableList<T>>()
    var newList = mutableListOf<T>()
    this.forEach {
        if(predicate(it)) {
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