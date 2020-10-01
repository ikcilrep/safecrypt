package com.ikcilrep.safecrypt

import com.ikcilrep.safecrypt.nse.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.math.BigInteger
import kotlin.random.Random

class GenerationTest {
    @Test
    fun testGenerateIV() {
        for (length in 1..10) {
            val rotatedData = Random.nextBytes(length)
            val derivedKey = Random.nextBytes(length).mapPrimes()
            val iv = generateIV(
                length,
                derivedKey,
                rotatedData
            )
            assertEquals(iv.size, length)
            assertNotEquals(derivedKey.dotProduct(rotatedData - iv), 0.toLong())
        }
    }

    @Test
    fun testDeriveKey() {
        for (length in 1..10) {
            val key = BigInteger(Random.nextBytes(length)).abs()
            val salt = Random.nextBytes(16)
            val derivedKey =
                deriveKey(length, key, salt)
            assertEquals(derivedKey.size, length)
        }
    }
}