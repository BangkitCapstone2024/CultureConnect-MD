package app.culturedev.cultureconnect.ui.allCafe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityAllCafeBinding
import app.culturedev.cultureconnect.ui.MainActivity
import app.culturedev.cultureconnect.ui.adapter.Adapter
import app.culturedev.cultureconnect.ui.viewmodel.AllCafeViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel

class AllCafeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllCafeBinding
    private val adapter: Adapter by lazy { Adapter() }
    private val allCafeViewModel: AllCafeViewModel by lazy {
        ViewModelProvider(this, FactoryViewModel.getInstance(this))[AllCafeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCafeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        backButton()
        setupSearchView()
        setupFilterButton()
    }

    private fun setupRecyclerView() {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@AllCafeActivity)
            adapter = this@AllCafeActivity.adapter
        }
    }

    private fun setupObservers() {
        allCafeViewModel.listAllCafe.observe(this) { cafes ->
            adapter.submitList(cafes)
        }

        allCafeViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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

    private fun setupFilterButton() {
        binding.btnFilter.setOnClickListener {
            // Example popup menu for filters
            val popupMenu = androidx.appcompat.widget.PopupMenu(this, binding.btnFilter)
            popupMenu.menuInflater.inflate(R.menu.sort_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.sort_name_asc -> allCafeViewModel.updateCategories(listOf("Cafe"))
                    R.id.sort_name_desc -> allCafeViewModel.updatePriceRange("25–50k")
                    R.id.sort_rating -> allCafeViewModel.updateRatingThreshold(4.0)
                }
                true
            }
            popupMenu.show()
        }
    }
}
