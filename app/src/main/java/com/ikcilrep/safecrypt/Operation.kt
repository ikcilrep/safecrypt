package com.ikcilrep.safecrypt

enum class Operation {
    ENCRYPT,
    DECRYPT
}

lateinit var currentOperation: Operation