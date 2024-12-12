package app.culturedev.cultureconnect.ui.allCafe

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityAllCafeBinding
import app.culturedev.cultureconnect.ui.viewmodel.AllCafeViewModel
import app.culturedev.cultureconnect.ui.viewmodel.HistoryViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel

class AllCafeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllCafeBinding
    private val allCafeViewModel: AllCafeViewModel by lazy {
        ViewModelProvider(this, FactoryViewModel.getInstance(this))[AllCafeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCafeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backButton()
        setupSearchView()
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {

        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { allCafeViewModel.filterCafes(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { allCafeViewModel.filterCafes(it) }
                return true
            }
        })
    }
}