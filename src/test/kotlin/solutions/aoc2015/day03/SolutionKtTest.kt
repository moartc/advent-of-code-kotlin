package solutions.aoc2015.day03

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SolutionKtTest {

    @Test
    fun part1Test() {
        val input1 = ">"
        val input2 = "^>v<"
        val input3 = "^v^v^v^v^v"
        assertEquals(2, part1(input1))
        assertEquals(4, part1(input2))
        assertEquals(2, part1(input3))
    }

    @Test
    fun part2Test() {
        val input1 = "^v"
        val input2 = "^>v<"
        val input3 = "^v^v^v^v^v"
        assertEquals(3, part2(input1))
        assertEquals(3, part2(input2))
        assertEquals(11, part2(input3))
    }
}