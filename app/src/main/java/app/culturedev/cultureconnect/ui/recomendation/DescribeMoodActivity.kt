package app.culturedev.cultureconnect.ui.recomendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityDescribeMoodBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.auth.login.LoginActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class DescribeMoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescribeMoodBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDescribeMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth = Firebase.auth

        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }
        if (firebaseAuth.uid == null) {
            startActivity(Intent(this@DescribeMoodActivity, LoginActivity::class.java))
            finish()
        }
        moveToResultMood()
    }

    private fun moveToResultMood() {
        binding.btnSendMood.setOnClickListener {
            startActivity(Intent(this@DescribeMoodActivity, MoodResultActivity::class.java))
        }
    }
}