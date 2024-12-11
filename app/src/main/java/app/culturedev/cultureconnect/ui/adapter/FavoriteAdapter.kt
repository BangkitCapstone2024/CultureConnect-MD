package app.culturedev.cultureconnect.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.culturedev.cultureconnect.data.database.DataEntity
import app.culturedev.cultureconnect.databinding.ListItemBinding
import app.culturedev.cultureconnect.helper.Utils
import app.culturedev.cultureconnect.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class FavoriteAdapter(private val onFavoriteClick: (DataEntity) -> Unit): ListAdapter<DataEntity, FavoriteAdapter.ViewHolder>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataEntity) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivItemImage)
            binding.tvItemTitle.text = data.name
            binding.tvItemRating.text = data.rating
            binding.tvItemPrice.text = data.price

            binding.ivItemImage.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(Utils.EXTRA_ID, data.id)
                context.startActivity(intent)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchList(listCafe: List<DataEntity>){
        submitList(listCafe)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = currentList.size

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataEntity>() {
            override fun areItemsTheSame(oldItem: DataEntity, newItem: DataEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataEntity, newItem: DataEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}