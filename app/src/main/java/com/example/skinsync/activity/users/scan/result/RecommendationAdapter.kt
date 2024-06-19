package com.example.skinsync.activity.users.scan.result

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skinsync.R
import com.example.skinsync.databinding.ListProductUsersBinding

class RecommendationAdapter : PagingDataAdapter<RecommendationDataItem, RecommendationAdapter.RecommendationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ListProductUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val recommendation = getItem(position)
        Log.d("RecommendationAdapter", "Binding recommendation at position $position: $recommendation")
        recommendation?.let { holder.bind(it) }
    }

    class RecommendationViewHolder(private val binding: ListProductUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recommendation: RecommendationDataItem) {
            Log.d("RecommendationViewHolder", "Binding recommendation: $recommendation")
            binding.apply {
                tvNameProduct.text = recommendation.name
                tvTypeProduct.text = recommendation.productType
                Glide.with(itemView.context)
                    .load(recommendation.picture)
                    .placeholder(R.drawable.kosongproduct)
                    .into(imageProduct)

                // Implement click event here if needed
                root.setOnClickListener {
                    // Your click event implementation
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecommendationDataItem>() {
            override fun areItemsTheSame(oldItem: RecommendationDataItem, newItem: RecommendationDataItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: RecommendationDataItem, newItem: RecommendationDataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}