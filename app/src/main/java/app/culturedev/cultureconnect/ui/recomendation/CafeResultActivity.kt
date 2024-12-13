package app.culturedev.cultureconnect.ui.recomendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.data.response.recommendation.CafeRecommendationItem
import app.culturedev.cultureconnect.databinding.ActivityCafeResultBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.helper.Utils
import app.culturedev.cultureconnect.ui.adapter.RecommenderAdapter

class CafeResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafeResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }
        backToSendMood()

        with(binding) {
            svCafe.setupWithSearchBar(sbSearchCafe)
        }
        val list = intent.getParcelableArrayListExtra<CafeRecommendationItem>(Utils.RECOMEN_PLACE)
        val recommendation = RecommenderAdapter()
        recommendation.submitList(list)
        binding.rvCafeRecomm.apply {
            adapter = recommendation
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun backToSendMood() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@CafeResultActivity, DescribeMoodActivity::class.java))
        }
    }
}