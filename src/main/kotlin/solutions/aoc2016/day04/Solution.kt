package solutions.aoc2016.day04

import utils.Resources
import utils.collections.extensions.toFrequencyMap

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    var res = 0
    main@ for (inputLine in inputLines) {
        val i = inputLine.indexOf("[")
        val checkSum = inputLine.substring(i).drop(1).dropLast(1)
        val partWithoutChecksum = inputLine.substring(0, i)
        val withoutSectorId = partWithoutChecksum.substring(0, partWithoutChecksum.indexOfLast { x -> x == '-' })
        val sectorId = partWithoutChecksum.split("-").last().toInt()

        val frequencyMap = withoutSectorId.filter { x -> x != '-' }.toList().toFrequencyMap()

        fun getXTopPairs(map: Map<Char, Long>, x: Int): Map<Char, Long> {
            val sortedList = map.entries.sortedByDescending { it.value }
            val result = mutableListOf<Pair<Char, Long>>()
            var count = 0
            for (entry in sortedList) {
                if (count < x || (result.isNotEmpty() && result.last().second == entry.value)) {
                    result.add(entry.toPair())
                    if (count < x) {
                        count++
                    }
                } else {
                    break
                }
            }
            return result.toMap()
        }

        val mostCommonSize = checkSum.length
        val topChars = getXTopPairs(frequencyMap, mostCommonSize).toMutableMap()
        for (c in checkSum) {
            val freq = topChars[c]
            if (freq == null) {
                continue@main
            }
            if (topChars.values.any { x -> x > freq }) {
                // there is something greater
                continue@main
            } else {
                topChars.remove(c)
            }
        }
        res += sectorId

    }

    return res
}

fun part2(inputLines: List<String>): Int {

    for (inputLine in inputLines) {
        val i = inputLine.indexOf("[")
        val partWithoutChecksum = inputLine.substring(0, i)
        val withoutSectorId = partWithoutChecksum.substring(0, partWithoutChecksum.indexOfLast { x -> x == '-' })
        val sectorId = partWithoutChecksum.split("-").last().toInt()

        fun rotateString(input: String, x: Int): String {
            return input.map { char ->
                val base = if (char.isLowerCase()) 'a' else 'A'
                ((char - base + x) % 26 + base.code).toChar()
            }.joinToString("")
        }

        val rotated = withoutSectorId.split("-").joinToString(" ") { rotateString(it, sectorId) }
        if (rotated.contains("northpole")) {
            return sectorId
        }
    }
    return -1
}
