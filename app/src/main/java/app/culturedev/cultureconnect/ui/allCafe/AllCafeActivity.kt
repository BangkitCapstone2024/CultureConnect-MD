package app.culturedev.cultureconnect.ui.allCafe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        backButton()
    }

    private fun setupRecyclerView() {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@AllCafeActivity)
            adapter = this@AllCafeActivity.adapter
        }
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
