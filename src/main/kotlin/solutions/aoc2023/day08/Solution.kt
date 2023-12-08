package solutions.aoc2023.day08

import utils.Resources
import utils.algorithms.lcm
import utils.splitOnEmpty


fun main() {

    val inputLine = Resources.getLines(2023, 8)
//        Resources.getLinesExample(2023, 8)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

/*
    Another offset task that I tried to brute force before I knew what to do.
    the initial implementation was to write out the offsets and calculate the lcm in some online calculator
 */
fun part2(input: List<String>): Any {

    input.forEach { it.log("p1") }

    val inst = input[0]
    val splitOnEmpty = input.splitOnEmpty()
    val pairs = splitOnEmpty[1]

    var myMap = mutableMapOf<String, Pair<String, String>>()
    for (pair in pairs) {
        val split = pair.split(" = ")
        val left = split[0]
        val right = split[1]
        val pairStr = split[1].split(", ")
        val leftPair = pairStr[0].drop(1)
        val rightPair = pairStr[1].dropLast(1)
        myMap[left] = leftPair to rightPair
    }


    var steps = 0
    var currInstIdx = 0


    var starting = myMap.keys.filter { k -> k.last() == 'A' }.toList()
    var newStart = mutableListOf<String>()

    newStart = starting.toMutableList()

    var ll = mutableListOf<Pair<Int, Int>>()
    val arrayOfOffsets = Array(newStart.size) { -1 }
    var currentStr = "AAA"
    while (true) {

        starting.forEachIndexed { index, s ->

            if (s.last() == 'Z' && arrayOfOffsets[index] == -1) {
                arrayOfOffsets[index] = steps
                println("index =$index after $steps steps")

            }
        }

        if (arrayOfOffsets.all { x -> x != -1 }) {
            return lcm(arrayOfOffsets.map { x -> x.toLong() }.toLongArray())
        }
//        starting.log("startings ")
        newStart.clear()
        for (st in starting) {

            val pair = myMap[st]

            if (inst[currInstIdx] == 'L') {
                newStart.add(pair!!.first)
            } else { // R
                newStart.add(pair!!.second)
            }
        }
        steps++

        currInstIdx = (currInstIdx + 1) % inst.length
        starting = newStart.toList()
        if (steps % 50000000L == 0L)
            steps.log("steps")
    }


}

fun part1(input: List<String>): Int {

    input.forEach { it.log("p1") }

    val inst = input[0]
    val splitOnEmpty = input.splitOnEmpty()
    val pairs = splitOnEmpty[1]

    var myMap = mutableMapOf<String, Pair<String, String>>()
    for (pair in pairs) {
        val split = pair.split(" = ")
        val left = split[0]
        val right = split[1]
        val pairStr = split[1].split(", ")
        val leftPair = pairStr[0].drop(1)
        val rightPair = pairStr[1].dropLast(1)
        myMap[left] = leftPair to rightPair
    }


    var steps = 0
    var currInstIdx = 0
    var currentStr = "AAA"
    while (true) {


        if (currentStr == "ZZZ") {
            return steps
        }
        val pair = myMap[currentStr]

        if (inst[currInstIdx] == 'L') {
            currentStr = pair!!.first
        } else { // R
            currentStr = pair!!.second
        }
        steps++
        currInstIdx = (currInstIdx + 1) % inst.length

        if (steps == 460000000)
            steps.log("steps")
    }



    return 12
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



