package solutions.day08

import utils.Resources

fun main() {
    val inputLines = Resources.getLines(8)
    println("part1 = " + part1(inputLines))
    println("part2 = " + part2(inputLines))
}

fun part1(input: List<String>) =
    input.sumOf(String::length) - input.fold(0) { acc, line -> acc + getNbOfCharsInMemory(line) }

fun part2(input: List<String>) = input.fold(0) { acc, line -> acc + encode(line) } - input.sumOf(String::length)

fun getNbOfCharsInMemory(string: String): Int {
    return string.substring(1, string.length - 1)
        .replace(Regex("""\\x[0-9a-f]{2}"""), "@")
        .replace("""\\""", "@")
        .replace("""\"""", "@")
        .length
}

fun encode(string: String): Int {
    return (string.substring(1, string.length - 1)
        .replace("""\""", """\\""")
        .replace(""""""", """\"""")
        .prependIndent(""""\"""") + """\""""")
        .length
}