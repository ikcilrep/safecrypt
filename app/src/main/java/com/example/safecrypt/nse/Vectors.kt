package com.example.safecrypt.nse

fun ByteArray.isZeroVector() = isEmpty() || filter { it == 0.toByte() }.size == size

fun ByteArray.dotProduct(other: ShortArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index].toLong()) }


operator fun ByteArray.minus(other: ByteArray): ShortArray {
    if (other.size != size)
        throw IllegalStateException("Vectors sizes are different.")
    return ShortArray(size) { (this[it] - other[it]).toShort() }
}


