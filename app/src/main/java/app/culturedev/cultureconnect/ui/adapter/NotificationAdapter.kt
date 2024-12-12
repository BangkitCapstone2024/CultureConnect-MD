//package app.culturedev.cultureconnect.ui.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import app.culturedev.cultureconnect.data.response.NotificationResult
//import app.culturedev.cultureconnect.databinding.NotificationItemBinding
//import com.bumptech.glide.Glide
//
//class NotificationAdapter:
//    ListAdapter<NotificationResult, NotificationAdapter.ViewHolder>(DIFF_CALLBACK) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val notification = getItem(position)
//        holder.bind(notification)
//    }
//
//    class ViewHolder(private val binding: NotificationItemBinding):
//        RecyclerView.ViewHolder(binding.root){
//            fun bind (data: NotificationResult){
//                Glide.with(binding.root)
//                    .load(data.image)
//                    .into(binding.notificationImageView)
//                binding.notificationTitle.text = data.title
//                binding.notificationContent.text = data.content
//                binding.notificationTime.text = data.time
//            }
//    }
//
//    companion object{
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NotificationResult>() {
//            override fun areItemsTheSame(oldItem: NotificationResult, newItem: NotificationResult): Boolean {
//                return oldItem == newItem
//            }
//
//            override fun areContentsTheSame(oldItem: NotificationResult, newItem: NotificationResult): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}