package solutions.day25

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class SolutionKtTest {

    @ParameterizedTest
    @CsvSource(
        "1,1,1",
        "1,3,6",
        "2,1,2",
        "2,2,5",
        "2,3,9",
        "2,4,14",
        "1,5,15",
        "3,2,8",
        "3,4,19",
        "4,1,7",
        "4,2,12",
        "4,3,18",
    )
    fun getNumberTest(row: Int, col: Int, expected: Int) {
        assertEquals(expected, getNumber(row, col))
    }

    @ParameterizedTest
    @CsvSource(
        "1,1,20151125",
        "1,3,17289845",
        "4,2,32451966",
        "4,3,21345942",
    )
    fun getCodeTest(row: Int, col: Int, expected: Long) {
        assertEquals(expected, getCode(row, col))
    }
}