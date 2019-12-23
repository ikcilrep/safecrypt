package com.example.safecrypt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import org.bouncycastle.crypto.generators.SCrypt
import java.math.BigInteger
import java.security.SecureRandom


lateinit var salt: ByteArray
lateinit var key: BigInteger
lateinit var password: String
fun deriveKeyFromPassword(password: String, salt: ByteArray): BigInteger =
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
    lateinit var  mAdView : AdView
    private val secureRandom = SecureRandom()
    private fun goToEncryptionPanel() {
        val encryptionPanelIntent = Intent(applicationContext, EncryptionPanel::class.java)
        startActivity(encryptionPanelIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(
            this
        ) { }
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        encryptButton.setOnClickListener {
            salt = ByteArray(16)
            secureRandom.nextBytes(salt)
            password = passwordInput.text.toString()
            key = deriveKeyFromPassword(password, salt)
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
