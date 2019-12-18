package com.example.safecrypt.nse

import java.math.BigInteger

fun encrypt(data: ByteArray, key: BigInteger, salt: ByteArray): Pair<LongArray, ByteArray> {
    /*val rightShifted = data.shiftRightBits(key)
    val derivedKey = deriveKey(data.size, key, salt)
    val IV = generateIV(rightShifted.size, rightShifted, derivedKey)
    val result = rightShifted * derivedKey.dotProduct(derivedKey) - derivedKey*derivedKey.dotProduct(rightShifted - IV)*2

     */
    TODO()
}


fun decrypt(data: LongArray, key: BigInteger, iv: ByteArray, salt: ByteArray): ByteArray =
    TODO()
