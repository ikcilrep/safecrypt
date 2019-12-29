package com.ikcilrep.safecrypt

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.ikcilrep.safecrypt.nse.deriveKeyFromPassword
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigInteger
import java.security.SecureRandom


lateinit var salt: ByteArray
lateinit var key: BigInteger
var password: String = ""


class MainActivity : AppCompatActivity() {
    private val secureRandom = SecureRandom()

    private fun goToEncryptionPanel() {
        val encryptionPanelIntent = Intent(applicationContext, EncryptionPanel::class.java)
        startActivity(encryptionPanelIntent)
    }

    private fun togglePasswordVisibility(buttonView: View, isChecked: Boolean) {
        if (isChecked) {
            passwordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
        }

    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val drawableActionBarColor = ColorDrawable(ResourcesCompat.getColor(resources,
            R.color.action_bar_color, null))
        supportActionBar?.setBackgroundDrawable(drawableActionBarColor)

        MobileAds.initialize(
            this
        ) { }
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        passwordInput.setText(password)

        encryptButton.setOnClickListener {
            salt = ByteArray(16)
            secureRandom.nextBytes(salt)
            password = passwordInput.text.toString()
            key =
                deriveKeyFromPassword(
                    password,
                    salt
                )
            currentOperation =
                Operation.ENCRYPT
            goToEncryptionPanel()
        }

        decryptButton.setOnClickListener {
            password = passwordInput.text.toString()
            currentOperation =
                Operation.DECRYPT
            goToEncryptionPanel()
        }

        showPasswordSwitch.setOnCheckedChangeListener (::togglePasswordVisibility)
    }


}
