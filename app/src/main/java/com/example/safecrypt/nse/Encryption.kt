package com.example.safecrypt.nse

import java.math.BigInteger

fun encrypt(data: ByteArray, key: BigInteger, salt: ByteArray): Pair<LongArray, ByteArray> {
    if (data.isEmpty()) {
        return Pair(LongArray(0), ByteArray(0))
    }
    val shiftedData = data.shiftRightBits(key)
    val derivedKey = deriveKey(data.size, key, salt)
    val iv = generateIV(shiftedData.size, shiftedData, derivedKey)
    val result =
        shiftedData * derivedKey.dotProduct(derivedKey) - derivedKey * derivedKey.dotProduct(
            shiftedData - iv
        ) * 2
    return Pair(result, iv)
}


fun decrypt(data: LongArray, key: BigInteger, iv: ByteArray, salt: ByteArray): ByteArray {
    val derivedKey = deriveKey(data.size, key, salt)
    val squaredDerivedKeyLength = derivedKey.dotProduct(derivedKey)
    val doubledDerivedKey = derivedKey * 2
    val decrypted =
        ((data + doubledDerivedKey * derivedKey.dotProduct(iv)) * squaredDerivedKeyLength - doubledDerivedKey * derivedKey.dotProduct(
            data
        )) / (squaredDerivedKeyLength * squaredDerivedKeyLength)
    return decrypted.shiftLeftBits(key)
}
