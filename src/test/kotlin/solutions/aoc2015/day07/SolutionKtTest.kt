package solutions.aoc2015.day07

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SolutionKtTest {

    @Test
    fun part1Test() {
        val input = listOf(
            "123 -> x",
            "456 -> y",
            "x AND y -> d",
            "x OR y -> e",
            "x LSHIFT 2 -> f",
            "y RSHIFT 2 -> g",
            "NOT x -> h",
            "NOT y -> i"
        )

        val expected:Map<String, UShort> = mapOf(
            "d" to 72.toUShort(),
            "e" to 507.toUShort(),
            "f" to 492.toUShort(),
            "g" to 114.toUShort(),
            "h" to 65412.toUShort(),
            "i" to 65079.toUShort(),
            "x" to 123.toUShort(),
            "y" to 456.toUShort()
        )
        val ops = input.map { s -> createOperation(s) }
        assertEquals(expected, processCircuit(ops))
    }
}