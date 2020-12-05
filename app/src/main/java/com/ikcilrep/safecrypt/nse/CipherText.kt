package com.ikcilrep.safecrypt.nse

class CipherText(val encryptedData: LongArray, val salt: ByteArray, val ivs: ByteArray, val salts: ByteArray) {
    fun toByteArray(): ByteArray {
        return encryptedData.toByteArray().join(salt).join(ivs).join(salts)
    }
}