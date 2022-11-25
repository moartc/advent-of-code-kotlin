package solutions.aoc2015.day17

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SolutionKtTest {

    @Test
    fun part1Test() {
        val input = listOf(20, 15, 10, 5, 5)
        assertEquals(4, part1(input, 25))
    }

    @Test
    fun part2Test() {
        val input = listOf(20, 15, 10, 5, 5)
        assertEquals(3, part2(input, 25))
    }
}