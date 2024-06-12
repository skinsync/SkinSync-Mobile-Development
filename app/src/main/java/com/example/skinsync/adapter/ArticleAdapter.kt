package com.example.skinsync.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skinsync.R
import com.example.skinsync.data.articleuser.Article

class ArticleAdapter(private var articleList: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_article_users, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun updateData(newList: List<Article>) {
        articleList = newList
        notifyDataSetChanged()
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewArticle: ImageView = itemView.findViewById(R.id.image_article)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title_article)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tv_description_article)

        fun bind(article: Article) {
            imageViewArticle.setImageResource(R.drawable.dasha)
            titleTextView.text = article.title
            descriptionTextView.text = article.content
        }
    }
}