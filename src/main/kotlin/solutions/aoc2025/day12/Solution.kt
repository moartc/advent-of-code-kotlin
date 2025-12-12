package solutions.aoc2025.day12

import utils.Resources
import utils.collections.extensions.splitOnEmpty
import utils.parser.getInts

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2025, day)
    println("part1 = ${part1(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val split = inputLines.splitOnEmpty()
    val shapes = split.dropLast(1).map { miniList ->
        miniList.drop(1).map { x -> x.toCharArray() }.toTypedArray()
    }.toList()

    val regions = split.last().map { line ->
        val (size, list) = line.split(':')
        val (x, y) = size.getInts()
        val l = list.trim().getInts()
        (x to y) to l
    }

    return regions.sumOf { region ->
        val (p, list) = region
        val (x, y) = p

        val totalShapesArea = list.withIndex().sumOf { (shapeIdx, occ) ->
            occ * shapes[shapeIdx].sumOf { l -> l.count { it == '#' } }
        }
        // looks like I don’t even need to check if the shapes fit (at least for the main input)
        if (totalShapesArea <= x * y) 1L else 0
    }
}


