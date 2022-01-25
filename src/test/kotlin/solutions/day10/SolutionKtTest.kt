package solutions.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class SolutionKtTest {

    @TestFactory
    fun generatorTest() = listOf(
        "1" to "11",
        "11" to "21",
        "21" to "1211",
        "1211" to "111221",
        "111221" to "312211"
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("number of characters for $input should be $expected") {
            assertEquals(expected, generateNewString(input))
        }
    }
}