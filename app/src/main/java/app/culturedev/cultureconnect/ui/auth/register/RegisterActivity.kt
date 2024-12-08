package app.culturedev.cultureconnect.ui.auth.register

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import app.culturedev.cultureconnect.databinding.ActivityRegisterBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import app.culturedev.cultureconnect.helper.NetworkUtil

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ColorUtils.changeStatusBarColor(window, "#CC444B")
        navigateUp()

        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }

    }

    private fun navigateUp() {
        binding.btnBackLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    companion object {
        private const val TAG = "CustomAuthActivity"
    }
}