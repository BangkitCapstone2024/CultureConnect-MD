package app.culturedev.cultureconnect.ui.recomendation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.data.result.ResultCafe
import app.culturedev.cultureconnect.databinding.ActivityDescribeMoodBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.helper.Utils
import app.culturedev.cultureconnect.ui.viewmodel.DescribeMoodViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.RecommendationFactoryViewModel

class DescribeMoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescribeMoodBinding
    private val vm by viewModels<DescribeMoodViewModel> {
        RecommendationFactoryViewModel.getInstance(this)
    }

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

        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }
        sendUserMood()
        navigateUp()
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun sendUserMood() {
        binding.btnSendMood.setOnClickListener {

            val moodInput = binding.edtUserInputMood.text.toString()

            if (moodInput.isEmpty()) {
                Toast.makeText(this, "Please input your mood ", Toast.LENGTH_SHORT).show()
            }
            vm.sendUserMood(moodInput).observe(this) { result ->

                when (result) {
                    is ResultCafe.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResultCafe.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        val intent =
                            Intent(this@DescribeMoodActivity, MoodResultActivity::class.java)
                        intent.putExtra(Utils.PREDICT_MOOD, result.data.predictedMood)
                        intent.putExtra(Utils.MOOD_QUOTE, result.data.quote)
                        intent.putExtra(Utils.LIST_PLACE, result.data.cafeRecommendation)
                        startActivity(intent)
                        finish()
                    }

                    is ResultCafe.Error -> {
                        Toast.makeText(this , result.error ,Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun navigateUp() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}