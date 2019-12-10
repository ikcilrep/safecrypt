package com.example.safecrypt.nse

import java.math.BigInteger

@ExperimentalUnsignedTypes
fun encrypt(data: UByteArray, key: BigInteger, iv: ByteArray, salt: UByteArray): Array<Long> =
    TODO()

@ExperimentalUnsignedTypes
fun decrypt(data: Array<Long>, key: BigInteger, iv: ByteArray, salt: UByteArray): UByteArray =
    TODO()
