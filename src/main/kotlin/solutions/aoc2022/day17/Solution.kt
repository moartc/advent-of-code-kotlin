package solutions.aoc2022.day17

import utils.Resources
import utils.collections.CircularList

fun main() {
    val input = Resources.getLines(2022, 17)

    val moves = CircularList(input[0].toList())
    val chamber = mutableListOf<Pair<Int, Int>>()
    val rock1 = Rock(mutableListOf(0 to 0, 0 to 1, 0 to 2, 0 to 3))
    val rock2 = Rock(mutableListOf(0 to 1, 1 to 0, 1 to 1, 1 to 2, 2 to 1))
    val rock3 = Rock(mutableListOf(0 to 0, 0 to 1, 0 to 2, 1 to 2, 2 to 2))
    val rock4 = Rock(mutableListOf(0 to 0, 1 to 0, 2 to 0, 3 to 0))
    val rock5 = Rock(mutableListOf(0 to 0, 0 to 1, 1 to 0, 1 to 1))

    val rocks = CircularList(listOf(rock1, rock2, rock3, rock4, rock5))

    println("part1 = ${part1(moves, chamber.toMutableList(), rocks, 2022)}")
    println("part2 = ${part2(moves, chamber.toMutableList(), rocks)}")
}


class Rock(var pos: List<Pair<Int, Int>>) {

    fun getStartPos(highestY: Int): Rock {
        return Rock(pos.map { it.first + highestY + 4 to it.second + 2 }.toMutableList())
    }

    fun moveLeft(chamber: List<Pair<Int, Int>>): Rock {
        return if (canMoveLeft(chamber))
            Rock(pos.map { it.first to it.second - 1 }.toMutableList())
        else {
            Rock(pos.map { it.first to it.second }.toMutableList())
        }
    }

    fun moveRight(chamber: List<Pair<Int, Int>>): Rock {
        return if (canMoveRight(chamber)) {
            Rock(pos.map { it.first to it.second + 1 }.toMutableList())
        } else {
            Rock(pos.map { it.first to it.second }.toMutableList())
        }
    }

    fun moveDown(): Rock = Rock(pos.map { it.first - 1 to it.second }.toMutableList())

    private fun canMoveRight(chamber: List<Pair<Int, Int>>): Boolean {
        if (pos.maxOfOrNull { it.second }!! >= 6) return false
        return pos.none { chamber.any { cp -> cp == it.first to it.second + 1 } }
    }

    private fun canMoveLeft(chamber: List<Pair<Int, Int>>): Boolean {
        if (pos.minOfOrNull { it.second }!! <= 0) return false
        return pos.none { chamber.any { cp -> cp == it.first to it.second - 1 } }
    }
}

fun part2(moves: CircularList<Char>, chamber: MutableList<Pair<Int, Int>>, rocks: CircularList<Rock>): Long {

    var prev = 0
    var current: Int
    val diffs = mutableListOf<Int>()

    var rockIndex = 0
    var moveIdx = 0
    (1..5000).forEach { it ->
        val rock = rocks[rockIndex++]
        var rockToMove = rock.getStartPos(chamber.maxOfOrNull { it.first } ?: -1)
        rockToMove = if (moves[moveIdx++] == '<') rockToMove.moveLeft(chamber) else rockToMove.moveRight(chamber)

        while (!shouldStop(chamber, rockToMove)) {
            rockToMove = rockToMove.moveDown()
            rockToMove = if (moves[moveIdx++] == '<') rockToMove.moveLeft(chamber) else rockToMove.moveRight(chamber)
        }
        chamber.addAll(rockToMove.pos)

        current = chamber.maxBy { it.first }.first + 1
        diffs.add(current - prev)
        prev = current
    }
    val pair = findPeriodAndPrevResult(diffs)

    val firstIndex = pair.second
    val period = pair.first

    val toCalc = 1000000000000L - firstIndex// maybe -1
    val numGr = toCalc.div(period.size)
    val rest = toCalc % period.size

    return diffs.take(firstIndex).sum() + (numGr * period.sum()) + period.take(rest.toInt()).sum()
}

fun findPeriodAndPrevResult(diffs : List<Int>): Pair<List<Int>, Int> {
    for (i in 0 until 5000 - 50) {
        val sub1 = diffs.subList(i, i + 50)
        for (j in i + 50 until 5000 - 50) {
            val sub2 = diffs.subList(j, j + 50)
            if (sub1 == sub2) {
                val newSub1 = diffs.subList(i, j )
                val newSub2 = diffs.subList(j, j + newSub1.size)
                if (newSub1 == newSub2) {
                    return (newSub1 to i)
                }
            }
        }
    }
    return (emptyList<Int>() to -1)
}

fun part1(moves: CircularList<Char>, chamber: MutableList<Pair<Int, Int>>, rocks: CircularList<Rock>, repeatNum: Int): Int {

    var rockIndex = 0
    var moveIdx = 0

    repeat(repeatNum) {
        val rock = rocks[rockIndex++]
        var rockToMove = rock.getStartPos(chamber.maxOfOrNull { it.first } ?: -1)
        rockToMove = if (moves[moveIdx++] == '<') rockToMove.moveLeft(chamber) else rockToMove.moveRight(chamber)

        while (!shouldStop(chamber, rockToMove)) {
            rockToMove = rockToMove.moveDown()
            rockToMove = if (moves[moveIdx++] == '<') rockToMove.moveLeft(chamber) else rockToMove.moveRight(chamber)
        }
        chamber.addAll(rockToMove.pos)
    }
    return chamber.maxBy { it.first }.first + 1
}

fun shouldStop(chamber: List<Pair<Int, Int>>, rock: Rock): Boolean {
    return rock.pos.first().first == 0 || rock.pos.any { chamber.contains(it.first - 1 to it.second) }
}