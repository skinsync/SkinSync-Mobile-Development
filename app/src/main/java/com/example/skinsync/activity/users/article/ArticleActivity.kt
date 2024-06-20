package com.example.skinsync.activity.users.article

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skinsync.R
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.activity.users.profile.ProfileViewModel
import com.example.skinsync.data.UserModel
import com.example.skinsync.databinding.ActivityArticleBinding
import com.example.skinsync.viewmodel.ArticleUserViewModel
import com.example.skinsync.viewmodel.LoadingViewModel
import com.example.skinsync.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.first
import java.util.Locale

class ArticleActivity : AppCompatActivity() {
    private val viewModel by viewModels<ArticleUserViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val viewModelArticle2 by viewModels<ArticleUserViewModel>{
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityArticleBinding
    private lateinit var articleAdapter: ArticleUserAdapter
    private lateinit var articleAdapter2: ArticleUserAdapter
    private lateinit var loadingViewModel: LoadingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val textViewDate: TextView = findViewById(R.id.textViewDate)

        // Membuat format tanggal
        val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(java.util.Date())

        // Menampilkan tanggal di TextView
        textViewDate.text = currentDate

        // Set up button back
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Memulai MainActivity
        }

        val seeMore = findViewById<TextView>(R.id.seeMore)
        seeMore.setOnClickListener {
            // Menampilkan toast
            Toast.makeText(this, "Fitur ini masih dalam tahap pengembangan", Toast.LENGTH_SHORT).show()
        }

        // Inisialisasi ViewModel
        loadingViewModel = ViewModelProvider(this).get(LoadingViewModel::class.java)

        // Observer untuk isLoading
        loadingViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        // Inisialisasi RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Inisialisasi ArticleAdapter
        articleAdapter = ArticleUserAdapter() // Mulai dengan list kosong
        binding.recyclerView.adapter = articleAdapter

        // Observasi LiveData untuk mendapatkan data artikel
        viewModel.articles.observe(this) {
            //binding.usernameUser.text = data.name
            loadingViewModel.setLoadingStatus(true)
            articleAdapter.submitData(lifecycle, it)
            loadingViewModel.setLoadingStatus(false)
        }

        profileViewModel.fetchProfile()

        profileViewModel.myProfile.observe(this) { profileResponse ->
            if (profileResponse?.data != null) {
                binding.usernameUser.text = profileResponse.data.name
                println(profileResponse.data.name)
            }else{
                Log.e("ProfileActivity", "Profile data is null")
            }
        }

        // Inisialisasi RecyclerView
        binding.recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Inisialisasi ArticleAdapter
        articleAdapter2 = ArticleUserAdapter() // Mulai dengan list kosong
        binding.recyclerView2.adapter = articleAdapter2

        // Observasi LiveData untuk mendapatkan data artikel
        viewModel.searchArticles.observe(this) {
            //binding.usernameUser.text = data.name
            loadingViewModel.setLoadingStatus(true)
            articleAdapter2.submitData(lifecycle, it)
            loadingViewModel.setLoadingStatus(false)
        }



        // Panggil metode untuk mendapatkan data artikel
        //viewModel.fetchArticles(1, 10, "createdAt", "desc", "") // Ganti parameter dengan yang sesuai
    }

//    private fun displayArticles(articleUserResponse: ArticleUserResponse) {
//        // Ambil list artikel dari response
//        val articleList: List<Article> = articleUserResponse.articles
//
//        // Update data di ArticleAdapter
//        articleAdapter.updateData(articleList)
//    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressIndicator.visibility = View.VISIBLE
        }else{
            binding.progressIndicator.visibility = View.GONE
        }
    }
}