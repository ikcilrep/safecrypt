package com.ikcilrep.safecrypt.nse

import java.math.BigInteger
import java.security.SecureRandom


@ExperimentalUnsignedTypes
fun encrypt(data: ByteArray, key: BigInteger, salt: ByteArray): CipherText {
    if (data.isEmpty()) {
        return CipherText(LongArray(0), ByteArray(0), ByteArray(0), ByteArray(0))
    }
    val blockSize = 256
    val saltSize = 16
    val random = SecureRandom()
    val randomData = ByteArray(512 - data.size % 256)
    random.nextBytes(randomData)
    val digestedKey = digestInteger(key)
    val shiftedData = (data + randomData).cycleRightBits(digestedKey)
    var encrypted = longArrayOf(data.size.toLong(), saltSize.toLong(), blockSize.toLong())
    var ivs = ByteArray(0)
    var salts = ByteArray(0)

    shiftedData.indices.step(blockSize).forEach {
        val block = shiftedData.drop(it).take(blockSize).toByteArray()
        val blockSalt = ByteArray(saltSize)
        random.nextBytes(blockSalt)

        val derivedKey = deriveKey(block.size, key, blockSalt)
        val iv = generateIV(
            block.size,
            derivedKey,
            block
        )
        ivs += iv
        encrypted += block * derivedKey.dotProduct(derivedKey) - derivedKey * derivedKey.dotProduct(
            block - iv
        ) * 2
        salts += blockSalt
    }
    return CipherText(encrypted, salt, ivs, salts)
}


@ExperimentalUnsignedTypes
fun decrypt(data: LongArray, key: BigInteger, ivs: ByteArray, salts: ByteArray): ByteArray {
    if (data.isEmpty()) {
        return ByteArray(0)
    }

    val plainTextLength = data[0].toInt()
    val saltSize = data[1].toInt()
    val blockSize = data[2].toInt()


    var decrypted = ByteArray(0)
    (3 until data.size).step(blockSize).forEachIndexed { index, _ ->
        val block = data.drop(index * blockSize + 3).take(blockSize).toLongArray()
        val salt = salts.drop(index * saltSize).take(saltSize).toByteArray()
        val iv = ivs.drop(index * blockSize).take(blockSize).toByteArray()

        val derivedKey = deriveKey(block.size, key, salt)
        val keyNormSquared = derivedKey.dotProduct(derivedKey)
        val a = block.vectorSum(derivedKey * derivedKey.dotProduct(iv) * 2)

        var b = derivedKey * derivedKey.dotProduct(block) * 2
        // (ac - b) / c^2 is int => (ac - b) / c is int => a - b/c is int => b/c is int
        b /= keyNormSquared

        // (ac - b) / c ^ 2 is int <=> (a - b/c) / c is int
        var d = a - b
        d /= keyNormSquared
        decrypted += d.map { it.toByte() }.toByteArray()
    }

    val digestedKey = digestInteger(key)
    return decrypted.cycleLeftBits(digestedKey).take(plainTextLength).toByteArray()
}
