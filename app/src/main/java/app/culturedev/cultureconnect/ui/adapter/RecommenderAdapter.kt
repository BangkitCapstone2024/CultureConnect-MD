package app.culturedev.cultureconnect.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.culturedev.cultureconnect.data.response.recommendation.CafeRecommendationItem
import app.culturedev.cultureconnect.databinding.ListItemBinding
import app.culturedev.cultureconnect.helper.Utils
import app.culturedev.cultureconnect.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class RecommenderAdapter :
    ListAdapter<CafeRecommendationItem, RecommenderAdapter.ViewHolder>(DIFF_CALLBACK) {
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

        fun bind(data: CafeRecommendationItem) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivItemImage)
            binding.tvItemTitle.text = data.title
            binding.tvItemRating.text = data.rating
            binding.tvItemPrice.text = data.category

            binding.ivItemImage.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(Utils.EXTRA_IMAGE, data.image)
                intent.putExtra(Utils.EXTRA_CAFE_NAME, data.title)
                intent.putExtra(Utils.EXTRA_RATING, data.rating)
                intent.putExtra(Utils.EXTRA_ADDRESS, data.address)
                intent.putExtra(Utils.EXTRA_PRICE, data.price)
                context.startActivity(intent)
            }

            binding.btnItemDetail.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(Utils.EXTRA_IMAGE, data.image)
                intent.putExtra(Utils.EXTRA_CAFE_NAME, data.title)
                intent.putExtra(Utils.EXTRA_RATING, data.rating)
                intent.putExtra(Utils.EXTRA_ADDRESS, data.address)
                intent.putExtra(Utils.EXTRA_PRICE, data.price)
                context.startActivity(intent)
            }

            binding.btnItemDirection.setOnClickListener {
                val context = binding.root.context
                val intentUri = Uri.parse(data.pageURL)
                val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                context.startActivity(mapIntent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CafeRecommendationItem>() {
            override fun areItemsTheSame(
                oldItem: CafeRecommendationItem,
                newItem: CafeRecommendationItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CafeRecommendationItem,
                newItem: CafeRecommendationItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}