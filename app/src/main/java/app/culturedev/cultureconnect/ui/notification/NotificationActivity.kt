package app.culturedev.cultureconnect.ui.notification

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.ActivityNotificationBinding
import app.culturedev.cultureconnect.ui.MainActivity
import app.culturedev.cultureconnect.ui.viewmodel.NotificationViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private val notificationViewModel: NotificationViewModel by lazy {
        ViewModelProvider(this, FactoryViewModel.getInstance(this))[NotificationViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchView()

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { notificationViewModel.filterNotify(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { notificationViewModel.filterNotify(it) }
                return true
            }
        })
    }
}