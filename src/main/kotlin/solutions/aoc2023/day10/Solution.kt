package solutions.aoc2023.day10

import utils.Resources
import utils.algorithms.bfsWithPath
import utils.algorithms.withoutDiagonal

// first 6:57

fun main() {

    val inputLine =
        Resources.getLines(2023, 10)
//        Resources.getLinesExample(2023, 10)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")

}


fun part1(input: List<String>): Int {


    var start = 0 to 0
    val toList = input.map { it }.toList()
    val map = input.map { it -> it.toList() }.toList()
    for (y in input.indices) {
        for (x in input[y].indices) {
            if (input[y].get(x) == 'S') {
                start = y to x
            }
        }
    }

    start.log("start")

    fun up(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
        val (cY, cX) = p1
        val (nY, nX) = p2
        return cY - 1 == nY && cX == nX
    }

    fun down(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
        val (cY, cX) = p1
        val (nY, nX) = p2
        return cY + 1 == nY && cX == nX
    }

    fun left(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
        val (cY, cX) = p1
        val (nY, nX) = p2
        return cY == nY && cX - 1 == nX
    }

    fun right(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
        val (cY, cX) = p1
        val (nY, nX) = p2
        return cY == nY && cX + 1 == nX
    }

    fun canVisitLoc(currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>): Boolean {

        var next = map[nextPos.first][nextPos.second]
        if (next == 'J' && right(currentPos, nextPos)) {
            return true
        } else if (next == 'J' && down(currentPos, nextPos)) { // up
            return true
        } else if (next == 'F' && left(currentPos, nextPos)) {
            return true
        } else if (next == 'F' && up(currentPos, nextPos)) { // up
            return true
        } else if (next == '|' && up(currentPos, nextPos)) {
            return true
        } else if (next == '|' && down(currentPos, nextPos)) {
            return true
        } else if (next == '-' && left(currentPos, nextPos)) {
            return true
        } else if (next == '-' && right(currentPos, nextPos)) {
            return true
        } else if (next == 'L' && down(currentPos, nextPos)) {
            return true
        } else if (next == 'L' && left(currentPos, nextPos)) {
            return true
        } else if (next == '7' && up(currentPos, nextPos)) {
            return true
        } else if (next == '7' && right(currentPos, nextPos)) {
            return true
        }
        return false

    }

    var max = 0
    for (y in input.indices) {
        for (x in input[y].indices) {
            val bfsWithPath = bfsWithPath(map, withoutDiagonal, start, y to x, ::canVisitLoc);
            if (max < bfsWithPath.first) {
                max = bfsWithPath.first
            }
//            bfsWithPath.log("for $y and $x is")

        }
    }

    max.log("answer")

    return 12
}

var bestFound = mutableSetOf<Pair<Int, Int>>()


fun part2(input: List<String>): Int {

    var start = 0 to 0
    val map = input.map { it -> it.toList() }.toList()
    for (y in input.indices) {
        for (x in input[y].indices) {
            if (input[y].get(x) == 'S') {
                start = y to x
            }
        }
    }

    start.log("start")

    fun up(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
        val (cY, cX) = p1
        val (nY, nX) = p2
        return cY - 1 == nY && cX == nX
    }

    fun down(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
        val (cY, cX) = p1
        val (nY, nX) = p2
        return cY + 1 == nY && cX == nX
    }

    fun left(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
        val (cY, cX) = p1
        val (nY, nX) = p2
        return cY == nY && cX - 1 == nX
    }

    fun right(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
        val (cY, cX) = p1
        val (nY, nX) = p2
        return cY == nY && cX + 1 == nX
    }

    fun canVisitLoc(currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>): Boolean {

        var next = map[nextPos.first][nextPos.second]
        if (next == 'J' && right(currentPos, nextPos)) {
            return true
        } else if (next == 'J' && down(currentPos, nextPos)) { // up
            return true
        } else if (next == 'F' && left(currentPos, nextPos)) {
            return true
        } else if (next == 'F' && up(currentPos, nextPos)) { // up
            return true
        } else if (next == '|' && up(currentPos, nextPos)) {
            return true
        } else if (next == '|' && down(currentPos, nextPos)) {
            return true
        } else if (next == '-' && left(currentPos, nextPos)) {
            return true
        } else if (next == '-' && right(currentPos, nextPos)) {
            return true
        } else if (next == 'L' && down(currentPos, nextPos)) {
            return true
        } else if (next == 'L' && left(currentPos, nextPos)) {
            return true
        } else if (next == '7' && up(currentPos, nextPos)) {
            return true
        } else if (next == '7' && right(currentPos, nextPos)) {
            return true
        } else if (next == 'S') {
            return true
        }
        return false
    }

    // collect again
    tailrec fun recCollect(
        map: List<List<Char>>,
        start: Pair<Int, Int>,
        end: Pair<Int, Int>,
        set: MutableSet<Pair<Int, Int>>,
        secTime: Boolean
    ) {

        fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
            return !(nextPos.first < 0 || nextPos.first >= map.size || nextPos.second < 0 || nextPos.second >= map[0].size)
        }
        if (start == end && secTime && bestFound.size < set.size) {
            bestFound = set
            return
        }
        if (set.contains(start)) {
            return
        }
        set.add(start)


        val possibleMoves = mutableListOf<Pair<Int, Int>>()
        for (move in arrayOf(0 to -1, -1 to 0, 1 to 0, 0 to 1)) {
            val nextY = start.first + move.first
            val nextX = start.second + move.second
            if (isPosValid(nextY to nextX) && canVisitLoc(
                    start,
                    nextY to nextX
                ) && ((nextY to nextX) == end || !set.contains(nextY to nextX))
            ) {
                possibleMoves.add(nextY to nextX)
            }
        }
        if (possibleMoves.size == 1) {
            recCollect(map, possibleMoves[0], end, set, true)
        } else {
            possibleMoves.forEach {
                recCollect(map, it, end, set.toMutableSet(), true)
            }
        }
    }



    recCollect(map, start, start, mutableSetOf(), false)

    bestFound.log("best from rec")

    val notVisited = HashSet<Pair<Int, Int>>()
    var countNotVisited = 0
    for (y in input.indices) {
        for (x in input[y].indices) {
            if (!bestFound.contains(y to x)) {
                notVisited.add(y to x)
                countNotVisited++
//                println("not visited ${y to x} = ${map[y][x]}")

            }
        }
    }


    // https://stackoverflow.com/questions/217578/how-can-i-determine-whether-a-2d-point-is-within-a-polygon
    fun IsPointInPolygon(testPoint: Pair<Int, Int>, polygon: List<Pair<Int, Int>>): Boolean {
        var result = false;
        var j = polygon.size - 1;
        for (i in polygon.indices) {
            if (polygon[i].first < testPoint.first && polygon[j].first >= testPoint.first || polygon[j].first < testPoint.first && polygon[i].first >= testPoint.first) {
                if (polygon[i].second + (testPoint.first - polygon[i].first) / (polygon[j].first - polygon[i].first) * (polygon[j].second - polygon[i].second) < testPoint.second) {
                    result = !result;
                }
            }
            j = i
        }
        return result;
    }

    var resultCtr = -1
    var resultCtr2 = 0
    for ((y, x) in notVisited) {

        println("checking ($y, $x)")

        if (IsPointInPolygon(y to x, bestFound.toList())) {
            resultCtr2++
        }
    }
    // check why it's = correct result -1
    resultCtr2.log("coutner2 = ")



    resultCtr.log("result ctr")


    countNotVisited.log("not visited ctr")

    var totMapSize = (map.size * map[0].size)
    totMapSize.log("total map size")
    return 12

}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }


// 82 wrong
// 304 wrong
// 13 13
// 179 wrong

//part2
// 264 wrong
// 452 wrong


/*
change recursive function
 */