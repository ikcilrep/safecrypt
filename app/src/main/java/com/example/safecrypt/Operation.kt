package com.example.safecrypt

enum class Operation {
    ENCRYPT,
    DECRYPT
}

lateinit var currentOperation: Operation