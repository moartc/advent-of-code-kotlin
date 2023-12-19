package solutions.aoc2023.day08

import utils.Resources
import utils.algorithms.lcm
import utils.collections.extensions.splitOnEmpty


fun main() {
    val inputLine = Resources.getLines(2023, 8)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: List<String>): Int {

    val instructions = input[0]
    val pairs = input.splitOnEmpty()[1]
    val graph = pairs.associate { pair ->
        val split = pair.split(" = ")
        val left = split[0]
        val pairStr = split[1].split(", ")
        val leftPair = pairStr[0].drop(1)
        val rightPair = pairStr[1].dropLast(1)
        left to (leftPair to rightPair)
    }

    var steps = 0
    var currInstIdx = 0
    var currentStr = "AAA"
    while (true) {
        if (currentStr == "ZZZ") {
            return steps
        }
        val pair = graph[currentStr]

        currentStr = if (instructions[currInstIdx] == 'L') {
            pair!!.first
        } else { // R
            pair!!.second
        }
        steps++
        currInstIdx = (currInstIdx + 1) % instructions.length
    }
}

/*
Another cycle task that I tried to brute force before I realized that it didn't make sense.
The initial implementation was to write out the cycles and calculate the lcm in some online calculator
lcm and gcd added to math utils.
 */
fun part2(input: List<String>): Any {


    val instructions = input[0]
    val pairs = input.splitOnEmpty()[1]
    val graph = pairs.associate { pair ->
        val split = pair.split(" = ")
        val left = split[0]
        val pairStr = split[1].split(", ")
        val leftPair = pairStr[0].drop(1)
        val rightPair = pairStr[1].dropLast(1)
        left to (leftPair to rightPair)
    }

    var steps = 0
    var currInstIdx = 0
    var startingNodes = graph.keys.filter { k -> k.last() == 'A' }.toList() // find all starting nodes
    val nextNodesToGo = startingNodes.toMutableList()
    val arrayOfCycles = Array(nextNodesToGo.size) { -1 }
    while (true) {
        startingNodes.forEachIndexed { index, s ->
            if (s.last() == 'Z' && arrayOfCycles[index] == -1) {
                arrayOfCycles[index] = steps
            }
        }
        // if there are all expected cycle found
        if (arrayOfCycles.all { x -> x != -1 }) {
            return lcm(arrayOfCycles.map { x -> x.toLong() }.toLongArray())
        }
        nextNodesToGo.clear()
        startingNodes.forEach { node ->
            val pair = graph[node]
            if (instructions[currInstIdx] == 'L') {
                nextNodesToGo.add(pair!!.first)
            } else { // R
                nextNodesToGo.add(pair!!.second)
            }
        }
        steps++
        currInstIdx = (currInstIdx + 1) % instructions.length
        startingNodes = nextNodesToGo.toList()
    }


}


