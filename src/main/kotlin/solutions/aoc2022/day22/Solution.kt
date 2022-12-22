package solutions.aoc2022.day22

import utils.Resources
import utils.splitOnEmpty

fun main() {
    val input = Resources.getLines(2022, 22)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part2(input: List<String>): Int {

    val sp = input.splitOnEmpty()
    var map = sp[0]


    val command = sp[1][0]
    val rotates = "(\\d+)([A-Z])".toRegex().findAll(sp[1][0]).map { it.groupValues[2] }.toList()
    val lengths =
        "(\\d+)([A-Z])".toRegex().findAll(sp[1][0]).map { it.groupValues[1] }.map { it.toInt() }.toMutableList()

    lengths.add(command.last().digitToInt())
    var realPos = 0 to map[0].indexOf(".")
    var dirIdx = 0
    var shouldSkip = false
    for (length in lengths) {
        shouldSkip = false
        var currL = length
        while (currL > 0 && !shouldSkip) {
            val next = nextPos2(realPos)
            if (canMoveToPos(map, next)) {
                realPos = next
                if (currL == 1) {
                    if (dirIdx < rotates.size) {
                        val nextRot = rotates[dirIdx]
                        dirIdx += 1
                        globDir = getDirection(globDir, nextRot)
                    }
                }
            } else {
                if (dirIdx < rotates.size) {
                    val nextRot = rotates[dirIdx]
                    dirIdx += 1
                    globDir = getDirection(globDir, nextRot)
                }
                shouldSkip = true
            }
            currL -= 1

        }
    }
    val facing = if (globDir == "E") 0 else if (globDir == "S") 1 else if (globDir == "N") 3 else 2
    val xToRet = (1000 * (realPos.first + 1)) + (4 * (realPos.second + 1)) + facing

    return xToRet
}

fun getStart(y: Int, x: Int, cube: Int): Pair<Int, Int> {
    val cRange = cubeRange[cube]
    return cRange.first.first + y to cRange.second.first + x
}

fun posInCube(pos: Pair<Int, Int>, cube: Int): Pair<Int, Int> {
    val cRange = cubeRange[cube]
    return pos.first - cRange.first.start to pos.second - cRange.second.start
}

var cubeRange = listOf(
    ((0..49) to (50..99)),
    ((0..49) to (100..149)),
    ((50..99) to (50..99)),
    ((100..149) to (0..49)),
    ((100..149) to (50..99)),
    ((150..199) to (0..49))
)
var globDir = "E"

fun nextPos2(pos: Pair<Int, Int>): Pair<Int, Int> {
    val (y, x) = pos
    val cubeRange = cubeRange.first { r -> y in r.first && x in r.second }
    val cube = solutions.aoc2022.day22.cubeRange.indexOf(cubeRange)

    val currentCubePos = posInCube(pos, cube)
    if (cube == 0) {
        if (globDir == "S") {
            return y + 1 to x
        } else if (globDir == "E") {
            return y to x + 1
        } else if (globDir == "W") {
            if (x - 1 in cubeRange.second) {
                return y to x - 1
            } else {
                globDir = "E" // y 0 w y 149 , y = 49 w y = 0
                return getStart(49 - currentCubePos.first, 0, 3)
            }
        } else if (globDir == "N") {
            if (y - 1 in cubeRange.first) {
                return y - 1 to x
            } else {
                globDir = "E"
                val newY = currentCubePos.second
                return getStart(newY, 0, 5)
            }
        } else {
            throw Exception("Incorrect dir")
        }
    } else if (cube == 1) {
        if (globDir == "N") {
            if (y - 1 in cubeRange.first) {
                return y - 1 to x
            } else {
                return getStart(49, currentCubePos.second, 5)
            }
        } else if (globDir == "S") {
            if (y + 1 in cubeRange.first) {
                return y + 1 to x
            } else {
                globDir = "W"
                return getStart(currentCubePos.second, 49, 2)
            }
        } else if (globDir == "E") {
            if (x + 1 in cubeRange.second) {
                return y to x + 1
            } else {
                globDir = "W"
                return getStart(49 - currentCubePos.first, 49, 4)
            }
        } else if (globDir == "W") {
            return y to x - 1
        } else {
            throw Exception("Incorrect dir")
        }
    } else if (cube == 2) {
        if (globDir == "N") {
            return y - 1 to x
        } else if (globDir == "S") {
            return y + 1 to x
        } else if (globDir == "E") {
            if (x + 1 in cubeRange.second) {
                return y to x + 1
            } else {
                globDir = "N"
                return getStart(49, currentCubePos.first, 1)
            }
        } else if (globDir == "W") {
            if (x - 1 in cubeRange.second) {
                return y to x - 1
            } else {
                globDir = "S"
                return getStart(0, currentCubePos.first, 3)
            }
        } else {
            throw Exception("Incorrect dir")
        }
    } else if (cube == 3) {
        if (globDir == "N") {
            if (y - 1 in cubeRange.first) {
                return y - 1 to x
            } else {
                globDir = "E"
                return getStart(currentCubePos.second, 0, 2)
            }
        } else if (globDir == "S") {
            return y + 1 to x
        } else if (globDir == "E") {
            return y to x + 1
        } else if (globDir == "W") {
            if (x - 1 in cubeRange.second) {
                return y to x - 1
            } else {
                globDir = "E"
                return getStart(49 - currentCubePos.first, 0, 0)
            }
        } else {
            throw Exception("Incorrect dir")
        }
    } else if (cube == 4) {
        if (globDir == "N") {
            return y - 1 to x
        } else if (globDir == "S") {
            if (y + 1 in cubeRange.first) {
                return y + 1 to x
            } else {
                globDir = "W"
                return getStart(currentCubePos.second, 49, 5)
            }
        } else if (globDir == "E") {
            if (x + 1 in cubeRange.second) {
                return y to x + 1
            } else {
                globDir = "W"
                return getStart(49 - currentCubePos.first, 49, 1)
            }
        } else if (globDir == "W") {
            return y to x - 1
        } else {
            throw Exception("Incorrect dir")
        }
    } else if (cube == 5) {
        if (globDir == "N") {
            return y - 1 to x
        } else if (globDir == "S") {
            if (y + 1 in cubeRange.first) {
                return y + 1 to x
            } else {
                return getStart(0, currentCubePos.second, 1)
            }
        } else if (globDir == "E") {
            if (x + 1 in cubeRange.second) {
                return y to x + 1
            } else {
                globDir = "N"
                return getStart(49, currentCubePos.first, 4)
            }
        } else if (globDir == "W") {
            if (x - 1 in cubeRange.second) {
                return y to x - 1
            } else {
                globDir = "S"
                return getStart(0, currentCubePos.first, 0)
            }
        } else {
            throw Exception("Incorrect dir")
        }
    } else { // cube == 6
        throw Exception("there is no such cube: $cube")
    }
}

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

    val rotates = "(\\d+)([A-Z])".toRegex().findAll(sp[1][0]).map { it.groupValues[2] }.toList()
    val lengths =
        "(\\d+)([A-Z])".toRegex().findAll(sp[1][0]).map { it.groupValues[1] }.map { it.toInt() }.toMutableList()

    lengths.add(7)
    var realPos = 0 to map[0].indexOf(".")
    var currentDir = "E"
    var dirIdx = 0
    var shouldSkip: Boolean
    for (length in lengths) {
        shouldSkip = false
        var currL = length
        while (currL > 0 && !shouldSkip) {
            val next = nextPos(map, realPos, currentDir)
            if (canMoveToPos(map, next)) {
                realPos = next
                if (currL == 1) {
                    if (dirIdx < rotates.size) {
                        val nextRot = rotates[dirIdx]
                        dirIdx += 1
                        currentDir = getDirection(currentDir, nextRot)
                    }
                }
            } else {
                if (dirIdx <= rotates.size) {
                    val nextRot = rotates[dirIdx]
                    dirIdx += 1
                    currentDir = getDirection(currentDir, nextRot)
                }
                shouldSkip = true
            }
            currL -= 1

        }
    }
    val facing = if (currentDir == "E") 0 else if (currentDir == "S") 1 else if (currentDir == "N") 3 else 2
    val xToRet = (1000 * (realPos.first + 1)) + (4 * (realPos.second + 1)) + facing
    return xToRet
}


fun nextPos(map: List<String>, pos: Pair<Int, Int>, dir: String): Pair<Int, Int> {
    val (y, x) = pos
    if (dir == "E") {
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
    } else if (dir == "S") {
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
    } else if (dir == "N") {
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
    } else if (dir == "W") {
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
    } else {
        throw Exception("Incorrect dir")
    }

}

fun canMoveToPos(map: List<String>, pos: Pair<Int, Int>): Boolean {
    val (y, x) = pos
    return if (map.size > y && y >= 0 && map[0].length > x && x >= 0) {
        map[y][x] == '.'
    } else {
        false
    }
}

fun getDirection(current: String, change: String): String {

    if (change == "R") {
        if (current == "S") {
            return "W"
        } else if (current == "W") {
            return "N"
        } else if (current == "E") {
            return "S"
        } else {    //N
            return "E"
        }
    } else { // L
        if (current == "S") {
            return "E"
        } else if (current == "W") {
            return "S"
        } else if (current == "E") {
            return "N"
        } else {    //N
            return "W"
        }
    }
}