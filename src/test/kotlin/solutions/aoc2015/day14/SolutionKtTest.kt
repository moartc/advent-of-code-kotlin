package solutions.aoc2015.day14

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SolutionKtTest {

    @Test
    fun part1Test() {
        val input = listOf(
            "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
            "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."
        )
        val reindeerList = parseReindeerList(input)
        assertEquals(1120, part1(reindeerList, 1000))
    }

    @Test
    fun part2Test() {
        val input = listOf(
            "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
            "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."
        )
        val reindeerList = parseReindeerList(input)
        assertEquals(689, part2(reindeerList, 1000))
    }
}