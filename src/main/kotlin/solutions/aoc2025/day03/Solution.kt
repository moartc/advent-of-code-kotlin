package solutions.aoc2025.day03

import utils.Resources
import kotlin.math.max

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    var sum = 0
    for (str in inputLines) {
        val ints = str.toCharArray().map { i -> i.digitToInt() }
        var totMax = 0
        for (i in 0..ints.lastIndex) {
            for (j in i + 1..ints.lastIndex) {
                totMax = max(ints[i] * 10 + ints[j], totMax)
            }
        }
        sum += totMax
    }
    return sum
}

fun part2(inputLines: List<String>): Long {

    var sum = 0L

    for (str in inputLines) {

        fun best12(str: String): String {
            var stillToTake = 12
            val sb = StringBuilder()
            var nextStartIdx = 0
            while (stillToTake > 0) {
                var posOfTheBest = nextStartIdx
                // first is the best so far
                var best = str[nextStartIdx]
                val lastPossibleIdx = str.length - stillToTake
                for (i in nextStartIdx + 1..lastPossibleIdx) {
                    if (str[i] > best) {
                        best = str[i]
                        posOfTheBest = i
                    }
                }
                sb.append(best)
                nextStartIdx = posOfTheBest + 1
                stillToTake--
            }
            return sb.toString()
        }
        sum += (best12(str).toLong())
    }
    return sum
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }

