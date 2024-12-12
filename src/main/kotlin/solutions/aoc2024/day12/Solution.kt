package solutions.aoc2024.day12

import utils.Resources
import utils.algorithms.floodAll


fun main() {

    val inputLines = Resources.getLines(2024, 12)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val map = inputLines.map { it.toList() }
    fun countEdges(points: Set<Pair<Int, Int>>): Long {
        var result = 0L
        points.forEach { point ->
            result += 4
            result -= points.count { x ->
                x == point.first - 1 to point.second
                        || x == point.first + 1 to point.second
                        || x == point.first to point.second - 1
                        || x == point.first to point.second + 1
            }
        }
        return result
    }

    var answer = 0L
    val floodAll = floodAll(map)
    floodAll.forEach { (_, points) ->
        val countEdges = countEdges(points.toSet())
        answer += points.size * countEdges
    }

    return answer
}

fun part2(inputLines: List<String>): Int {
    val map = inputLines.map { it.toList() }

    fun countCorners(allPoints: Set<Pair<Int, Int>>): Int {

        fun countOuterCorner(p: Pair<Int, Int>): Int {
            // L - left, D- down, and so on...
            val LU = p.first - 1 to p.second - 1
            val LD = p.first + 1 to p.second - 1
            val RU = p.first - 1 to p.second + 1
            val RD = p.first + 1 to p.second + 1
            val U = p.first - 1 to p.second
            val D = p.first + 1 to p.second
            val L = p.first to p.second - 1
            val R = p.first to p.second + 1

            var corners = 0
            // outer corners
            if (allPoints.contains(LU) && (!allPoints.contains(L) && !allPoints.contains(U))) {
                corners++
            }
            if (allPoints.contains(RU) && (!allPoints.contains(R) && !allPoints.contains(U))) {
                corners++
            }
            if (allPoints.contains(LD) && (!allPoints.contains(L) && !allPoints.contains(D))) {
                corners++
            }
            if (allPoints.contains(RD) && (!allPoints.contains(R) && !allPoints.contains(D))) {
                corners++
            }
            // inner corners
            if (allPoints.contains(LU) && allPoints.contains(L) && allPoints.contains(U)) {
                corners++
            }
            if (allPoints.contains(RU) && allPoints.contains(R) && allPoints.contains(U)) {
                corners++
            }
            if (allPoints.contains(LD) && allPoints.contains(L) && allPoints.contains(D)) {
                corners++
            }
            if (allPoints.contains(RD) && allPoints.contains(R) && allPoints.contains(D)) {
                corners++
            }
            // corner cases part
            // for the LU / RD diag, detect from RU point
            if (allPoints.contains(L) && !allPoints.contains(LD) && allPoints.contains(D)) {
                corners += 2
            }
            /*
            XX   empty
            ___|.....
        empty  | XX
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA
             */
            // for the LD / UR diag, detect from LU point
            if (allPoints.contains(R) && !allPoints.contains(RD) && allPoints.contains(D)) {
                corners += 2
            }
            return corners
        }

        val minY = allPoints.minBy { it.first }.first - 1
        val maxY = allPoints.maxBy { it.first }.first + 1
        val minX = allPoints.minBy { it.second }.second - 1
        val maxX = allPoints.maxBy { it.second }.second + 1


        var result = 0
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val pointToCheck = y to x
                // check only if it's not part of area
                if (!allPoints.contains(pointToCheck)) {
                    result += countOuterCorner(pointToCheck)
                }
            }
        }
        return result
    }
    var result = 0
    val floodAll = floodAll(map)
    floodAll.forEach { (_, allPoints) ->
        result += countCorners(allPoints.toSet()) * allPoints.size

    }
    return result
}

