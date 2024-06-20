package com.example.skinsync.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.skinsync.R
import com.example.skinsync.activity.users.article.ArticleActivity
import com.example.skinsync.activity.users.listproduct.ListProductActivity
import com.example.skinsync.activity.users.listproduct.ListProductAdapter
import com.example.skinsync.activity.users.profile.ProfileActivity
import com.example.skinsync.activity.users.profile.ProfileViewModel
import com.example.skinsync.activity.users.scan.ScanActivity
import com.example.skinsync.activity.users.scheduling.morning.MorningSchedulingActivity
import com.example.skinsync.activity.users.welcome.WelcomeActivity
import com.example.skinsync.databinding.ActivityMainBinding
import com.example.skinsync.databinding.NavHeaderBinding
import com.example.skinsync.viewmodel.ListProductViewModel
import com.example.skinsync.viewmodel.LoadingViewModel
import com.example.skinsync.viewmodel.MainViewModel
import com.example.skinsync.viewmodel.ViewModelFactory
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val productViewModel by viewModels<ListProductViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var loadingViewModel: LoadingViewModel

    lateinit var toogle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Inisialisasi ViewModel
        loadingViewModel = ViewModelProvider(this).get(LoadingViewModel::class.java)

        // Observer untuk isLoading
        loadingViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        val drawerLayout : DrawerLayout = findViewById(R.id.main)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        val navHeaderBinding = NavHeaderBinding.bind(headerView)
        profileViewModel.fetchProfile()

        profileViewModel.myProfile.observe(this) { profileResponse ->
            if (profileResponse?.data != null) {
                binding.titleWelcomeMain.text = "Welcome, " + profileResponse.data.name
                navHeaderBinding.gmailUser.text = profileResponse.data.email
                navHeaderBinding.usernameUser.text = profileResponse.data.name
                if (profileResponse.data.profilePicture != null) {
                    Glide.with(this)
                        .load(profileResponse.data.profilePicture)
                        .into(navHeaderBinding.userImage)
                }

            } else {
                Log.e("ProfileActivity", "Profile data is null")
            }
        }
        

        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_user -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                R.id.nav_article -> {
                    startActivity(Intent(this, ArticleActivity::class.java))
                    true
                }

                R.id.nav_list_product -> {
                    startActivity(Intent(this, ListProductActivity::class.java))
                    true
                }

                R.id.nav_scheduling -> {
                    startActivity(Intent(this, MorningSchedulingActivity::class.java))
                    true
                }

                R.id.nav_logout -> {
                    mainViewModel.logout()
                    true
                }

                else -> false
            }
        }

        val buttonTryNow = findViewById<Button>(R.id.buttonTryNow)
        buttonTryNow.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent) // Memulai MainActivity
        }

        val ibFilter = findViewById<ImageButton>(R.id.ib_filter)
        ibFilter.setOnClickListener {
            // Menampilkan toast
            Toast.makeText(this, "Fitur filter masih dalam tahap pengembangan", Toast.LENGTH_SHORT).show()
        }

        binding.searchBar.setOnClickListener(){
            Toast.makeText(this, "Fitur search masih dalam tahap pengembangan", Toast.LENGTH_SHORT).show()
        }

        // Set up RecyclerView
        val listProductAdapter = ListProductAdapter()
        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = listProductAdapter
        }

        // Observe data from ViewModel and submit to adapter
        productViewModel.products.observe(this) { pagingData ->
            loadingViewModel.setLoadingStatus(true)
            Log.d("MainActivity", "Submitting data to adapter: $pagingData")
            listProductAdapter.submitData(lifecycle, pagingData)
            loadingViewModel.setLoadingStatus(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toogle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressIndicator.visibility = View.VISIBLE
        }else{
            binding.progressIndicator.visibility = View.GONE
        }
    }
}