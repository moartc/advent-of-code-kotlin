package solutions.aoc2015.day13

import utils.Resources

fun main() {
    val inputLines = Resources.getLines(2015, 13)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(input: List<String>): Int {
    val map = parseInput(input)
    return generatePermutations(map.keys.toList())
        .map { list -> list.mapIndexed { index, string -> if (index < list.size - 1) string to list[index + 1] else string to list[0] } }
        .maxOf { p -> getTotalChangeInHappiness(p, map) }
}

fun part2(input: List<String>): Int {
    val map = parseInput(input)
    return generatePermutations(listOf(*map.keys.toList().toTypedArray() + "Me"))
        .map { list -> list.mapIndexed { index, string -> if (index < list.size - 1) string to list[index + 1] else string to list[0] } }
        .maxOf { p -> getTotalChangeInHappinessPart2(p, map) }
}

fun getTotalChangeInHappiness(list: List<Pair<String, String>>, inputMap: Map<String, List<Pair<String, Int>>>) =
    list.sumOf { pair -> inputMap[pair.first]!!.first { p -> p.first == pair.second }.second + inputMap[pair.second]!!.first { p -> p.first == pair.first }.second }

fun getTotalChangeInHappinessPart2(list: List<Pair<String, String>>, inputMap: Map<String, List<Pair<String, Int>>>) =
    list.sumOf { pair -> if (pair.first == "Me" || pair.second == "Me") 0 else inputMap[pair.first]!!.first { p -> p.first == pair.second }.second + inputMap[pair.second]!!.first { p -> p.first == pair.first }.second }

fun generatePermutations(keys: List<String>): List<List<String>> = addNextPerson(listOf(listOf(keys[0])), keys.toList())

fun addNextPerson(startList: List<List<String>>, all: List<String>): List<List<String>> {
    val list =
        startList.flatMap { list -> all.filter { p -> !list.contains(p) }.map { p -> listOf(*list.toTypedArray(), p) } }
    if (list[0].containsAll(all)) return list
    return addNextPerson(list, all)
}

fun parseInput(line: List<String>) =
    line.map { l -> Regex("""(.+) would (.+) (\d+).+ (.+).""").matchEntire(l) }
        .map { matchResult -> matchResult!!.groups }
        .groupBy({ it[1]!!.value }, { Pair(it[4]!!.value, getHappinessValue(it[2]!!.value, it[3]!!.value)) })

fun getHappinessValue(gainOrLose: String, value: String) = if (gainOrLose == "gain") value.toInt() else -value.toInt()