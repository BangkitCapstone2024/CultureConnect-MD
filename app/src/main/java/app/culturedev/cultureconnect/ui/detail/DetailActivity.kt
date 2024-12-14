package app.culturedev.cultureconnect.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityDetailBinding
import app.culturedev.cultureconnect.helper.NetworkUtil.isOnline
import app.culturedev.cultureconnect.helper.NetworkUtil.netToast
import app.culturedev.cultureconnect.helper.Utils
import com.bumptech.glide.Glide
import app.culturedev.cultureconnect.ui.MainActivity
import app.culturedev.cultureconnect.ui.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!isOnline(this)) {
            netToast(this)
        }

        navigateUp()
        fetchDetailCafe()
        updateFavoriteIcon(false)

        val cafeId = intent.getStringExtra(Utils.EXTRA_ID) ?: ""
    }

    private fun navigateUp() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun fetchDetailCafe() {
        val cafeImage = intent.getStringExtra(Utils.EXTRA_IMAGE)
        val cafeName = intent.getStringExtra(Utils.EXTRA_CAFE_NAME)
        val rating = intent.getStringExtra(Utils.EXTRA_RATING)
        val address = intent.getStringExtra(Utils.EXTRA_ADDRESS)
        val price = intent.getStringExtra(Utils.EXTRA_PRICE)
        val schedule = intent.getStringExtra(Utils.EXTRA_SCHEDULE)
        val menu = intent.getStringExtra(Utils.EXTRA_MENU)

        Glide.with(binding.root)
            .load(cafeImage)
            .into(binding.detailImage)

        binding.detailTitle.text = cafeName
        binding.detailRating.text = rating
        binding.detailAddress.text = address
        binding.detailPrice.text = price
        binding.detailSchedule.text = schedule
        binding.detailMenu.text = menu
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.fabBookmark.tag = if (isFavorite) "favorite" else "not_favorite"
        // Update favorite icon accordingly
        binding.fabBookmark.setImageResource(
            if (isFavorite) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24
        )
    }
}
