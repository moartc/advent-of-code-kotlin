package solutions.day24

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SolutionKtTest {

    @Test
    fun part1Test() {
        val input = listOf(1, 2, 3, 4, 5, 7, 8, 9, 10, 11)
        assertEquals(99, solve(input, 3))
    }

    @Test
    fun part2Test() {
        val input = listOf(1, 2, 3, 4, 5, 7, 8, 9, 10, 11)
        assertEquals(44, solve(input, 4))
    }
}