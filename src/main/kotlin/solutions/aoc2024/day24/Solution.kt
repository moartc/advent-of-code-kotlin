package solutions.aoc2024.day24

import utils.Resources
import utils.collections.extensions.splitOnEmpty

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {
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
    val binaryResult = simulate(initialValues, gates)
    return binaryResult.toLong(2)
}

/*
automated - at least for my input
only 2 cases are supported:
1. the expression assigned to the output is invalid, e.g:
psw OR nng -> z12
but, it should be:
nhb XOR cdq -> z12
expected value (nhb XOR cdq) is assign to 'kth':
nhb XOR cdq -> kth
so replace 'z12' with 'kth'

2. the value in the expression is incorrect
expected
htb AND vpm
but, it contains:
htb AND qnf
because there is no whole expression 'htb AND vpm' that could be used - 'qnf' value is replaced by 'vpm' value.
 */
fun part2(inputLines: List<String>): String {
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
    val xBitsList = initialValues.values.take(midIndex).reversed()
    val yBitsList = initialValues.values.drop(midIndex).reversed()
    val xBinaryString = xBitsList.joinToString("")
    val yBinaryString = yBitsList.joinToString("")
    val xLong = xBinaryString.toLong(2)
    val yLong = yBinaryString.toLong(2)

    val expectedSum = xLong + yLong
    val expectedBinaryArr = expectedSum.toString(2).map { it.digitToInt() }

    fun isCurrentResultAsExpected(currentArray: List<Int>, expectedArray: List<Int>): Boolean {
        for (index in currentArray.indices) {
            if (currentArray[index] != expectedArray[index]) {
                return false
            }
        }
        return true
    }

    /*
     for z04 - n=04
     z04 = A XOR B  - so it's always xor
     A = C OR D
     B = y04 XOR x04  - it's yn XOR xn
     C = E AND F  - it's z{n-1} but with AND
     D = y03 AND x03  - it's x{n-1} AND y{n-1}
     */
    fun check(zToCheck: String, gates: List<Pair<String, String>>): Pair<String, String>? {
        val zIndex = zToCheck.takeLast(2)
        val prevNumb = zIndex.toInt() - 1
        val prevNumStr = if (prevNumb < 10) "0$prevNumb" else prevNumb
        val gatesMap = mutableMapOf<String, String>()
        gates.forEach { gate ->
            gatesMap[gate.second] = gate.first
        }
        // expected values based on patter
        val prevZId = "z$prevNumStr"
        val prevZ = gatesMap[prevZId]!!
        val (expectedValueE, _, expectedValueF) = prevZ.split(" ")
        val expectedValueB = listOf("x$zIndex", "XOR", "y$zIndex")
        val expectedValueC = setOf(expectedValueE, "AND", expectedValueF) //twj and jfr
        val expectedValueD = setOf("x$prevNumStr", "AND", "y$prevNumStr")

        val actualBVariableName = gatesMap.filter { p ->
            p.value.split(" ").toSet().containsAll(expectedValueB)
        }.map { e -> e.key }.first()

        val actualCVariableName = gatesMap.filter { p ->
            p.value.split(" ").toSet().containsAll(expectedValueC)
        }.map { e -> e.key }.first()

        val actualDVariableName = gatesMap.filter { p ->
            p.value.split(" ").toSet().containsAll(expectedValueD)
        }.map { e -> e.key }.first()

        val actualAVariableName = gatesMap.filter { p ->
            p.value.split(" ").toSet().containsAll(setOf(actualCVariableName, "OR", actualDVariableName))
        }.map { e -> e.key }.first()

        val valuesThatShouldContainZ = setOf(actualAVariableName, "XOR", actualBVariableName)
        val valueThatShouldBeAssignToZ = gatesMap.filter { p ->
            p.value.split(" ").toSet().containsAll(valuesThatShouldContainZ)
        }.map { e -> e.key }.firstOrNull()

        // check actual values
        val actualZ = gatesMap[zToCheck]!!
        val actualZContent = actualZ.split(" ").toSet()
        if (!actualZContent.containsAll(valuesThatShouldContainZ)) {
            println("z doesn't contain: $valuesThatShouldContainZ, but contains $actualZContent")
            if (valueThatShouldBeAssignToZ != null) {
                println("the content of z is = $valueThatShouldBeAssignToZ")
                println("to swap '$zToCheck' with '$valueThatShouldBeAssignToZ'")
                return zToCheck to valueThatShouldBeAssignToZ
            } else {
                val missingValue = valuesThatShouldContainZ.minus(actualZContent).first()
                val unwantedValue = actualZContent.minus(valuesThatShouldContainZ).first()
                println("to swap '$missingValue' with '$unwantedValue'")
                return missingValue to unwantedValue
            }
        }
        return null
    }

    fun swapGates(first: String, second: String, gates: MutableList<Pair<String, String>>): MutableList<Pair<String, String>> {
        val indexOfFirst = gates.indexOfFirst { g -> g.second == first }
        val indexOfSecond = gates.indexOfFirst { g -> g.second == second }
        gates[indexOfFirst] = gates[indexOfFirst].first to second
        gates[indexOfSecond] = gates[indexOfSecond].first to first
        return gates
    }

    // skip first 2 and check for the rest sorted alphabetically z02, z03, z04,...
    val swappedOutputs = mutableListOf<String>()
    var gatesToUpdate = gates.toMutableList()
    wh@ while (true) {
        // skip first 2 and the last one
        val toCheck = gatesToUpdate.map { it.second }.filter { it.startsWith("z") }.sorted().drop(2).dropLast(1)
        for (g in toCheck) {
            val checkResult = check(g, gatesToUpdate)
            if (checkResult != null) {
                val (first, second) = checkResult
                gatesToUpdate = swapGates(first, second, gatesToUpdate)
                swappedOutputs.add(first)
                swappedOutputs.add(second)
                continue@wh
            }
        }
        break
    }

    // check if it's correct
    val simulationResultAfterFixes = simulate(initialValues, gatesToUpdate).map { it.digitToInt() }

    if (!isCurrentResultAsExpected(simulationResultAfterFixes, expectedBinaryArr)) {
        throw Exception("The result is not correct after fixes!")
    }

    return swappedOutputs.sorted().joinToString(",")
}

fun simulate(initialValues: Map<String, Int>, gates: List<Pair<String, String>>): String {
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
