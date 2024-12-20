package solutions.aoc2024.day20

import utils.Resources
import utils.algorithms.dijkstraGenWithPathHistory
import utils.collections.extensions.findPosition
import kotlin.math.abs

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val start = inputLines.findPosition('S')
    val dijkstraGen = dijkstraGenWithPathHistory(start, { _, _ -> 1 }, { p1 -> getNext(p1, inputLines) })
    val path = mutableListOf<Pair<Int, Int>>()
    path.add(start)
    dijkstraGen.second.keys.forEach { path.add(it) }

    val savedSteps = calculateStepsSaved(path, 2)

    return savedSteps.groupingBy { x -> x.third }.eachCount()
        .filter { x -> x.key > 100 }
        .values.sum()
}

fun part2(inputLines: List<String>): Int {

    val start = inputLines.findPosition('S')
    val dijkstraGen = dijkstraGenWithPathHistory(start, { _, _ -> 1 }, { p1 -> getNext(p1, inputLines) })
    val path = mutableListOf<Pair<Int, Int>>()
    path.add(start)
    dijkstraGen.second.keys.forEach { path.add(it) }

    val savedSteps = calculateStepsSaved(path, 20)

    return savedSteps.groupingBy { x -> x.third }.eachCount()
        .filter { x -> x.key > 100 }
        .values.sum()
}

fun getNext(pair: Pair<Int, Int>, inputLinesToUse: List<String>): List<Pair<Int, Int>> {
    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    val toRet = mutableListOf<Pair<Int, Int>>()
    for (dir in directions) {
        val nextPos = Pair(pair.first + dir.first, pair.second + dir.second)
        if (nextPos.first in inputLinesToUse.indices && nextPos.second in 0 until inputLinesToUse[0].length && inputLinesToUse[nextPos.first][nextPos.second] != '#') {
            toRet.add(nextPos)
        }
    }
    return toRet
}

fun calculateStepsSaved(points: List<Pair<Int, Int>>, maxDist: Int): List<Triple<Pair<Int, Int>, Pair<Int, Int>, Int>> {
    fun dist(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Int {
        return abs(p1.first - p2.first) + abs(p1.second - p2.second)
    }

    val savedSteps = mutableListOf<Triple<Pair<Int, Int>, Pair<Int, Int>, Int>>()
    for (i in points.indices) {
        for (j in i + 1 until points.size) {
            val p1 = points[i]
            val p2 = points[j]
            val dist = dist(p1, p2)
            if (dist in 2..maxDist) {
                val stepsSaved = j - i + 1 - dist
                if (stepsSaved > 0) {
                    savedSteps.add(Triple(p1, p2, stepsSaved))
                }
            }
        }
    }
    return savedSteps
}
