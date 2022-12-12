package solutions.aoc2022.day12

import utils.*

fun main() {
    val input = Resources.getLines(2022, 12)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {

    var start = -1 to -1
    var end = -1 to -1

    val grid = input.map { it.toCharArray() }
        .mapIndexed { i, chars ->
            chars.mapIndexed { j, c ->
                when (c) {
                    'S' -> 'a'.also { start = i to j }
                    'E' -> 'z'.also { end = i to j }
                    else -> c
                }
            }
        }

    return bfs(grid.map { it.toList() }, withoutDiagonal, start, end) { f, s -> grid[f.first][f.second] + 1 >= grid[s.first][s.second] }
}

fun part2(input: List<String>): Int {

    var end = -1 to -1
    val grid = input.map { it.toCharArray() }.mapIndexed { i, chars ->
        chars.mapIndexed { j, c ->
            when (c) {
                'S' -> 'a'
                'E' -> 'z'.also { end = i to j }
                else -> c
            }
        }
    }

    return grid.flatMapIndexed { i, chars -> chars.mapIndexed { j, c -> if (c == 'a') i to j else null } }.filterNotNull()
        .map { start ->
            bfs(grid.map { it.toList() }, withoutDiagonal, start, end) { f, s -> grid[f.first][f.second] + 1 >= grid[s.first][s.second] }
        }.filter { it != -1 }
        .min()
}


fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }