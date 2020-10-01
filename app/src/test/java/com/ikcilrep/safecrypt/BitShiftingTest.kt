package com.ikcilrep.safecrypt

import com.ikcilrep.safecrypt.nse.cycleLeftBits
import com.ikcilrep.safecrypt.nse.cycleRightBits
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class BitShiftingTest {

    @Test
    fun testBitShift() {
        for (length in 0..128) {
            for (numberToShift in 0..(length shl 3)) {
                val data = Random.nextBytes(length)
                val leftShiftedData = data.cycleLeftBits(numberToShift.toBigInteger())
                val unLeftShiftedData = leftShiftedData.cycleRightBits(numberToShift.toBigInteger())
                assertTrue(unLeftShiftedData contentEquals data)
            }
        }
    }
}