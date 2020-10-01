package com.ikcilrep.safecrypt.nse

class CipherText(val encryptedData: LongArray, val ivs: ByteArray, val salts: ByteArray) {
    fun toByteArray(): ByteArray {
        return encryptedData.toByteArray().join(ivs).join(salts)
    }
}