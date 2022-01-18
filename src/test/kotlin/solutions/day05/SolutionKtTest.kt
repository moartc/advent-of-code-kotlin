package solutions.day05

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SolutionKtTest {

    @Test
    fun containsVowelsTest() {
        val input1 = "aei"
        val input2 = "xazegov"
        val input3 = "aeiouaeiouaeiou"
        assertTrue(containsVowels(input1))
        assertTrue(containsVowels(input2))
        assertTrue(containsVowels(input3))
    }

    @Test
    fun twiceLettersTest() {
        val input1 = "xx"
        val input2 = "abcdde"
        val input3 = "aabbccdd"
        assertTrue(twiceLetters(input1))
        assertTrue(twiceLetters(input2))
        assertTrue(twiceLetters(input3))
    }

    @Test
    fun doesNotContainsTest() {
        val input1 = "xx"
        val input2 = "abcdde"
        val input3 = "aapqbbccdd"
        assertTrue(doesNotContains(input1))
        assertFalse(doesNotContains(input2))
        assertFalse(doesNotContains(input3))
    }

    @Test
    fun isNicePart1Test() {
        val input1 = "ugknbfddgicrmopn"
        val input2 = "aaa"
        val input3 = "jchzalrnumimnmhp"
        val input4 = "haegwjzuvuyypxyu"
        val input5 = "dvszwmarrgswjxmb"
        assertTrue(isNicePart1(input1))
        assertTrue(isNicePart1(input2))
        assertFalse(isNicePart1(input3))
        assertFalse(isNicePart1(input4))
        assertFalse(isNicePart1(input5))
    }

    @Test
    fun twoLettersTest() {
        val input1 = "xyxy"
        val input2 = "aabcdefgaa"
        val input3 = "aaa"
        assertTrue(twoLetters(input1))
        assertTrue(twoLetters(input2))
        assertFalse(twoLetters(input3))
    }

    @Test
    fun repeatWithBetweenTest() {
        val input1 = "xyx"
        val input2 = "abcdefeghi"
        val input3 = "aaa"
        assertTrue(repeatWithBetween(input1))
        assertTrue(repeatWithBetween(input2))
        assertTrue(repeatWithBetween(input3))
    }

    @Test
    fun isNicePart2Test() {
        val input1 = "qjhvhtzxzqqjkmpb"
        val input2 = "xxyxx"
        val input3 = "uurcxstgmygtbstg"
        val input4 = "ieodomkazucvgmuy"
        assertTrue(isNicePart2(input1))
        assertTrue(isNicePart2(input2))
        assertFalse(isNicePart2(input3))
        assertFalse(isNicePart2(input4))
    }
}