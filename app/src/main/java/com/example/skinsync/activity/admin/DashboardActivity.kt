package com.example.skinsync.activity.admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.skinsync.R
import com.example.skinsync.databinding.ActivityDashboardBinding
import com.example.skinsync.dataclass.UserModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        applyWindowInsets()
        clickListenerSetup()
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
    }
    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}