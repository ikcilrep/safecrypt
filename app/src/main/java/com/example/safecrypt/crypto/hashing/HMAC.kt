package com.example.safecrypt.crypto.hashing

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun hmacSHA512(
    key: ByteArray,
    data: ByteArray
): ByteArray {
    val mac: Mac = Mac.getInstance("HmacSHA512")
    mac.init(SecretKeySpec(key, "HmacSHA512"))
    return mac.doFinal(data)
}