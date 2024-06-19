package com.example.skinsync.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.skinsync.R
import com.example.skinsync.activity.users.article.ArticleActivity
import com.example.skinsync.activity.users.listproduct.ListProductActivity
import com.example.skinsync.activity.users.profile.ProfileActivity
import com.example.skinsync.activity.users.profile.ProfileViewModel
import com.example.skinsync.activity.users.scan.ScanActivity
import com.example.skinsync.activity.users.scheduling.morning.MorningSchedulingActivity
import com.example.skinsync.activity.users.welcome.WelcomeActivity
import com.example.skinsync.databinding.ActivityMainBinding
import com.example.skinsync.databinding.NavHeaderBinding
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

    private lateinit var binding: ActivityMainBinding

    lateinit var toogle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val drawerLayout : DrawerLayout = findViewById(R.id.main)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        val navHeaderBinding = NavHeaderBinding.bind(headerView)
        profileViewModel.fetchProfile()

        profileViewModel.myProfile.observe(this) { profileResponse ->
            if (profileResponse?.data != null) {
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
                    Toast.makeText(
                        applicationContext,
                        "Clicked User Profile Page",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                R.id.nav_article -> {
                    Toast.makeText(applicationContext, "Clicked Article Page", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this, ArticleActivity::class.java))
                    true
                }

                R.id.nav_list_product -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked List of Product Page",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, ListProductActivity::class.java))
                    true
                }

                R.id.nav_scheduling -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Morning Scheduling Page",
                        Toast.LENGTH_SHORT
                    ).show()
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toogle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}