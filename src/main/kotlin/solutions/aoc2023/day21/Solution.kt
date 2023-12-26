package solutions.aoc2023.day21

import utils.Resources
import utils.algorithms.withoutDiagonal

fun main() {

    val inputLine = Resources.getLines(2023, 21)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine, 26501365)}")
}

fun part1(input: List<String>): Int {

    val grid = input.map { it.toList() }
    val start = input.indices.flatMap { y -> input.indices.map { x -> y to x } }.first { p -> grid[p.first][p.second] == 'S' }

    fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
        return !(nextPos.first < 0 || nextPos.first > grid.lastIndex || nextPos.second < 0 || nextPos.second > grid[0].lastIndex)
    }

    fun getNext(current: Pair<Int, Int>) = withoutDiagonal
        .map { move -> current.first + move.first to current.second + move.second }
        .filter { n -> isPosValid(n) && grid[n.first][n.second] in ".S" }

    var step = 0
    var toVisit = LinkedHashSet<Pair<Int, Int>>()
    toVisit.add(start)
    while (toVisit.isNotEmpty()) {
        if (step == 64) {
            return toVisit.size
        }
        step++
        val newQueue = LinkedHashSet<Pair<Int, Int>>()
        toVisit.forEach { s ->
            val nextMoves = getNext(s)
            newQueue.addAll(nextMoves)
        }
        toVisit = newQueue
    }
    return -1
}


fun part2(input: List<String>, numOfSteps: Int): Any {


    val grid = input.map { it.toList() }
    val start = input.indices.flatMap { y -> input.indices.map { x -> y to x } }.first { p -> grid[p.first][p.second] == 'S' }

    var step = 0

    fun mapMove(curr: Pair<Int, Int>, move: Pair<Int, Int>): Pair<Int, Int> = curr.first + move.first to curr.second + move.second

    fun getNext(current: Pair<Int, Int>): List<Pair<Int, Int>> = withoutDiagonal
        .map { mapMove(current, it) }
        .filter { n ->
            grid[if (n.first < 0) (Math.floorMod(n.first, 131)) else n.first % 131][if (n.second < 0)
                (Math.floorMod(n.second, 131)) else n.second % 131] in ".S"
        }

    var queue: MutableSet<Pair<Int, Int>>
    var previousSize = 0
    var prevDiff = 0
    val evenPoints = mutableSetOf<Pair<Int, Int>>()
    val oddPoints = mutableSetOf<Pair<Int, Int>>()
    var prevDiffDiff = 0
    evenPoints.add(start)
    while (true) {
        queue = if (step % 2 == 0) {
            evenPoints
        } else {
            oddPoints
        }

        queue.forEach { s ->
            val nextMoves = getNext(s)
            nextMoves.forEach { m ->
                if (step % 2 == 0) {
                    oddPoints.add(m)
                } else {
                    evenPoints.add(m)
                }
            }
        }

        if (step % grid.size == start.first) {
            val currentSize = queue.size
            val diff = currentSize - previousSize
            previousSize = currentSize
            if (prevDiffDiff == diff - prevDiff) {
                var currentDiff = diff.toLong()
                var mutCurrSize = currentSize.toLong()
                while (step < numOfSteps) {
                    step += grid.size
                    currentDiff += prevDiffDiff.toLong()
                    mutCurrSize += currentDiff
                }
                return mutCurrSize
            }
            prevDiffDiff = diff - prevDiff
            prevDiff = diff
        }
        step++
    }
}
