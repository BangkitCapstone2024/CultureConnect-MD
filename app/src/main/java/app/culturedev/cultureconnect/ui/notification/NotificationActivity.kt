package app.culturedev.cultureconnect.ui.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.culturedev.cultureconnect.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotificationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)


        // Handle back button action
        val backButton: FloatingActionButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}
