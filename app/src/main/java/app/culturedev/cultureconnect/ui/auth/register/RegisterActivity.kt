package app.culturedev.cultureconnect.ui.auth.register

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityRegisterBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ColorUtils.changeStatusBarColor(window, "#CC444B")
        navigateUp()

        auth = Firebase.auth
    }

    private fun navigateUp() {
        binding.btnBackLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}