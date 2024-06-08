package com.example.skinsync.activity.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.activity.admin.DashboardActivity
import com.example.skinsync.databinding.ActivityLoginBinding
import com.example.skinsync.dataclass.AuthRepository
import com.example.skinsync.dataclass.LoginRequest
import com.example.skinsync.dataclass.UserModel
import com.example.skinsync.dataclass.UserPreference
import com.example.skinsync.dataclass.dataStore
import com.example.skinsync.viewmodel.LoginViewModel
import com.example.skinsync.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRepository: AuthRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPreference = UserPreference.getInstance(this.dataStore)
        authRepository = AuthRepository.getInstance(userPreference)

        buttonSetup()
    }

    private fun buttonSetup() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            authRepository.login(LoginRequest(email = email, password = password)) { response, error ->
                if (response != null) {
                    Log.d("Auth", "Login successful: ${response.message}")
                    toastMessage("Login successful\n${response.message}")
                    viewModel.saveSession(UserModel(email, response.token, true))
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("Auth", "Login failed: ${error?.message}")
                    toastMessage("Login failed: ${error?.message}")
                }
            }
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}