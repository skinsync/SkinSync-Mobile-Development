package com.example.skinsync.activity.users.listproduct

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skinsync.databinding.ListProductUsersBinding

class ListProductAdapter : PagingDataAdapter<ProductDataItem, ListProductAdapter.ListProductViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListProductViewHolder {
        val binding = ListProductUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        println("ppppp1")
        return ListProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListProductViewHolder, position: Int) {
        val product = getItem(position)
        Log.d("ListProductAdapter", "Binding product at position $position: $product")
        if (product != null) {
            holder.bind(product)
        }
    }

    class ListProductViewHolder(private val binding: ListProductUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductDataItem) {
            Log.d("ListProductViewHolder", "Binding product: $product")
            binding.apply {

                tvNameProduct.text = product.name
                //tvTypeProduct.text = product.productType!!.name
                Glide.with(itemView.context)
                    .load(product.picture)
                    .into(imageProduct)

                root.setOnClickListener {
                    //val intent = Intent(itemView.context, DetailArticleAdminActivity::class.java)
                    //intent.putExtra("article", article)
//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            itemView.context as Activity,
//                            Pair(ivItemPhoto, "photo"),
//                            Pair(tvItemName, "name"),
//                            Pair(tvItemDesc, "description"),
//                        )
//                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductDataItem>() {
            override fun areItemsTheSame(oldItem: ProductDataItem, newItem: ProductDataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductDataItem, newItem: ProductDataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}