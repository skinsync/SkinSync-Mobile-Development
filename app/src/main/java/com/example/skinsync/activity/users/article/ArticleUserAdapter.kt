package com.example.skinsync.activity.users.article

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skinsync.activity.admin.article.ArticleAdminAdapter
import com.example.skinsync.activity.admin.article.DetailArticleAdminActivity
import com.example.skinsync.data.articleadmin.ArticleData
import com.example.skinsync.databinding.ListArticleAdminBinding
import com.example.skinsync.databinding.ListArticleUsersBinding

class ArticleUserAdapter : PagingDataAdapter<ArticleData, ArticleUserAdapter.ArticleUserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleUserViewHolder {
        val binding = ListArticleUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleUserViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    class ArticleUserViewHolder(private val binding: ListArticleUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleData) {
            Log.i("Adapter Data: ", article.toString())
            binding.apply {
                tvTitleArticle.text = article.title
                Glide.with(itemView.context)
                    .load(article.picture)
                    .into(imageArticle)

                root.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(article.url)
                    val context = itemView.context
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Log.e("Link Article: ", e.toString())
                    }
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