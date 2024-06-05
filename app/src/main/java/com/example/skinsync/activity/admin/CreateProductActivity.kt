package com.example.skinsync.activity.admin

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.skinsync.R
import com.example.skinsync.databinding.ActivityCreateProductBinding

class CreateProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listTipeProduct = listOf("Face Wash", "Moisturizer", "Serum")
        val adapter1 = ArrayAdapter(this, R.layout.list_tipe_product, listTipeProduct)
        binding.actDropdownTipeProduk.setAdapter(adapter1)

        val listTipeWajah = listOf("Kering", "Berminyak", "Campuran")
        val adapter2 = ArrayAdapter(this, R.layout.list_tipe_product, listTipeWajah)
        binding.actDropdownTipeWajah.setAdapter(adapter2)
    }
}