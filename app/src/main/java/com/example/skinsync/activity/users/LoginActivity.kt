package com.example.skinsync.activity.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.activity.admin.dashboard.DashboardActivity
import com.example.skinsync.databinding.ActivityLoginBinding
import com.example.skinsync.data.auth.AuthRepository
import com.example.skinsync.data.auth.LoginRequest
import com.example.skinsync.data.UserModel
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.dataStore
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
        setupMakeAccountLink()
    }

    private fun buttonSetup() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            authRepository.login(LoginRequest(email = email, password = password)) { response, error ->
                if (response != null) {
                    Log.d("Auth", "Login successful: ${response.message}")
                    toastMessage("Login successful\n${response.message}")
                    val userData = response.data
                    viewModel.saveSession(UserModel(email, response.token!!, userData!!.role!!, true))
                    var intent = Intent(this@LoginActivity, MainActivity::class.java)
                    if (userData.role == "admin") {
                        intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    }
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

    private fun setupMakeAccountLink() {
        binding.textMakeAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}