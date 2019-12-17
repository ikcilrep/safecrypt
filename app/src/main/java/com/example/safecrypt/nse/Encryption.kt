package com.example.safecrypt.nse

import java.math.BigInteger

@ExperimentalUnsignedTypes
fun encrypt(data: UByteArray, key: BigInteger, salt: ByteArray): Pair<Array<Long>, ByteArray> {
    /*val rightShifted = data.shiftRightBits(key)
    val derivedKey = deriveKey(data.size, key, salt)*/
    TODO()
}

@ExperimentalUnsignedTypes
fun decrypt(data: Array<Long>, key: BigInteger, iv: ByteArray, salt: ByteArray): UByteArray =
    TODO()
