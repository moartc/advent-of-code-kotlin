package solutions.aoc2024.day13

import utils.Resources
import utils.collections.extensions.splitOnEmpty
import utils.math.solveCramerLong
import utils.parser.getLongs

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val all = inputLines.splitOnEmpty().map { line ->
        line.map { i ->
            val ints = i.getLongs()
            ints[0] to ints[1]
        }
    }

    var result = 0L
    all.forEach { threeElem ->
        val f = longArrayOf(threeElem[0].first, threeElem[1].first)
        val s = longArrayOf(threeElem[0].second, threeElem[1].second)
        val const = longArrayOf(threeElem[2].first, threeElem[2].second)

        val solveCramerLong = solveCramerLong(arrayOf(f, s), const)

        if (solveCramerLong != null) {
            result += (3 * solveCramerLong[0] + solveCramerLong[1])
        }
    }
    return result
}

fun part2(inputLines: List<String>): Long {


    val all = inputLines.splitOnEmpty().map { line ->
        line.mapIndexed { index, s ->
            val ints = s.getLongs()
            if (index == 2) {
                10000000000000 + ints[0] to 10000000000000 + ints[1]
            } else {
                ints[0] to ints[1]
            }
        }
    }

    var result = 0L
    all.forEach { threeElem ->
        val f = longArrayOf(threeElem[0].first, threeElem[1].first)
        val s = longArrayOf(threeElem[0].second, threeElem[1].second)
        val const = longArrayOf(threeElem[2].first, threeElem[2].second)

        val solveCramerLong = solveCramerLong(arrayOf(f, s), const)

        if (solveCramerLong != null) {
            result += (3 * solveCramerLong[0] + solveCramerLong[1])
        }
    }
    return result
}

