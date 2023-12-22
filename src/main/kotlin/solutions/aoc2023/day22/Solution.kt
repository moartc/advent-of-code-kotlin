package solutions.aoc2023.day22

import utils.Resources
import utils.parser.getInt
import utils.parser.getInts

// TODO refactor it when d21p2 is done
fun main() {

    val inputLine =
        Resources.getLines(2023, 22)
//        Resources.getLinesExample(2023, 22)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part2(input: List<String>): Long {

    var ctr = 0
    val bricks = input.map {
        val ints = it.getInts()
        Brick(ctr++, ints[0], ints[1], ints[2], ints[3], ints[4], ints[5])
    }.toMutableList()

    bricks.forEach { it.log() }


    bricks.sortBy { it.z1 }

    val settledBricks = mutableListOf<Brick>()
    b@ for (brick in bricks) {
        if (brick.z1 == 1) { // cant go lower
            settledBricks.add(brick)
        } else {
            var copy = brick.copy() // create copy
            while (true) {
                val newCopy = copy.copy(z1 = copy.z1 - 1, z2 = copy.z2 - 1) // create copy below
                val filter = settledBricks.filter { b -> b.idx != newCopy.idx }
                val isCollision = filter.any { b -> b.isCollision(newCopy) }
                if (isCollision) {
                    settledBricks.add(copy) // then add original
                    continue@b
                } else if (newCopy.z1 == 1) { // is on the ground
                    settledBricks.add(newCopy)
                    continue@b
                } else { // no collision, no on the ground -> try another
                    copy = newCopy
                }
            }
        }
    }

    assert(settledBricks == bricks)


    var brickToGround = mutableMapOf<Brick, MutableSet<Brick>>()

    for ((index1, brick1) in settledBricks.withIndex()) {
        for ((index2, brick2) in settledBricks.withIndex()) {
            if (index1 == index2) continue
            if (brick2.stayOnBrick(brick1)) {
                if (brickToGround[brick2] == null) {
                    brickToGround[brick2] = mutableSetOf()
                }
                brickToGround[brick2]!!.add(brick1)
            }

        }
    }

    "brick to ground".log()
    brickToGround.forEach { it.log() }


    var cannotBeDisint = mutableListOf<Brick>()
    var res = 0
    for (settledBrick in settledBricks) {
        val allThatSupports = brickToGround.filter { entry -> entry.value.contains(settledBrick) }
        if (allThatSupports.any { l -> l.value.size == 1 }) {
            cannotBeDisint.add(settledBrick)
        }
    }

    var ctrDis = 0

    fun countDisintegration(initialBrick: Brick): Int {

        var disintegrated = mutableSetOf(initialBrick)
        do {
            var prevSize = disintegrated.size
            for (mutableEntry in brickToGround) {
                if (mutableEntry.value.none { brickSupporting -> !disintegrated.contains(brickSupporting) }) {
                    // if there is no other support
                    disintegrated.add(mutableEntry.key)
                }
            }
        } while(prevSize != disintegrated.size)
        return disintegrated.size - 1

    }

    var final = 0L
    for (brick in cannotBeDisint) {

        val countDisintegration = countDisintegration(brick)
        countDisintegration.log("for ${brick.idx} res")
        final += countDisintegration
    }

    // 33069 too low
    // 39418 too low

    final.log("final res ")
    return final
}

fun part1(input: List<String>): Int {

    var ctr = 0
    val bricks = input.map {
        val ints = it.getInts()
        Brick(ctr++, ints[0], ints[1], ints[2], ints[3], ints[4], ints[5])
    }.toMutableList()

    bricks.forEach { it.log() }


    bricks.sortBy { it.z1 }

    val settledBricks = mutableListOf<Brick>()
    b@ for (brick in bricks) {
        if (brick.z1 == 1) { // cant go lower
            settledBricks.add(brick)
        } else {
            var copy = brick.copy() // create copy
            while (true) {
                val newCopy = copy.copy(z1 = copy.z1 - 1, z2 = copy.z2 - 1) // create copy below
                val filter = settledBricks.filter { b -> b.idx != newCopy.idx }
                val isCollision = filter.any { b -> b.isCollision(newCopy) }
                if (isCollision) {
                    settledBricks.add(copy) // then add original
                    continue@b
                } else if (newCopy.z1 == 1) { // is on the ground
                    settledBricks.add(newCopy)
                    continue@b
                } else { // no collision, no on the ground -> try another
                    copy = newCopy
                }
            }
        }
    }

    assert(settledBricks == bricks)


    var brickToGround = mutableMapOf<Brick, MutableSet<Brick>>()

    for ((index1, brick1) in settledBricks.withIndex()) {
        for ((index2, brick2) in settledBricks.withIndex()) {
            if (index1 == index2) continue
            if (brick2.stayOnBrick(brick1)) {
                if (brickToGround[brick2] == null) {
                    brickToGround[brick2] = mutableSetOf()
                }
                brickToGround[brick2]!!.add(brick1)
            }

        }
    }

    "brick to ground".log()
    brickToGround.forEach { it.log() }


    var res = 0
    for (settledBrick in settledBricks) {
        val allThatSupports = brickToGround.filter { entry -> entry.value.contains(settledBrick) }
        if (allThatSupports.any { l -> l.value.size == 1 }) {
            // if it's the only one
            continue
        } else {
            res++
        }
    }
    res.log("result")

    return res
}


data class Brick(var idx: Int, var x1: Int, var y1: Int, var z1: Int, var x2: Int, var y2: Int, var z2: Int) {

    fun overlap(a: IntProgression, b: IntProgression): Boolean {
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
        if (xOverlap && yOverlap && this.z1 == another.z2 + 1) {
            return true
        } else {
            return false
        }
    }

    fun isCollision(another: Brick): Boolean {
//        another.log("compare $this with ")

        val xOverlap = overlap(this.x1..this.x2, another.x1..another.x2)
        val yOverlap = overlap(this.y1..this.y2, another.y1..another.y2)
        val zOverlap = overlap(this.z1..this.z2, another.z1..another.z2)
        if (xOverlap && yOverlap && zOverlap) {
            return true
        } else {
            return false
        }
    }
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



