package com.ikcilrep.safecrypt.nse

import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun LongArray.toByteArray(): ByteArray =
    flatMap {
        val result = ByteBuffer.allocate(8).putLong(it).order(ByteOrder.BIG_ENDIAN).array()
        val realSize = 8 - result.takeWhile { byte -> byte == 0.toByte() }.size
        listOf(realSize.toByte()) + result.takeLast(realSize)
    }.toByteArray()

fun ByteArray.toLongArray(): LongArray {
    var byteIndex = 0
    var index = 0
    val result = LongArray(size)
    try {
        while (byteIndex < size) {
            result[index] =
                ByteBuffer.wrap(ByteArray(8 - this[byteIndex]) + sliceArray((byteIndex + 1)..byteIndex + this[byteIndex]))
                    .order(
                        ByteOrder.BIG_ENDIAN
                    ).long
            byteIndex += this[byteIndex] + 1
            index++
        }
    } catch (e: IndexOutOfBoundsException) {
        throw IllegalArgumentException("This array cannot be converted into LongArray.")
    }
    return result.take(index).toLongArray()
}
