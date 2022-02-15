package solutions.day23

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SolutionKtTest {

    @Test
    fun part1() {
        val input = listOf(
            "inc a",
            "jio a, +2",
            "tpl a",
            "inc a"
        )
        val registers = process(mapOf('a' to 0, 'b' to 0), 0, input)
        assertEquals(2, registers['a'])
    }
}