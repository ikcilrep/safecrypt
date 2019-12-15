package com.example.safecrypt.nse

import java.lang.Exception


fun ByteArray.dotProduct(other: ByteArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index].toLong()) }

fun ByteArray.minus(other: ByteArray): ShortArray {
    if (other.size != size)
        throw Exception("Vectors sizes are different.")
    return ShortArray(size) { (this[it] - other[it]).toShort() }
}


