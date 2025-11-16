package solutions.aoc2021.day16

import utils.Resources
import utils.collections.extensions.product

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {
    val line = inputLines[0]
    val str = line.map { c ->
        hexCharToDecimal4(c)
    }.joinToString("")
    return parseOneForPart1(str, 0).second
}

fun part2(inputLines: List<String>): Any {
    val line = inputLines[0]
    val str = line.map { c ->
        hexCharToDecimal4(c)
    }.joinToString("")
    return parseOneForPart2(str, 0).second
}

fun getNumbFromLiteral(str: String, start: Int): Pair<Long, Int> {
    var i = start
    var v = ""
    while (i < str.length) {
        val c = str[i]
        if (c == '1') {
            v += str.substring(i + 1, i + 5)
            i += 5
        } else {
            v += str.substring(i + 1, i + 5)
            break
        }
    }
    return v.toLong(2) to i + 5
}


fun calcPart2ValueForSubpackages(packetTypeInt: Int, allSubpackages: List<Long>): Long {
    return when (packetTypeInt) {
        0 -> allSubpackages.sum()
        1 -> allSubpackages.product()
        2 -> allSubpackages.min()
        3 -> allSubpackages.max()
        5 -> if (allSubpackages[0] > allSubpackages[1]) 1 else 0
        6 -> if (allSubpackages[0] < allSubpackages[1]) 1 else 0
        else -> if (allSubpackages[0] == allSubpackages[1]) 1 else 0 // 7
    }
}

// return last idx to total value
fun parseOneForPart2(str: String, startIdx: Int): Pair<Int, Long> {
    val allSubpackages = mutableListOf<Long>()
    val packetType = str.substring(startIdx + 3, startIdx + 6)
    val packetTypeInt = packetType.toInt(2)
    if (packetTypeInt != 4) {
        val lengthType = str[startIdx + 6]
        if (lengthType == '0') {
            val bitsInSubpacket = str.substring(startIdx + 7, startIdx + 7 + 15)
            val bitsInThisPacket = bitsInSubpacket.toInt(2)
            val substringToParse = str.substring(startIdx + 7 + 15, startIdx + 7 + 15 + bitsInThisPacket)
            var startSub = 0
            while (startSub < bitsInThisPacket) {
                val (next, value) = parseOneForPart2(substringToParse, startSub)
                allSubpackages.add(value)
                startSub = next
            }
            return startIdx + 7 + 15 + bitsInThisPacket to calcPart2ValueForSubpackages(packetTypeInt, allSubpackages)
        } else {
            val numbOfSubpackets = str.substring(startIdx + 7, startIdx + 7 + 11)
            val nosInt = numbOfSubpackets.toLong(2)
            var currSub = 0
            var currStart = startIdx + 7 + 11
            while (currSub < nosInt) {
                val (next, value) = parseOneForPart2(str, currStart)
                allSubpackages.add(value)
                currStart = next
                currSub++
            }
            return currStart to calcPart2ValueForSubpackages(packetTypeInt, allSubpackages)
        }
    } else { // literal
        val (numb, idx) = getNumbFromLiteral(str, startIdx + 6)
        return idx to numb
    }
}


fun hexCharToDecimal4(c: Char): String {
    val v = c.digitToInt(16)
    return v.toString(2).padStart(4, '0')
}


fun parseOneForPart1(str: String, startIdx: Int): Pair<Int, Int> {
    val ver = str.substring(startIdx, startIdx + 3)
    val verInt = ver.toInt(2)
    var totalVersion = verInt
    val packetType = str.substring(startIdx + 3, startIdx + 6)
    val packetTypeInt = packetType.toInt(2)
    if (packetTypeInt != 4) {
        val lengthType = str[startIdx + 6]
        if (lengthType == '0') {
            val bitsInSubpacket = str.substring(startIdx + 7, startIdx + 7 + 15)
            val bitsInThisPacket = bitsInSubpacket.toInt(2)
            val substringToParse = str.substring(startIdx + 7 + 15, startIdx + 7 + 15 + bitsInThisPacket)
            var startSub = 0
            while (startSub < bitsInThisPacket) {
                val (next, ver) = parseOneForPart1(substringToParse, startSub)
                totalVersion += ver
                startSub = next
            }
            return startIdx + 7 + 15 + bitsInThisPacket to totalVersion
        } else {
            val numbOfSubpackets = str.substring(startIdx + 7, startIdx + 7 + 11)
            val nosInt = numbOfSubpackets.toLong(2)
            var currSub = 0
            var currStart = startIdx + 7 + 11
            while (currSub < nosInt) {
                val (next, ver) = parseOneForPart1(str, currStart)
                totalVersion += ver
                currStart = next
                currSub++
            }
            return currStart to totalVersion
        }
    } else { // literal
        val (_, idx) = getNumbFromLiteral(str, startIdx + 6)
        return idx to totalVersion
    }
}

