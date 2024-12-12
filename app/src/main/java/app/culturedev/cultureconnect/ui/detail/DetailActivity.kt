package app.culturedev.cultureconnect.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityDetailBinding
import app.culturedev.cultureconnect.helper.NetworkUtil.isOnline
import app.culturedev.cultureconnect.helper.NetworkUtil.netToast
import app.culturedev.cultureconnect.helper.Utils
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (!isOnline(this)) {
            netToast(this)
        }
        navigateUp()
        fetchDetailCafe()
    }

    private fun navigateUp() {
        binding.detailBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun fetchDetailCafe(){
        val cafeImage = intent.getStringExtra(Utils.EXTRA_IMAGE)
        val cafeName = intent.getStringExtra(Utils.EXTRA_CAFE_NAME)
        val rating = intent.getStringExtra(Utils.EXTRA_RATING)
        val address = intent.getStringExtra(Utils.EXTRA_ADDRESS)
        val price = intent.getStringExtra(Utils.EXTRA_PRICE)
        Glide.with(binding.root)
            .load(cafeImage)
            .into(binding.detailImage)
        binding.detailTitle.text = cafeName
        binding.tvRating.text = rating
        binding.tvAlamat.text = address
        binding.tvPrice.text  = price
    }
}