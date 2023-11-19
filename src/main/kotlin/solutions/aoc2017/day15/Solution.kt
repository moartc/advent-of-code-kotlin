package solutions.aoc2017.day15

import utils.Resources

fun main() {
    val input = Resources.getLines(2017, 15)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {

    val genAInp = input[0].split(" ").last().toInt()
    val genBInp = input[1].split(" ").last().toInt()
    var genA = (genAInp * 16807) % 2147483647L
    var genB = (genBInp * 48271) % 2147483647L
    var ctr = 0

    repeat(40000000) {
        val takeLastA = java.lang.Long.toBinaryString(genA).takeLast(16)
        val takeLastB = java.lang.Long.toBinaryString(genB).takeLast(16)

        if (takeLastA == takeLastB) {
            ctr++
        }
        genA = (genA * 16807) % 2147483647
        genB = (genB * 48271) % 2147483647
    }
    return ctr
}

fun part2(input: List<String>): Int {

    val genAInp = input[0].split(" ").last().toInt()
    val genBInp = input[1].split(" ").last().toInt()
    var genA = (genAInp * 16807) % 2147483647L
    var genB = (genBInp * 48271) % 2147483647L
    var ctr = 0

    repeat(5000000) {
        while (genA % 4L != 0L) {
            genA = (genA * 16807) % 2147483647L
        }
        while (genB % 8L != 0L) {
            genB = (genB * 48271) % 2147483647L
        }
        val takeLastA = java.lang.Long.toBinaryString(genA).takeLast(16)
        val takeLastB = java.lang.Long.toBinaryString(genB).takeLast(16)
        if (takeLastA == takeLastB) {
            ctr++
        }
        genA = (genA * 16807) % 2147483647L
        genB = (genB * 48271) % 2147483647L
    }
    return ctr
}