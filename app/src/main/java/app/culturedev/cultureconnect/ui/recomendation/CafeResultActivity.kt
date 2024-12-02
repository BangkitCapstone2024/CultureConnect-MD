package app.culturedev.cultureconnect.ui.recomendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityCafeResultBinding

class CafeResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCafeResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        backToSendMood()
    }

    private fun backToSendMood() {
        binding.btnBackSend.setOnClickListener {
            startActivity(Intent(this@CafeResultActivity, DescribeMoodActivity::class.java))
        }
    }
}