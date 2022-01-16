package solutions.day01

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import solutions.day01.part2

internal class SolutionKtTest {

    @Test
    fun part1Test1() {
        val input1 = "(())"
        val input2 = "()()"
        val expected = 0
        assertEquals(expected, part1(input1))
        assertEquals(expected, part1(input2))
    }

    @Test
    fun part1Test2() {
        val input1 = "((("
        val input2 = "(()(()("
        val input3 ="))((((("
        val expected = 3
        assertEquals(expected, part1(input1))
        assertEquals(expected, part1(input2))
        assertEquals(expected, part1(input3))
    }

    @Test
    fun part1Test3() {
        val input1 = "())"
        val input2 = "))("
        val expected = -1
        assertEquals(expected, part1(input1))
        assertEquals(expected, part1(input2))
    }

    @Test
    fun part1Test4() {
        val input1 = ")))"
        val input2 = ")())())"
        val expected = -3
        assertEquals(expected, part1(input1))
        assertEquals(expected, part1(input2))
    }

    @Test
    fun part2Test1() {
        val input = ")))"
        val expected = 1
        assertEquals(expected, part2(input))
    }

    @Test
    fun part2Test2() {
        val input = "()())"
        val expected = 5
        assertEquals(expected, part2(input))
    }
}