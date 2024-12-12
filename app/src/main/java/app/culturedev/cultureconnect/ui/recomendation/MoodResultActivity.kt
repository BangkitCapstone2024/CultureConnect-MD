package app.culturedev.cultureconnect.ui.recomendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.data.response.recommendation.CafeRecommendation
import app.culturedev.cultureconnect.databinding.ActivityMoodResultBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.helper.Utils

class MoodResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoodResultBinding
    private lateinit var listed:CafeRecommendation
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
        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }
        getCafeRecommendations()

        listed = intent.getParcelableExtra(Utils.LIST_PLACE)!!
        fetchUserMood()
    }

    private fun getCafeRecommendations() {

        binding.btnGetRecCafe.setOnClickListener {
            val intent = Intent(this@MoodResultActivity, CafeResultActivity::class.java)
            intent.putExtra(Utils.RECOMEN_PLACE, listed)
            startActivity(intent)
        }
    }

    private fun fetchUserMood() {
        val predictedMood = intent.getStringExtra(Utils.PREDICT_MOOD)
        val quoteMood = intent.getStringExtra(Utils.MOOD_QUOTE)

        when (predictedMood) {
            "Sadness" -> {
                binding.ivMoodResult.setImageResource(R.drawable.sad)
            }

            "Joy" -> {
                binding.ivMoodResult.setImageResource(R.drawable.joy)
            }

            "Anger" -> {
                binding.ivMoodResult.setImageResource(R.drawable.angry)
            }

            "Love" -> {
                binding.ivMoodResult.setImageResource(R.drawable.love)
            }

            "Fear" -> {
//                binding.ivMoodResult.setImageResource(R.drawable.ic_surprise)
            }

            "Surprise" -> {

            }

        }
        binding.tvQuoteMood.text = predictedMood
        binding.tvDescriptionQuote.text = quoteMood
    }
}