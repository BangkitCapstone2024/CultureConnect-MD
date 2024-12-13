import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.data.database.NotificationData
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationAdapter(private val notificationList: MutableList<NotificationData>) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notificationList[position]
        holder.bind(notification)
    }

    override fun getItemCount(): Int = notificationList.size

    // Method to add new notifications
    fun addNotification(notification: NotificationData) {
        notificationList.add(notification)
        notifyItemInserted(notificationList.size - 1)
    }

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.notificationTitle)
        private val body: TextView = itemView.findViewById(R.id.notificationContent)
        private val time: TextView = itemView.findViewById(R.id.notificationTime)
        private val imageView: ImageView = itemView.findViewById(R.id.notificationImageView)

        fun bind(notification: NotificationData) {
            title.text = notification.title
            body.text = notification.body

            // Format the timestamp to 12-hour format with AM/PM
            val formattedTime = formatTime(notification.timestamp)
            time.text = formattedTime

            if (notification.imageUrl != null) {
                Glide.with(itemView.context)
                    .load(notification.imageUrl)
                    .placeholder(R.drawable.baseline_notifications_active_24)
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.baseline_notifications_active_24)
            }
        }

        private fun formatTime(timestamp: Long): String {
            val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val date = Date(timestamp)
            return dateFormat.format(date)
        }
    }

}
