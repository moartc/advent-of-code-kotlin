package solutions.day11

import utils.Resources

fun main() {
    val inputLine = Resources.getLine(11)
    val part1 = getAnswer(inputLine)
    println("part1 = $part1")
    val part2 = getAnswer(getNextString(part1))
    println("part2 = $part2")
}

fun getAnswer(input: String) = generateSequence(input) { getNextString(it) }.first { string ->
    notContainLetter(string) && contains2Pairs(string) && contains3IncreasingLetters(string)
}

fun contains3IncreasingLetters(password: String) =
    password.windowed(3, 1).any { s -> s[0] == s[1] - 1 && s[0] == s[2] - 2 }

fun notContainLetter(password: String) = Regex("[oiu]").containsMatchIn(password).not()
fun contains2Pairs(password: String): Boolean {
    val list = password.windowed(2, 1).withIndex().filter { it.value[0] == it.value[1] }.map { it.index }
    return list.isNotEmpty() && list.first() + 2 <= list.last()
}

fun getNextString(current: String) = process(current, current.length - 1, true, StringBuilder())

fun process(input: String, idx: Int, shouldChange: Boolean, result: StringBuilder): String {
    return if (idx < 0)
        result.toString().reversed()
    else
        process(
            input, idx - 1, if (!shouldChange) false else input[idx] == 'z',
            result.append(if (shouldChange) generateNextChar(input[idx]) else input[idx])
        )
}

fun generateNextChar(current: Char): Char =
    if (current == 'z') 'a' else if ("oiu".contains(current + 1).not()) current + 1 else current + 2
