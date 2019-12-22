package com.example.safecrypt

import com.example.safecrypt.nse.join
import com.example.safecrypt.nse.split
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class BytesJoiningTest {
    @Test
    fun testByteArrayJoin() {
        for (length1 in 0..128) {
            for (length2 in 0..128) {
                val bytes1 = Random.nextBytes(length1)
                val bytes2 = Random.nextBytes(length2)
                val joined = bytes1.join(bytes2)
                val (part1, part2) = joined.split()
                assertTrue(bytes1 contentEquals part1)
                assertTrue(bytes2 contentEquals part2)
            }
        }
    }
}