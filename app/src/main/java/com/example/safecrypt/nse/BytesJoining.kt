package com.example.safecrypt.nse

import java.nio.ByteBuffer

fun ByteArray.join(other: ByteArray): ByteArray =
    this + other + ByteBuffer.allocate(4).putInt(other.size).array()

fun ByteArray.split(): Pair<ByteArray, ByteArray> {
    val addedSize = ByteBuffer.wrap(takeLast(4).toByteArray()).int
    val firstPart = take(size - addedSize - 4).toByteArray()
    val secondPart = takeLast(addedSize + 4).take(addedSize).toByteArray()
    return Pair(firstPart, secondPart)
}
