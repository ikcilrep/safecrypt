package com.ikcilrep.safecrypt

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.ikcilrep.safecrypt.nse.*
import kotlinx.android.synthetic.main.activity_encryption_panel.*


class EncryptionPanel : AppCompatActivity() {

    @ExperimentalUnsignedTypes
    @SuppressLint("ResourceAsColor")
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encryption_panel)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val drawableActionBarColor = ColorDrawable(ResourcesCompat.getColor(resources,
            R.color.action_bar_color, null))

        supportActionBar?.setBackgroundDrawable(drawableActionBarColor)

        setNonConstantTexts()

        messageInput.addTextChangedListener {doOperation()}

        resultView.setOnClickListener {hideKeyboard(this)}

        copyButton.setOnClickListener {copyToClipboard()}
    }

    @ExperimentalUnsignedTypes
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

    @ExperimentalUnsignedTypes
    @ExperimentalStdlibApi
    private fun setNonConstantTexts() {
        when(currentOperation) {
            Operation.ENCRYPT -> {
                messageInput.hint = getString(R.string.encrypt_message_hint)
                supportActionBar?.title = getString(R.string.encrypt_title)
                doOperation()
            }
            Operation.DECRYPT -> {
                messageInput.hint = getString(R.string.decrypt_message_hint)
                supportActionBar?.title = getString(R.string.decrypt_title)
            }
        }
    }

    @ExperimentalUnsignedTypes
    @ExperimentalStdlibApi
    private fun doDecryption() {
        if (messageInput.text.toString().isNotEmpty()) {
            try {
                val bytes = Base64.decode(messageInput.text.toString(), Base64.DEFAULT)
                salt = bytes.takeLast(16).toByteArray()
                key =
                    deriveKeyFromPassword(
                        password,
                        salt
                    )
                val (cipherText, iv) = bytes.take(bytes.size - 16).toByteArray().split()
                resultView.text = decrypt(
                    cipherText.toLongArray(),
                    key,
                    iv,
                    salt
                ).decodeToString()
            } catch (e: Exception) {
                resultView.text = getString(R.string.decryption_error)
                resultView.setTextColor(Color.parseColor("#ff0000"))
            }
        }
    }

    @ExperimentalUnsignedTypes
    @ExperimentalStdlibApi
    private fun doOperation() {
        resultView.setTextColor(Color.parseColor("#ffffff"))
        resultView.text = ""
        when(currentOperation) {
            Operation.ENCRYPT -> doEncryption()
            Operation.DECRYPT -> doDecryption()
        }
    }
    private fun hideKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun copyToClipboard() {
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(messageInput.text.toString(), resultView.text.toString())
        clipboard.setPrimaryClip(clip)
    }
}


