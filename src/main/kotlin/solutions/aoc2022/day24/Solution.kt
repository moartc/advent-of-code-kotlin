package solutions.aoc2022.day24

import utils.Resources
import kotlin.math.abs

var bestFound = Int.MAX_VALUE
var lastBlizzard = emptyList<Blizzard>()
var BLIZZARDS = mutableMapOf<Int, List<Blizzard>>()
var BLIZZARD_MOVES = mutableMapOf<Pair<Int, Pair<Int, Int>>, List<Pair<Int, Int>>>()
var GLOB_VISITED = mutableSetOf<State>()
var MAX_Y = -1
var MAX_X = -1

fun main() {
    val input = Resources.getLines(2022, 24)
    val blizzards = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (c == '>' || c == '<' || c == '^' || c == 'v') {
                Blizzard(y, x, c)
            } else {
                null
            }
        }
    }.filterNotNull()
    MAX_Y = input.size - 2
    MAX_X = input[0].length - 2

    println("part1 = ${part1(blizzards)}")
    println("part2 = ${part2(blizzards)}")
}

fun part1(blizzards: List<Blizzard>): Int {

    val player = Pair(0, 1)

    resetStates()
    play(blizzards, player, 0, mutableListOf(), MAX_Y + 1 to MAX_X)

    return bestFound
}

fun part2(blizzards: List<Blizzard>): Int {

    val start = Pair(0, 1)
    val end = MAX_Y + 1 to MAX_X

    var resultPart2 = 0

    resetStates()
    play(blizzards, start, 0, mutableListOf(), end)
    resultPart2 += bestFound

    resetStates()
    play(lastBlizzard, end, 0, mutableListOf(), 0 to 1)
    resultPart2 += bestFound

    resetStates()
    play(lastBlizzard, start, 0, mutableListOf(), end)
    resultPart2 += bestFound


    return resultPart2
}

data class State(val pos: Pair<Int, Int>, val current: Int)

fun play(blizzards: List<Blizzard>, player: Pair<Int, Int>, current: Int, visited: MutableList<Pair<Int, Int>>, end: Pair<Int, Int>) {

    val st = State(player, current)

    if (GLOB_VISITED.contains(st)) {
        return
    } else {
        GLOB_VISITED.add(st)
    }

    val (y, x) = player
    visited.add(player)

    if (current + abs(end.first - player.first) + abs(end.second - player.second) > bestFound) {
        return
    }
    if (y == end.first && x == end.second) {
        if (bestFound >= current) {
            bestFound = current
            lastBlizzard = blizzards.toList()
        }
        return
    }

    val newBlizzard: List<Blizzard>
    if (BLIZZARDS.containsKey(current + 1)) {
        newBlizzard = BLIZZARDS[current + 1]!!
    } else {
        newBlizzard = blizzards.map { it.nextMove(MAX_Y, MAX_X) }
        BLIZZARDS[current + 1] = newBlizzard.toList()
    }

    val nextMove: List<Pair<Int, Int>>
    if (BLIZZARD_MOVES.containsKey(current + 1 to player)) {
        nextMove = BLIZZARD_MOVES[current + 1 to player]!!
    } else {
        nextMove = getMoves(newBlizzard, player, MAX_Y, MAX_X)
        BLIZZARD_MOVES[current + 1 to player] = nextMove
    }

    if (nextMove.isEmpty()) {
        if (newBlizzard.none { it.y == player.first && it.x == player.second }) {
            play(newBlizzard, player, current + 1, visited.toMutableList(), end)
        } else {
            return
        }
    } else {
        nextMove.forEach {
            play(newBlizzard, it, current + 1, visited.toMutableList(), end)
        }
        if (newBlizzard.none { it.y == player.first && it.x == player.second }) {
            play(newBlizzard, player, current + 1, visited.toMutableList(), end)
        }
    }
}

fun getMoves(blizzards: List<Blizzard>, pos: Pair<Int, Int>, maxY: Int, maxX: Int): List<Pair<Int, Int>> {
    val (y, x) = pos
    val toRet = mutableListOf<Pair<Int, Int>>()
    if (y == 0 && x == 1) {
        toRet.add(1 to 1)
    } else if (y == maxY + 1 && x == maxX) {
        toRet.add(maxY to maxX)
    } else {
        if (y + 1 <= maxY) {
            toRet.add(y + 1 to x)
        }
        if (y - 1 >= 1) {
            toRet.add(y - 1 to x)
        }
        if (x + 1 <= maxX) {
            toRet.add(y to x + 1)
        }
        if (y + 1 == maxY + 1 && x == maxX) {
            toRet.add(y + 1 to x)
        }
        if (x - 1 >= 1) {
            toRet.add(y to x - 1)
        }
        if (y - 1 == 0 && x == 1) {
            toRet.add(0 to 1)
        }
    }
    return toRet.filter { p -> blizzards.none { b -> b.y == p.first && b.x == p.second } }
}

data class Blizzard(var y: Int, var x: Int, var c: Char) {
    fun nextMove(maxY: Int, maxX: Int): Blizzard {
        return when (c) {
            '>' -> if (x + 1 <= maxX) this.copy(x = x + 1) else this.copy(x = 1)
            '<' -> if (x - 1 >= 1) this.copy(x = x - 1) else this.copy(x = maxX)
            '^' -> if (y - 1 >= 1) this.copy(y = y - 1) else this.copy(y = maxY)
            else -> if (y + 1 <= maxY) this.copy(y = y + 1) else this.copy(y = 1)
        }
    }
}

fun resetStates() {
    bestFound = 400
    BLIZZARDS = mutableMapOf()
    BLIZZARD_MOVES = mutableMapOf()
    GLOB_VISITED = mutableSetOf()
}