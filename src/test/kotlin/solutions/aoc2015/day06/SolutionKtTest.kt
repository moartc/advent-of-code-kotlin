package solutions.aoc2015.day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SolutionKtTest {

    @Test
    fun part1Test1() {
        val input = listOf("turn on 0,0 through 999,999")
        assertEquals(1000 * 1000, part1(input))
    }

    @Test
    fun part1Test2() {
        val input = listOf("turn on 0,0 through 999,999", "toggle 0,0 through 999,0")
        assertEquals((1000 * 1000) - 1000, part1(input))
    }

    @Test
    fun part1Test3() {
        val input =
            listOf("turn on 0,0 through 999,999", "toggle 0,0 through 999,0", "turn off 499,499 through 500,500")
        assertEquals((1000 * 1000) - 1000 - 4, part1(input))
    }

    @Test
    fun part2Test1() {
        val input = listOf("turn on 0,0 through 0,0")
        assertEquals(1, part2(input))
    }

    @Test
    fun part2Test2() {
        val input = listOf("toggle 0,0 through 999,999")
        assertEquals(2000000, part2(input))
    }

    @Test
    fun part2Test3() {
        val input = listOf("turn on 0,0 through 0,0", "turn on 0,0 through 0,0", "turn on 0,0 through 0,0")
        assertEquals(3, part2(input))
    }

    @Test
    fun part2Test4() {
        val input = listOf("toggle 0,0 through 999,999", "turn on 0,0 through 0,0")
        assertEquals(2000000+1, part2(input))
    }

    @Test
    fun part2Test5() {
        val input = listOf("toggle 0,0 through 999,999", "turn off 0,0 through 0,0")
        assertEquals(2000000-1, part2(input))
    }

    @Test
    fun part2Test6() {
        val input = listOf("turn off 0,0 through 0,0", "turn off 0,0 through 0,0", "toggle 0,0 through 999,999")
        assertEquals(2000000, part2(input))
    }

    @Test
    fun getCommandFromString() {
        val input = "turn on 12,55 through 999,992"
        val expected = Command("turn on", Pair(12, 55), Pair(999, 992))
        val actual = getCommandFromString(input)
        assertEquals(expected, actual)
    }
}