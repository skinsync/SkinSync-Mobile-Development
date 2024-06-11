package com.example.skinsync.activity.admin.article

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.skinsync.data.articleadmin.ArticleData
import com.example.skinsync.databinding.ListArticleAdminBinding
import com.bumptech.glide.Glide

class ArticleAdminAdapter : PagingDataAdapter<ArticleData, ArticleAdminAdapter.ArticleAdminViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdminViewHolder {
        val binding = ListArticleAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleAdminViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleAdminViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    class ArticleAdminViewHolder(private val binding: ListArticleAdminBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleData) {
            Log.i("Adapter Data: ", article.toString())
            binding.apply {

                textTitle.text = article.title
                textContent.text = article.description
                Glide.with(itemView.context)
                    .load(article.picture)
                    .into(imageView)

                root.setOnClickListener {
                    val intent = Intent(itemView.context, DetailArticleAdminActivity::class.java)
                    intent.putExtra("article", article)
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleData>() {
            override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem == newItem
            }
        }
    }
}
