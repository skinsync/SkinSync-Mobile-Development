package com.example.skinsync.activity.users.profile.edit

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.skinsync.R

class EditProfileActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var pickDateButton: Button
    private lateinit var uploadImageTV: TextView
    private lateinit var uploadImage: ImageView

    private val selectImageLauncher =
        this.registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                uploadImage.setImageURI(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pickDateButton = findViewById(R.id.pickDate)
        uploadImageTV = findViewById(R.id.uploadImageTV)
        uploadImage = findViewById(R.id.uploadImage)

        pickDateButton.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, this, year, month, day).show()
        }

        uploadImageTV.setOnClickListener {
            selectImageFromGallery()
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val selectedDate = "$day/${month + 1}/$year"
        pickDateButton.text = selectedDate
    }

    private fun selectImageFromGallery() {
        selectImageLauncher.launch("image/*")
    }
}