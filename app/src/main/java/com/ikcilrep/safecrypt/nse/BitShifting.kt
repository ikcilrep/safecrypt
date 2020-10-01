package com.ikcilrep.safecrypt.nse

import java.math.BigInteger
import kotlin.experimental.or


@ExperimentalUnsignedTypes
infix fun Byte.shl(shift: Int): Byte = (this.toUByte().toInt() shl shift).toByte()


@ExperimentalUnsignedTypes
infix fun Byte.shr(shift: Int): Byte = (this.toUByte().toInt() shr shift).toByte()


@ExperimentalUnsignedTypes
fun shiftLeftBitsInBytes(byte1: Byte, byte2: Byte, numberOfBitsToShift: Int): Byte =
    (byte1 shl numberOfBitsToShift) or (byte2 shr (8 - numberOfBitsToShift))


@ExperimentalUnsignedTypes
fun shiftRightBitsInBytes(byte1: Byte, byte2: Byte, numberOfBitsToShift: Int): Byte =
    (byte1 shl (8 - numberOfBitsToShift)) or (byte2 shr numberOfBitsToShift)


@ExperimentalUnsignedTypes
fun ByteArray.cycleLeftBits(numberOfBitsToShift: BigInteger): ByteArray {
    if (isEmpty()) {
        return ByteArray(0)
    }

    val (numberOfBytesToShift, numberOfBitsToShiftWithoutBytes) = numberOfBytesToShiftWithRest(
        this,
        numberOfBitsToShift
    )

    val bitShifted = ByteArray(size)
    bitShifted[size - 1] =
        shiftLeftBitsInBytes(
            this[size - 1],
            this[0],
            numberOfBitsToShiftWithoutBytes
        )
    (0 until (size - 1)).forEach {
        bitShifted[it] =
            shiftLeftBitsInBytes(
                this[it],
                this[it + 1],
                numberOfBitsToShiftWithoutBytes
            )
    }

    val byteShifted = ByteArray(size)
    (0 until size - numberOfBytesToShift).forEach {
        byteShifted[it] = bitShifted[it + numberOfBytesToShift]
    }
    (size - numberOfBytesToShift until size).forEach {
        byteShifted[it] = bitShifted[it + numberOfBytesToShift - size]
    }
    return byteShifted
}


@ExperimentalUnsignedTypes
fun ByteArray.cycleRightBits(numberOfBitsToShift: BigInteger): ByteArray {
    if (isEmpty()) {
        return ByteArray(0)
    }
    val (numberOfBytesToShift, numberOfBitsToShiftWithoutBytes) = numberOfBytesToShiftWithRest(
        this,
        numberOfBitsToShift
    )

    val byteShifted = ByteArray(size)
    (0 until numberOfBytesToShift).forEach {
        byteShifted[it] = this[size + it - numberOfBytesToShift]
    }
    (numberOfBytesToShift until size).forEach { byteShifted[it] = this[it - numberOfBytesToShift] }

    val bitShifted = ByteArray(size)
    bitShifted[0] = shiftRightBitsInBytes(
        byteShifted[size - 1],
        byteShifted[0],
        numberOfBitsToShiftWithoutBytes
    )
    (1 until size).forEach {
        bitShifted[it] = shiftRightBitsInBytes(
            byteShifted[it - 1],
            byteShifted[it],
            numberOfBitsToShiftWithoutBytes
        )
    }

    return bitShifted
}


fun numberOfBytesToShiftWithRest(
    data: ByteArray,
    numberOfBitsToShift: BigInteger
): Pair<Int, Int> {
    val numberOfBitsToShiftWithoutSize =
        numberOfBitsToShift.mod((data.size shl 3).toBigInteger()).toInt()
    val numberOfBytesToShift = numberOfBitsToShiftWithoutSize shr 3
    val numberOfBitsToShiftWithoutBytes = numberOfBitsToShiftWithoutSize and 7
    return Pair(numberOfBytesToShift, numberOfBitsToShiftWithoutBytes)
}

