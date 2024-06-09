package com.example.skinsync.activity.admin.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.skinsync.activity.admin.ProductAdminActivity
import com.example.skinsync.activity.admin.UserAdminActivity
import com.example.skinsync.activity.admin.article.ArticleAdminActivity
import com.example.skinsync.activity.users.welcome.WelcomeActivity
import com.example.skinsync.databinding.ActivityDashboardBinding
import com.example.skinsync.viewmodel.MainViewModel
import com.example.skinsync.viewmodel.ViewModelFactory

class DashboardActivity : AppCompatActivity() {
    private val dashboardViewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        enableEdgeToEdge()
        applyWindowInsets()
        clickListenerSetup()
        dashboardViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun clickListenerSetup() {
        binding.card1.setOnClickListener {
            startActivity(Intent(this, ArticleAdminActivity::class.java))
        }
        binding.card2.setOnClickListener {
            startActivity(Intent(this, ProductAdminActivity::class.java))
        }
        binding.card3.setOnClickListener {
            startActivity(Intent(this, UserAdminActivity::class.java))
        }
        binding.card4.setOnClickListener {
            //startActivity(Intent(this, Pro::class.java))
        }
        binding.card5.setOnClickListener {
            //startActivity(Intent(this, ArticleAdminActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            dashboardViewModel.logout()
        }
    }
    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}