package utils

fun String.getUInts(): List<Int> = """\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toInt).toList()
fun String.getUInt(): Int? = """\d+""".toRegex().find(this)?.value?.toInt()

fun String.getInts(): List<Int> = """-?\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toInt).toList()
fun String.getInt(): Int? = """-?\d+""".toRegex().find(this)?.value?.toInt()
fun String.getChars(start: Int, interval: Int): List<Char?> = this.filterIndexed { index, _ -> index == start || (index - start) % interval == 0 }
    .map { if (it.isLetter()) it else null }.toList()

fun parseArray(line: String): List<Any> {

    var i = 1
    fun parse(line: String): List<Any> {
        val list = mutableListOf<Any>()
        var current = ""
        while (i < line.length) {
            val c = line[i]
            if (c == '[') {
                i++
                list.add(parse(line))
            } else if (c == ']') {
                if (current != "") {
                    list.add(current.toInt())
                }
                return list
            } else if (c == ',') {
                if (current != "")
                    list.add(current.toInt())
                current = ""
            } else { // char
                current += c
            }
            i++
        }
        return list
    }

    return parse(line)
}

fun allPointsBetweenStringPairs(p1: Pair<String, String>, p2: Pair<String, String>): List<Pair<Int, Int>> {

    val p1Int = p1.first.toInt() to p1.second.toInt()
    val p2Int = p2.first.toInt() to p2.second.toInt()
    return allPointsBetweenIntPairs(p1Int, p2Int)
}

fun allPointsBetweenIntPairs(p1: Pair<Int, Int>, p2: Pair<Int, Int>): List<Pair<Int, Int>> {

    val y = if (p1.first < p2.first) p1.first..p2.first else p2.first..p1.first
    val x = if (p1.second < p2.second) p1.second..p2.second else p2.second..p1.second
    return allCombinations(y.toList(), x.toList())
}