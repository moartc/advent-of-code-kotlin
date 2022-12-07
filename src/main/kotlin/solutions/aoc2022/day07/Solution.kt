package solutions.aoc2022.day07

import utils.Resources

fun main() {
    val input = Resources.getLines(2022, 7)

    val map = createMap(input)
    println("part1 = ${part1(map)}")
    println("part2 = ${part2(map)}")
}

fun part1(map: Map<String, Int>) = map.values.filter { it <= 100000 }.sum()
fun part2(map: Map<String, Int>) = map.values.filter { map["/"]!! - it <= (70000000 - 30000000) }.min()

fun createMap(input: List<String>): Map<String, Int> {
    val map = mutableMapOf<String, MutableList<String>>()
    val stack = ArrayDeque<String>()
    input.filter { !it.startsWith("$ ls") }.forEach {
        if (it == "\$ cd ..") {
            stack.removeLast()
        } else if (it.startsWith("\$ cd ")) {
            stack.add(it.split(" ").last())
        } else {
            val path = stack.getPath()
            val toAdd = if (it.startsWith("dir")) (stack.getPath() + "/" + it.split(" ").last()) else it
            if (map.containsKey(path)) {
                map[path]!!.add(toAdd)
            } else {
                map[path] = mutableListOf(toAdd)
            }
        }
    }
    return map.map { kv ->
        kv.key to getTotalForKey(kv.key, map)
    }.toMap()
}

fun getTotalForKey(key: String, map: MutableMap<String, MutableList<String>>): Int {
    return map[key]!!.sumOf {
        if (it.startsWith("/")) getTotalForKey(it, map) else it.split(" ").first().toInt()
    }
}

fun ArrayDeque<String>.getPath() = this.joinToString("/")