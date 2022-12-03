package solutions.aoc2017.day11

import utils.Resources

fun main() {
    val inputLine = Resources.getLine(2017, 11)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(inputLine: String): Int {
    val splitted = inputLine.split(",".toRegex()).toList()
    return calc(splitted)
}

fun calc(splitted: List<String>): Int {

    var newMap = splitted.groupingBy { it }.eachCount()
    var oldMap: MutableMap<String, Int>
    var wasChange = true
    while (wasChange) {
        wasChange = false
        val filtered = newMap.filter { it.value > 0 }.keys
        val pairs = filtered.flatMap { i -> filtered.mapNotNull { if (it > i) Pair(i, it) else null } }
            .filter { p -> mapping.containsKey(p) }
        for (pair in pairs) {
            oldMap = newMap.toMutableMap()
            newMap = applyChange(pair, newMap.toMutableMap())
            if (newMap != oldMap) {
                wasChange = true
            }
        }
    }
    return newMap.values.sum()
}

fun applyChange(key: Pair<String, String>, map: MutableMap<String, Int>): MutableMap<String, Int> {
    val k1 = map[key.first]!!
    val k2 = map[key.second]!!
    val toRem = k1.coerceAtMost(k2)
    val keyVal = mapping[key]
    if (keyVal != "") {
        val value = map.getOrDefault(keyVal, 0)
        map[keyVal!!] = value + toRem
    }
    map[key.first] = k1 - toRem
    map[key.second] = k2 - toRem
    return map
}

fun part2(inputLine: String): Int {
    val splitted = inputLine.split(",".toRegex()).toList()
    return (1..splitted.size).toList().maxOfOrNull { size ->
        val subList = splitted.subList(0, size).sorted()
        calc(subList)
    }!!
}

val mapping = mapOf(
    ("n" to "s") to "",
    ("n" to "se") to "ne",
    ("n" to "sw") to "nw",
    ("ne" to "sw") to "",
    ("ne" to "nw") to "n",
    ("nw" to "s") to "sw",
    ("nw" to "se") to "",
    ("se" to "sw") to "s",
)



