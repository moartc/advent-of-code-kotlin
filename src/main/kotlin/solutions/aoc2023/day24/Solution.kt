package solutions.aoc2023.day24

import utils.Resources
import utils.parser.getLongs
import java.math.BigInteger

fun main() {

    val inputLine = Resources.getLines(2023, 24)

    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

data class Hails(var px: Long, var py: Long, var pz: Long, var vx: Long, var vy: Long, var vz: Long)

fun intersectionOf2LinesGoingThroughPoints(
    p11: Pair<Long, Long>,  // first X
    p12: Pair<Long, Long>,  // first Y
    p21: Pair<Long, Long>,  // second X
    p22: Pair<Long, Long>   // second Y
): Pair<Long, Long> {       // returns X Y
    val a1 = p12.first - p11.first
    val b1 = p11.second - p12.second
    val c1 = a1 * (p11.second) + b1 * (p11.first)
    val a2 = p22.first - p21.first
    val b2 = p21.second - p22.second
    val c2 = a2 * (p21.second) + b2 * (p21.first)
    val determinant = a1 * b2 - a2 * b1

    if (determinant == 0L) {
        return 0L to 0L
    } else {
        val y = (b2.toBigInteger() * c1.toBigInteger() - b1.toBigInteger() * c2.toBigInteger()).divide(determinant.toBigInteger())
        val x = (a1.toBigInteger() * c2.toBigInteger() - a2.toBigInteger() * c1.toBigInteger()).divide(determinant.toBigInteger())
        return x.toLong() to y.toLong()
    }
}


fun part1(input: List<String>): Int {

    val range = 200000000000000L..400000000000000L
    val allH = mutableListOf<Hails>()
    input.forEach {
        val ints = it.getLongs()
        allH.add(Hails(ints[0], ints[1], ints[2], ints[3], ints[4], ints[5]))
    }

    var ctr = 0
    for ((index1, h1) in allH.withIndex()) {
        val fxs = h1.px
        val fxe = h1.px + h1.vx
        val fys = h1.py
        val fye = h1.py + h1.vy

        val a = fxs to fys
        val b = fxe to fye
        for ((index2, h2) in allH.withIndex()) {
            if (index1 < index2) {
                val sxs = h2.px
                val sxe = h2.px + h2.vx
                val sys = h2.py
                val sye = h2.py + h2.vy

                val c = sxs to sys
                val d = sxe to sye

                val lineLineIntersection = intersectionOf2LinesGoingThroughPoints(a, b, c, d)
                if (lineLineIntersection.first != 0L && lineLineIntersection.second != 0L) {

                    if (lineLineIntersection.first in range && lineLineIntersection.second in range) {
                        // additional check if intersection is in the current range
                        val intX = lineLineIntersection.first
                        val intY = lineLineIntersection.second
                        val firstYOk = (intY >= fys && h1.vy >= 0) || (intY <= fys && h1.vy < 0)
                        val secXOk = (intX >= sxs && h2.vx >= 0) || (intX <= sxs && h2.vx < 0)
                        if (firstYOk && secXOk) {
                            ctr++
                        }
                    }
                }
            }
        }
    }
    return ctr
}

fun part2(input: List<String>): BigInteger {

    val allH = mutableListOf<Hails>()
    input.forEach {
        val ints = it.getLongs()
        allH.add(Hails(ints[0], ints[1], ints[2], ints[3], ints[4], ints[5]))
    }

    // select 3 random
    val first = allH[1]
    val second = allH[2]
    val third = allH[3]


    // random velocities to test
    for (nvx in -700..700) {
        for (nvy in -700..700) {

            // instead of moving stone, move hailstones with changed velocity
            val firstChanged = first.copy(vx = first.vx - nvx, vy = first.vy - nvy)
            val secondChanged = second.copy(vx = second.vx - nvx, vy = second.vy - nvy)
            val thirdChanged = third.copy(vx = third.vx - nvx, vy = third.vy - nvy)

            val fs = firstChanged.px to firstChanged.py
            val fe = firstChanged.px + firstChanged.vx to (firstChanged.py + firstChanged.vy)

            val ss = secondChanged.px to secondChanged.py
            val se = secondChanged.px + secondChanged.vx to (secondChanged.py + secondChanged.vy)
            val intersection1 = intersectionOf2LinesGoingThroughPoints(fs, fe, ss, se)

            val ts = thirdChanged.px to thirdChanged.py
            val te = thirdChanged.px + thirdChanged.vx to (thirdChanged.py + thirdChanged.vy)
            val intersection2 = intersectionOf2LinesGoingThroughPoints(fs, fe, ts, te)

            if (intersection1.first != 0L && intersection2.first != 0L
                && intersection2.first - intersection1.first == 0L
                && intersection2.second - intersection1.second == 0L
            ) {
                val foundX = intersection1.first
                val foundY = intersection1.second

                val intersectionWithFirst = intersectionOf2LinesGoingThroughPoints(
                    foundX to foundY,
                    foundX + nvx to foundY + nvy,
                    first.px to first.py,
                    first.px + first.vx to first.py + first.vy
                )
                val intersectionWithSecond = intersectionOf2LinesGoingThroughPoints(
                    foundX to foundY,
                    foundX + nvx to foundY + nvy,
                    second.px to second.py,
                    second.px + second.vx to second.py + second.vy
                )
                val firstIntersectionSteps = (intersectionWithFirst.first - first.px) / first.vx
                val secondIntersectionSteps = (intersectionWithSecond.first - second.px) / second.vx

                val zPos1 = first.pz + (firstIntersectionSteps * first.vz)
                val zPos2 = second.pz + (secondIntersectionSteps * second.vz)

                val diffIntersections = zPos2 - zPos1
                val diffSteps = secondIntersectionSteps - firstIntersectionSteps
                val newVz = diffIntersections / diffSteps
                val pz = zPos1 - (newVz * firstIntersectionSteps)
                return pz.toBigInteger() + foundX.toBigInteger() + foundY.toBigInteger()
            }
        }
    }
    // if not found
    return BigInteger.ZERO

}




