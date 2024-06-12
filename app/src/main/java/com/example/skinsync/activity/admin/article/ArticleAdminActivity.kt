package com.example.skinsync.activity.admin.article

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skinsync.R
import com.example.skinsync.data.articleadmin.ArticleData
import com.example.skinsync.databinding.ActivityArticleAdminBinding
import com.example.skinsync.viewmodel.ViewModelFactory

class ArticleAdminActivity : AppCompatActivity() {
    private val articleAdminViewModel by viewModels<ArticleAdminViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityArticleAdminBinding
    private lateinit var articleAdminAdapter: ArticleAdminAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityArticleAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadArticle()
        buttonConfig()
    }

    private fun buttonConfig() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, CreateArticleAdminActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadArticle() {
        articleAdminAdapter = ArticleAdminAdapter()

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = articleAdminAdapter

        articleAdminViewModel.article.observe(this) {
            articleAdminAdapter.submitData(lifecycle, it)
        }
    }

}