package solutions.aoc2017.day03

import utils.Resources
import kotlin.math.abs

fun main() {

    val inputLine = Resources.getLine(2017, 3)
    println("part1 = " + part1(inputLine.toInt()))
    println("part2 = " + part2(inputLine.toInt()))
}

fun part1(input: Int): Int {
    val generated = generateSequence(Square(0 to 0, 1, Dir.R)) { it.generateNext() }.first { f -> f.value == input }
    return abs(generated.getX()) + abs(generated.getY())
}

fun part2(input: Int): Int {
    val map = mutableMapOf((0 to 0) to 1)
    return generateSequence(Square(0 to 0, 1, Dir.R)) { it.generateNext2(map) }.first { f -> f.value > input }.value
}

fun getPos(pos: Pair<Int, Int>): List<Pair<Int, Int>> {

    return (pos.first - 1..pos.first + 1)
        .flatMap { first -> (pos.second - 1..pos.second + 1).map { second -> first to second } }
        .filter { pair -> pair.first != pos.first || pair.second != pos.second }
}

class Square(val pos: Pair<Int, Int>, val value: Int, val dir: Dir) {

    fun getY() = this.pos.first
    fun getX() = this.pos.second

    fun generateNext2(map: MutableMap<Pair<Int, Int>, Int>): Square {
        val nextDir = getNextDirection()
        val nextPos = nextPos(nextDir)
        val neighborSum = getPos(nextPos).sumOf { pair -> map.getOrDefault(pair, 0) }
        map[nextPos] = neighborSum
        return Square(nextPos, neighborSum, nextDir)
    }

    fun generateNext(): Square {
        val nextDir = getNextDirection()
        return Square(nextPos(nextDir), this.value + 1, nextDir)
    }

    private fun nextPos(direction: Dir): Pair<Int, Int> {
        return when (direction) {
            Dir.L -> getY() to getX() - 1
            Dir.R -> getY() to getX() + 1
            Dir.D -> getY() - 1 to getX()
            else -> getY() + 1 to getX()
        }
    }

    private fun getNextDirection(): Dir {
        return if (getY() == 0 && getX() == 0) {
            Dir.R
        } else if (getY() > 0 && getX() > 0 && abs(getX()) == abs(getY())) { // right up corner
            Dir.L
        } else if (getY() > 0 && getX() < 0 && abs(getX()) == abs(getY())) {   // left up corner
            Dir.D
        } else if (getY() < 0 && getX() < 0 && abs(getX()) == abs(getY())) {    // left down corner
            Dir.R
        } else if (getY() <= 0 && getX() > 0 && abs(getX() - 1) == abs(getY())) {   // right down corner
            Dir.U
        } else {
            this.dir
        }
    }
}

enum class Dir {
    U, D, L, R
}