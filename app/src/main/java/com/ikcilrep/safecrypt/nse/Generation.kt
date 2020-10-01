package com.ikcilrep.safecrypt.nse

import org.bouncycastle.crypto.digests.SHA512Digest
import org.bouncycastle.crypto.generators.HKDFBytesGenerator
import org.bouncycastle.crypto.generators.SCrypt
import org.bouncycastle.crypto.params.HKDFParameters
import java.math.BigInteger
import java.security.SecureRandom


@ExperimentalUnsignedTypes
fun generateIV(length: Int, derivedKey: UShortArray, rotatedData: ByteArray): ByteArray {
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

@ExperimentalUnsignedTypes
fun deriveKey(length: Int, key: BigInteger, salt: ByteArray): UShortArray {
    val hkdf = HKDFBytesGenerator(SHA512Digest())
    hkdf.init(HKDFParameters(key.toByteArray(), salt, byteArrayOf()))

    val derivedKey = ByteArray(length);
    hkdf.generateBytes(derivedKey, 0, derivedKey.size)

    return derivedKey.mapPrimes()
}

fun deriveKeyFromPassword(password: String, salt: ByteArray): BigInteger =
    BigInteger(
        SCrypt.generate(
            password.toByteArray(),
            salt,
            16384,
            8,
            1,
            33
        )
    ).abs()
