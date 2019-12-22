package com.example.safecrypt

import com.example.safecrypt.nse.toByteArray
import com.example.safecrypt.nse.toLongArray
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class BytesConversionTest {
    @Test
    fun testLongArrayToByteArray() {
        for (length1 in 0..128) {
            val data = LongArray(length1) { Random.nextLong() }
            println(data.joinToString())
            val convertedData = data.toByteArray()
            println(convertedData.joinToString())
            val unconvertedData = convertedData.toLongArray()
            println(unconvertedData.joinToString())
            assertTrue(data contentEquals unconvertedData)
        }
    }
}