package com.example.safecrypt.nse

import java.security.SecureRandom

fun generateIV(length: Int, derivedKey: ByteArray, rotatedData: ByteArray): ByteArray {
    if (length < 1)
        throw IllegalStateException("Length is not positive.")
    var iv: ByteArray
    val random = SecureRandom()
    do {
        iv = ByteArray(length)
        random.nextBytes(iv)
    } while (derivedKey.dotProduct(rotatedData - iv) == 0.toLong())
    return iv
}

