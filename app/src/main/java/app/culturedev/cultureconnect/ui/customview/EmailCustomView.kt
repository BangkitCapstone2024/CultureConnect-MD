package app.culturedev.cultureconnect.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class EmailCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val emailRegex: Regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
                if (!text.toString().matches(emailRegex)) {
                    setError("Email harus memiliki karakter unik ", null)
                }
            }
        })
    }
}