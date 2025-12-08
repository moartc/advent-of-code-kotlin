package utils.parser

// === EXISTING (keeping your favorites) ===

fun String.getUInts(): List<Int> = """\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toInt).toList()
fun String.getUInt(): Int? = """\d+""".toRegex().find(this)?.value?.toInt()

fun String.getInts(): List<Int> = """-?\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toInt).toList()
fun String.getInt(): Int? = """-?\d+""".toRegex().find(this)?.value?.toInt()

fun String.getLongs(): List<Long> = """-?\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toLong).toList()
fun String.getLong(): Long? = """-?\d+""".toRegex().find(this)?.value?.toLong()

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


/**
 * Extract all words (sequences of letters)
 */
fun String.getWords(): List<String> = """[a-zA-Z]+""".toRegex().findAll(this).map { it.value }.toList()

/**
 * Extract all lowercase words
 */
fun String.getLowerWords(): List<String> = """[a-z]+""".toRegex().findAll(this).map { it.value }.toList()

/**
 * Extract all uppercase words
 */
fun String.getUpperWords(): List<String> = """[A-Z]+""".toRegex().findAll(this).map { it.value }.toList()

/**
 * Split on whitespace and parse each as int/long
 */
fun String.splitInts(): List<Int> = split("""\s+""".toRegex()).mapNotNull { it.toIntOrNull() }
fun String.splitLongs(): List<Long> = split("""\s+""".toRegex()).mapNotNull { it.toLongOrNull() }

/**
 * Parse coordinate pairs like "x,y" or "x y"
 */
fun String.toPair(): Pair<Int, Int>? {
    val nums = getInts()
    return if (nums.size >= 2) nums[0] to nums[1] else null
}

fun String.toLongPair(): Pair<Long, Long>? {
    val nums = getLongs()
    return if (nums.size >= 2) nums[0] to nums[1] else null
}

/**
 * Parse "x,y" into Point (y,x format)
 */
fun String.toPoint(): utils.grid.Point? {
    val nums = getInts()
    return if (nums.size >= 2) utils.grid.Point(nums[1], nums[0]) else null
}

/**
 * Parse "y,x" into Point directly
 */
fun String.toPointYX(): utils.grid.Point? {
    val nums = getInts()
    return if (nums.size >= 2) utils.grid.Point(nums[0], nums[1]) else null
}

/**
 * Extract hex strings
 */
fun String.getHexStrings(): List<String> = """[0-9a-fA-F]+""".toRegex().findAll(this).map { it.value }.toList()

/**
 * Parse hex string to Long
 */
fun String.hexToLong(): Long = this.toLong(16)
fun String.hexToInt(): Int = this.toInt(16)

/**
 * Extract binary strings
 */
fun String.getBinaryStrings(): List<String> = """[01]+""".toRegex().findAll(this).map { it.value }.toList()

/**
 * Parse binary string to Long/Int
 */
fun String.binaryToLong(): Long = this.toLong(2)
fun String.binaryToInt(): Int = this.toInt(2)

/**
 * Split by multiple delimiters
 */
fun String.splitBy(vararg delimiters: String): List<String> {
    var result = listOf(this)
    delimiters.forEach { delimiter ->
        result = result.flatMap { it.split(delimiter) }
    }
    return result.filter { it.isNotBlank() }
}

/**
 * Parse grid-style input
 */
fun List<String>.toCharGrid(): List<List<Char>> = map { it.toList() }
fun List<String>.toIntGrid(): List<List<Int>> = map { line -> line.map { it.digitToInt() } }
fun List<String>.toLongGrid(): List<List<Long>> = map { line -> line.map { it.digitToInt().toLong() } }

/**
 * Parse groups separated by blank lines
 */
fun List<String>.splitByBlankLines(): List<List<String>> {
    val groups = mutableListOf<MutableList<String>>()
    var currentGroup = mutableListOf<String>()

    forEach { line ->
        if (line.isBlank()) {
            if (currentGroup.isNotEmpty()) {
                groups.add(currentGroup)
                currentGroup = mutableListOf()
            }
        } else {
            currentGroup.add(line)
        }
    }

    if (currentGroup.isNotEmpty()) {
        groups.add(currentGroup)
    }

    return groups
}

/**
 * Parse key-value pairs from "key: value" format
 */
fun String.toKeyValue(separator: String = ":"): Pair<String, String>? {
    val parts = split(separator, limit = 2)
    return if (parts.size == 2) {
        parts[0].trim() to parts[1].trim()
    } else null
}

fun List<String>.toKeyValueMap(separator: String = ":"): Map<String, String> {
    return mapNotNull { it.toKeyValue(separator) }.toMap()
}

/**
 * Extract all characters (useful for grids)
 */
fun String.allChars(): List<Char> = toList()

/**
 * Count character frequencies
 */
fun String.charFrequency(): Map<Char, Int> = groupingBy { it }.eachCount()

/**
 * Parse ranges like "1-5" or "10-20"
 */
fun String.toIntRange(): IntRange? {
    val nums = getInts()
    return if (nums.size >= 2) nums[0]..nums[1] else null
}

fun String.toLongRange(): LongRange? {
    val nums = getLongs()
    return if (nums.size >= 2) nums[0]..nums[1] else null
}

/**
 * Check if string is all digits
 */
fun String.isNumeric(): Boolean = all { it.isDigit() }

/**
 * Check if string is a valid integer
 */
fun String.isInt(): Boolean = toIntOrNull() != null
fun String.isLong(): Boolean = toLongOrNull() != null

/**
 * Extract doubles/floats
 */
fun String.getDoubles(): List<Double> = """-?\d+\.?\d*""".toRegex()
    .findAll(this)
    .mapNotNull { it.value.toDoubleOrNull() }
    .toList()

/**
 * Parse comma-separated values
 */
fun String.csvToInts(): List<Int> = split(",").mapNotNull { it.trim().toIntOrNull() }
fun String.csvToLongs(): List<Long> = split(",").mapNotNull { it.trim().toLongOrNull() }
fun String.csvToStrings(): List<String> = split(",").map { it.trim() }

/**
 * Remove all non-digit characters
 */
fun String.digitsOnly(): String = filter { it.isDigit() }

/**
 * Remove all non-letter characters
 */
fun String.lettersOnly(): String = filter { it.isLetter() }