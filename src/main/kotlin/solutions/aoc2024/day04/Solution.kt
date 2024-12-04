package solutions.aoc2024.day04

import utils.Resources


fun main() {
    val inputLines = Resources.getLines(2024, 4)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    var grid = inputLines.map { it.toCharArray() }
    val rows = grid.size
    val cols = grid[0].size

    fun countSubstringOccurrencesRegex(string: String, substring: String): Int {
        return Regex(Regex.escape(substring)).findAll(string).count()
    }

    var result = 0
    // horizontal
    inputLines.forEach {
        result += countSubstringOccurrencesRegex(it, "XMAS")
        result += countSubstringOccurrencesRegex(it.reversed(), "XMAS")
    }

    // vertical
    for (i in 0 until cols) {
        val verticalLine = inputLines.mapNotNull { if (i < it.length) it[i].toString() else null }.joinToString("")
        result += countSubstringOccurrencesRegex(verticalLine, "XMAS")
        result += countSubstringOccurrencesRegex(verticalLine.reversed(), "XMAS")
    }

    // diag 1
    for (d in 0 until rows + cols - 1) {
        val diagonal = StringBuilder()
        for (i in 0 until rows) {
            val j = d - i
            if (j in 0 until cols && i < grid.size && j < grid[i].size) {
                diagonal.append(grid[i][j])
            }
        }
        result += countSubstringOccurrencesRegex(diagonal.toString(), "XMAS")
        result += countSubstringOccurrencesRegex(diagonal.toString().reversed(), "XMAS")
    }

    // diag 2
    for (d in 0 until rows + cols - 1) {
        val diagonal = StringBuilder()
        for (i in 0 until rows) {
            val j = d + i - (cols - 1)
            if (j in 0 until cols && i < grid.size && j < grid[i].size) {
                diagonal.append(grid[i][j])
            }
        }
        result += countSubstringOccurrencesRegex(diagonal.toString(), "XMAS")
        result += countSubstringOccurrencesRegex(diagonal.toString().reversed(), "XMAS")
    }
    return result
}

fun part2(inputLines: List<String>): Int {

    var grid = inputLines.map { it.toCharArray() }

    fun getDiagonalsFromSubarr(array: List<CharArray>, x: Int, y: Int): Pair<String, String>? {
        if (x - 1 < 0 || x + 1 >= array.size || y - 1 < 0 || y + 1 >= array[0].size) {
            return null
        }
        val topLeftToBottomRight = StringBuilder()
        val topRightToBottomLeft = StringBuilder()
        for (i in -1..1) {
            val row = x + i
            val colLeft = y + i
            val colRight = y - i
            if (row in array.indices) {
                if (colLeft in array[row].indices) {
                    topLeftToBottomRight.append(array[row][colLeft])
                }
                if (colRight in array[row].indices) {
                    topRightToBottomLeft.append(array[row][colRight])
                }
            }
        }
        return Pair(topLeftToBottomRight.toString(), topRightToBottomLeft.toString())
    }

    var result = 0
    for (y in grid.indices) {
        for (x in 0 until grid[0].size) {
            val q = getDiagonalsFromSubarr(grid, x, y)
            if (q != null) {
                val first = q.first
                val second = q.second
                if ((first == "MAS" || first == "SAM") && ((second == "MAS" || second == "SAM"))) {
                    result++
                }
            }
        }
    }
    return result
}