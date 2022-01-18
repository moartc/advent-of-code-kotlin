package solutions.day04

import utils.Resources
import java.math.BigInteger
import java.security.MessageDigest


fun main() {

    val inputLine = Resources.getLine(4)
    println("part1 = " + part1(inputLine))
    println("part2 = " + part2(inputLine))
}

fun part1(input: String): Int {
    return findNumber(input, 5)
}

fun part2(input: String): Int {
    return findNumber(input, 6)
}

fun findNumber(input: String, leadingZeros: Int): Int {
    return generateSequence(1, Int::inc)
        .map { i -> md5Hash(input + i) }
        .indexOfFirst { i -> i.startsWith("0".repeat(leadingZeros)) }
        .plus(1)
}

fun md5Hash(str: String): String {
    val md = MessageDigest.getInstance("MD5")
    val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
    return String.format("%032x", bigInt)
}
