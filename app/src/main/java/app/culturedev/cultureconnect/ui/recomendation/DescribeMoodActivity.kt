package app.culturedev.cultureconnect.ui.recomendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityDescribeMoodBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.viewmodel.DescribeMoodViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel

class DescribeMoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescribeMoodBinding
    private val vm by viewModels<DescribeMoodViewModel> {
        FactoryViewModel.getInstance(this)
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
        moveToResultMood()
        navigateUp()
    }

    private fun moveToResultMood() {
        binding.btnSendMood.setOnClickListener {
            startActivity(Intent(this@DescribeMoodActivity, MoodResultActivity::class.java))
            finish()
        }
    }

    private fun navigateUp() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}