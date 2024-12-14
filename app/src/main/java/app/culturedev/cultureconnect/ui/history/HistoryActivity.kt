package app.culturedev.cultureconnect.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.culturedev.cultureconnect.databinding.ActivityHistoryBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.helper.Utils
import app.culturedev.cultureconnect.ui.MainActivity
import app.culturedev.cultureconnect.ui.adapter.FavoriteAndHistoryAdapter
import app.culturedev.cultureconnect.ui.detail.DetailActivity
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

        val adapter = FavoriteAndHistoryAdapter(
            onItemClick = { cafe ->
                // Save to history when item is clicked
                historyViewModel.addHistory(cafe)

                // Navigate to detail activity
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra(Utils.EXTRA_ID, cafe.id)
                }
                startActivity(intent)
            },
            onFavoriteClick = { _ ->
                // Do nothing
            }
        )

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            this.adapter = adapter
        }

        historyViewModel.listCafeHistory.observe(this) { events ->
            if (events != null) {
                adapter.submitList(events)
            } else {
                Toast.makeText(this, "No favorite cafe", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}