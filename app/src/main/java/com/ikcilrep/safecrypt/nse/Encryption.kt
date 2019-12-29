package com.ikcilrep.safecrypt.nse

import java.math.BigInteger

@ExperimentalUnsignedTypes
fun encrypt(data: ByteArray, key: BigInteger, salt: ByteArray): Pair<LongArray, ByteArray> {
    if (data.isEmpty()) {
        return Pair(LongArray(0), ByteArray(0))
    }
    val shiftedData = data.shiftRightBits(key)
    val derivedKey = deriveKey(data.size, key, salt)
    val iv = generateIV(
        shiftedData.size,
        shiftedData,
        derivedKey
    )
    val result =
        shiftedData * derivedKey.dotProduct(derivedKey) - derivedKey * derivedKey.dotProduct(
            shiftedData - iv
        ) * 2
    return Pair(result, iv)
}


@ExperimentalUnsignedTypes
fun decrypt(data: LongArray, key: BigInteger, iv: ByteArray, salt: ByteArray): ByteArray {
    if (data.isEmpty()) {
        return ByteArray(0)
    }
    val derivedKey = deriveKey(data.size, key, salt)
    val squaredDerivedKeyLength = derivedKey.dotProduct(derivedKey)
    val doubledDerivedKey = derivedKey * 2
    val decrypted =
        ((data + doubledDerivedKey * derivedKey.dotProduct(iv)) * squaredDerivedKeyLength - doubledDerivedKey * derivedKey.dotProduct(
            data
        )) / (squaredDerivedKeyLength * squaredDerivedKeyLength)
    return decrypted.shiftLeftBits(key)
}