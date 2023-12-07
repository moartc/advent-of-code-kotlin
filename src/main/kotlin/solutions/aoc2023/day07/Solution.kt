package solutions.aoc2023.day07

import utils.Resources
import kotlin.reflect.KFunction1

fun main() {

    val inputLine = Resources.getLines(2023, 7)
    println("part1 = ${part1(inputLine) == 252052080}")
    println("part2 = ${part2(inputLine) == 252898370}")
}

fun part1(input: List<String>): Int {

    fun getValHand(hand: Map<Char, Int>): Int {
        val sortedBy = hand.entries.sortedByDescending { it.value }.toMutableList()
        return when (sortedBy[0].value) {
            5 -> 7                                      // five of a kind
            4 -> 6                                      // 5 of a kind
            3 -> if (sortedBy[1].value == 2) 5 else 4   // full house else three of a kind
            2 -> if (sortedBy[1].value == 2) 3 else 2   // two pair else pair
            else -> 1                                   // high card
        }
    }

    val labels = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()
    return solve(input, ::getValHand, labels)
}


fun part2(input: List<String>): Int {

    fun getValHand(hand: Map<Char, Int>): Int {
        val jInHand = hand.getOrDefault('J', 0)
        val filter = hand.filter { k -> k.key != 'J' }.toMutableMap()
        if (filter.isEmpty()) { // there are only 'J'
            return 7
        }
        // sort values in hand descending
        val sortedBy = filter.entries.sortedByDescending { it.value }.toMutableList()
        val value = sortedBy.first().value

        // add J's to the strongest cards
        sortedBy[0].setValue(value + jInHand)

        return when (sortedBy[0].value) {
            5 -> 7                                      // five of a kind
            4 -> 6                                      // 5 of a kind
            3 -> if (sortedBy[1].value == 2) 5 else 4   // full house else three of a kind
            2 -> if (sortedBy[1].value == 2) 3 else 2   // two pair else pair
            else -> 1                                   // high card
        }
    }

    val labels = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()
    return solve(input, ::getValHand, labels)
}

fun solve(input: List<String>, getValHand: KFunction1<Map<Char, Int>, Int>, labels: List<Char>): Int {
    // map each line to value for hand, actual hand (needed when comparing 2 hands with the same value) and bid
    val maps = input.map { line ->
        val split = line.split(" ")
        val handValue = getValHand(split[0].groupingBy { it }.eachCount())
        val bid = split[1].toInt()
        Triple(handValue, split[0], bid)
    }
    val sortedWith = maps.sortedWith(
        compareBy({ triple -> triple.first },
            { triple -> labels.indexOf(triple.second[0]) },
            { triple -> labels.indexOf(triple.second[1]) },
            { triple -> labels.indexOf(triple.second[2]) },
            { triple -> labels.indexOf(triple.second[3]) },
            { triple -> labels.indexOf(triple.second[4]) })
    )
    return sortedWith.withIndex().sumOf { triple ->
        triple.value.third * (triple.index + 1)
    }
}
