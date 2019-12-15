package com.example.safecrypt.nse

import java.math.BigInteger

@ExperimentalUnsignedTypes
fun encrypt(data: UByteArray, key: BigInteger, salt: UByteArray): Pair<Array<Long>, ByteArray> =
    TODO()

@ExperimentalUnsignedTypes
fun decrypt(data: Array<Long>, key: BigInteger, iv: ByteArray, salt: UByteArray): UByteArray =
    TODO()
