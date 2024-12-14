package app.culturedev.cultureconnect.ui.recomendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.data.response.recommendation.CafeRecommendationItem
import app.culturedev.cultureconnect.databinding.ActivityCafeResultBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.helper.Utils
import app.culturedev.cultureconnect.ui.MainActivity
import app.culturedev.cultureconnect.ui.adapter.RecommenderAdapter
import app.culturedev.cultureconnect.ui.viewmodel.SharedViewModel

class CafeResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeResultBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }

        val list = intent.getParcelableArrayListExtra<CafeRecommendationItem>(Utils.RECOMEN_PLACE)

        // Set data to SharedViewModel
        if (!list.isNullOrEmpty()) {
            sharedViewModel.setCafeRecommendations(list)
        }

        val recommendation = RecommenderAdapter()
        recommendation.submitList(list)
        binding.rvCafeRecomm.apply {
            adapter = recommendation
            layoutManager = LinearLayoutManager(context)
        }

        backToSendMood()
    }

    private fun backToSendMood() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@CafeResultActivity, MainActivity::class.java))
            finish()
        }
    }
}