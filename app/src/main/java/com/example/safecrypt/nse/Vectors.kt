package com.example.safecrypt.nse

import java.lang.IllegalArgumentException

fun ByteArray.isZeroVector() = isEmpty() || filter { it == 0.toByte() }.size == size

fun ByteArray.dotProduct(other: ShortArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index].toLong()) }

fun ByteArray.dotProduct(other: ByteArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index].toLong()) }

fun ByteArray.dotProduct(other: LongArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index]) }

operator fun ByteArray.minus(other: ByteArray): ShortArray {
    if (other.size != size)
        throw IllegalStateException("Vectors sizes are different.")
    return ShortArray(size) { (this[it] - other[it]).toShort() }
}

operator fun LongArray.minus(other: LongArray): LongArray {
    if (other.size != size)
        throw IllegalStateException("Vectors sizes are different.")
    return LongArray(size) { this[it] - other[it] }
}

operator fun LongArray.plus(other: LongArray): LongArray {
    if (other.size != size)
        throw IllegalStateException("Vectors sizes are different.")
    return LongArray(size) { this[it] + other[it] }
}

operator fun ByteArray.times(multiplier: Long): LongArray =
    LongArray(size) { this[it] * multiplier }

operator fun LongArray.times(multiplier: Long): LongArray =
    LongArray(size) { this[it] * multiplier }

operator fun LongArray.div(divisor: Long): ByteArray {
    if (divisor == 0.toLong())
        throw IllegalArgumentException("Divisor is 0.")
    return ByteArray(size) { (this[it] / divisor).toByte() }
}

