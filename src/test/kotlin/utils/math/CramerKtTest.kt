package utils.math

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CramerKtTest {

    @Test
    fun solveCramerLong1() {
        //no long results
        val coeff = arrayOf(
            longArrayOf(2, 1, 1),
            longArrayOf(3, 2, 4),
            longArrayOf(1, 3, 3)
        )
        val const = longArrayOf(10, 18, 13)

        val actual = solveCramerLong(coeff, const)

        assertNull(actual)
    }

    @Test
    fun solveCramerLong2() {
        // 1a + b + c = 10,
        // 3a + 2b + 4c = 18
        // 3a + 1b + 3c = 13

        // result: 5, 8.5, 3.5

        val coeff = arrayOf(
            longArrayOf(1, 1, 1),
            longArrayOf(3, 2, 4),
            longArrayOf(1, 3, 3)
        )
        val const = longArrayOf(10, 18, 13)

        val actual = solveCramerLong(coeff, const)

        assertNull(actual)
    }

    @Test
    fun solveCramerLong3() {
        val coeff = arrayOf(
            longArrayOf(94, 22),
            longArrayOf(34, 67),
        )
        val const = longArrayOf(8400, 5400)

        val actual = solveCramerLong(coeff, const)

        assertArrayEquals(longArrayOf(80, 40), actual)
    }

    @Test
    fun solveCramerDouble() {
        val coeff = arrayOf(
            doubleArrayOf(2.0, 1.0, 1.0),
            doubleArrayOf(3.0, 2.0, 4.0),
            doubleArrayOf(1.0, 3.0, 3.0)
        )
        val const = doubleArrayOf(10.0, 18.0, 13.0)

        val actual = solveCramerDouble(coeff, const)

        assertArrayEquals(doubleArrayOf(3.4, 2.5, 0.7), actual)
    }
}



