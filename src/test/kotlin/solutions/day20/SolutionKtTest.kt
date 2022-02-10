package solutions.day20

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class SolutionKtTest {

    @TestFactory
    fun houseNumberToPresents() = listOf(
        1 to 10,
        2 to 30,
        3 to 40,
        4 to 70,
        5 to 60,
        6 to 120,
        7 to 80,
        8 to 150,
        9 to 130
    ).map { (number, expected) ->
        DynamicTest.dynamicTest("number of presents for house number $number should be $expected") {
            assertEquals(expected, numsOfPresentsPart1(number))
        }
    }

    @Test
    fun part1Test() {
        val input = 140
        assertEquals(8, part1(input))
    }
}