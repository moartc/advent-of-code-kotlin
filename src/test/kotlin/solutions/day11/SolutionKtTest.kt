package solutions.day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class SolutionKtTest {

    @ParameterizedTest
    @MethodSource("threeLettersProvider")
    fun `contains 3 increasing letters`(pair: Pair<Boolean, String>) {
        assertEquals(pair.first, contains3IncreasingLetters(pair.second))
    }

    @ParameterizedTest
    @CsvSource(
        "true, abc",
        "false, ablio",
        "false, cdfdfewehie",
        "false, i",
        "false, abdo",
        "true, x",
        "true, xqwerrtyasdfgh"
    )
    fun `does not contain letters`(assert: Boolean, input: String) {
        assertEquals(assert, notContainLetter(input))
    }

    @ParameterizedTest
    @CsvSource(
        "false, abc",
        "false, aaa",
        "true, naaaa",
        "true, baacdd",
        "true, aabb",
        "true, xaabb",
        "true, aabbx",
        "true, caaaxybbz",
        "true, xaaybbz"
    )
    fun `contains 2 pairs`(assert: Boolean, input: String) {
        assertEquals(assert, contains2Pairs(input))
    }

    @Test
    fun `hijklmmn test`() {
        val input = "hijklmmn"
        assertTrue(contains3IncreasingLetters(input))
        assertFalse(notContainLetter(input))
    }

    @Test
    fun `abbceffg test`() {
        val input = "abbceffg"
        assertTrue(contains2Pairs(input))
        assertFalse(contains3IncreasingLetters(input))
    }

    @Test
    fun `abbcegjk test`() {
        val input = "abbcegjk"
        assertTrue(notContainLetter(input))
        assertFalse(contains2Pairs(input))
    }

    @Test
    fun `abcdffaa test`() {
        val input = "abcdffaa"
        assertTrue(contains3IncreasingLetters(input))
        assertTrue(notContainLetter(input))
        assertTrue(contains2Pairs(input))
    }

    @Test
    fun `ghjaabcc test`() {
        val input = "ghjaabcc"
        assertTrue(contains3IncreasingLetters(input))
        assertTrue(notContainLetter(input))
        assertTrue(contains2Pairs(input))
    }

    @ParameterizedTest
    @CsvSource(
        "xx, xy",
        "xy, xz",
        "xz, ya",
        "ya, yb",
        "azz, baa",
        "cazz, cbaa"
    )
    fun `next string to check`(input: String, expected: String) {
        assertEquals(expected, getNextString(input))
    }

    @Test
    fun `next after abcdefgh should be abcdffaa`() {
        val input = "abcdefgh"
        assertEquals("abcdffaa", getAnswer(input))
    }

    @Test
    fun `next after ghijklmn should be ghjaabcc`() {
        val input = "ghijklmn"
        assertEquals("ghjaabcc", getAnswer(input))
    }


    companion object {
        @JvmStatic
        private fun threeLettersProvider() = Stream.of(
            Pair(true, "abc"),
            Pair(true, "bcd"),
            Pair(true, "cde"),
            Pair(true, "xyz"),
            Pair(false, "abd")
        )
    }
}