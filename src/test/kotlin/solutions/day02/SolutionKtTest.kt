package solutions.day02

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SolutionKtTest {

    @Test
    fun part1Test1() {
        val input = listOf("2x3x4")
        val expected = 58
        assertEquals(expected, part1(input))
    }

    @Test
    fun part1Test2() {
        val input = listOf("1x1x10")
        val expected = 43
        assertEquals(expected, part1(input))
    }

    @Test
    fun part2Test1() {
        val input = listOf("2x3x4")
        val expected = 34
        assertEquals(expected, part2(input))
    }

    @Test
    fun part2Test2() {
        val input = listOf("1x1x10")
        val expected = 14
        assertEquals(expected, part2(input))
    }
}