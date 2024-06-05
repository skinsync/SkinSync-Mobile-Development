package com.example.skinsync.activity.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.databinding.ActivityRegisterBinding
import com.example.skinsync.dataclass.AuthRepository
import com.example.skinsync.dataclass.RegisterRequest
import com.example.skinsync.dataclass.UserModel
import com.example.skinsync.dataclass.UserPreference
import com.example.skinsync.dataclass.dataStore
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authRepository: AuthRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userPreference = UserPreference.getInstance(this.dataStore)
        authRepository = AuthRepository.getInstance(userPreference)

        binding.buttonRegister.setOnClickListener {
            // Logika registrasi Anda di sini
            val name = binding.inputUsername.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            authRepository.register(RegisterRequest(name = name, email = email, password = password)){ response, error ->
                if (response != null) {
                    Log.d("Auth", "Registration successful: ${response.message}")
                        // Setelah registrasi berhasil, mulai MainActivity
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e("Auth", "Registration failed: ${error?.message}")
                }
            }

            // Setelah registrasi berhasil, mulai MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Tutup RegisterActivity sehingga pengguna tidak kembali ke sini dengan back button
        }
    }
}