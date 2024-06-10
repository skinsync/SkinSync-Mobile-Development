package com.example.skinsync.activity.users.profile.edit

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.skinsync.R
import com.example.skinsync.activity.users.profile.ProfileActivity
import com.google.android.material.textfield.TextInputEditText

class EditProfileActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var pickDateButton: Button
    private lateinit var uploadImageTV: TextView
    private lateinit var uploadImage: ImageView
    private var uploadedImageUri: Uri? = null

    private val selectImageLauncher =
        this.registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                uploadedImageUri = it
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

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonCancel = findViewById<Button>(R.id.buttonCancel)
        val backButton = findViewById<ImageView>(R.id.back)

        buttonSave.setOnClickListener {
            // Mengambil nilai dari setiap elemen yang diedit
            val username = findViewById<TextInputEditText>(R.id.usernameEditText).text.toString()
            val selectedDate = findViewById<Button>(R.id.pickDate).text.toString()
            val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)
            val selectedRadioButtonId = genderRadioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val gender = selectedRadioButton.text.toString()
            val email = findViewById<TextInputEditText>(R.id.emailEditText).text.toString()
            val password = findViewById<TextInputEditText>(R.id.passwordEditText).text.toString()

            // Mengirim data menggunakan Intent
            val intent = Intent().apply {
                putExtra("uploadedImageUri", uploadedImageUri.toString())
                putExtra("username", username)
                putExtra("selectedDate", selectedDate)
                putExtra("gender", gender)
                putExtra("email", email)
                putExtra("password", maskPassword(password))
            }
            setResult(RESULT_OK, intent)
            finish()
        }

        buttonCancel.setOnClickListener {
            navigateToProfileActivity()
        }

        backButton.setOnClickListener {
            navigateToProfileActivity()
        }
    }

    private fun navigateToProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val selectedDate = "$day/${month + 1}/$year"
        pickDateButton.text = selectedDate
    }

    private fun selectImageFromGallery() {
        selectImageLauncher.launch("image/*")
    }

    // Fungsi untuk menyamarkan password
    private fun maskPassword(password: String): String {
        return "*".repeat(password.length)
    }
}