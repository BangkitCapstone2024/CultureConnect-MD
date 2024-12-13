package app.culturedev.cultureconnect.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityHistoryBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.helper.Utils
import app.culturedev.cultureconnect.ui.MainActivity
import app.culturedev.cultureconnect.ui.adapter.FavoriteAdapter
import app.culturedev.cultureconnect.ui.detail.DetailActivity
import app.culturedev.cultureconnect.ui.viewmodel.FavoriteViewModel
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
        setupSearchView()

        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }

        val adapter = FavoriteAdapter { dataEntity ->
            dataEntity.id.let {
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra(Utils.EXTRA_ID, it)
                }
                startActivity(intent)
            }
        }

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            this.adapter = adapter
        }

        historyViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { historyViewModel.searchHistory(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { historyViewModel.searchHistory(it) }
                return true
            }
        })
    }
}