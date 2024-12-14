package app.culturedev.cultureconnect.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.culturedev.cultureconnect.data.database.DataEntity
import app.culturedev.cultureconnect.databinding.ListItemBinding
import app.culturedev.cultureconnect.helper.Utils
import app.culturedev.cultureconnect.ui.adapter.FavoriteAdapter.Companion.DIFF_CALLBACK
import app.culturedev.cultureconnect.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class HistoryAdapter (
    private val onHistoryClick: (DataEntity) -> Unit
) : ListAdapter<DataEntity, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataEntity) {
            // Load image using Glide
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivItemImage)

            // Bind other data
            binding.tvItemTitle.text = data.name
            binding.tvItemRating.text = data.rating
            binding.tvItemPrice.text = data.price

            // Handle click for detail navigation
            binding.ivItemImage.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(Utils.EXTRA_ID, data.id)
                context.startActivity(intent)
            }

            // Handle favorite click using the provided callback
            binding.root.setOnLongClickListener {
                onHistoryClick(data)
                true
            }
        }
    }

    // Replace data without forcing UI refresh
    fun submitSearchList(listCafe: List<DataEntity>) {
        submitList(listCafe)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataEntity>() {
            override fun areItemsTheSame(oldItem: DataEntity, newItem: DataEntity): Boolean {
                // Compare by unique identifier
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataEntity, newItem: DataEntity): Boolean {
                // Compare contents of items
                return oldItem == newItem
            }
        }
    }
}