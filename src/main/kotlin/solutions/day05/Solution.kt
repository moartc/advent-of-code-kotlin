package solutions.day05

import utils.Resources

fun main() {

    val inputLines = Resources.getLines(5)
    println("part1 = " + part1(inputLines))
    println("part2 = " + part2(inputLines))
}

fun part1(input: List<String>): Int {
    return input.count { isNicePart1(it) }
}

fun part2(input: List<String>): Int {
    return input.count { isNicePart2(it) }
}

fun containsVowels(input: String) = input.fold(0) { sum, c -> if ("aeiou".contains(c)) sum + 1 else sum } > 2
fun twiceLetters(input: String) = input.zipWithNext().any { it.first == it.second }
fun doesNotContains(input: String) = listOf("ab", "cd", "pq", "xy").none { i -> input.contains(i) }
fun isNicePart1(input: String) = containsVowels(input) && twiceLetters(input) && doesNotContains(input)

fun twoLetters(input: String) = IntRange(2, input.length - 2).any { idx -> containsTwoLetters(input, idx) }
fun repeatWithBetween(input: String) = IntRange(0, input.length - 3).any { i -> input[i] == input[i + 2] }
fun containsTwoLetters(input: String, idx: Int): Boolean {
    val substring = input.substring(idx, idx + 2)
    return input.substring(0, idx).contains(substring) || input.substring(idx + 2).contains(substring)
}

fun isNicePart2(input: String) = twoLetters(input) && repeatWithBetween(input)
