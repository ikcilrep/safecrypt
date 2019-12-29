package com.ikcilrep.safecrypt.nse

import org.bouncycastle.crypto.digests.SHA512Digest
import org.bouncycastle.crypto.generators.HKDFBytesGenerator
import org.bouncycastle.crypto.params.HKDFParameters
import java.math.BigInteger
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

fun deriveKey(length: Int, key: BigInteger, salt: ByteArray): ByteArray {
    val hkdf = HKDFBytesGenerator(SHA512Digest())
    hkdf.init(HKDFParameters(key.toByteArray(), salt, byteArrayOf()))

    var derivedKey: ByteArray
    do {
        derivedKey = ByteArray(length)
        hkdf.generateBytes(derivedKey, 0, derivedKey.size)
    } while (derivedKey.isZeroVector())

    return derivedKey
}
