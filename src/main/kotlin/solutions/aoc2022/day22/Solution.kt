package solutions.aoc2022.day22

import utils.CircularList
import utils.Resources
import utils.splitOnEmpty

fun main() {
    val input = Resources.getLines(2022, 22)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

var cubeRange = listOf(
    ((0..49) to (50..99)), ((0..49) to (100..149)), ((50..99) to (50..99)), ((100..149) to (0..49)), ((100..149) to (50..99)), ((150..199) to (0..49))
)

fun part1(input: List<String>): Int {

    val sp = input.splitOnEmpty()
    var map = sp[0]
    val maxL = map.maxOf { i -> i.length }
    map = map.map {
        if (it.length < maxL) {
            it + " ".repeat(maxL - it.length)
        } else {
            it
        }
    }
    val rotates = "([A-Z])".toRegex().findAll(sp[1][0]).map { it.groupValues[1] }.toList()
    val lengths = "(\\d+)".toRegex().findAll(sp[1][0]).map { it.groupValues[1] }.map { it.toInt() }.toMutableList()
    var realPos = 0 to map[0].indexOf(".")
    var currentDir = "E"
    var dirIdx = 0
    for (length in lengths) {
        var currL = length
        while (currL > 0) {
            val next = nextPos(map, realPos, currentDir)
            if (canMoveToPos(map, next)) {
                realPos = next
            }
            currL -= 1
        }
        if (dirIdx < rotates.size) {
            val nextRot = rotates[dirIdx++]
            currentDir = getDirection(currentDir, nextRot)
        }
    }
    val facing = if (currentDir == "E") 0 else if (currentDir == "S") 1 else if (currentDir == "N") 3 else 2
    return (1000 * (realPos.first + 1)) + (4 * (realPos.second + 1)) + facing
}

fun part2(input: List<String>): Int {

    val sp = input.splitOnEmpty()
    val map = sp[0]
    val rotates = "([A-Z])".toRegex().findAll(sp[1][0]).map { it.groupValues[1] }.toList()
    val lengths = "(\\d+)".toRegex().findAll(sp[1][0]).map { it.groupValues[1] }.map { it.toInt() }.toMutableList()
    var realPos = 0 to map[0].indexOf(".")
    var dirIdx = 0
    var currentDir = "E"
    for (length in lengths) {
        var currL = length
        while (currL > 0) {
            val (next, nextDir) = nextPosPart2(realPos, currentDir)
            if (canMoveToPos(map, next)) {
                realPos = next
                currentDir = nextDir
            }
            currL -= 1
        }
        if (dirIdx < rotates.size) {
            val nextRot = rotates[dirIdx++]
            currentDir = getDirection(currentDir, nextRot)
        }
    }
    val facing = if (currentDir == "E") 0 else if (currentDir == "S") 1 else if (currentDir == "N") 3 else 2
    return (1000 * (realPos.first + 1)) + (4 * (realPos.second + 1)) + facing
}

fun getNextPos(y: Int, x: Int, cube: Int): Pair<Int, Int> {
    val cRange = cubeRange[cube]
    return cRange.first.first + y to cRange.second.first + x
}

fun getPosInCurrentSquare(pos: Pair<Int, Int>, cube: Int): Pair<Int, Int> {
    val cRange = cubeRange[cube]
    return pos.first - cRange.first.first to pos.second - cRange.second.first
}

fun nextPosPart2(pos: Pair<Int, Int>, currentDir: String): Pair<Pair<Int, Int>, String> {
    val (y, x) = pos
    val cubeRange = cubeRange.first { r -> y in r.first && x in r.second }
    val cube = solutions.aoc2022.day22.cubeRange.indexOf(cubeRange)

    val currentPos = getPosInCurrentSquare(pos, cube)
    when (cube) {
        0 -> {
            when (currentDir) {
                "S" -> return y + 1 to x to currentDir
                "E" -> return y to x + 1 to currentDir
                "W" -> return if (x - 1 in cubeRange.second) {
                    y to x - 1 to currentDir
                } else {
                    getNextPos(49 - currentPos.first, 0, 3) to "E"
                }

                "N" -> return if (y - 1 in cubeRange.first) {
                    y - 1 to x to currentDir
                } else {
                    getNextPos(currentPos.second, 0, 5) to "E"
                }
            }
        }

        1 -> {
            when (currentDir) {
                "N" -> {
                    return if (y - 1 in cubeRange.first) {
                        y - 1 to x to currentDir
                    } else {
                        getNextPos(49, currentPos.second, 5) to currentDir
                    }
                }

                "S" -> {
                    return if (y + 1 in cubeRange.first) {
                        y + 1 to x to currentDir
                    } else {
                        getNextPos(currentPos.second, 49, 2) to "W"
                    }
                }

                "E" -> {
                    return if (x + 1 in cubeRange.second) {
                        y to x + 1 to currentDir
                    } else {
                        getNextPos(49 - currentPos.first, 49, 4) to "W"
                    }
                }

                "W" -> return y to x - 1 to currentDir
            }
        }

        2 -> {
            when (currentDir) {
                "N" -> return y - 1 to x to currentDir
                "S" -> return y + 1 to x to currentDir
                "E" -> {
                    return if (x + 1 in cubeRange.second) {
                        y to x + 1 to currentDir
                    } else {
                        getNextPos(49, currentPos.first, 1) to "N"
                    }
                }

                else -> return if (x - 1 in cubeRange.second) {
                    y to x - 1 to currentDir
                } else {
                    getNextPos(0, currentPos.first, 3) to "S"
                }

            }
        }

        3 -> {
            when (currentDir) {
                "N" -> {
                    return if (y - 1 in cubeRange.first) {
                        y - 1 to x to currentDir
                    } else {
                        getNextPos(currentPos.second, 0, 2) to "E"
                    }
                }

                "S" -> return y + 1 to x to currentDir

                "E" -> return y to x + 1 to currentDir
                else -> { //if (currentDir == "W")
                    return if (x - 1 in cubeRange.second) {
                        y to x - 1 to currentDir
                    } else {
                        getNextPos(49 - currentPos.first, 0, 0) to "E"
                    }
                }
            }
        }

        4 -> {
            when (currentDir) {
                "N" -> return y - 1 to x to currentDir
                "S" -> {
                    return if (y + 1 in cubeRange.first) {
                        y + 1 to x to currentDir
                    } else {
                        getNextPos(currentPos.second, 49, 5) to "W"
                    }
                }

                "E" -> {
                    return if (x + 1 in cubeRange.second) {
                        y to x + 1 to currentDir
                    } else {
                        getNextPos(49 - currentPos.first, 49, 1) to "W"
                    }
                }

                else -> return y to x - 1 to currentDir

            }
        }

        else -> { // if (cube == 5) {
            when (currentDir) {
                "N" -> return y - 1 to x to currentDir


                "S" -> {
                    return if (y + 1 in cubeRange.first) {
                        y + 1 to x to currentDir
                    } else {
                        getNextPos(0, currentPos.second, 1) to currentDir
                    }
                }

                "E" -> {
                    return if (x + 1 in cubeRange.second) {
                        y to x + 1 to currentDir
                    } else {
                        getNextPos(49, currentPos.first, 4) to "N"
                    }
                }

                else -> {
                    return if (x - 1 in cubeRange.second) {
                        y to x - 1 to currentDir
                    } else {
                        getNextPos(0, currentPos.first, 0) to "S"
                    }
                }
            }
        }
    }
    throw Exception("Cannot find next position")
}


fun nextPos(map: List<String>, pos: Pair<Int, Int>, dir: String): Pair<Int, Int> {
    val (y, x) = pos
    when (dir) {
        "E" -> {
            var nx = x + 1
            while (true) {
                if (nx == map[y].length) {
                    nx = 0
                }
                if (map[y][nx] != ' ') {
                    return y to nx
                } else {
                    nx += 1
                }
            }
        }

        "S" -> {
            var ny = y + 1
            while (true) {
                if (ny == map.size) {
                    ny = 0
                }
                if (map[ny][x] != ' ') {
                    return ny to x
                } else {
                    ny += 1
                }
            }
        }

        "N" -> {
            var ny = y - 1
            while (true) {
                if (ny == -1) {
                    ny = map.size - 1
                }
                if (map[ny][x] != ' ') {
                    return ny to x
                } else {
                    ny -= 1
                }
            }
        }

        "W" -> {
            var nx = x - 1
            while (true) {
                if (nx < 0) {
                    nx = map[y].length - 1
                }
                if (map[y][nx] != ' ') {
                    return y to nx
                } else {
                    nx -= 1
                }
            }
        }

        else -> {
            throw Exception("Incorrect dir")
        }
    }
}

fun canMoveToPos(map: List<String>, pos: Pair<Int, Int>): Boolean {
    val (y, x) = pos
    return y in 0..map.size && x in 0..map[0].length && map[y][x] == '.'
}

fun getDirection(current: String, change: String): String {
    val dirs = CircularList(listOf("N", "E", "S", "W"))
    val currIdx = dirs.indexOf(current)
    return if (change == "R") dirs[currIdx + 1] else dirs[currIdx - 1]
}