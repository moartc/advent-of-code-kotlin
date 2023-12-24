package solutions.aoc2023.day24

import utils.Resources
import utils.parser.getLongs
import java.math.BigInteger
import kotlin.math.*

fun main() {

    val inputLine =
        Resources.getLines(2023, 24)
//        Resources.getLinesExample(2023, 24)

    println("part1 = ${part1(inputLine)}")


}
// 12018 too low
var range = 200000000000000L..400000000000000L
//    var range = 7L..27L

data class Hails(var px: Long, var py: Long, var pz: Long, var vx: Long, var vy: Long, var vz: Long) {

    fun movep1() {
        this.px += vx
        this.py += vy
//        this.pz += pz
    }

//    fun distanceToRange(range: LongRange): Pair<Long, Long> {
//        var yd = -1
//        var xd = -1
//        if (py < range.first) {
//            yd = range.first - py
//        } else {
//            yd = yd - range.last
//        }
//        if (py < range.first) {
//            xd = range.first - px
//        } else if (py in range && px in range) {
//            return 0 to 0
//        }
//
//        return yd to xd
//    }

    fun inAreaWithPast(r: LongRange): LongRange {
        println("in area for $px $py")

        var yInRangeAfter = -1L
        var xInRangeAfter = -1L

        var xsir = -1L
        var ysir = -1L

        var yOnPos = -1L
        var xOnPos = -1L


        if (py < r.first) {
            if (vy > 0) {
                var dist = r.first - py
                yInRangeAfter = ceil(dist / vy.toDouble()).toLong().absoluteValue
                yOnPos = py + yInRangeAfter * vy

            } else {
                return -1L..-1L
            }
        } else if (py > r.last) {
            if (vy < 0) {
                var dist = py - r.last
                yInRangeAfter = floor(dist / vy.toDouble()).toLong().absoluteValue
                yOnPos = py - yInRangeAfter * vy
            } else {
                return -1L..-1L
            }
        }

        if (px < r.first) {
            if (vx > 0) {
                var dist = r.first - px
                xInRangeAfter = ceil(dist / vx.toDouble()).toLong().absoluteValue
                yOnPos = px + yInRangeAfter * vy

            } else {
                return -1L..-1L
            }
        } else if (px > r.last) {
            if (vx < 0) {
                var dist = px - r.last
                xInRangeAfter = floor(dist / vx.toDouble()).toLong().absoluteValue
                xOnPos = px - yInRangeAfter * vx
            }
        }

        if (py in r) {
            if (vy > 0) {
                ysir = (r.last - py) / vy.absoluteValue
            } else {
                ysir = (py - r.first) / vy.absoluteValue
            }
        }

        if (px in r) {
            if (vx > 0) {
                xsir = (r.last - px) / vx.absoluteValue
            } else {
                xsir = (px - r.first) / vx.absoluteValue
            }
        }

        if (ysir == -1L && yInRangeAfter != -1L) {
            if (vy > 0) {
                ysir = (r.last - yOnPos) / vy.absoluteValue
            } else {
                ysir = (yOnPos - r.first) / vy.absoluteValue
            }
        }

        if (xsir == -1L && xInRangeAfter != -1L) {
            if (vx > 0) {
                xsir = (r.last - xOnPos) / vx.absoluteValue
            } else {
                xsir = (xOnPos - r.first) / vx.absoluteValue
            }
        }

        var yFinS = if (yInRangeAfter == -1L) 0 else yInRangeAfter
        var xFinS = if (xInRangeAfter == -1L) 0 else xInRangeAfter

        var yFinEnd = yFinS + ysir
        var xFinEnd = xFinS + xsir

        val yRange = yFinS..yFinEnd
        val xRange = xFinS..xFinEnd

        yRange.log("yRange")
        xRange.log("xRange")
        val intsc = max(yRange.first, xRange.first)..min(yRange.last, xRange.last)
        intsc.log("intersection")
        println("------------")
        return intsc
    }


}

fun part1(input: List<String>): Int {

    val allH = mutableListOf<Hails>()
    input.forEach {
        it.log()

        val ints = it.getLongs()
        ints.log("ints")
        allH.add(Hails(ints[0], ints[1], ints[2], ints[3], ints[4], ints[5]))
    }




    val associateWith = allH.associateWith {
        it.inAreaWithPast(range)
    }

    // ----------------


    fun intersectionOf2LinesGoingThroughPoints(
        A: Pair<BigInteger, BigInteger>,    // first point line 1
        B: Pair<BigInteger, BigInteger>,// 2nd point line 1
        C: Pair<BigInteger, BigInteger>,// first point line 2
        D: Pair<BigInteger, BigInteger> // 2nd point line 2
    ): Pair<BigInteger, BigInteger> {
        // Line AB represented as a1x + b1y = c1
        val a1 = B.first - A.first;
        val b1 = A.second - B.second;
        val c1 = a1 * (A.second) + b1 * (A.first);

        // Line CD represented as a2x + b2y = c2
        val a2 = D.first - C.first;
        val b2 = C.second - D.second;
        val c2 = a2 * (C.second) + b2 * (C.first);

        val determinant = a1 * b2 - a2 * b1;

        if (determinant == BigInteger.ZERO) {
            return BigInteger.ZERO to BigInteger.ZERO
        } else {
            val x = (b2 * c1 - b1 * c2).divide(determinant)
            val y = (a1 * c2 - a2 * c1).divide(determinant)
            return x to y
        }
    }

    //-------------

    val doubleRange = range.first.toDouble()..range.last.toDouble()

    var ctr = 0
//    for ((index1, h1) in allH.withIndex()) {
//        println("for h1 = $h1")
//        val r1 = associateWith[h1]!!
//        val fxs = h1.px + (h1.vx * r1.first)
//        val fxe = h1.px + (h1.vx * r1.last)
//        val fys = h1.py + (h1.vy * r1.first)
//        val fye = h1.py + (h1.vy * r1.last)
//
//        val a = fxs to fys
//        val b = fxe to fye
//        println("1st start $a end $b")
//        for ((index2, h2) in allH.withIndex()) {
//            val r2 = associateWith[h2]!!
//            if (index1 < index2) {
//                val sxs = h2.px + (h2.vx * r2.first)
//                val sxe = h2.px + (h2.vx * r2.last)
//                val sys = h2.py + (h2.vy * r2.first)
//                val sye = h2.py + (h2.vy * r2.last)
//
//                val c = sxs to sys
//                val d = sxe to sye
//
//                println("for h2 = $h2")
//                println("2nd start $c end $d")
//
//                val lineLineIntersection = intersectionOf2LinesGoingThroughPoints(a, b, c, d)
//                println("!!!!!!!intersection between $h1 and $h2 is = $lineLineIntersection")
//
//                if (lineLineIntersection.first in doubleRange && lineLineIntersection.second in doubleRange) {
//                    // additional check if intersection is in the current range
//                    val intX = lineLineIntersection.second
//                    val intY = lineLineIntersection.first
//
//                    val firstXOk = intX in min(fxs.toDouble(), fxe.toDouble())..max(fxs.toDouble(), fxe.toDouble())
//                    val firstYOk = intY in min(fys.toDouble(), fye.toDouble())..max(fys.toDouble(), fye.toDouble())
//                    val secXOk = intX in min(sxs.toDouble(), sxe.toDouble())..max(sxs.toDouble(), sxe.toDouble())
//                    val secYOk = intY in min(sys.toDouble(), sye.toDouble())..max(sys.toDouble(), sye.toDouble())
//                    if (firstYOk && firstXOk && secXOk && secYOk) {
//                        lineLineIntersection.log(">>>>>>>>>>>>ctr++ for line intersection")
//                        ctr++
//                    }
//                }
//            }
//        }
//    }
    for ((index1, h1) in allH.withIndex()) {
        println("for h1 = $h1")
        val fxs = h1.px
        val fxe = h1.px + h1.vx
        val fys = h1.py
        val fye = h1.py + h1.vy

        val a = fxs.toBigInteger() to fys.toBigInteger()
        val b = fxe.toBigInteger() to fye.toBigInteger()
        println("1st start $a end $b")
        for ((index2, h2) in allH.withIndex()) {
            if (index1 < index2) {
                val sxs = h2.px
                val sxe = h2.px + h2.vx
                val sys = h2.py
                val sye = h2.py + h2.vy

                val c = sxs.toBigInteger() to sys.toBigInteger()
                val d = sxe.toBigInteger() to sye.toBigInteger()

                println("for h2 = $h2")
                println("2nd start $c end $d")

                val lineLineIntersection = intersectionOf2LinesGoingThroughPoints(a, b, c, d)
                if(lineLineIntersection.first != BigInteger.ZERO && lineLineIntersection.second != BigInteger.ZERO) {
                    println("!!!!!!!intersection between $h1 and $h2 is = $lineLineIntersection")

                    if (lineLineIntersection.first.toDouble() in doubleRange && lineLineIntersection.second.toDouble() in doubleRange) {
                        // additional check if intersection is in the current range
                        val intX = lineLineIntersection.second.toDouble()
                        val intY = lineLineIntersection.first.toDouble()

                        val firstXOk = (intX >= fxs && h1.vx >=0) || (intX <= fxs && h1.vx <0)
                        val firstYOk = (intY >= fys && h1.vy >=0) || (intY <= fys && h1.vy <0)

                        val secXOk = (intX >= sxs && h2.vx >=0) || (intX <= sxs && h2.vx <0)
                        val secYOk = (intY >= sys && h2.vy >=0) || (intY <= sys && h2.vy <0)
                        if (firstYOk && firstXOk && secXOk && secYOk) {
                            lineLineIntersection.log(">>>>>>>>>>>>ctr++ for line intersection")
                            ctr++
                        }
                    }
                }

            }
        }
    }

//    val lineLineIntersection = lineLineIntersection(a, b, c, d)
//    lineLineIntersection.log("line intersection test")


    return ctr
}

fun crossInside(h1: Hails, h2: Hails, s: Int, e: Int): Boolean {
    val range = s..e
    if (h1.px in range && h2.px in range && h1.py in range && h2.py in range) {
        return true
    } else {
        return false
    }

}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



