package com.example.skinsync.activity.admin.article

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.skinsync.R
import com.example.skinsync.data.articleadmin.ArticleAdminRepository
import com.example.skinsync.databinding.ActivityArticleAdminBinding
import com.example.skinsync.databinding.ActivityCreateArticleBinding
import com.example.skinsync.viewmodel.ViewModelFactory

class CreateArticleAdminActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 1001
    private val PICK_IMAGE_REQUEST_CODE = 1002
    private val viewModel by viewModels<ArticleAdminViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityCreateArticleBinding
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityCreateArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonConfig()
    }

    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        } else {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                openGallery()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                binding.ivPreviewImageCa.setImageURI(it)
                uri = it
                println(it)
            }
        }
    }

    private fun buttonConfig() {
        with(binding){
            chooseImage.setOnClickListener {
                checkAndRequestPermissions()
            }
            btnCreateCa.setOnClickListener {
                val judul = edJudulCa.text.toString()
                val deskripsi = edDeskripsiCa.text.toString()
                val url = edUrlCa.text.toString()
                if (judul == "" || deskripsi == "" || url == "") {
                    showToast("Input anda belum lengkap")
                } else {
                    try {
                        viewModel.addArticle(judul, deskripsi, deskripsi, url)
                    } catch (e: Error){
                        Log.e("Add Artcle: ", e.message!!)
                    }
                    showToast("Artikel berhasil ditambahkan!")

                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}