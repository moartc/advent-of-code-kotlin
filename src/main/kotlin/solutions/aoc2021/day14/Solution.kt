package solutions.aoc2021.day14

import utils.Resources
import utils.collections.extensions.toFrequencyMap

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


/*
I could use the solution implemented for the 2nd part, but I'm keeping my original one
 */
fun part1(inputLines: List<String>): Long {
    val pattern = inputLines[0]
    val mapping = inputLines.drop(2).associate { string ->
        val split = string.split(" -> ")
        val two = split[0]
        val last = split[1]
        two to last[0]
    }

    var s = pattern
    var sn = ""
    repeat(10) {
        for ((index, string) in s.windowed(2, 1).withIndex()) {
            val n = mapping[string]
            if (n != null) {
                val get = string[0]
                if (index == 0) {
                    sn += get
                }
                sn += (n.toString() + string[1])
            }
        }
        s = sn
        sn = ""
    }
    val toSortedMap = s.toList().toFrequencyMap().values.sorted()
    return toSortedMap.last() - toSortedMap.first()
}

fun part2(inputLines: List<String>): Any {
    val pattern = inputLines[0]
    val mapping = inputLines.drop(2).associate { string ->
        val split = string.split(" -> ")
        val two = split[0]
        val last = split[1]
        two to last[0]
    }

    var newMap = mutableMapOf<String, Long>()
    for (string in pattern.windowed(2, 1)) {
        val currV = newMap.getOrDefault(string, 0)
        newMap[string] = currV + 1
    }
    repeat(40) {
        val newNewM = mutableMapOf<String, Long>()
        for ((k, vI) in newMap.entries) {
            val v = mapping[k]!!
            val v1 = k[0].toString() + v
            val v2 = v.toString() + k[1]
            newNewM[v1] = newNewM.getOrDefault(v1, 0) + vI
            newNewM[v2] = newNewM.getOrDefault(v2, 0) + vI
        }
        newMap = newNewM.toMutableMap()
        newNewM.clear()
    }
    val fm = mutableMapOf<Char, Long>()
    newMap.entries.forEachIndexed { i, (pair, count) ->
        if (i == 0) {    // skip the first char for all but the first one
            val fc = pair[0]
            fm[fc] = 1L
        }
        val sc = pair[1]
        val c2 = fm.getOrDefault(sc, 0)
        fm[sc] = c2 + count
    }
    val toSortedMap = fm.values.sorted()
    return toSortedMap.last() - toSortedMap.first()
}
