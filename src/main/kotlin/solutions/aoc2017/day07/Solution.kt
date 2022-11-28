package solutions.aoc2017.day07

import utils.Resources

fun main() {
    val inputLine = Resources.getLines(2017, 7)
    val inputMap = parse(inputLine)
    val part1 = part1(inputMap)
    println("part1 = $part1")
    println("part2 = ${part2(inputMap, part1)}")
}

fun part2(map: Map<Pair<String, Int>, Set<String>>, part1: String): Int {

    val keys = map.filter { keySet -> keySet.key.first != part1 && keySet.value.isNotEmpty() }.keys
    val nodeValueMap = keys.groupBy { it.first }.mapValues { getKeyWeight(it.key, map) }
    val minIncorrect = keys.map { it.first to (getKeyWeight(it.first, map)) }.groupingBy { it.second }.eachCount()
        .filter { it.value == 1 }
        .keys
        .associateBy { i -> nodeValueMap.filterValues { it == i }.keys.iterator().next() }
        .filter { !isCorrect(it.key, nodeValueMap, map) }
        .minBy { it.value }
    val incorrectWeight = map.keys.first { it.first == minIncorrect.key }.second
    val valueWithCorrectValue = map.values.first { it.contains(minIncorrect.key) }.first { it != minIncorrect.key }
    val diff = nodeValueMap[valueWithCorrectValue]!! - minIncorrect.value
    return incorrectWeight + diff
}

fun isCorrect(key: String, nodeValueMap: Map<String, Int>, inputMap: Map<Pair<String, Int>, Set<String>>) =
    inputMap.filter { it.key.first == key }.iterator().next().value.map { nodeValueMap[it] }.toSet().size == 1

fun getKeyWeight(key: String, map: Map<Pair<String, Int>, Set<String>>): Int {
    val keyFromMap = map.filterKeys { it.first == key }.toList()[0]
    return keyFromMap.first.second + keyFromMap.second
        .map { k -> map.keys.first { it.first == k } }
        .sumOf { getKeyWeight(it.first, map) }
}

fun part1(map: Map<Pair<String, Int>, Set<String>>): String {
    return map.filter { keyVal -> map.filter { it.value.contains(keyVal.key.first) }.isEmpty() }
        .keys.find { it.first.isNotEmpty() }!!.first
}

fun parse(input: List<String>): Map<Pair<String, Int>, Set<String>> {
    return input.map { it.split(" -> ".toRegex()) }
        .associate { split -> getNameWeightPair(split[0]) to getChildSet(split) }
}

fun getNameWeightPair(firstPart: String): Pair<String, Int> {
    val split = firstPart.split(" ".toRegex())
    val weight = split[1]
    return split[0] to weight.substring(1, weight.length - 1).toInt()
}

fun getChildSet(split: List<String>): Set<String> {
    return if (split.size > 1) {
        val rest = split[1]
        return rest.split(",".toRegex()).map { it.trim() }.toSet()
    } else {
        emptySet()
    }
}