package utils.parser

fun String.getUInts(): List<Int> = """\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toInt).toList()
fun String.getUInt(): Int? = """\d+""".toRegex().find(this)?.value?.toInt()

fun String.getInts(): List<Int> = """-?\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toInt).toList()
fun String.getInt(): Int? = """-?\d+""".toRegex().find(this)?.value?.toInt()

fun String.getLongs(): List<Long> = """-?\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toLong).toList()
fun String.getChars(start: Int, interval: Int): List<Char?> =
    this.filterIndexed { index, _ -> index == start || (index - start) % interval == 0 }
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
