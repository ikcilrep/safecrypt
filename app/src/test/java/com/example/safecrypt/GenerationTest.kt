package com.example.safecrypt

import com.example.safecrypt.nse.dotProduct
import com.example.safecrypt.nse.generateIV
import com.example.safecrypt.nse.minus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import kotlin.random.Random

class GenerationTest {
    @Test
    fun testGenerateIV() {
        for (length in 1..10) {
            val rotatedData = Random.nextBytes(length)
            val derivedKey = Random.nextBytes(length)
            val iv = generateIV(length, derivedKey, rotatedData)
            assertEquals(iv.size, length)
            assertNotEquals(derivedKey.dotProduct(rotatedData - iv), 0.toLong())
        }
    }
}