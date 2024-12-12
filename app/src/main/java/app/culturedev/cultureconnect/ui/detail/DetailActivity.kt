package app.culturedev.cultureconnect.ui.detail

import android.content.Intent
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
import app.culturedev.cultureconnect.ui.MainActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!isOnline(this)) {
            netToast(this)
        }
        navigateUp()
        fetchDetailCafe()
    }

    private fun navigateUp() {
        binding.btnBack.setOnClickListener {
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
        binding.detailRating.text = rating
        binding.detailAddress.text = address
        binding.detailPrice.text  = price
    }
}