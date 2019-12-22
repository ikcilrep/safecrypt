package com.example.safecrypt

import com.example.safecrypt.nse.shiftLeftBits
import com.example.safecrypt.nse.shiftRightBits
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class BitShiftingTest {

    @Test
    fun testBitShift() {
        for (length in 0..128) {
            for (numberToShift in 0..(length shl 3)) {
                val data = Random.nextBytes(length)
                val leftShiftedData = data.shiftLeftBits(numberToShift.toBigInteger())
                val unLeftShiftedData = leftShiftedData.shiftRightBits(numberToShift.toBigInteger())
                assertTrue(unLeftShiftedData contentEquals data)
            }
        }
    }
}