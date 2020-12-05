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
        val key = BigInteger(Random.nextBytes(32))
        val length = 16001
        val data = Random.nextBytes(length)
        val cipherText = encrypt(
            data,
            key,
            ByteArray(0)
        )
        val decryptedData =
            decrypt(cipherText.encryptedData, key, cipherText.ivs, cipherText.salts)
        Assert.assertTrue(decryptedData contentEquals data)
    }
}