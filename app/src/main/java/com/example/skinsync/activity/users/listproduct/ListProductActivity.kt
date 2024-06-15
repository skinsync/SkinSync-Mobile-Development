package com.example.skinsync.activity.users.listproduct

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinsync.R
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.data.listproduct.DataListProduct
import com.example.skinsync.viewmodel.ListProductViewModel
import com.example.skinsync.viewmodel.ViewModelFactory

class ListProductActivity : AppCompatActivity() {

    private val viewModel by viewModels<ListProductViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var rvProduct: RecyclerView
    private val list = ArrayList<DataListProduct>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_product)

        // Set up button back
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Memulai MainActivity
        }

        rvProduct = findViewById(R.id.rvProduct)
        rvProduct.setHasFixedSize(true)

        //list.addAll(getListProduct())
        rvProduct.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listProductAdapter = ListProductAdapter()
        rvProduct.adapter = listProductAdapter
        println("luar observe")
        viewModel.products.observe(this) {
            println("masuk observe $it")
            listProductAdapter.submitData(lifecycle, it)
        }
        //showRecyclerList()
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

//    private fun showRecyclerList() {
//        rvProduct.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        val listProductAdapter = com.example.skinsync.activity.users.listproduct.ListProductAdapter()
//        rvProduct.adapter = listProductAdapter
//    }
}