package app.culturedev.cultureconnect.ui.recomendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityMoodResultBinding

class MoodResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoodResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMoodResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getCafeRecommendations()
    }

    private fun getCafeRecommendations() {
        binding.btnGetRecCafe.setOnClickListener {
            startActivity(Intent(this@MoodResultActivity, CafeResultActivity::class.java))
        }
    }
}