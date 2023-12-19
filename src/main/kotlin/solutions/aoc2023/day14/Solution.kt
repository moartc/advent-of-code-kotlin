package solutions.aoc2023.day14

import utils.Resources
import utils.array.extensions.deepCopy
import utils.array.extensions.rotateClockwise

fun main() {

    val inputLine = Resources.getLines(2023, 14)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: List<String>): Int {

    val initBoard = input.map { line -> line.toCharArray() }.toTypedArray()
    var prev: Array<CharArray>
    var newer = initBoard
    do {
        prev = newer.deepCopy()
        newer = singleMove(newer)
    } while (!eq(prev, newer))

    return calcRes(prev)
}


fun part2(input: List<String>): Int {

    val initBoard = input.map { q -> q.toCharArray() }.toTypedArray()
    var prev = initBoard
    val hash = mutableMapOf<List<String>, Int>()

    fun singleCycle(matrix: Array<CharArray>): Array<CharArray> {
        val north = fullMove(matrix)
        val setToWest = north.rotateClockwise()
        val moveWest = fullMove(setToWest)
        val rotateToSouth = moveWest.rotateClockwise()
        val moveSouth = fullMove(rotateToSouth)
        val rotateToEast = moveSouth.rotateClockwise()
        val moveEast = fullMove(rotateToEast)
        return moveEast.rotateClockwise()
    }

    repeat(1000000000) { it ->

        prev = singleCycle(prev)
        val map = prev.map { it.concatToString() }
        if (map in hash) {
            val length = it - hash[map]!!
            val toDo = (1000000000 - it - 1) % length
            repeat(toDo) {
                prev = singleCycle(prev)
            }
            return calcRes(prev)
        } else {
            hash[map] = it
        }
    }
    return -1
}

fun calcRes(prev: Array<CharArray>): Int {
    var ans = 0
    var multiplication = prev.indices.last + 1
    for (y in prev.indices) {
        for (x in prev.indices) {
            if (prev[y][x] == 'O') {
                ans += multiplication
            }
        }
        multiplication--
    }
    return ans
}

fun eq(first: Array<CharArray>, second: Array<CharArray>): Boolean {
    for (y in first.indices) {
        for (x in first[0].indices) {
            if (first[y][x] != second[y][x]) {
                return false
            }
        }
    }
    return true
}

fun singleMove(matrix: Array<CharArray>): Array<CharArray> {

    for ((indexRow, row) in matrix.withIndex()) {
        if (indexRow == 0) {
            matrix[indexRow] = matrix[indexRow] // first is initially the same
            continue
        }
        for ((indexC, char) in row.withIndex()) {
            if (char == '.' || char == '#') { // nothing to do
                matrix[indexRow][indexC] = char // set the same
                continue
            } else { // it is a rock
                if (matrix[indexRow - 1][indexC] == '.') { // can move up
                    matrix[indexRow - 1][indexC] = 'O'// move it
                    matrix[indexRow][indexC] = '.' // set empty
                }
            }
        }
    }
    return matrix
}

fun fullMove(initBoard: Array<CharArray>): Array<CharArray> {
    var prev = initBoard
    var newer = prev.deepCopy()
    do {
        prev = newer.deepCopy()
        newer = singleMove(newer)
    } while (!eq(prev, newer))
    return newer
}





