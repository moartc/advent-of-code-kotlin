package solutions.aoc2023.day24

import utils.Resources
import utils.parser.getLongs
import java.math.BigInteger

fun main() {

    val inputLine =
        Resources.getLines(2023, 24)
//        Resources.getLinesExample(2023, 24)

//    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")


}

// 12018 too low
var range = 200000000000000L..400000000000000L
//    var range = 7L..27L

data class Hails(var px: Long, var py: Long, var pz: Long, var vx: Long, var vy: Long, var vz: Long) {

}

// TODO clean up / rewrite to be able to get the result for the input data without intermediate manual calculations
fun part2(input: List<String>): Int {

    val allH = mutableListOf<Hails>()
    input.forEach {
        val ints = it.getLongs()
        allH.add(Hails(ints[0], ints[1], ints[2], ints[3], ints[4], ints[5]))
    }


    fun getIntersection(
        p11: Pair<BigInteger, BigInteger>,    // first point line 1
        p12: Pair<BigInteger, BigInteger>,// 2nd point line 1
        p21: Pair<BigInteger, BigInteger>,// first point line 2
        p22: Pair<BigInteger, BigInteger> // 2nd point line 2
    ): Pair<BigInteger, BigInteger> {
        // Line AB represented as a1x + b1y = c1
        val a1 = p12.first - p11.first;
        val b1 = p11.second - p12.second;
        val c1 = a1 * (p11.second) + b1 * (p11.first);

        // Line CD represented as a2x + b2y = c2
        val a2 = p22.first - p21.first;
        val b2 = p21.second - p22.second;
        val c2 = a2 * (p21.second) + b2 * (p21.first);

        val determinant = a1 * b2 - a2 * b1;

        if (determinant == BigInteger.ZERO) {
            return BigInteger.ZERO to BigInteger.ZERO
        } else {
            val y = (b2 * c1 - b1 * c2).divide(determinant)
            val x = (a1 * c2 - a2 * c1).divide(determinant)
            return x to y
        }
    }


    // select 3 random
    var first = allH[1]
    var second = allH[2]
    var third = allH[3]

    // random velocities to test
    for (nvx in -700..700) {
        for (nvy in -700..700) {

            // instead of moving stone, move hailstones with changed velocity
            val firstChanged = first.copy(vx = first.vx - nvx, vy = first.vy - nvy)
            val secondChanged = second.copy(vx = second.vx - nvx, vy = second.vy - nvy)
            val thirdChanged = third.copy(vx = third.vx - nvx, vy = third.vy - nvy)

            val fs = firstChanged.px.toBigInteger() to firstChanged.py.toBigInteger()
            val fe =
                firstChanged.px.toBigInteger() + firstChanged.vx.toBigInteger() to (firstChanged.py.toBigInteger() + firstChanged.vy.toBigInteger())

            val ss = secondChanged.px.toBigInteger() to secondChanged.py.toBigInteger()
            val se =
                secondChanged.px.toBigInteger() + secondChanged.vx.toBigInteger() to (secondChanged.py + secondChanged.vy).toBigInteger()

            val intersection1 = getIntersection(fs, fe, ss, se)


            val ts = thirdChanged.px.toBigInteger() to thirdChanged.py.toBigInteger()
            val te =
                thirdChanged.px.toBigInteger() + thirdChanged.vx.toBigInteger() to (thirdChanged.py + thirdChanged.vy).toBigInteger()

            val intersection2 = getIntersection(fs, fe, ts, te)
            if ((intersection2.first - intersection1.first).abs() < BigInteger.TWO && (intersection2.second - intersection1.second).abs() < BigInteger.TWO) {
                println("intersection == 0 for nvx=$nvx and nvy=$nvy")
                println("intersection1 $intersection1")
                println("intersection2 $intersection2") // it found intersection1 (reversed y x) (441968877324087, 218159652637145) for nvx=131 and nvy=-259
            }
        }
    }

    // change it after result from line aboce
    val newStart = Hails(218159652637145, 441968877324087, 0, 131, -259, 0)
//    val newStart = Hails(24, 13, 0, -3, 1, 0) // for test
    var z: BigInteger = BigInteger.ZERO

    val newS = newStart.px.toBigInteger() to newStart.py.toBigInteger()
    val newE = (newStart.px + newStart.vx).toBigInteger() to (newStart.py + newStart.vy).toBigInteger()
    val fS = first.px.toBigInteger() to first.py.toBigInteger()
    val fE = first.px.toBigInteger() + first.vx.toBigInteger() to (first.py + first.vy).toBigInteger()
    val sS = second.px.toBigInteger() to second.py.toBigInteger()
    val sE = second.px.toBigInteger() + second.vx.toBigInteger() to (second.py + second.vy).toBigInteger()
    val intersectionWithFirst = getIntersection(newS, newE, fS, fE)
    val intersectionWithSecond = getIntersection(newS, newE, sS, sE)

    intersectionWithFirst.log("int with first")
    val steps = (intersectionWithFirst.first - first.px.toBigInteger()).divide(first.vx.toBigInteger())
    println("after $steps steps z is on pos ${first.pz.toBigInteger() + (steps * first.vz.toBigInteger())}")
    intersectionWithSecond.log("int with sec")
    val steps2 = (intersectionWithSecond.first - second.px.toBigInteger()).divide(second.vx.toBigInteger())
    println("after $steps2 steps z is on pos ${second.pz.toBigInteger() + (steps2 * second.vz.toBigInteger())}")

    /*
    after 133276408329 steps z is on pos 234012912244761
    after 504617559466 steps z is on pos 271889709660735
    271889709660735 = pz + vz * 504617559466
    234012912244761 = pz + vz * 133276408329
    =
    37876797415974 = vz 371341151137 => vz = 102
    234012912244761 = pz + 102 * 133276408329
    234012912244761 = pz + 13594193649558
    pz = 220418718595203


    final res (x, y, z) = (218159652637145,441968877324087,220418718595203)
    sum = 880547248556435
     */
//


    return 12
}


fun part1(input: List<String>): Int {

    val allH = mutableListOf<Hails>()
    input.forEach {
        it.log()

        val ints = it.getLongs()
        ints.log("ints")
        allH.add(Hails(ints[0], ints[1], ints[2], ints[3], ints[4], ints[5]))
    }

    fun intersectionOf2LinesGoingThroughPoints(
        p11: Pair<BigInteger, BigInteger>,    // first point line 1
        p12: Pair<BigInteger, BigInteger>,// 2nd point line 1
        p21: Pair<BigInteger, BigInteger>,// first point line 2
        p22: Pair<BigInteger, BigInteger> // 2nd point line 2
    ): Pair<BigInteger, BigInteger> {
        // Line AB represented as a1x + b1y = c1
        val a1 = p12.first - p11.first;
        val b1 = p11.second - p12.second;
        val c1 = a1 * (p11.second) + b1 * (p11.first);

        // Line CD represented as a2x + b2y = c2
        val a2 = p22.first - p21.first;
        val b2 = p21.second - p22.second;
        val c2 = a2 * (p21.second) + b2 * (p21.first);

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
                if (lineLineIntersection.first != BigInteger.ZERO && lineLineIntersection.second != BigInteger.ZERO) {
                    println("!!!!!!!intersection between $h1 and $h2 is = $lineLineIntersection")

                    if (lineLineIntersection.first.toDouble() in doubleRange && lineLineIntersection.second.toDouble() in doubleRange) {
                        // additional check if intersection is in the current range
                        val intX = lineLineIntersection.second.toDouble()
                        val intY = lineLineIntersection.first.toDouble()

                        val firstXOk = (intX >= fxs && h1.vx >= 0) || (intX <= fxs && h1.vx < 0)
                        val firstYOk = (intY >= fys && h1.vy >= 0) || (intY <= fys && h1.vy < 0)

                        val secXOk = (intX >= sxs && h2.vx >= 0) || (intX <= sxs && h2.vx < 0)
                        val secYOk = (intY >= sys && h2.vy >= 0) || (intY <= sys && h2.vy < 0)
                        if (firstYOk && firstXOk && secXOk && secYOk) {
                            lineLineIntersection.log(">>>>>>>>>>>>ctr++ for line intersection")
                            ctr++
                        }
                    }
                }

            }
        }
    }
    return ctr
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



