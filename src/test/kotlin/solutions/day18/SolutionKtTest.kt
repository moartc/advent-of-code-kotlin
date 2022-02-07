package solutions.day18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

internal class SolutionKtTest {

    @Test
    fun part1Test() {
        val input = listOf(
            ".#.#.#",
            "...##.",
            "#....#",
            "..#...",
            "#.#..#",
            "####.."
        ).map { it.toList() }
        assertEquals(4, part1(input, 4))
    }

    @Test
    fun step1Test() {
        val input = listOf(
            ".#.#.#",
            "...##.",
            "#....#",
            "..#...",
            "#.#..#",
            "####.."
        ).map { it.toList() }
        val expectedList = listOf(
            "..##..",
            "..##.#",
            "...##.",
            "......",
            "#.....",
            "#.##.."
        )
        val expected = expectedList.map { it.toList() }
        val actual = step(input)
        for (i in expected.indices)
            assertContentEquals(expected[i], actual[i])
    }

    @Test
    fun step2Test() {
        val input = listOf(
            "..##..",
            "..##.#",
            "...##.",
            "......",
            "#.....",
            "#.##.."
        ).map { it.toList() }
        val expectedList = listOf(
            "..###.",
            "......",
            "..###.",
            "......",
            ".#....",
            ".#...."
        )
        val expected = expectedList.map { it.toList() }
        val actual = step(input)
        for (i in expected.indices)
            assertContentEquals(expected[i], actual[i])
    }

    @Test
    fun step3Test() {
        val input = listOf(
            "..###.",
            "......",
            "..###.",
            "......",
            ".#....",
            ".#...."
        ).map { it.toList() }
        val expectedList = listOf(
            "...#..",
            "......",
            "...#..",
            "..##..",
            "......",
            "......"
        )
        val expected = expectedList.map { it.toList() }
        val actual = step(input)
        for (i in expected.indices)
            assertContentEquals(expected[i], actual[i])
    }

    @Test
    fun step4Test() {
        val input = listOf(
            "...#..",
            "......",
            "...#..",
            "..##..",
            "......",
            "......"
        ).map { it.toList() }
        val expectedList = listOf(
            "......",
            "......",
            "..##..",
            "..##..",
            "......",
            "......"
        )
        val expected = expectedList.map { it.toList() }
        val actual = step(input)
        for (i in expected.indices)
            assertContentEquals(expected[i], actual[i])
    }

    @Test
    fun getNeighborsBTest() {
        val input = listOf(
            "1B5...",
            "234...",
            "......",
            "..123.",
            "..8A4.",
            "..765.",
        ).map { it.toList() }
        val expected = ('1'..'5').toList()
        val actual = getNeighbors(Pair(0, 1), input)
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected))
    }

    @Test
    fun getNeighborsATest() {
        val input = listOf(
            "1B5...",
            "234...",
            "......",
            "..123.",
            "..8A4.",
            "..765.",
        ).map { it.toList() }
        val expected = ('1'..'8').toList()
        val actual = getNeighbors(Pair(4, 3), input)
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected))
    }

    @Test
    fun step1Part2Test() {
        val input = listOf(
            "##.#.#",
            "...##.",
            "#....#",
            "..#...",
            "#.#..#",
            "####.#"
        ).map { it.toList() }
        val expectedList = listOf(
            "#.##.#",
            "####.#",
            "...##.",
            "......",
            "#...#.",
            "#.####"
        )
        val expected = expectedList.map { it.toList() }
        val actual = stepPart2(input)
        for (i in expected.indices)
            assertContentEquals(expected[i], actual[i])
    }

    @Test
    fun step2Part2Test() {
        val input = listOf(
            "#.##.#",
            "####.#",
            "...##.",
            "......",
            "#...#.",
            "#.####"
        ).map { it.toList() }
        val expectedList = listOf(
            "#..#.#",
            "#....#",
            ".#.##.",
            "...##.",
            ".#..##",
            "##.###"
        )
        val expected = expectedList.map { it.toList() }
        val actual = stepPart2(input)
        for (i in expected.indices)
            assertContentEquals(expected[i], actual[i])
    }

    @Test
    fun step3Part2Test() {
        val input = listOf(
            "#..#.#",
            "#....#",
            ".#.##.",
            "...##.",
            ".#..##",
            "##.###"
        ).map { it.toList() }
        val expectedList = listOf(
            "#...##",
            "####.#",
            "..##.#",
            "......",
            "##....",
            "####.#"
        )
        val expected = expectedList.map { it.toList() }
        val actual = stepPart2(input)
        for (i in expected.indices)
            assertContentEquals(expected[i], actual[i])
    }

    @Test
    fun step4Part2Test() {
        val input = listOf(
            "#...##",
            "####.#",
            "..##.#",
            "......",
            "##....",
            "####.#"
        ).map { it.toList() }
        val expectedList = listOf(
            "#.####",
            "#....#",
            "...#..",
            ".##...",
            "#.....",
            "#.#..#"
        )
        val expected = expectedList.map { it.toList() }
        val actual = stepPart2(input)
        for (i in expected.indices)
            assertContentEquals(expected[i], actual[i])
    }

    @Test
    fun step5Part2Test() {
        val input = listOf(
            "#.####",
            "#....#",
            "...#..",
            ".##...",
            "#.....",
            "#.#..#"
        ).map { it.toList() }
        val expectedList = listOf(
            "##.###",
            ".##..#",
            ".##...",
            ".##...",
            "#.#...",
            "##...#"
        )
        val expected = expectedList.map { it.toList() }
        val actual = stepPart2(input)
        for (i in expected.indices)
            assertContentEquals(expected[i], actual[i])
    }

    @Test
    fun part2Test() {
        val input = listOf(
            "##.#.#",
            "...##.",
            "#....#",
            "..#...",
            "#.#..#",
            "####.#"
        ).map { it.toList() }
        assertEquals(17, part2(input, 5))
    }

}