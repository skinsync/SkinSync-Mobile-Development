package com.example.skinsync.activity.users.listproduct

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skinsync.R
import com.example.skinsync.activity.MainActivity
import com.example.skinsync.data.listproduct.DataListProduct
import com.example.skinsync.databinding.ActivityListProductBinding
import com.example.skinsync.viewmodel.ListProductViewModel
import com.example.skinsync.viewmodel.LoadingViewModel
import com.example.skinsync.viewmodel.ViewModelFactory

class ListProductActivity : AppCompatActivity() {

    private val viewModel by viewModels<ListProductViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityListProductBinding
    private lateinit var loadingViewModel: LoadingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Set up back button
        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val ibFilter = findViewById<ImageButton>(R.id.ib_filter)
        ibFilter.setOnClickListener {
            // Menampilkan toast
            Toast.makeText(this, "Fitur filter masih dalam tahap pengembangan", Toast.LENGTH_SHORT).show()
        }

        // Inisialisasi ViewModel
        loadingViewModel = ViewModelProvider(this).get(LoadingViewModel::class.java)

        // Observer untuk isLoading
        loadingViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        // Set up RecyclerView
        val listProductAdapter = ListProductAdapter()
        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(this@ListProductActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = listProductAdapter
        }

        // Observe data from ViewModel and submit to adapter
        viewModel.products.observe(this) { pagingData ->
            loadingViewModel.setLoadingStatus(true)
            Log.d("ListProductActivity", "Submitting data to adapter: $pagingData")
            listProductAdapter.submitData(lifecycle, pagingData)
            loadingViewModel.setLoadingStatus(false)
        }
    }

//    private fun getListProduct(): ArrayList<DataListProduct> {
//        val dataName = resources.getStringArray(R.array.data_name_product)
//        val dataTypeProduct = resources.getStringArray(R.array.data_type_product)
//        val dataPhoto = resources.obtainTypedArray(R.array.data_photo_product)
//        val listProduct = ArrayList<DataListProduct>()
//        for (i in dataName.indices) {
//            val product = DataListProduct(dataName[i], dataTypeProduct[i], dataPhoto.getResourceId(i, -1))
//            listProduct.add(product)
//        }
//        return listProduct
//    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressIndicator.visibility = View.VISIBLE
        }else{
            binding.progressIndicator.visibility = View.GONE
        }
    }

}