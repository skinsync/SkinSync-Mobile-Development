package com.example.skinsync.activity.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.skinsync.databinding.ActivityRegisterBinding
import com.example.skinsync.data.auth.AuthRepository
import com.example.skinsync.data.auth.RegisterRequest
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.dataStore

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authRepository: AuthRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val userPreference = UserPreference.getInstance(this.dataStore)
        authRepository = AuthRepository.getInstance(userPreference)

        buttonSetup()
    }

    private fun buttonSetup() {
        binding.buttonRegister.setOnClickListener {
            val name = binding.inputUsername.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if (name.isEmpty()) {
                binding.inputUsername.error = "Nama tidak boleh kosong"
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                binding.inputEmail.error = "Email tidak valid"
                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.inputPassword.error = "Password harus memiliki minimal 8 karakter"
                return@setOnClickListener
            }
            authRepository.register(RegisterRequest(name = name, email = email, password = password)){ response, error ->
                if (response != null) {
                    Log.d("Auth", "Registration successful: ${response.message}")
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("Auth", "Registration failed: ${error?.message}")
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}