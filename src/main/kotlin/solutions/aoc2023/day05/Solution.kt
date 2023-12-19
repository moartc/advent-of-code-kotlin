package solutions.aoc2023.day05

import utils.Resources
import utils.collections.extensions.splitOn
import utils.parser.getLongs


fun main() {

    val inputLine = Resources.getLines(2023, 5)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")

}

fun part1(input: List<String>): Long {
    val splitOnEmpty = input.splitOn { it.isEmpty() }
    val seeds = splitOnEmpty[0][0].getLongs()
    val mappings = splitOnEmpty.drop(1).map { it.drop(1).map(String::getLongs) }
    return seeds.minOf { seed -> mappings.fold(seed) { x, y -> findMapping(y, x) } }
}

fun findMapping(mapping: List<List<Long>>, valToCheck: Long): Long {
    val range = mapping.firstOrNull { ranges -> valToCheck in ranges[1]..<ranges[1] + ranges[2] } ?: return valToCheck
    return range[0] + valToCheck - range[1]
}

/*
Part 2 is solved in the same way as part 1, despite quite slow performance.
The most problems were caused by a bug in line 24. in the findMapping function.
Instead of '..<' I had '..'.
Despite this bug, the program produced correct answer for the example and my own input data in part 1.
It also gave the correct answer for the test data in part 2, but the wrong answer (difference of 1 as it turned out) for my data in part 2.
I searched for this error for 1.5 hour, mainly around the creation of ranges because I assumed that the mapping itself must be correct,
since so far it has given good results...
 */
fun part2(input: List<String>): Long {

    val splitOnEmpty = input.splitOn { it.isEmpty() }
    val seeds = splitOnEmpty[0][0].getLongs()
    // droop 1 which contains seeds and drop 1 in each part because it contains a title
    val mappings = splitOnEmpty.drop(1).map { it.drop(1).map(String::getLongs) }

    var globalMin = Long.MAX_VALUE
    val chunks = seeds.chunked(2).map { it[0]..<it[0] + it[1] }

    chunks.parallelStream().forEach {
        var minInChunk = Long.MAX_VALUE
        it.forEach { seed ->
            val loc = mappings.fold(seed) { x, y -> findMapping(y, x) }
            minInChunk = Math.min(loc, minInChunk)
        }
        globalMin = Math.min(minInChunk, globalMin)
    }
    return globalMin
}