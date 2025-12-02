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
        val mid = s.length / 2
        return s.take(mid) == s.substring(mid)
    }

    var sum = BigInteger.ZERO
    for ((l, r) in listOfPair) {
        var i = l
        while (i <= r) {
            if (shouldCount(i.toString())) {
                sum += i.toBigInteger()
            }
            i++
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
        val fullSize = s.length
        ex@ for (i in 2..fullSize) {
            if (fullSize % i == 0) {
                val size = fullSize / i
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
        var i = l
        while (i <= r) {
            if (shouldCount(i.toString())) {
                sum += i.toBigInteger()
            }
            i++
        }
    }
    return sum
}

