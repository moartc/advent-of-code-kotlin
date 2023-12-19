package utils

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import utils.array.extensions.*
import java.util.*

class ArrayKtTest {

    @Test
    fun shiftLeftMutable() {

        val arr = arrayOf(1, 2, 3, 4, 5)
        val expected = arrayOf(3, 4, 5, 1, 2)
        arr.shiftLeftMutable(2)
        assertArrayEquals(expected, arr)
    }

    @Test
    fun shiftRightMutable() {

        val arr = arrayOf(1, 2, 3, 4, 5)
        val expected = arrayOf(4, 5, 1, 2, 3)
        arr.shiftRightMutable(2)
        assertArrayEquals(expected, arr)
    }

    @Test
    fun shiftLeft() {

        val arr = arrayOf(1, 2, 3, 4, 5)
        val expected = arrayOf(3, 4, 5, 1, 2)
        val shiftLeft = arr.shiftLeft(2)
        assertArrayEquals(expected, shiftLeft)
        assertArrayEquals(arrayOf(1, 2, 3, 4, 5), arr)
    }

    @Test
    fun shiftRight() {

        val arr = arrayOf(1, 2, 3, 4, 5)
        val expected = arrayOf(4, 5, 1, 2, 3)
        val shiftRight = arr.shiftRight(2)
        assertArrayEquals(expected, shiftRight)
        assertArrayEquals(arrayOf(1, 2, 3, 4, 5), arr)
    }

    @Test
    fun rotaterotateClockwise() {
        val arr = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9)
        )
        val copyArr = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9)
        )

        val expected = arrayOf(
            arrayOf(7, 4, 1), arrayOf(8, 5, 2), arrayOf(9, 6, 3)
        )

        val rotateRight = arr.rotateClockwise()
        assertArrayEquals(expected, rotateRight)
        assertArrayEquals(arr, copyArr)
    }

    @Test
    fun rotateClockwise2() {
        val arr = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
        )

        val expected = arrayOf(
            arrayOf(4, 1),
            arrayOf(5, 2),
            arrayOf(6, 3)
        )

        val rotateRight = arr.rotateClockwise()
        assertArrayEquals(expected, rotateRight)
    }

    @Test
    fun rotateCounterClockwise() {
        val arr = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9)
        )
        val copyArr = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9)
        )
        val expected = arrayOf(
            arrayOf(3, 6, 9),
            arrayOf(2, 5, 8),
            arrayOf(1, 4, 7)
        )

        val rotateLeft = arr.rotateCounterClockwise()
        assertArrayEquals(expected, rotateLeft)
        assertArrayEquals(arr, copyArr)
    }

    @Test
    fun rotateCounterClockwise2() {
        val arr = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
        )
        val expected = arrayOf(
            arrayOf(3, 6),
            arrayOf(2, 5),
            arrayOf(1, 4)
        )

        val rotateLeft = arr.rotateCounterClockwise()
        assertArrayEquals(expected, rotateLeft)
    }

    @Test
    fun rotate180() {
        val arr1 = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
        )
        val expected1 = arrayOf(
            arrayOf(6, 5, 4),
            arrayOf(3, 2, 1),
        )
        val arr2 = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9),
        )
        val expected2 = arrayOf(
            arrayOf(9, 8, 7),
            arrayOf(6, 5, 4),
            arrayOf(3, 2, 1),
        )

        val first = arr1.rotate180()

        first.forEach {
            println(Arrays.toString(it))
        }

        assertArrayEquals(expected1, first)
        val second = arr2.rotate180()
        assertArrayEquals(expected2, second)
    }

    @Test
    fun mixedRotate() {
        val arr1 = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
        )
        val expected1 = arrayOf(
            arrayOf(6, 5, 4),
            arrayOf(3, 2, 1),
        )
        val arr2 = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9),
        )
        val expected2 = arrayOf(
            arrayOf(9, 8, 7),
            arrayOf(6, 5, 4),
            arrayOf(3, 2, 1),
        )

        val doubleClockwise1 = arr1.rotateClockwise().rotateClockwise()
        val doubleCounterClockwise1 = arr1.rotateCounterClockwise().rotateCounterClockwise()
        val doubleClockwise2 = arr2.rotateClockwise().rotateClockwise()
        val doubleCounterClockwise2 = arr2.rotateCounterClockwise().rotateCounterClockwise()
        val notChanged = arr1.rotateClockwise().rotateCounterClockwise()
        val notChanged2 = arr2.rotateCounterClockwise().rotateClockwise()

        assertArrayEquals(expected1, doubleClockwise1)
        assertArrayEquals(expected1, doubleCounterClockwise1)
        assertArrayEquals(expected2, doubleClockwise2)
        assertArrayEquals(expected2, doubleCounterClockwise2)
        assertArrayEquals(arr1, notChanged)
        assertArrayEquals(arr2, notChanged2)
    }
}