package solutions.day20

import utils.Resources

fun main() {
    val input = Resources.getLine(20).toInt()
    val part1 = part1(input)
    println("part1 = $part1")
    println("part2 = ${part2(input, part1)}")
}

fun part1(input: Int) = generateSequence(1) { it + 1 }.first { numsOfPresentsPart1(it) >= input }
fun part2(input: Int, start: Int) = generateSequence(start) { it + 1 }.first { numsOfPresentsPart2(it) >= input }
fun numsOfPresentsPart1(number: Int) = (1..number / 2).filter { number % it == 0 }.sumOf { it * 10 } + 10 * number
fun numsOfPresentsPart2(number: Int) =
    (1..number / 2).filter { it * 50 >= number && number % it == 0 }.sumOf { it * 11 } + 11 * number

