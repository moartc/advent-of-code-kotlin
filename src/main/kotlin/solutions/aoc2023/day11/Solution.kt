package solutions.aoc2023.day11

import utils.Resources
import utils.algorithms.bfsWithPath
import utils.algorithms.withoutDiagonal
import utils.allUniqueCombinations

fun main() {

    val inputLine = Resources.getLines(2023, 11)
    println("part1 = ${solve(inputLine, 2)}")
    println("part2 = ${solve(inputLine, 1000000)}")
}

fun solve(input: List<String>, increaseBy: Int): Long {

    val chars = input.map { l -> l.toMutableList() }.toMutableList()

    input.forEachIndexed { index, str ->
        if (str.all { x -> x == '.' }) {
            chars[index] = "E".repeat(input[0].length).toMutableList()
        }
    }

    fun isEmptyColumn(col: Int, list: List<String>): Boolean = list.none { line -> line[col] != '.' && line[col] != 'E' }

    input[0].indices.forEach { col ->
        if (isEmptyColumn(col, input)) {
            chars.indices.forEach { row ->
                chars[row][col] = 'E'
            }
        }
    }

    val positions = chars.flatMapIndexed { y, ch -> ch.mapIndexed { x, c -> (y to x) to c } }
        .filter { p -> p.second == '#' }
        .map { it.first }

    val allUniqueCombinations = allUniqueCombinations(positions)

    return allUniqueCombinations.sumOf { (i1, i2) ->
        val bfs = bfsWithPath(chars, withoutDiagonal, i1, i2) { _, _ -> true }
        var bfsRes = bfs.first.toLong()
        for (pair in bfs.second) {
            if (chars[pair.first][pair.second] == 'E') {
                bfsRes += (increaseBy - 1)
            }
        }
        bfsRes
    }
}
