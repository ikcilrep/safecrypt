package com.example.safecrypt

import com.example.safecrypt.nse.dotProduct
import org.junit.Assert.assertEquals
import org.junit.Test

class VectorsTest {
    @Test
    fun testDotProduct() {
        val vectors = listOf(
            Triple(byteArrayOf(), byteArrayOf(), 0.toLong()),
            Triple(byteArrayOf(-1, 1), byteArrayOf(2, 3), 1.toLong()),
            Triple(byteArrayOf(2, 31, 54), byteArrayOf(5, -46, 12), (-768).toLong()),
            Triple(byteArrayOf(8, 12, -121, 90), byteArrayOf(123, -57, 89, 13), (-9299).toLong())
        )
        for ((vector1, vector2, expectedResult) in vectors) {
            val result1 = vector1.dotProduct(vector2)
            val result2 = vector2.dotProduct(vector1)
            assertEquals(result1, result2)
            assertEquals(result1, expectedResult)
        }
    }


}