package com.example.safecrypt.nse

import java.math.BigInteger

@ExperimentalUnsignedTypes
infix fun UByte.shl(shift: Int): UByte = (this.toInt() shl shift).toUByte()

@ExperimentalUnsignedTypes
infix fun UByte.shr(shift: Int): UByte = (this.toInt() shr shift).toUByte()

@ExperimentalUnsignedTypes
fun shiftLeftBitsInBytes(byte1: UByte, byte2: UByte, numberOfBitsToShift: Int): UByte =
    (byte1 shl numberOfBitsToShift) or (byte2 shr (8 - numberOfBitsToShift))

@ExperimentalUnsignedTypes
fun shiftRightBitsInBytes(byte1: UByte, byte2: UByte, numberOfBitsToShift: Int): UByte =
    (byte1 shl (8 - numberOfBitsToShift)) or (byte2 shr numberOfBitsToShift)


@ExperimentalUnsignedTypes
fun UByteArray.shiftLeftBits(numberOfBitsToShift: BigInteger): UByteArray {
    if (isEmpty()) {
        return UByteArray(0)
    }

    val (numberOfBytesToShift, numberOfBitsToShiftWithoutBytes) = numberOfBytesToShiftWithRest(
        this,
        numberOfBitsToShift
    )

    val bitShifted = UByteArray(size)
    bitShifted[size - 1] =
        shiftLeftBitsInBytes(this[size - 1], this[0], numberOfBitsToShiftWithoutBytes)
    (0 until (size - 1)).forEach {
        bitShifted[it] =
            shiftLeftBitsInBytes(this[it], this[it + 1], numberOfBitsToShiftWithoutBytes)
    }

    val byteShifted = UByteArray(size)
    (0 until size - numberOfBytesToShift).forEach {
        byteShifted[it] = bitShifted[it + numberOfBytesToShift]
    }
    (size - numberOfBytesToShift until size).forEach {
        byteShifted[it] = bitShifted[it + numberOfBytesToShift - size]
    }
    return byteShifted
}

@ExperimentalUnsignedTypes
fun UByteArray.shiftRightBits(numberOfBitsToShift: BigInteger): UByteArray {
    if (isEmpty()) {
        return UByteArray(0)
    }
    val (numberOfBytesToShift, numberOfBitsToShiftWithoutBytes) = numberOfBytesToShiftWithRest(
        this,
        numberOfBitsToShift
    )

    val byteShifted = UByteArray(size)
    (0 until numberOfBytesToShift).forEach {
        byteShifted[it] = this[size + it - numberOfBytesToShift]
    }
    (numberOfBytesToShift until size).forEach { byteShifted[it] = this[it - numberOfBytesToShift] }

    val bitShifted = UByteArray(size)
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

@ExperimentalUnsignedTypes
fun numberOfBytesToShiftWithRest(
    data: UByteArray,
    numberOfBitsToShift: BigInteger
): Pair<Int, Int> {
    val numberOfBitsToShiftWithoutSize =
        numberOfBitsToShift.mod((data.size shl 3).toBigInteger()).toInt()
    val numberOfBytesToShift = numberOfBitsToShiftWithoutSize shr 3
    val numberOfBitsToShiftWithoutBytes = numberOfBitsToShiftWithoutSize and 7
    return Pair(numberOfBytesToShift, numberOfBitsToShiftWithoutBytes)
}

