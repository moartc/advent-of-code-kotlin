@file:Suppress("UNUSED_EXPRESSION")

package solutions.aoc2023.day23

import utils.Resources
import utils.algorithms.withoutDiagonal
import utils.parser.getInts

fun main() {

    val inputLine =
//        Resources.getLines(2023, 23)
        Resources.getLinesExample(2023, 23)
//    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part2(input: List<String>): Long {


    val map = input.map { it.toList() }.toList()

    val longestVisitedTime


    fun can23(currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>): Boolean {
        val nextChar = map[nextPos.first][nextPos.second]
        val currChar = map[currentPos.first][currentPos.second]
        val (cy, cx) = currentPos
        val (ny, nx) = nextPos
        if  (nextChar in ".^v<>") {
            return true
        } else {
            return false
        }
    }


    var bestFound : Int= 0


    tailrec fun <T> bfs23(
        grid: List<List<T>>,
        possibleMoves: Array<Pair<Int, Int>>,
        current: Pair<Int, Int>,
        end: Pair<Int, Int>,
        canVisit: (currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>) -> Boolean,
        visited: Array<BooleanArray>,
        step: Int,
    ): Int {


        val sizeY = grid.size - 1
        val sizeX = grid[0].size - 1

        fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
            return !(nextPos.first < 0 || nextPos.first > sizeY || nextPos.second < 0 || nextPos.second > sizeX)
        }
        visited[current.first][current.second]=true

        if (longestVisitedTime[current.first][current.second] < step) {
            longestVisitedTime[current.first][current.second] = step
        } else {
            return -1
        }


        val toVisit = mutableListOf<Pair<Int, Int>>()

        for (move in possibleMoves) {
            val nextY = current.first + move.first
            val nextX = current.second + move.second
            val nextPos = nextY to nextX
            if (isPosValid(nextPos) && canVisit(current, nextPos)
                && !visited[nextY][nextX] && longestVisitedTime[nextY][nextX] < step+1
            ) {
                if (nextY to nextX == end) {
                    step.log("found")
                    if (step > bestFound) {
                        step.log("foundbb")
                        bestFound = step
                        step.log("found best:")
                    }
                } else {
                    toVisit.add(nextY to nextX)
                }
            }
        }
        val best  = if (toVisit.size == 1) {
            bfs23(map, withoutDiagonal, toVisit[0], end, ::can23, visited, (step + 1))
        } else if(toVisit.size > 0) {
            toVisit.maxOf { tv ->
                bfs23(map, withoutDiagonal, tv, end, ::can23, visited.clone(), (step + 1))
            }
        } else {
            -1
        }
        return best
    }


    val end = map.lastIndex to map[map.lastIndex].lastIndex - 1
    val bfs23 = bfs23(map, withoutDiagonal, 0 to 1, end, ::can23, Array(map.size) { BooleanArray(map[0].size) }, 1)



    bestFound.log("some res")


    val q = 12

    return 12L
}

fun part1(input: List<String>): Long {


    val map = input.map { it.toList() }.toList()


    tailrec fun can23(currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>): Boolean {
        val nextChar = map[nextPos.first][nextPos.second]
        val currChar = map[currentPos.first][currentPos.second]
        val (cy, cx) = currentPos
        val (ny, nx) = nextPos
        if (currChar == '>') {
            if (cy == ny && nx == cx + 1) {
                return true

            } else {
                return false
            }
        } else if (currChar == '<') {
            if (ny == cy && nx == cx - 1) {
                return true
            } else {
                return false
            }
        } else if (currChar == 'v') {
            if (ny == cy + 1 && nx == cx) {
                return true
            } else {
                return false
            }
        } else if (currChar == '^') {
            if (ny == cy - 1 && nx == cx) {
                return true

            } else {
                return false
            }
        } else if (nextChar in ".^v<>") {
            return true
        } else {
            return false
        }
    }


    var bestFound = 0


    fun <T> bfs23(
        grid: List<List<T>>,
        possibleMoves: Array<Pair<Int, Int>>,
        current: Pair<Int, Int>,
        end: Pair<Int, Int>,
        canVisit: (currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>) -> Boolean,
        collected: MutableList<Pair<Int, Int>>,
    ) {

        val sizeY = grid.size - 1
        val sizeX = grid[0].size - 1

        fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
            return !(nextPos.first < 0 || nextPos.first > sizeY || nextPos.second < 0 || nextPos.second > sizeX)
        }


        collected.add(current)

        for (move in possibleMoves) {
            val nextY = current.first + move.first
            val nextX = current.second + move.second
            val nextPos = nextY to nextX
            if (isPosValid(nextPos) && canVisit(current, nextPos) && !collected.contains(nextPos)) {
                if (nextY to nextX == end) {
                    collected.size.log("found")
                    if (collected.size > bestFound) {
                        collected.size.log("foundbb")
                        bestFound = collected.size
                        collected.log("found best:")
                    }
                } else {
                    bfs23(map, withoutDiagonal, nextPos, end, ::can23, collected.toMutableList())
                }
            }
        }
    }


    val end = map.lastIndex to map[map.lastIndex].lastIndex - 1
    val bfs23 = bfs23(map, withoutDiagonal, 0 to 1, end, ::can23, mutableListOf())

    bestFound.log("some res")


    val q = 12

    return 12L
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



