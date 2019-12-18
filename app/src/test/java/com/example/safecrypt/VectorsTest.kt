package com.example.safecrypt

import com.example.safecrypt.nse.dotProduct
import com.example.safecrypt.nse.isZeroVector
import com.example.safecrypt.nse.minus
import com.example.safecrypt.nse.times
import org.junit.Assert.assertEquals
import org.junit.Test

class VectorsTest {
    @Test
    fun testDotProduct() {
        val vectors = listOf(
            Triple(byteArrayOf(), shortArrayOf(), 0.toLong()),
            Triple(byteArrayOf(-3), shortArrayOf(27), (-81).toLong()),
            Triple(byteArrayOf(-1, 1), shortArrayOf(2, 3), 1.toLong()),
            Triple(byteArrayOf(2, 31, 54), shortArrayOf(5, -46, 12), (-768).toLong()),
            Triple(byteArrayOf(8, 12, -121, 90), shortArrayOf(123, -57, 89, 13), (-9299).toLong())
        )
        for ((vector1, vector2, expectedResult) in vectors) {
            val result1 = vector1.dotProduct(vector2)
            assertEquals(result1, expectedResult)
        }
    }

    @Test
    fun testMinus() {
        val vectors = listOf(
            Triple(byteArrayOf(), byteArrayOf(), shortArrayOf()),
            Triple(byteArrayOf(1), byteArrayOf(3), shortArrayOf(-2)),
            Triple(byteArrayOf(53, -47), byteArrayOf(21, 32), shortArrayOf(32, -79)),
            Triple(byteArrayOf(16, 25, 3), byteArrayOf(-36, -49, 2), shortArrayOf(52, 74, 1))
        )
        for ((vector1, vector2, expectedResult) in vectors) {
            val result1 = vector1 - vector2
            assert(result1 contentEquals expectedResult)
        }

    }

    @Test
    fun testMultiply() {
        val vectors = listOf(
            Triple(byteArrayOf(), 23.toLong(), longArrayOf()),
            Triple(byteArrayOf(1), 37.toLong(), longArrayOf(37)),
            Triple(byteArrayOf(53, -47), 53.toLong(), longArrayOf(2809, -2491)),
            Triple(byteArrayOf(16, 25, 3), (-3).toLong(), longArrayOf(-48, -75, -9))
        )
        for ((vector1, multiplier, expectedResult) in vectors) {
            val result1 = vector1 * multiplier
            assert(result1 contentEquals expectedResult)
        }

    }

    @Test
    fun testIsZeroVector() {
        val vectors = listOf(
            Pair(byteArrayOf(), true),
            Pair(byteArrayOf(0), true),
            Pair(byteArrayOf(27), false),
            Pair(byteArrayOf(0, 0), true),
            Pair(byteArrayOf(1, 1), false)
        )

        for ((vector, expectedResult) in vectors) {
            assertEquals(vector.isZeroVector(), expectedResult)
        }
    }

}