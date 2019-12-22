package com.example.safecrypt.nse

import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun LongArray.toByteArray(): ByteArray {
    var maxSize: Byte = 0
    val notFlattenResult = List<ByteArray>(size) {
        val result = ByteBuffer.allocate(8).putLong(this[it]).order(ByteOrder.BIG_ENDIAN).array()
        if (result.size > maxSize) {
            maxSize = result.size.toByte()
        }
        result
    }

    return notFlattenResult.flatMap { it.takeLast( maxSize.toInt()) }.toByteArray() + maxSize
}

fun ByteArray.toLongArray(): LongArray {
    val maxSize: Byte = this[size -1]
    if (maxSize == 0.toByte()) {
        return LongArray(0)
    }
    val result = LongArray((size - 1) / maxSize)
    try {
        for ((resultIndex, index) in (0 until size-1 step maxSize.toInt()).withIndex()) {
            result[resultIndex] = ByteBuffer.wrap(sliceArray(index until index + maxSize)).long

        }
    } catch (e: IndexOutOfBoundsException) {
        throw IllegalArgumentException("This array can't be converted into LongArray.")
    }
    return result
}
