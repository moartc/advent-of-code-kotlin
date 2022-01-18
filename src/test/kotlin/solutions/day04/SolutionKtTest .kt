package solutions.day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SolutionKtTest {

    @Test
    fun part1Test() {
        val input1 = "abcdef"
        val input2 = "pqrstuv"
        assertEquals(609043, part1(input1))
        assertEquals(1048970, part1(input2))
    }
}
