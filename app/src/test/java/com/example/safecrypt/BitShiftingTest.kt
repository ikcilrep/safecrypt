package com.example.safecrypt

import com.example.safecrypt.nse.shiftLeftBits
import com.example.safecrypt.nse.shiftRightBits
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random
import kotlin.random.nextUBytes

class BitShiftingTest {
    @ExperimentalUnsignedTypes
    @Test
    fun testBitShift() {
        for (length in 0..128) {
            for (numberToShift in 0..(length shl 3)) {
                val data = Random.nextUBytes(length)
                val leftShiftedData = data.shiftLeftBits(numberToShift.toBigInteger())
                val unLeftShiftedData = leftShiftedData.shiftRightBits(numberToShift.toBigInteger())
                assertTrue(unLeftShiftedData contentEquals  data)
            }
        }
    }
}