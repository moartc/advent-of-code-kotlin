package solutions.aoc2015.day19

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SolutionKtTest {

    @Test
    fun part1TestTest1() {
        val input = listOf("H => HO", "H => OH", "O => HH", "", "HOH")
        assertEquals(4, part1(input))
    }

    @Test
    fun part1Test2() {
        val input = listOf("H => HO", "H => OH", "O => HH", "", "HOHOHO")
        assertEquals(7, part1(input))
    }

    @Test
    fun part2TestTest1() {
        val input = listOf("e => H", "e => O", "H => HO", "H => OH", "O => HH", "", "HOH")
        assertEquals(3, part2(input))
    }

    @Test
    fun part2Test2() {
        val input = listOf("e => H", "e => O", "H => HO", "H => OH", "O => HH", "", "HOHOHO")
        assertEquals(6, part2(input))
    }
}