package solutions.aoc2021.day17

import utils.Resources
import utils.parser.getInts

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val inp = inputLines[0].getInts()
    val x0 = inp[0]
    val x1 = inp[1]
    val y0 = inp[2]
    val y1 = inp[3]
    var maxFound = Integer.MIN_VALUE
    for (x in 1..100) {
        for (y in 0..100) {
            var curr = 0 to 0
            var step = 0
            var maxYOnPath = 0
            while (curr.first <= x1 && curr.second >= y0) {
                curr = getNext(curr, x, y, step)
                if (curr.second > maxYOnPath) {
                    maxYOnPath = curr.second
                }
                if (curr.first == 0) {
                    break
                }
                step++
                if (curr.first in x0..x1 && curr.second >= y0 && curr.second <= y1) {
                    if (maxYOnPath > maxFound) {
                        maxFound = maxYOnPath
                        break
                    }
                }
            }
        }
    }
    return maxFound
}

fun part2(inputLines: List<String>): Any {
    val inp = inputLines[0].getInts()
    val x0 = inp[0]
    val x1 = inp[1]
    val y0 = inp[2]
    val y1 = inp[3]
    var solCtr = 0
    // x is always positive
    for (x in 1..1000) {
        for (y in -200..1000) {
            var curr = 0 to 0
            var step = 0
            var maxYOnPath = 0
            while (curr.first <= x1 && curr.second >= y0) {
                curr = getNext(curr, x, y, step)
                if (curr.second > maxYOnPath) {
                    maxYOnPath = curr.second
                }
                if (curr.first == 0) {
                    break
                }
                step++
                if (curr.first in x0..x1 && curr.second >= y0 && curr.second <= y1) {
                    solCtr++
                    break
                }
            }
        }
    }
    return solCtr
}


fun getNext(curr: Pair<Int, Int>, x: Int, y: Int, num: Int): Pair<Int, Int> {
    val newX = curr.first + if (x - num > 0) x - num else 0
    val newY = curr.second + y + (-1 * num)
    return newX to newY
}