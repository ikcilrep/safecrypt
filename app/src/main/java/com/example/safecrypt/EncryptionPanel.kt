package com.example.safecrypt

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.safecrypt.nse.*
import kotlinx.android.synthetic.main.activity_encryption_panel.*

class EncryptionPanel : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encryption_panel)
        messageInput.addTextChangedListener {doOperation()}

        resultView.setOnClickListener (::hideKeyboard)

        copyButton.setOnClickListener (::copyToClipboard)
    }

    @ExperimentalStdlibApi
    private fun doEncryption() {
        val (cipherText, iv) = encrypt(
            messageInput.text.toString().encodeToByteArray(),
            key,
            salt
        )
        resultView.text =
            Base64.encodeToString(cipherText.toByteArray().join(iv) + salt, Base64.DEFAULT)
    }


    @ExperimentalStdlibApi
    private fun doDecryption() {
        try {
            val bytes = Base64.decode(messageInput.text.toString(), Base64.DEFAULT)
            salt = bytes.takeLast(16).toByteArray()
            key = deriveKeyFromPassword(password, salt)
            val (cipherText, iv) = bytes.take(bytes.size - 16).toByteArray().split()
            resultView.text = decrypt(cipherText.toLongArray(), key, iv, salt).decodeToString()
        } catch (e: Exception) {
            resultView.text = "This message can't be decrypted."
        }
    }

    @ExperimentalStdlibApi
    private fun doOperation() {
        if (currentOperation == Operation.ENCRYPT) {
            doEncryption()
        } else {
            doDecryption()
        }
    }

    private fun hideKeyboard(view: View) {
        val inputManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun copyToClipboard(view: View) {
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(messageInput.text.toString(), resultView.text.toString())
        clipboard.setPrimaryClip(clip)
    }
}



