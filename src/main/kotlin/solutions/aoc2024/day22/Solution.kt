package solutions.aoc2024.day22

import utils.Resources
import utils.parser.getLong
import kotlin.math.max

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val initialSecretNumbers = inputLines.map { it.getLong()!! }
    val results = initialSecretNumbers.sumOf { sec ->
        var secret = sec
        repeat(2000) {
            secret = getNextSecret(secret)
        }
        secret
    }
    return results
}


fun part2(inputLines: List<String>): Long {

    fun generatePrices(initial: Long, count: Int): List<Long> {
        val prices = mutableListOf<Long>()
        var secret = initial
        repeat(count) {
            prices.add((secret % 10))
            secret = getNextSecret(secret)
        }
        return prices
    }

    val initialSecretNumbers = inputLines.map { it.getLong()!! }
    val seqToPriceForEachInitial = mutableListOf<MutableMap<List<Long>, Long>>()
    initialSecretNumbers.forEach {
        val prices = generatePrices(it, 2000)
        val changes = prices.zipWithNext { a, b -> b - a }
        val sequencesOf4 = changes.windowed(4)
        val seqToPrice = mutableMapOf<List<Long>, Long>()
        for (index in 4 until prices.lastIndex) {
            val price = prices[index]
            seqToPrice.putIfAbsent(sequencesOf4[index - 4], price)
        }
        seqToPriceForEachInitial.add(seqToPrice)
    }

    var bestResult = Long.MIN_VALUE
    val allPossibleSequences = seqToPriceForEachInitial.flatMap { x -> x.keys }.toSet()
    allPossibleSequences.parallelStream().forEach { singleSequence ->
        val sum = seqToPriceForEachInitial.sumOf { seqToPriceForSingleInput -> seqToPriceForSingleInput.getOrDefault(singleSequence, 0) }
        bestResult = max(sum, bestResult)
    }
    return bestResult
}

fun getNextSecret(secret: Long): Long {
    var result = secret
    result = (result xor (result * 64)) % 16777216
    result = (result xor (result / 32)) % 16777216
    result = (result xor (result * 2048)) % 16777216
    return result
}