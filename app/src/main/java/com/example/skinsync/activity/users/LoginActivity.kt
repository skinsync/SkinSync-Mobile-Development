package com.example.skinsync.activity.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.databinding.ActivityLoginBinding
import com.example.skinsync.data.auth.AuthRepository
import com.example.skinsync.data.auth.LoginRequest
import com.example.skinsync.data.UserModel
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.dataStore
import com.example.skinsync.viewmodel.LoadingViewModel
import com.example.skinsync.viewmodel.LoginViewModel
import com.example.skinsync.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var loadingViewModel: LoadingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPreference = UserPreference.getInstance(this.dataStore)
        authRepository = AuthRepository.getInstance(userPreference)

        buttonSetup()
        setupMakeAccountLink()

        // Inisialisasi ViewModel
        loadingViewModel = ViewModelProvider(this).get(LoadingViewModel::class.java)

        // Observer untuk isLoading
        loadingViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        // Contoh penggunaan, misalnya saat loading dimulai
        loadingViewModel.setLoadingStatus(true)

        // ... Lakukan proses loading

        // Contoh penggunaan, misalnya saat loading selesai
        loadingViewModel.setLoadingStatus(false)
    }

    private fun buttonSetup() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if (email == "") {
                binding.inputEmail.error = "Email Belum Diisi"
                return@setOnClickListener
            }

            if (password == "") {
                binding.inputPassword.error = "Password Belum Diisi"
                return@setOnClickListener
            }

            loadingViewModel.setLoadingStatus(true)

            authRepository.login(LoginRequest(email = email, password = password)) { response, error ->
                if (response != null) {
                    Log.d("Auth", "Login successful: ${response.message}")
                    toastMessage("Login successful\n${response.message}")
                    val userData = response.data
                    viewModel.saveSession(UserModel(email, response.token!!, userData!!.role!!, true, "Normal"))
                    var intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    loadingViewModel.setLoadingStatus(false)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("Auth", "Login failed: ${error?.message}")
                    toastMessage("Email atau Password Belum Terdaftar")
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

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressIndicator.visibility = View.VISIBLE
        }else{
            binding.progressIndicator.visibility = View.GONE
        }
    }

}