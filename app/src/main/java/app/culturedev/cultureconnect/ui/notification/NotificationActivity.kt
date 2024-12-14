package app.culturedev.cultureconnect.ui.notification

import NotificationAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.data.database.NotificationData
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationAdapter: NotificationAdapter
    private val notificationList = mutableListOf<NotificationData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // Initialize RecyclerView and Adapter
        val notificationRecyclerView: RecyclerView = findViewById(R.id.notificationList)
        notificationRecyclerView.layoutManager = LinearLayoutManager(this)
        notificationAdapter = NotificationAdapter(notificationList)
        notificationRecyclerView.adapter = notificationAdapter

        // Handle back button action
        val backButton: FloatingActionButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    // Function to add notifications to the list
    fun addNotification(notification: NotificationData) {
        notificationAdapter.addNotification(notification)
    }
}
