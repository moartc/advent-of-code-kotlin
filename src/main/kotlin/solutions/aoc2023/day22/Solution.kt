package solutions.aoc2023.day22

import utils.Resources
import utils.parser.getInts

fun main() {

    val inputLine = Resources.getLines(2023, 22)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: List<String>): Int {

    val (settledBricks, brickToGround) = commonPart(input)
    return settledBricks.count { settledBrick ->
        brickToGround.filter { entry -> entry.value.contains(settledBrick) }.none { l -> l.value.size == 1 }
    }
}


fun part2(input: List<String>): Int {

    val (settledBricks, brickToGround) = commonPart(input)

    fun countDisintegration(initialBrick: Brick): Int {

        val disintegrated = mutableSetOf(initialBrick)
        do {
            val prevSize = disintegrated.size
            brickToGround.forEach { mutableEntry ->
                if (mutableEntry.value.none { brickSupporting -> !disintegrated.contains(brickSupporting) }) {
                    disintegrated.add(mutableEntry.key)
                }
            }
        } while (prevSize != disintegrated.size)
        return disintegrated.size - 1
    }

    val cannotBeDistinct = settledBricks.filter { settledBrick ->
        brickToGround.filter { entry -> entry.value.contains(settledBrick) }.any { l -> l.value.size == 1 }
    }

    return cannotBeDistinct.sumOf { brick ->
        countDisintegration(brick)
    }
}

fun commonPart(input: List<String>): Pair<MutableList<Brick>, MutableMap<Brick, MutableSet<Brick>>> {
    var ctr = 0
    val bricks = input.map {
        val ints = it.getInts()
        Brick(ctr++, ints[0], ints[1], ints[2], ints[3], ints[4], ints[5])
    }.sortedBy { it.z1 }

    val settledBricks = mutableListOf<Brick>()
    for (brick in bricks) {
        if (brick.z1 == 1) {
            settledBricks.add(brick)
        } else {
            var copy = brick.copy()
            while (true) {
                val newCopy = copy.copy(z1 = copy.z1 - 1, z2 = copy.z2 - 1)
                val isCollision = settledBricks.filter { b -> b.idx != newCopy.idx }.any { b -> b.isCollision(newCopy) }
                if (isCollision) {
                    settledBricks.add(copy)
                    break
                } else if (newCopy.z1 == 1) {
                    settledBricks.add(newCopy)
                    break
                } else {
                    copy = newCopy
                }
            }
        }
    }

    val brickToGround = mutableMapOf<Brick, MutableSet<Brick>>()

    for ((index1, brick1) in settledBricks.withIndex()) {
        for ((index2, brick2) in settledBricks.withIndex()) {
            if (index1 >= index2) continue
            if (brick2.stayOnBrick(brick1)) {
                brickToGround.computeIfAbsent(brick2) { mutableSetOf() }.also { it.add(brick1) }
            }
        }
    }
    return settledBricks to brickToGround
}

data class Brick(var idx: Int, var x1: Int, var y1: Int, var z1: Int, var x2: Int, var y2: Int, var z2: Int) {

    private fun overlap(a: IntProgression, b: IntProgression): Boolean {
        val max = maxOf(a.first, a.last)
        val min = minOf(a.first, a.last)
        val max2 = maxOf(b.first, b.last)
        val min2 = minOf(b.first, b.last)
        return (b.first in min..max) || (b.last in min..max)
                || (a.first in min2..max2) || (a.last in min2..max2)
    }

    fun stayOnBrick(another: Brick): Boolean {
        val xOverlap = overlap(this.x1..this.x2, another.x1..another.x2)
        val yOverlap = overlap(this.y1..this.y2, another.y1..another.y2)
        return xOverlap && yOverlap && this.z1 == another.z2 + 1
    }

    fun isCollision(another: Brick): Boolean {
        val xOverlap = overlap(this.x1..this.x2, another.x1..another.x2)
        val yOverlap = overlap(this.y1..this.y2, another.y1..another.y2)
        val zOverlap = overlap(this.z1..this.z2, another.z1..another.z2)
        return xOverlap && yOverlap && zOverlap
    }
}




