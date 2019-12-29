package com.ikcilrep.safecrypt

import com.ikcilrep.safecrypt.nse.decrypt
import com.ikcilrep.safecrypt.nse.encrypt
import org.junit.Assert
import org.junit.Test
import java.math.BigInteger
import kotlin.random.Random

class EncryptionTest {
    @ExperimentalUnsignedTypes
    @Test
    fun testEncrypt() {
        val salt = Random.nextBytes(16)
        val key = BigInteger(Random.nextBytes(32))
        for (length in 1..128) {
            val data = Random.nextBytes(length)
            val (encryptedData, iv) = encrypt(
                data,
                key,
                salt
            )
            val decryptedData =
                decrypt(encryptedData, key, iv, salt)
            Assert.assertTrue(decryptedData contentEquals data)
        }
    }
}