package solutions.day15

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SolutionKtTest {

    @Test
    fun part1() {
        val input = listOf(
            "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8",
            "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"
        )
        assertEquals(62842880, part1(parseReindeerList(input)))
    }

    @Test
    fun part2() {
        val input = listOf(
            "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8",
            "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"
        )
        assertEquals(57600000, part2(parseReindeerList(input)))
    }
}