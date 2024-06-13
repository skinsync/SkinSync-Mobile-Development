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
    private lateinit var user: ProfileResponse

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
            startActivityForResult(intent, 1)
        }

        viewModel.fetchProfile()
        viewModel.myProfile.observe(this) { profileResponse ->
            if (profileResponse != null && profileResponse.data != null) {
                val userData = profileResponse.data
                Log.i("ProfileActivity", "Profile data observed: $userData")
                binding.usernameProfile.text = userData.name
                binding.dataBirthDate.text = userData.birthdate?.toString() ?: "N/A"
                binding.genderDesc.text = userData.gender?.toString() ?: "N/A"
                binding.gmailTextView.text = userData.email
                binding.passwordDesc.text = userData.password
            } else {
                Log.e("ProfileActivity", "Profile data is null")
            }
        }
//        // Terima data yang dikirim dari EditProfileActivity
//        intent.extras?.let {
//            val imageUri = it.getString("uploadedImageUri")
//            val username = it.getString("username")
//            val birthDate = it.getString("selectedDate")
//            val gender = it.getString("gender")
//            val email = it.getString("email")
//            val password= it.getString("password")
//
//            imageUri?.let { uri ->
//                binding.avatarImageProfile.setImageURI(Uri.parse(uri))
//            }
//        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            data?.extras?.let {
//                val imageUri = it.getString("uploadedImageUri")
//                val username = it.getString("username")
//                val birthDate = it.getString("selectedDate")
//                val gender = it.getString("gender")
//                val email = it.getString("email")
//                val password= it.getString("password")
//
//                imageUri?.let { uri ->
//                    binding.avatarImageProfile.setImageURI(Uri.parse(uri))
//                }
//                binding.usernameProfile.text = username
//                binding.dataBirthDate.text = birthDate
//                binding.genderDesc.text = gender
//                binding.gmailTextView.text = email
//                binding.emailDesc2.text = email
//                binding.passwordDesc.text = password
//            }
//        }
//    }
}