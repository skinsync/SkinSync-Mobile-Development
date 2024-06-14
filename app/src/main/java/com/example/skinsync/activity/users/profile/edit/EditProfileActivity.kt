package com.example.skinsync.activity.users.profile.edit

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.skinsync.activity.users.profile.DataEditProfile
import com.example.skinsync.activity.users.profile.DataProfile
import com.example.skinsync.activity.users.profile.ProfileActivity
import com.example.skinsync.activity.users.profile.ProfileViewModel
import com.example.skinsync.databinding.ActivityEditProfileBinding
import com.example.skinsync.databinding.ActivityProfileBinding
import com.example.skinsync.viewmodel.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import java.net.URL

class EditProfileActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityEditProfileBinding
    private var uploadedImageUri: Uri? = null

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                uploadedImageUri = it
                binding.uploadImage.setImageURI(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val user = intent.getParcelableExtra<DataProfile>("user")
        if (user != null) {
            with(binding) {
                usernameEditText.setText(user.name)
                pickDate.text = user.birthdate?:""
                // Loop through each radio button in the RadioGroup
                for (i in 0 until genderRadioGroup.childCount) {
                    val radioButton = genderRadioGroup.getChildAt(i) as? RadioButton
                    // Check if the RadioButton is not null and its text matches the gender value
                    if (radioButton != null && user.gender != null && radioButton.text.toString() == user.gender) {
                        radioButton.isChecked = true // Set the radio button as checked
                        break // Exit the loop once the radio button is found and checked
                    }
                }
                emailEditText.setText(user.email)
                pickDate.text = user.birthdate?:"Pick date"
                //passwordEditText.setText(user.password)
            }
        }

        viewModel.isEditSuccess.observe(this) {
            if (it!!) {
                startActivity(Intent(this@EditProfileActivity, ProfileActivity::class.java))
            }
        }
        binding.pickDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, this, year, month, day).show()
        }

        binding.uploadImageTV.setOnClickListener {
            selectImageFromGallery()
        }

        binding.buttonSave.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val selectedDate = binding.pickDate.text.toString()
            val selectedGenderId = binding.genderRadioGroup.checkedRadioButtonId
            val genderRadioButton = findViewById<RadioButton>(selectedGenderId)
            val gender = genderRadioButton?.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            Log.d("EditProfileActivity", "Uploaded image URI: $uploadedImageUri")
            val imageUrlString = uploadedImageUri.toString()
            Log.d("EditProfileActivity", "Image URL string: $imageUrlString")
            val profileRequest = EditProfileRequest(
                name = username,
                email = email,
                gender = gender,
                birthdate = selectedDate,
                profile_picture = uploadedImageUri.toString(),
                password = password
            )
            viewModel.editProfile(profileRequest)

        }

        binding.buttonCancel.setOnClickListener {
            navigateToProfileActivity()
        }

        binding.back.setOnClickListener {
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
        binding.pickDate.text = selectedDate
    }

    private fun selectImageFromGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun maskPassword(password: String): String {
        return "*".repeat(password.length)
    }
}