package solutions.aoc2024.day15

import utils.Resources
import utils.collections.extensions.findPosition
import utils.collections.extensions.splitOnEmpty

val day = (object {}).javaClass.packageName.takeLast(2).toInt()


fun main() {

    val inputLines = Resources.getLines(2024, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val splitOnEmpty = inputLines.splitOnEmpty()
    val gridPart = splitOnEmpty[0]

    val grid = gridPart.map { it.toCharArray() }

    var (y, x) = gridPart.findPosition('@')

    grid[y][x] = '.' // remove robot

    val listOfMoves = splitOnEmpty[1]
    val moves = listOfMoves.joinToString("")

    move@ for (move in moves) {
        val (dy, dx) = when (move) {
            '<' -> 0 to -1
            '>' -> 0 to 1
            '^' -> -1 to 0
            'v' -> 1 to 0
            else -> throw Exception("unknown move")
        }

        val ny = y + dy
        val nx = x + dx

        if (grid[ny][nx] == '#') {
            continue
        }

        if (grid[ny][nx] == 'O') {
            var pushY = ny
            var pushX = nx
            val toMove = mutableListOf<Pair<Int, Int>>()
            while (grid[pushY][pushX] == 'O') {
                toMove.add(pushY to pushX)
                pushY += dy
                pushX += dx
                if (grid[pushY][pushX] == '#') {
                    continue@move
                }
            }
            if (grid[pushY][pushX] == '.') {
                val pushes = toMove.size
                toMove.reverse()
                repeat(pushes) {
                    val (oy, ox) = toMove[it]
                    grid[oy + dy][ox + dx] = 'O'
                    grid[oy][ox] = '.'
                }
                y = ny
                x = nx
            } else {
                continue
            }
        } else if (grid[ny][nx] == '.') {
            y = ny
            x = nx
        }
    }
    var res = 0L
    for ((y, chars) in grid.withIndex()) {
        for ((x, c) in chars.withIndex()) {
            if (c == 'O') {
                res += 100 * y + x
            }
        }
    }
    return res
}


fun part2(inputLines: List<String>): Long {

    val splitOnEmpty = inputLines.splitOnEmpty()
    val gridPart = splitOnEmpty[0]
    val origGrid = gridPart.map { it.toCharArray() }

    fun refineGrid(original: List<CharArray>): List<String> {
        val newGrid = mutableListOf<String>()
        for ((index, chars) in original.withIndex()) {
            if (index == 0 || index == original.size - 1) {
                newGrid.add("#".repeat(2 * original[0].size))
            } else {
                var newLine = ""
                for (char in chars) {
                    newLine += when (char) {
                        '#' -> "##"
                        'O' -> "[]"
                        '.' -> ".."
                        '@' -> "@."
                        else -> throw Exception("wrong!!! + character $char")

                    }
                }
                newGrid.add(newLine)
            }
        }
        return newGrid
    }

    val refinedGrid = refineGrid(origGrid)

    var (y, x) = refinedGrid.findPosition('@')
    val grid = refinedGrid.map { it.toCharArray() }

    grid[y][x] = '.' // remove robot

    val moves = splitOnEmpty[1].joinToString("")

    move@ for (move in moves) {
        val (dy, dx) = when (move) {
            '<' -> 0 to -1
            '>' -> 0 to 1
            '^' -> -1 to 0
            'v' -> 1 to 0
            else -> throw Exception("unknown move")
        }

        val ny = y + dy
        val nx = x + dx
        if (grid[ny][nx] == '#') {
            continue
        }
        if (move in "<>") {
            if (grid[ny][nx] == '[' || grid[ny][nx] == ']') {
                var py = ny
                var px = nx
                val positions = mutableListOf<Pair<Int, Int>>()

                while (grid[py][px] in "[]") {
                    positions.add(py to px)
                    py += dy
                    px += dx
                    if (grid[py][px] == '#') {
                        continue@move
                    }
                }

                if (grid[py][px] == '.') {
                    val pushes = positions.size
                    positions.reverse()
                    repeat(pushes) {
                        val (oy, ox) = positions[it]
                        val currentElem = grid[oy][ox]
                        grid[oy + dy][ox + dx] = currentElem
                        grid[oy][ox] = '.'
                    }
                    y = ny
                    x = nx
                } else {
                    continue
                }
            } else if (grid[ny][nx] == '.') {
                y = ny
                x = nx
            } else {
                break
            }
        } else { // move up and down
            if (grid[ny][nx] == '[' || grid[ny][nx] == ']') {
                val py = ny
                val px = nx
                val pushedAndCanMove = pushBoxesInGridIfPossible(grid, py, px, dy)
                if (pushedAndCanMove) {
                    y = ny
                    x = nx
                } else {
                    continue
                }
            } else if (grid[ny][nx] == '.') {
                y = ny
                x = nx
            }
        }
    }
    var res = 0L
    for ((y, chars) in grid.withIndex()) {
        for ((x, c) in chars.withIndex()) {
            if (c == '[') {
                res += 100 * y + x
            }
        }
    }
    return res
}

fun pushBoxesInGridIfPossible(grid: List<CharArray>, startY: Int, startX: Int, dy: Int): Boolean {

    val listOfPoint = mutableSetOf<Pair<Int, Int>>()
    val stackToCheck = mutableListOf<Pair<Int, Int>>()

    stackToCheck.add(startY to startX)

    while (stackToCheck.isNotEmpty()) {
        val (y, x) = stackToCheck.removeFirst()
        if (grid[y][x] == '#') {
            // cannot push
            return false
        }
        if (grid[y][x] == '.') {
            continue
        }

        val isLeftBracket = grid[y][x] == '['
        val leftBracketX = if (isLeftBracket) x else x - 1
        val rightBracketX = if (isLeftBracket) x + 1 else x

        listOfPoint.add(y to leftBracketX)
        listOfPoint.add(y to rightBracketX)

        if (grid[y][leftBracketX] == '.' && grid[y][rightBracketX] == '.') {
            continue
        }

        val nextLevelLeft = y + dy to leftBracketX
        val nextLevelRight = y + dy to rightBracketX

        stackToCheck.add(nextLevelLeft)
        stackToCheck.add(nextLevelRight)
    }
    // push part
    for (pair in listOfPoint.reversed()) {
        val (y, x) = pair
        grid[y + dy][x] = grid[y][x]
        grid[y][x] = '.' // there must be '.'
    }
    return true
}
