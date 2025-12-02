package solutions.aoc2025.day02

import utils.Resources
import java.math.BigInteger

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): BigInteger {

    val listOfPair = inputLines[0].split(",").map {
        val (a, b) = it.split("-")
        a.toLong() to b.toLong()
    }

    fun shouldCount(s: String): Boolean {
        if (s.length % 2 != 0) {
            return false
        }
        val mid = s.length / 2
        return s.take(mid) == s.substring(mid)
    }

    var sum = BigInteger.ZERO
    for ((l, r) in listOfPair) {
        val start = l.toBigInteger()
        val end = r.toBigInteger()
        var i = start
        while (i <= end) {
            if (shouldCount(i.toString())) {
                sum += i
            }
            i += BigInteger.ONE
        }
    }
    return sum
}


fun part2(inputLines: List<String>): BigInteger {

    val listOfPair = inputLines[0].split(",").map {
        val (a, b) = it.split("-")
        a.toLong() to b.toLong()
    }

    fun shouldCount(s: String): Boolean {
        val sSize = s.length
        ex@ for (i in 2..sSize) {
            if (sSize % i == 0) {
                val size = sSize / i
                val firstPart = s.take(size)
                for (j in 1 until i) {
                    if (s.substring(j * size, (j + 1) * size) != firstPart) {
                        continue@ex
                    }
                }
                return true
            }
        }
        return false
    }

    var sum = BigInteger.ZERO
    for ((l, r) in listOfPair) {
        val start = l.toBigInteger()
        val end = r.toBigInteger()
        var i = start
        while (i <= end) {
            if (shouldCount(i.toString())) {
                sum += i
            }
            i++
        }
    }
    return sum
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }

