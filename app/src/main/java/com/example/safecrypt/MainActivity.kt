package com.example.safecrypt

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigInteger
import org.bouncycastle.crypto.generators.SCrypt
import java.security.SecureRandom

lateinit var salt: ByteArray
lateinit var key: BigInteger
lateinit var password: String
fun deriveKey(): BigInteger =
    BigInteger(
        SCrypt.generate(
            password.toByteArray(),
            salt,
            16384,
            8,
            1,
            33
        )
    ).abs()

class MainActivity : AppCompatActivity() {
    private fun goToEncryptionPanel() {
        val encryptionPanelIntent = Intent(applicationContext, EncryptionPanel::class.java)
        startActivity(encryptionPanelIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        encryptButton.setOnClickListener {
            password = passwordInput.text.toString()
            currentOperation = Operation.ENCRYPT
            goToEncryptionPanel()
        }

        decryptButton.setOnClickListener {
            password = passwordInput.text.toString()
            currentOperation = Operation.DECRYPT
            goToEncryptionPanel()
        }
    }


}
