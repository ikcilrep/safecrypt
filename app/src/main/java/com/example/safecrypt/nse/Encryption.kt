package com.example.safecrypt.nse

import java.math.BigInteger

fun encrypt(data: ByteArray, key: BigInteger, salt: ByteArray): Pair<Array<Long>, ByteArray> {
    /*val rightShifted = data.shiftRightBits(key)
    val derivedKey = deriveKey(data.size, key, salt)*/
    TODO()
}

fun decrypt(data: Array<Long>, key: BigInteger, iv: ByteArray, salt: ByteArray): ByteArray =
    TODO()
