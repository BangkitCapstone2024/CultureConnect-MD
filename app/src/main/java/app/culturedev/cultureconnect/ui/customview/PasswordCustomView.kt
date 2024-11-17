package app.culturedev.cultureconnect.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class PasswordCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (text.toString().length < 8) {
                    setError("Kata sandi  tidak kurang dari 8 karakter ", null)
                } else {
                    error = null
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
}