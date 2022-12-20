package solutions.aoc2022.day20

import utils.Resources
import kotlin.math.abs

fun main() {
    val input = Resources.getLines(2022, 20)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Long {

    val initial = input.map { it.toInt() }

    var updated = (0..initial.size).zip(initial).map {
        MyPair(it.first.toLong(), it.second.toLong())
    }.toMutableList()

    repeat(initial.size) { updated = update(updated, it) }

    val v1 = getValue(1000, updated)
    val v2 = getValue(2000, updated)
    val v3 = getValue(3000, updated)
    return v1 + v2 + v3
}

fun part2(input: List<String>): Long {

    val initial = input.map { it.toLong() * 811589153 }

    var updated = (0..initial.size).zip(initial).map {
        MyPair(it.first.toLong(), it.second)
    }.toMutableList()

    repeat(10) {
        repeat(initial.size) {
            updated = update(updated, it)
        }
    }

    val v1 = getValue(1000, updated)
    val v2 = getValue(2000, updated)
    val v3 = getValue(3000, updated)
    return v1 + v2 + v3
}

fun getValue(offset: Int, updated: List<MyPair>): Long {
    val val0Idx = updated.find { it.second == 0L }!!.first
    val idx = (val0Idx + offset) % updated.size
    return updated.find { it.first == idx }!!.second
}

fun update(list: MutableList<MyPair>, idx: Int): MutableList<MyPair> {

    val curr = list[idx]
    val moveSize = curr.second % (list.size - 1)
    val move =
        if (curr.second > 0) {
            getNextPositive(curr.first, moveSize, list.size.toLong())
        } else {
            getNextNegative(curr.first, moveSize, list.size.toLong())
        }
    if (curr.first > move) {
        list.filter { it.first in move..curr.first }.forEach { it.updateRight(list.size) }
    } else {
        list.filter { it.first in (curr.first + 1)..move }.forEach { it.updateLeft(list.size) }
    }
    curr.first = move
    return list
}

fun getNextPositive(current: Long, move: Long, size: Long): Long {
    var ret = current
    repeat(move.toInt()) { ret = if (ret + 1 == size) 1 else ret + 1 }
    return ret
}

fun getNextNegative(current: Long, move: Long, size: Long): Long {
    var ret = current
    repeat(abs(move.toInt())) {
        when (ret) {
            0L -> ret = size - 2
            1L -> ret = size - 1
            else -> ret -= 1
        }
    }
    return ret
}

class MyPair(var first: Long, var second: Long) {
    fun updateLeft(size: Int) {
        this.first = (first - 1) % size
    }

    fun updateRight(size: Int) {
        this.first = (first + 1) % size
    }
}