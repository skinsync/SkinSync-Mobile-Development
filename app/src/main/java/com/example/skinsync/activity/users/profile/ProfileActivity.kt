package com.example.skinsync.activity.users.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.skinsync.R
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.activity.users.LoginActivity
import com.example.skinsync.activity.users.profile.edit.EditProfileActivity
import com.example.skinsync.databinding.ActivityProfileBinding
import com.example.skinsync.viewmodel.MainViewModel
import com.example.skinsync.viewmodel.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: DataProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Set up button back
        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Memulai MainActivity
        }

        binding.buttonEdit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra("user", user)
            startActivityForResult(intent, 1)
        }

        viewModel.fetchProfile()
        viewModel.myProfile.observe(this) { profileResponse ->
            if (profileResponse?.data != null) {
                user = profileResponse.data
                Log.i("ProfileActivity", "Profile data observed: $user")
                binding.usernameProfile.text = user.name
                binding.dataBirthDate.text = user.birthdate?.toString() ?: "N/A"
                binding.genderDesc.text = user.gender?.toString() ?: "N/A"
                binding.gmailTextView.text = user.email
                binding.emailDesc2.text = user.email
                binding.passwordDesc.text = "********"
                if (user.profilePicture != null) {
                    Glide.with(this)
                        .load(user.profilePicture)
                        .into(binding.avatarImageProfile)
                }

            } else {
                Log.e("ProfileActivity", "Profile data is null")
            }
        }
    }
}