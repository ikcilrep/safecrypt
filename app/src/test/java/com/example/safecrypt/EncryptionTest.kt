package com.example.safecrypt

import com.example.safecrypt.nse.decrypt
import com.example.safecrypt.nse.encrypt
import org.junit.Assert
import org.junit.Test
import java.math.BigInteger
import kotlin.random.Random
import kotlin.random.nextUBytes

class EncryptionTest {
    @ExperimentalUnsignedTypes
    @Test
    fun testEncrypt() {
        val salt = Random.nextUBytes(16)
        val key = BigInteger(Random.nextBytes(32))
        for (length in 0..128) {
            val data = Random.nextUBytes(length)
            val iv = Random.nextBytes(length)
            val encryptedData = encrypt(data, key, iv, salt)
            val decryptedData = decrypt(encryptedData, key, iv, salt)
            Assert.assertEquals(decryptedData, data)
        }
    }
}