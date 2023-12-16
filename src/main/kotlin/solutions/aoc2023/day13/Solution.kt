package solutions.aoc2023.day13

import utils.Resources
import utils.splitOnEmpty

fun main() {

    val inputLine = Resources.getLines(2023, 13)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: List<String>): Int {

    val splitOnEmpty = input.splitOnEmpty()

    var totRow = 0
    var totCol = 0
    splitOnEmpty.forEach {
        val toTypedArray = it.map { it.toCharArray() }.toTypedArray()
        val checkCol = findCol(toTypedArray, -1)
        if (checkCol != -1) {
            totCol += checkCol
        }
        val checkRow = findRow(toTypedArray, -1)
        if (checkRow != -1) {
            totRow += checkRow
        }
    }
    return totRow * 100 + totCol
}


fun part2(input: List<String>): Int {

    val splitOnEmpty = input.splitOnEmpty()

    var totRow = 0
    var totCol = 0
    splitOnEmpty.forEach {
        val matrix = it.map { it.toCharArray() }.toTypedArray()
        val checkCol = findCol(matrix, -1)
        if (checkCol != -1) {
            val rowPart2 = find(matrix, -2, ::findRow)
            if (rowPart2 != -1) {
                totRow += rowPart2
            } else {
                totCol += find(matrix, checkCol, ::findCol)
            }
        }
        val checkRow = findRow(matrix, -1)
        if (checkRow != -1) {
            val colPart2 = find(matrix, -2, ::findCol)
            if (colPart2 != -1) {
                totCol += colPart2
            } else {
                totRow += find(matrix, checkRow, ::findRow)
            }
        }
    }
    return totRow * 100 + totCol
}

fun findRow(pattern: Array<CharArray>, diffThan: Int): Int {

    m@ for (y in 0..<pattern.indices.last) {
        for (move in 0..y) {
            val secondRow = y + 1 + move
            if (secondRow == pattern.indices.last + 1) {
                if (y + 1 == diffThan) {
                    continue@m
                }
                return y + 1
            }
            val firstRow = y - move
            if (!pattern[firstRow].contentEquals(pattern[secondRow])) {
                continue@m
            }
        }
        if (y + 1 == diffThan) {
            continue
        }
        return y + 1
    }
    return -1
}


fun findCol(newMap: Array<CharArray>, diffThan: Int): Int {

    m@ for (y in 0..<newMap[0].indices.last) {
        for (move in 0..y) {
            val sCol = y + 1 + move
            if (sCol == newMap[0].indices.last + 1) {
                if (y + 1 == diffThan) {
                    continue@m
                }
                return y + 1
            }
            val fCol = y - move
            if (newMap.any { line -> line[fCol] != line[sCol] }) {
                continue@m
            }
        }
        if (y + 1 == diffThan) {
            continue
        }
        return y + 1
    }
    return -1
}

fun find(matrix: Array<CharArray>, diffThan: Int, findMethod: (matrix: Array<CharArray>, diffThan: Int) -> Int): Int {

    for (row in matrix.indices) {
        for (col in matrix[0].indices) {
            if (matrix[row][col] == '.') {
                matrix[row][col] = '#'
            } else {
                matrix[row][col] = '.'
            }
            val found = findMethod(matrix, diffThan)
            if (found != -1) {
                // revert before return, it will be reused
                if (matrix[row][col] == '.') {
                    matrix[row][col] = '#'
                } else {
                    matrix[row][col] = '.'
                }
                return found
            }
            // revert before next iteration
            if (matrix[row][col] == '.') {
                matrix[row][col] = '#'
            } else {
                matrix[row][col] = '.'
            }
        }
    }
    return -1
}


