package solutions.aoc2015.day16

import utils.Resources

fun main() {

    val inputLines = Resources.getLines(2015, 16)
    val sueList = inputLines.map { s -> parseString(s) }
    val compounds = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1,
    )
    println("part1 = " + part1(sueList, compounds))
    println("part2 = " + part2(sueList, compounds))
}

fun part1(sueList: List<Map<String, Int>>, compounds: Map<String, Int>): Int {
    return sueList.indexOfFirst { matchCompound(it, compounds) } + 1
}

fun part2(sueList: List<Map<String, Int>>, compounds: Map<String, Int>): Int {
    return sueList.indexOfFirst { matchCompoundPart2(it, compounds) } + 1
}

fun parseString(line: String): Map<String, Int> =
    line.substring(line.indexOf(":") + 2).split(", ")
        .map { spec -> spec.split(": ") }
        .associate { split -> split[0] to split[1].toInt() }


fun matchCompound(sueMap: Map<String, Int>, compounds: Map<String, Int>) =
    sueMap.entries.all { compounds[it.key] == it.value }

fun matchCompoundPart2(sueMap: Map<String, Int>, compounds: Map<String, Int>) =
    sueMap.entries.all {
        when (it.key) {
            "cats", "trees" -> it.value > compounds[it.key]!!
            "pomeranians", "goldfish" -> it.value < compounds[it.key]!!
            else -> compounds[it.key] == it.value
        }
    }

