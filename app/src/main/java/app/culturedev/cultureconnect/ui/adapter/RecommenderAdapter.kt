package app.culturedev.cultureconnect.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.culturedev.cultureconnect.data.response.recommendation.CafeRecommendation
import app.culturedev.cultureconnect.databinding.ListItemBinding
import com.bumptech.glide.Glide

class RecommenderAdapter :
    ListAdapter<CafeRecommendation, RecommenderAdapter.ViewHolder>(DIFF_CALLBACK) {
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

        fun bind(data: CafeRecommendation) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivItemImage)
            binding.tvItemTitle.text = data.title
            binding.tvItemRating.text = data.rating
            binding.tvItemPrice.text = data.price

//            binding.ivItemImage.setOnClickListener {
//                val context = binding.root.context
//                val intent = Intent(context, DetailActivity::class.java)
//                intent.putExtra(Utils.EXTRA_ID, data.)
//                context.startActivity(intent)
//            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CafeRecommendation>() {
            override fun areItemsTheSame(
                oldItem: CafeRecommendation,
                newItem: CafeRecommendation
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CafeRecommendation,
                newItem: CafeRecommendation
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}