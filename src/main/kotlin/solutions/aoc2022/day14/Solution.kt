package solutions.aoc2022.day14

import utils.Resources
import utils.allPointsBetweenIntPairs
import utils.allPointsBetweenStringPairs

fun main() {

    val input = Resources.getLines(2022, 14)
    val pairs = input.flatMap { it ->
        it.split(" -> ").windowed(2).map { i ->
            i[0].split(",").let { it[0] to it[1] } to i[1].split(",").let { it[0] to it[1] }
        }
    }
    val borders = pairs.flatMap { pair -> allPointsBetweenStringPairs(pair.first, pair.second) }

    println("part1 = ${simulation(borders.toMutableSet())}")
    println("part2 = ${part2(borders.toMutableSet())}")
}

fun part2(borders: Set<Pair<Int, Int>>): Int {

    val maxY = borders.maxBy { it.second }.second
    val updated = borders + allPointsBetweenIntPairs(300 to maxY + 2, 700 to maxY + 2)
    return simulation(updated.toMutableSet())
}

fun simulation(borders: MutableSet<Pair<Int, Int>>): Int {
    val maxY = borders.maxBy { it.second }.second
    var counter = 0
    while (!borders.contains(500 to 0)) {
        var ball = 500 to 0
        var stop = false
        while (!stop && ball.second < maxY) {
            val next = ball.first to ball.second + 1
            if (borders.contains(next)) {
                if (!borders.contains(next.first - 1 to next.second)) {
                    ball = next.first - 1 to next.second
                } else if (!borders.contains(next.first + 1 to next.second)) {
                    ball = next.first + 1 to next.second
                } else {
                    borders.add(ball)
                    stop = true
                }
            } else {
                ball = next
            }
        }
        if (!stop) {
            return counter
        }
        counter += 1
    }
    return counter
}