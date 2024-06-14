package com.example.skinsync.activity.users.listproduct

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinsync.R
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.adapter.ListProductAdapter
import com.example.skinsync.data.listproduct.DataListProduct

class ListProductActivity : AppCompatActivity() {

    private lateinit var rvProduct: RecyclerView
    private val list = ArrayList<DataListProduct>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up button back
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Memulai MainActivity
        }

        rvProduct = findViewById(R.id.rvProduct)
        rvProduct.setHasFixedSize(true)

        list.addAll(getListProduct())
        showRecyclerList()
    }

    private fun getListProduct(): ArrayList<DataListProduct> {
        val dataName = resources.getStringArray(R.array.data_name_product)
        val dataTypeProduct = resources.getStringArray(R.array.data_type_product)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo_product)
        val listProduct = ArrayList<DataListProduct>()
        for (i in dataName.indices) {
            val product = DataListProduct(dataName[i], dataTypeProduct[i], dataPhoto.getResourceId(i, -1))
            listProduct.add(product)
        }
        return listProduct
    }

    private fun showRecyclerList() {
        rvProduct.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listProductAdapter = ListProductAdapter(list)
        rvProduct.adapter = listProductAdapter
    }
}