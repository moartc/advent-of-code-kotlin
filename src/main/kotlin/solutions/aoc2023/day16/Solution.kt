package solutions.aoc2023.day16

import utils.Resources

fun main() {

    val inputLine = Resources.getLines(2023, 16)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: List<String>): Int {

    val toTypedArray = input.map { it.toCharArray() }.toTypedArray()
    return solve(0 to 0, toTypedArray, ROT.RIGHT)
}

fun part2(input: List<String>): Int {

    val toTypedArray = input.map { it.toCharArray() }.toTypedArray()

    return maxOf(
        toTypedArray.indices.maxOf { solve(it to 0, toTypedArray, ROT.RIGHT) },
        toTypedArray.indices.maxOf { solve(it to toTypedArray[0].lastIndex, toTypedArray, ROT.LEFT) },
        toTypedArray[0].indices.maxOf { solve(toTypedArray.lastIndex to it, toTypedArray, ROT.UP) },
        toTypedArray[0].indices.maxOf { solve(0 to it, toTypedArray, ROT.DOWN) }
    )
}

fun solve(startingPos: Pair<Int, Int>, grid: Array<CharArray>, rot: ROT): Int {

    val alreadyVisited = mutableSetOf<Pair<Pair<Int, Int>, ROT>>()
    val energized = mutableSetOf<Pair<Int, Int>>()

    tailrec fun visit(current: Pair<Int, Int>, grid: Array<CharArray>, rot: ROT, visited: Array<BooleanArray>) {

        fun isPosValid(pos: Pair<Int, Int>): Boolean {
            return !(pos.first < 0 || pos.first > grid.size - 1 || pos.second < 0 || pos.second > grid[0].size - 1)
        }

        if (!isPosValid(current)) {
            return
        } else {
            if (!energized.contains(current)) {
                energized.add(current)
            }
        }
        if (alreadyVisited.contains(current to rot)) {
            return
        } else {
            alreadyVisited.add(current to rot)
        }

        val c = grid[current.first][current.second]
        when (c) {
            '.' -> { // continue walk
                when (rot) {
                    ROT.LEFT -> visit(current.first to current.second - 1, grid, rot, visited)
                    ROT.RIGHT -> visit(current.first to current.second + 1, grid, rot, visited)
                    ROT.UP -> visit(current.first - 1 to current.second, grid, rot, visited)
                    else -> visit(current.first + 1 to current.second, grid, rot, visited)
                }
            }

            '\\' -> {
                when (rot) {
                    ROT.LEFT -> visit(current.first - 1 to current.second, grid, ROT.UP, visited)
                    ROT.RIGHT -> visit(current.first + 1 to current.second, grid, ROT.DOWN, visited)
                    ROT.UP -> visit(current.first to current.second - 1, grid, ROT.LEFT, visited)
                    else -> visit(current.first to current.second + 1, grid, ROT.RIGHT, visited)
                }
            }

            '/' -> {
                when (rot) {
                    ROT.LEFT -> visit(current.first + 1 to current.second, grid, ROT.DOWN, visited)
                    ROT.RIGHT -> visit(current.first - 1 to current.second, grid, ROT.UP, visited)
                    ROT.UP -> visit(current.first to current.second + 1, grid, ROT.RIGHT, visited)
                    else -> visit(current.first to current.second - 1, grid, ROT.LEFT, visited)
                }
            }

            '-' -> {
                when (rot) {
                    ROT.LEFT -> visit(current.first to current.second - 1, grid, ROT.LEFT, visited)
                    ROT.RIGHT -> visit(current.first to current.second + 1, grid, ROT.RIGHT, visited)
                    ROT.UP -> {
                        visit(current.first to current.second + 1, grid, ROT.RIGHT, visited)
                        visit(current.first to current.second - 1, grid, ROT.LEFT, visited)
                    }

                    else -> {// DOWN
                        visit(current.first to current.second + 1, grid, ROT.RIGHT, visited)
                        visit(current.first to current.second - 1, grid, ROT.LEFT, visited)
                    }
                }
            }

            '|' -> {
                when (rot) {
                    ROT.LEFT -> {
                        visit(current.first - 1 to current.second, grid, ROT.UP, visited)
                        visit(current.first + 1 to current.second, grid, ROT.DOWN, visited)
                    }

                    ROT.RIGHT -> {
                        visit(current.first - 1 to current.second, grid, ROT.UP, visited)
                        visit(current.first + 1 to current.second, grid, ROT.DOWN, visited)
                    }

                    ROT.UP -> visit(current.first - 1 to current.second, grid, ROT.UP, visited)
                    else -> visit(current.first + 1 to current.second, grid, ROT.DOWN, visited)
                }
            }
        }
    }
    visit(startingPos, grid, rot, emptyArray())
    return energized.size
}

enum class ROT {
    LEFT, RIGHT, UP, DOWN
}




