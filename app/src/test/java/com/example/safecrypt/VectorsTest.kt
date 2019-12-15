package com.example.safecrypt

import com.example.safecrypt.nse.dotProduct
import org.junit.Assert.assertEquals
import org.junit.Test

class VectorsTest {
    @Test
    fun testDotProduct() {
        val vectors = listOf(
            Triple(byteArrayOf(), shortArrayOf(), 0.toLong()),
            Triple(byteArrayOf(-1, 1), shortArrayOf(2, 3), 1.toLong()),
            Triple(byteArrayOf(2, 31, 54), shortArrayOf(5, -46, 12), (-768).toLong()),
            Triple(byteArrayOf(8, 12, -121, 90), shortArrayOf(123, -57, 89, 13), (-9299).toLong())
        )
        for ((vector1, vector2, expectedResult) in vectors) {
            val result1 = vector1.dotProduct(vector2)
            assertEquals(result1, expectedResult)
        }
    }


}