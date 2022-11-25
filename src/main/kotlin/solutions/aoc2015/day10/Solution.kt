package solutions.aoc2015.day10

import utils.Resources

fun main() {
    val inputLine = Resources.getLine(2015, 10)
    println("part1 = " + getAnswer(inputLine, 40))
    println("part2 = " + getAnswer(inputLine, 50))
}

fun getAnswer(input: String, nb: Int) =
    generateSequence(input) { s -> generateNewString(s) }.take(nb + 1).last().length

fun generateNewString(input: String) = buildString {
    input.foldIndexed(1) { index, acc, c ->
        if (index == input.length - 1 || c != input[index + 1]) {
            append(acc)
            append(c)
            1
        } else {
            acc + 1
        }
    }
}