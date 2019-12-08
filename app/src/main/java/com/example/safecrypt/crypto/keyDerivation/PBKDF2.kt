package com.example.safecrypt.crypto.keyDerivation

import kotlin.experimental.xor

fun ByteArray.xor(other: ByteArray) = mapIndexed{index, byte -> byte.xor(other[index])}.toByteArray()
fun Int.toByteArray(): ByteArray =
    byteArrayOf((this shr 24).toByte(),(this shr 16).toByte(),(this shr 8).toByte())

fun f(prf: (ByteArray, ByteArray) -> ByteArray, password: ByteArray, salt: ByteArray,
      iterations: Int, index: Int): ByteArray {
    val u = mutableListOf(prf(password, salt + index.toByteArray()))
    (1 until iterations).forEach{u.add(prf(password, u[it -1]))}
    return u.reduce{ acc, el -> acc.xor(el)}
}

fun PBKDF2(prf: (ByteArray, ByteArray) -> ByteArray, password: ByteArray, salt: ByteArray,
           iterations: Int, desiredLength: Int, hashLength: Int): ByteArray =
    (1..desiredLength/hashLength).map { f(prf, password, salt, iterations, it) }.reduce(ByteArray::plus)

