package solutions.aoc2017.day09

import utils.Resources

fun main() {
    val inputLine = Resources.getLine(2017, 9)
    val (part1, part2) = solve(inputLine)
    println("part1 = $part1")
    println("part2 = $part2")
}

fun solve(input: String): Pair<Int, Int> {

    var ignoreNext = false
    var openGarbage = false
    var countOpenGarbage = 0
    var level = 0
    var answer = 0
    input.forEach {
        if (ignoreNext) {
            ignoreNext = false
        } else if (it == '!')
            ignoreNext = true
        else if (it == '>' && openGarbage) {
            openGarbage = false
        } else if (openGarbage) {
            countOpenGarbage++
        } else if (it == '{') {
            level++
        } else if (it == '}') {
            answer += level
            level--
        } else if (it == '<') {
            openGarbage = true
        }
    }
    return Pair(answer, countOpenGarbage)
}