package app.culturedev.cultureconnect.ui.history

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import app.culturedev.cultureconnect.databinding.ActivityHistoryBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.MainActivity
import app.culturedev.cultureconnect.ui.viewmodel.HistoryViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val historyViewModel: HistoryViewModel by lazy {
        ViewModelProvider(this, FactoryViewModel.getInstance(this))[HistoryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backButton()

        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }

    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}