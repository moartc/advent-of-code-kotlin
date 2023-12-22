package solutions.aoc2023.day21

import utils.Resources
import utils.algorithms.withoutDiagonal

fun main() {

    val inputLine = Resources.getLines(2023, 21)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine, 26501365)}")
}

// TODO refactor
fun part2(input: List<String>, numOfSteps: Int): Any {

    fun locBfs(
        grid: List<List<Char>>,
        possibleMoves: Array<Pair<Int, Int>>,
        start: Pair<Pair<Int, Int>, Pair<Int, Int>>,
    ): Any {
        var step = 0
        fun mapMove(curr: Pair<Pair<Int, Int>, Pair<Int, Int>>, move: Pair<Int, Int>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
            val possibleY = (curr.first.first + move.first) % grid.size
            val possibleX = (curr.first.second + move.second) % grid[0].size
            val nextX = if (possibleX == -1) grid[0].lastIndex else possibleX
            val nextY = if (possibleY == -1) grid.lastIndex else possibleY
            val inGrid = nextY to nextX
            val realPosY = (curr.second.first + move.first)
            val realPosX = (curr.second.second + move.second)

            return inGrid to (realPosY to realPosX)
        }

        fun getNext(current: Pair<Pair<Int, Int>, Pair<Int, Int>>) = possibleMoves
            .map { mapMove(current, it) }
            .filter { n -> grid[n.first.first][n.first.second] in ".S" }

        var queue: LinkedHashSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>
        var prevPl = 0
        var prevDiff = 0
        val evenPoints = LinkedHashSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        val oddPoints = LinkedHashSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        var prevDiffDiff = 0
        evenPoints.add(start)
        while (true) {
            if (step % 2 == 0) {
                queue = evenPoints
            } else {
                queue = oddPoints
            }

            queue.forEach { s ->
                val nextMoves = getNext(s)
                nextMoves.forEach { m ->
                    if (step % 2 == 0) {
                        if (!oddPoints.contains(m)) {
                            oddPoints.add(m)
                        }
                    } else {
                        if (!evenPoints.contains(m)) {
                            evenPoints.add(m)
                        }
                    }
                }
            }

            if (step % grid.size == start.first.first) {
                val currPl = queue.map { it.second }.size
                val diff = currPl - prevPl
                prevPl = currPl
                if (prevDiffDiff == diff - prevDiff) {
                    var currentDiff = diff.toLong()
                    var newValStart = currPl.toLong()
                    while (step < numOfSteps) {
                        step += grid.size
                        currentDiff += prevDiffDiff.toLong()
                        newValStart += currentDiff
                    }
                    return newValStart
                }
                prevDiffDiff = diff - prevDiff
                prevDiff = diff
            }
            step++
        }
    }

    val map = input.map { it.toList() }
    val start = input.indices.flatMap { y -> input.indices.map { x -> y to x } }.first { p -> map[p.first][p.second] == 'S' }
    val locBfs = locBfs(map, withoutDiagonal, start to start)
    return locBfs
}

fun part1(input: List<String>): Int {


    fun locBfs(
        grid: List<List<Char>>,
        possibleMoves: Array<Pair<Int, Int>>,
        start: Pair<Int, Int>,
    ): Set<Pair<Int, Int>> {

        val sizeY = grid.size - 1
        val sizeX = grid[0].size - 1

        var step = 0

        fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
            return !(nextPos.first < 0 || nextPos.first > sizeY || nextPos.second < 0 || nextPos.second > sizeX)
        }

        fun getNext(current: Pair<Int, Int>) = possibleMoves
            .map { move -> current.first + move.first to current.second + move.second }
            .filter { n -> isPosValid(n) && grid[n.first][n.second] in ".S" }

        var queue = LinkedHashSet<Pair<Int, Int>>()
        queue.add(start)
        while (queue.isNotEmpty()) {
            if (step == 64) {
                return queue.toSet()
            }
            step++

            val newQueue = LinkedHashSet<Pair<Int, Int>>()
            queue.forEach { s ->
                val nextMoves = getNext(s)
                newQueue.addAll(nextMoves)
            }
            queue = newQueue
        }
        return emptySet()

    }

    val map = input.map { it.toList() }
    val start = input.indices.flatMap { y -> input.indices.map { x -> y to x } }.first { p -> map[p.first][p.second] == 'S' }
    val locBfs = locBfs(map, withoutDiagonal, start)
    return locBfs.size
}
