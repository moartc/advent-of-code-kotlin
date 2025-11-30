package solutions.aoc2021.day18

import utils.Resources
import kotlin.math.ceil

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {
    val parsed = inputLines.map { l -> parseLine(l) }
    var current = parsed[0]
    current = fullProcess(current)
    for (i in 1..<parsed.size) {
        val toAdd = parsed[i]
        current = add(current, toAdd)
        current = fullProcess(current)
    }
    return calcMag(current)
}

fun part2(inputLines: List<String>): Any {
    val parsed = inputLines.map { l -> parseLine(l) }
    var highestFound = Int.MIN_VALUE
    for (i in 0..parsed.lastIndex) {
        for (j in i + 1..parsed.lastIndex) {
            val s1 = parsed[i]
            val s2 = parsed[j]
            val addRes = add(s1, s2)
            val afterProcess = fullProcess(addRes)
            val calcMag = calcMag(afterProcess)
            highestFound = calcMag.coerceAtLeast(highestFound)
            val addRes2 = add(s2, s1)
            val afterProcess2 = fullProcess(addRes2)
            val calcMag2 = calcMag(afterProcess2)
            highestFound = calcMag2.coerceAtLeast(highestFound)
        }
    }
    return highestFound
}

fun parseLine(string: String): List<ValueLevel> {
    val parsingResult = mutableListOf<ValueLevel>()
    var op = -1
    for (ch in string) {
        when (ch) {
            '[' -> op++
            ']' -> op--
            ',' -> {}
            else -> parsingResult.add(ValueLevel(ch.digitToInt(), op))
        }
    }
    return parsingResult
}

fun explode(list: MutableList<ValueLevel>): Pair<MutableList<ValueLevel>, Boolean> {
    val new = list.toMutableList()
    var i = 0
    var wasExplode = false
    while (i < new.size - 1) {
        val left = new[i]
        val right = new[i + 1]
        if (left.level == 4 && right.level == 4) {
            val leftIndex = i - 1
            if (leftIndex >= 0) {
                val x = new[leftIndex]
                new[leftIndex] = ValueLevel(x.value + left.value, x.level)
            }
            val rightIndex = i + 1 + 1
            if (rightIndex <= new.lastIndex) {
                val x = new[rightIndex]
                new[rightIndex] = ValueLevel(x.value + right.value, x.level)
            }
            new[i] = ValueLevel(0, 3)
            new.removeAt(i + 1)
            wasExplode = true
            break
        } else {
            i++
        }
    }
    return new to wasExplode
}

fun split(list: MutableList<ValueLevel>): Pair<MutableList<ValueLevel>, Boolean> {
    val new = mutableListOf<ValueLevel>()
    var wasSplit = false
    for (vl in list) {
        val (value, level) = vl
        if (value >= 10 && !wasSplit) {
            val newL = value / 2
            val newR = ceil(value / 2.0).toInt()
            new.add(ValueLevel(newL, level + 1))
            new.add(ValueLevel(newR, level + 1))
            wasSplit = true
        } else {
            new.add(vl)
        }
    }
    return new to wasSplit
}

fun add(list1: List<ValueLevel>, list2: List<ValueLevel>): List<ValueLevel> {
    return (list1 + list2).map { el -> ValueLevel(el.value, el.level + 1) }
}

fun fullProcess(toProcess: List<ValueLevel>): List<ValueLevel> {
    var toRet = toProcess.toMutableList()
    while (true) {
        val (newAfterExplosion, exploded) = explode(toRet)
        if (exploded) {
            toRet = newAfterExplosion
            continue
        }
        val (newAfterSplit, wasSplit) = split(toRet)
        if (wasSplit) {
            toRet = newAfterSplit
        }
        if (!wasSplit) {
            return toRet
        }
    }
}

fun calcMag(l: List<ValueLevel>): Int {
    var nl = l
    var toRep = mutableListOf<ValueLevel>()
    while (nl.size > 1) {
        var i = 0
        while (i <= nl.lastIndex - 1) {
            val f = nl[i]
            val s = nl[i + 1]
            if (f.level == s.level) {
                val m = f.value * 3 + s.value * 2
                toRep.add(ValueLevel(m, f.level - 1))
                i += 2
            } else {
                i++
            }
        }
        nl = toRep
        toRep = mutableListOf()
    }
    // might be empty when one element is equal to 0
    return if (nl.isNotEmpty()) nl[0].value else 0
}


data class ValueLevel(val value: Int, val level: Int) {
    override fun toString(): String {
        return "[$value,$level]"
    }
}