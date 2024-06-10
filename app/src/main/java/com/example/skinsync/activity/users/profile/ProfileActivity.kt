package com.example.skinsync.activity.users.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.skinsync.R
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.activity.users.LoginActivity
import com.example.skinsync.activity.users.profile.edit.EditProfileActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var birthDateTextView: TextView
    private lateinit var genderTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var emailDesc2: TextView
    private lateinit var passwordTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImageView = findViewById(R.id.avatarImageProfile)
        usernameTextView = findViewById(R.id.usernameProfile)
        birthDateTextView = findViewById(R.id.dataBirthDate)
        genderTextView = findViewById(R.id.genderDesc)
        emailTextView = findViewById(R.id.gmail_textView)
        emailDesc2 = findViewById(R.id.emailDesc2)
        passwordTextView = findViewById(R.id.passwordDesc)

        // Set up button back
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Memulai MainActivity
        }

        findViewById<Button>(R.id.buttonEdit).setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivityForResult(intent, 1)
        }

        // Terima data yang dikirim dari EditProfileActivity
        intent.extras?.let {
            val imageUri = it.getString("uploadedImageUri")
            val username = it.getString("username")
            val birthDate = it.getString("selectedDate")
            val gender = it.getString("gender")
            val email = it.getString("email")
            val password= it.getString("password")

            imageUri?.let { uri ->
                profileImageView.setImageURI(Uri.parse(uri))
            }
            usernameTextView.text = username
            birthDateTextView.text = birthDate
            genderTextView.text = gender
            emailTextView.text = email
            emailDesc2.text = email
            passwordTextView.text = password
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            data?.extras?.let {
                val imageUri = it.getString("uploadedImageUri")
                val username = it.getString("username")
                val birthDate = it.getString("selectedDate")
                val gender = it.getString("gender")
                val email = it.getString("email")
                val password= it.getString("password")

                imageUri?.let { uri ->
                    profileImageView.setImageURI(Uri.parse(uri))
                }
                usernameTextView.text = username
                birthDateTextView.text = birthDate
                genderTextView.text = gender
                emailTextView.text = email
                emailDesc2.text = email
                passwordTextView.text = password
            }
        }
    }
}