package solutions.aoc2015.day09

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SolutionKtTest {

    @Test
    fun part1Test() {
        val input = listOf(
            "London to Dublin = 464",
            "London to Belfast = 518",
            "Dublin to Belfast = 141"
        )
        assertEquals(605, part1(input))
    }

    @Test
    fun part2Test() {
        val input = listOf(
            "London to Dublin = 464",
            "London to Belfast = 518",
            "Dublin to Belfast = 141"
        )
        assertEquals(982, part2(input))
    }
}