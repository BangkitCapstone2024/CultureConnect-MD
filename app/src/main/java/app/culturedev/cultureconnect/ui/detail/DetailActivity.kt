package app.culturedev.cultureconnect.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityDetailBinding
import app.culturedev.cultureconnect.helper.NetworkUtil.isOnline
import app.culturedev.cultureconnect.helper.NetworkUtil.netToast
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

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}