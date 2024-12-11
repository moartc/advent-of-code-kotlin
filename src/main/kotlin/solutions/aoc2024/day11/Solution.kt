package solutions.aoc2024.day11

import utils.Resources
import utils.collections.extensions.toFrequencyMap
import utils.parser.getLongs


fun main() {

    val inputLines = Resources.getLines(2024, 11)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    fun processStones(stones: MutableList<Long>): MutableList<Long> {
        val newList = mutableListOf<Long>()

        for ((index, _) in stones.withIndex()) {
            val stone = stones[index]
            if (stone == 0L) {
                newList.add(1)
            } else if (stone.toString().length % 2 == 0) {
                val digits = stone.toString()
                val left = digits.substring(0, digits.length / 2).toLong()
                val right = digits.substring(digits.length / 2).toLong()
                newList.add(left)
                newList.add(right)
            } else {
                newList.add(stone * 2024L)
            }
        }
        return newList
    }

    var stones = inputLines[0].getLongs().toMutableList()

    repeat(25) {
        stones = processStones(stones)
    }
    return stones.size
}

fun part2(inputLines: List<String>): Any {

    fun processSingle(stone: Long): MutableList<Long> {
        val newList = mutableListOf<Long>()
        if (stone == 0L) {
            newList.add(1)
        } else if (stone.toString().length % 2 == 0) {
            val digits = stone.toString()
            val left = digits.substring(0, digits.length / 2).toLong()
            val right = digits.substring(digits.length / 2).toLong()
            newList.add(left)
            newList.add(right)
        } else {
            newList.add(stone * 2024L)
        }
        return newList
    }

    var stones = inputLines[0].getLongs().toFrequencyMap()

    repeat(75) {
        val newMap = mutableMapOf<Long, Long>()
        stones.keys.forEach {
            val newStones = processSingle(it)
            val prevValue = stones[it]!!
            newStones.forEach { newStone ->
                newMap[newStone] = newMap.getOrDefault(newStone, 0L) + prevValue
            }
        }
        stones = newMap
    }
    return stones.values.sum()

}


