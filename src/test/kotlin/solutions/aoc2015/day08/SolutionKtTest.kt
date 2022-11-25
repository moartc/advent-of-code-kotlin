package solutions.aoc2015.day08

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class SolutionKtTest {

    @TestFactory
    fun justStringLengthTest() = listOf(
        """""""" to 2,
        """"abc"""" to 5,
        """"aaa\"aaa"""" to 10,
        """"\x27"""" to 6,
        """"\""""" to 4,
        """"\\"""" to 4,
        """"\x27123"""" to 9,
        """"abc\\"""" to 7,
        """"a\\\"\\\""""" to 11, // -> a\"\"
        """"\"""" to 3,
        """"abc\\"""" to 7,
        """"a"""" to 3,
        """"\xbb"""" to 6
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("number of characters for $input should be $expected") {
            assertEquals(expected, input.length)
        }
    }

    @TestFactory
    fun getNbOfCharsInMemoryTest() = listOf(
        """""""" to 0,
        """"abc"""" to 3,
        """"aaa\"aaa"""" to 7,
        """"\x27"""" to 1,
        """"\""""" to 1,
        """"\\"""" to 1,
        """"\x27123"""" to 4,
        """"abc\\"""" to 4,
        """"a\\\"\\\""""" to 5, // -> a\"\"
        """"\"""" to 1,
        """"abc\\"""" to 4,
        """"a"""" to 1,
        """"\xbb"""" to 1
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("number of characters in memory for $input should be $expected") {
            assertEquals(expected, getNbOfCharsInMemory(input))
        }
    }

    @Test
    fun part1Test() {
        val input = listOf("""""""", """"abc"""", """"aaa\"aaa"""", """"\x27"""")
        assertEquals(12, part1(input))
    }

    @TestFactory
    fun encodeTest() = listOf(
        """""""" to 6,
        """"abc"""" to 9,
        """"aaa\"aaa"""" to 16,
        """"\x27"""" to 11,
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("number of characters in encoded string for $input should be $expected") {
            assertEquals(expected, encode(input))
        }
    }

    @Test
    fun part2Test() {
        val input = listOf("""""""", """"abc"""", """"aaa\"aaa"""", """"\x27"""")
        assertEquals(19, part2(input))
    }
}