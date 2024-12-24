package solutions.aoc2024.day24

import utils.Resources
import utils.collections.extensions.splitOnEmpty
import java.math.BigInteger

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

// todo clean it up and automate somehow
fun part2(inputLines: List<String>): Any {
    val splitOnEmpty = inputLines.splitOnEmpty()
    val firstPart = splitOnEmpty[0]
    val secondPart = splitOnEmpty[1]

    val initialValues = firstPart.map {
        val s = it.split(": ")
        s[0] to s[1].toInt()
    }.toMap()

    val gates = secondPart.map {
        val v = it.split(" -> ")
        val p1 = v[0]
        val p2 = v[1]
        p1 to p2
    }

    val midIndex = initialValues.values.size / 2
    val firstHalf = initialValues.values.toList().subList(0, midIndex).reversed()
    val secondHalf = initialValues.values.toList().subList(midIndex, initialValues.values.size).toList().reversed()

    firstHalf.log("first")
    secondHalf.log("second")

    val bin1 = firstHalf.joinToString("")
    val bin2 = secondHalf.joinToString("")

    bin1.log("bin1")
    bin2.log("bin2")

    val bigInteger1 = BigInteger(bin1, 2)
    val bigInteger2 = BigInteger(bin2, 2)

    bigInteger1.log("big int 1")
    bigInteger2.log("big int 2")

    val expectedSum = bigInteger1 + bigInteger2
    expectedSum.log("exp")

    val expectedBinary = expectedSum.toString(2)
    expectedBinary.log("expected binary")
    val expectedIntArr = expectedBinary.map { it.digitToInt() }.toIntArray()

    val binaryResult = calcRes(initialValues, gates)
    val actualArray = binaryResult.map { it.digitToInt() }

    /*
     for  z02
     z02 = rsk XOR rhr
     rsk = x02 XOR y02
     rhr = cbq OR gwd
     gwd = y01 AND x01
     cbq = twd AND bdj
     twd = y01 XOR x01
     bdj = x00 AND y00

     for z03
     z03 = jfr XOR twj
     jfr = x03 XOR y03
     twj = fph OR nkm
     nkm = rhr AND rsk - looks like z02 but with AND
     fph = y02 AND x02

     for z04
     z04 = dft XOR vwf  - so it's always xor
     dft = nbj OR rnw
     vwf = y04 XOR x04  - it's always yn XOR xn for zn
     nbj = twj AND jfr  - again - like z03 but with and
     rnw = y03 AND x03
     ... and probably so on
     */

    fun isOk(zToCheck: String, gates: List<Pair<String, String>>) {
        var numb = zToCheck.takeLast(2)
        val gateMap = mutableMapOf<String, String>()
        gates.forEach { gate ->
            gateMap[gate.second] = gate.first
        }
        val first = gateMap[zToCheck]

        val (left, op, right) = first!!.split(" ")
        if (op != "XOR") {
            println("operator should be xor!!!")
            return
        }
        val lToCheck1 = gateMap[left]!!
        val rToCheck1 = gateMap[right]!!
        val (ll, lo, lr) = lToCheck1.split(" ")
        val (rl, ro, rr) = rToCheck1.split(" ")
        // one of them should be xn yn with or
        if (lo != "XOR" && ro != "XOR") {
            println("missing xor!")
        } else if (lo == "XOR") {
            if (!setOf(ll, lr).sorted().containsAll(setOf("x$numb", "y$numb"))) {
                println("no x y atts")
            }
        } else {
            // here should be xor for ro
            if (!setOf(rl, rr).sorted().containsAll(setOf("x$numb", "y$numb"))) {
                println("no x y atts")
            }
        }
        var wasLeftXor = lo == "XOR"

        var (ltodo, optodo, rtodo) = if (wasLeftXor) rToCheck1.split(" ") else lToCheck1.split(" ")

        if (optodo != "OR") {
            println("op should be or!!!")
        }

        var prevNumb = numb.toInt() - 1
        var prevNumStr = if (prevNumb < 10) "0$prevNumb" else prevNumb
        var prevZ = gateMap["z$prevNumStr"]!!


        val left2 = gateMap[ltodo]!!
        val right2 = gateMap[rtodo]!!

        var (l3, o3, r3) = left2.split(" ")
        var (l4, o4, r4) = right2.split(" ")

        if (!l3.startsWith("x")
            && !r3.startsWith("x")
            && !l4.startsWith("x")
            && !r4.startsWith("x")
        ) {
            println("missing x here <-!")
        }

        var foundInFirst = false
        if (l3.startsWith("x") || r3.startsWith("x")) {
            foundInFirst = true
            if (!setOf(l3, r3).containsAll(setOf("x${prevNumStr}", "y${prevNumStr}"))) {
                println("some problem 123")
            }
            if (o3 != "AND") {
                println("No AND op!")
            }
        } else {
            if (!setOf(l4, r4).containsAll(setOf("x${prevNumStr}", "y${prevNumStr}"))) {
                println("some problem 123")
            }
            if (o4 != "AND") {
                println("No AND op!")
            }
        }
        // x is check now if it's line in prev z
        val (prevZL, _, prevZR) = prevZ.split(" ")

        val (left5, op5, right5) = if (foundInFirst) right2.split(" ") else left2.split(" ")

        if (!setOf(prevZL, prevZR).containsAll(setOf(left5, right5))) {
            println(
                "here is missing somethinf!\n" +
                        "${setOf(prevZL, prevZR)} should contain${setOf(left5, right5)}"
            )
        }
        if (op5 != "AND") {
            println("op is not And!")
        }
    }

    /*
    Changes manual
    vpm -> qnf because of missing xor
    z32 -> tbt
    kth -> z12
    z26 -> gsd
     */

    // answer part, how many wrong should be 0 below!
    setOf("vpm", "qnf", "z32", "tbt", "kth", "z12", "z26", "gsd").sorted().joinToString(",").log("answer")

    // -------------------------
    val toCheck = gates.map { it.second }.filter { it.startsWith("z") && it != "z00" && it != "z01" }.sorted()
    toCheck.forEach { g ->
        println("-------------- checking $g ---------------")
        isOk(g, gates)
        println("-----------------------------------------")
    }


    var howManyWrong = 0
    for (index in actualArray.indices) {
        if (actualArray[index] != expectedIntArr[index]) {
            howManyWrong++
        }
    }
    howManyWrong.log("there was wrong:")
    println(binaryResult)
    return 12
}

fun part1(inputLines: List<String>): BigInteger {
    val splitOnEmpty = inputLines.splitOnEmpty()
    val firstPart = splitOnEmpty[0]
    val secondPart = splitOnEmpty[1]
    val initialValues = firstPart.map {
        val s = it.split(": ")
        s[0] to s[1].toInt()
    }.toMap()

    val gates = secondPart.map {
        val v = it.split(" -> ")
        val p1 = v[0]
        val p2 = v[1]
        p1 to p2
    }

    val binaryResult = calcRes(initialValues, gates)
    return BigInteger(binaryResult, 2)
}

fun calcRes(initialValues: Map<String, Int>, gates: List<Pair<String, String>>): String {
    val wires = initialValues.toMutableMap()
    while (true) {
        var updated = false
        for ((operation, output) in gates) {
            if (wires.containsKey(output)) {
                continue
            }

            val (part1, op, part2) = operation.split(" ")
            if (!wires.containsKey(part1) || !wires.containsKey(part2)) {
                continue
            }
            val left = wires[part1]!!
            val right = wires[part2]!!

            val result = when (op) {
                "AND" -> left and right
                "OR" -> left or right
                "XOR" -> left xor right
                else -> throw Exception("Unknown op:$op")
            }
            wires[output] = result
            updated = true
        }

        if (!updated) {
            break
        }
    }

    val zValues = wires.filterKeys { it.startsWith("z") }
    val binaryResult = zValues.entries.sortedByDescending { it.key }.joinToString("") { it.value.toString() }
    return binaryResult
}

private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }