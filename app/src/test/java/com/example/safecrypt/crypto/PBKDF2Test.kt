package com.example.safecrypt.crypto

import com.example.safecrypt.crypto.hashing.hmacSHA512
import com.example.safecrypt.crypto.keyDerivation.PBKDF2
import org.junit.Test
import org.junit.Assert.assertEquals

class PBKDF2Test {
    @Test
    fun testPBKDF2() {
        assertEquals(PBKDF2(::hmacSHA512, "test".toByteArray(), "salt".toByteArray(), 1000,
            1000, 64).size, 1000)

    }
}