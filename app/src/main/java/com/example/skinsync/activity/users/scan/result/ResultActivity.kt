package com.example.skinsync.activity.users.scan.result

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.skinsync.R
import com.example.skinsync.activity.users.scan.ScanActivity
import com.example.skinsync.activity.users.scheduling.morning.MorningSchedulingActivity
import com.example.skinsync.databinding.ActivityResultBinding
import com.example.skinsync.helper.ImageClassifierHelper
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.skinsync.helper.MultiSelectAdapter

class ResultActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var adapter: MultiSelectAdapter
    private lateinit var defaultCategory: String // Tambahkan variabel defaultCategory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up button back
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent) // Memulai MainActivity
        }

        //Set up button scheduling
        val buttonTryNow = findViewById<Button>(R.id.buttonTryNow)
        buttonTryNow.setOnClickListener {
            val intent = Intent(this, MorningSchedulingActivity::class.java)
            startActivity(intent)
        }

        val listTipeProduct = listOf("Face Wash", "Moisturizer", "Serum", "Toner", "Sunscreen")
        val adapter1 = ArrayAdapter(this, R.layout.list_tipe_product, listTipeProduct)
        binding.actDropdownTipeProduk.setAdapter(adapter1)

        // Set defaultCategory dengan kategori pertama dari listTipeProduct
        defaultCategory = listTipeProduct.first()

        //val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        //val items = listOf<String>()
        val items = when (defaultCategory) {
            "Face Wash" -> listOf("acne-free", "pore-care", "brightening", "anti-aging", "soothing", "balancing", "moisturizing", "hydrating", "refreshing", "oil-control", "skin-barrier", "acne-spot", "uv-protection", "black-spot")
            "Moisturizer" -> listOf("moisturizing", "brightening", "hydrating", "balancing", "anti-aging", "uv-protection", "acne-free", "oil-control", "black-spot", "pore-care", "soothing", "skin-barrier", "refreshing")
            "Serum" -> listOf("moisturizing", "brightening", "anti-aging", "uv-protection", "acne-free", "pore-care", "soothing", "hydrating", "skin-barrier", "black-spot", "oil-control", "balancing")
            "Toner" -> listOf("soothing", "balancing", "acne-free", "pore-care", "brightening", "anti-aging", "oil-control", "moisturizing", "refreshing", "hydrating", "black-spot", "skin-barrier", "uv-protection")
            "Sunscreen" -> listOf("anti-aging", "uv-protection", "hydrating", "moisturizing", "soothing", "no-whitecast", "skin-barrier", "brightening", "acne-free", "oil-control", "pore-care", "black-spot")
            else -> emptyList()
        }
        adapter = MultiSelectAdapter(this, R.layout.item_multiselect, items)

        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        // Manually call onItemSelected for initial selection
        if (spinner.selectedItem != null) {
            val position = spinner.selectedItemPosition
            onItemSelected(spinner, spinner.selectedView, position, 0)
        }

//        val listTipeWajah = listOf("Kering", "Berminyak", "Campuran")
//        val adapter2 = ArrayAdapter(this, R.layout.list_tipe_product, listTipeWajah)
//        binding.actDropdownNotable.setAdapter(adapter2)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
            try {
                val bitmap = getBitmapFromUri(it)
                if (bitmap != null) {
                    val prediction = ImageClassifierHelper(this).classifyImage(bitmap)
                    //println("Predicted class: $prediction")
                    val label = arrayOf("Berjerawat", "Berminyak", "Kering", "Normal")
                    binding.typeSkin.text = label[prediction]
                } else {
                    showToast("Failed to decode image")
                }
            } catch (e: Exception) {
                showToast("Failed to analyze image: ${e.message}")
                e.printStackTrace()
            }
        }


        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
//        // Inisialisasi ImageClassifierHelper
//        imageClassifierHelper = ImageClassifierHelper(
//            context = this,
//            classifierListener = object : ImageClassifierHelper.ClassifierListener {
//                override fun onError(error: String) {
//                    runOnUiThread {
//                        showToast(error)
//                    }
//                }
//
//                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
//                    runOnUiThread {
//                        // Tampilkan hasil prediksi ke dalam UI
//                        results?.let { it ->
//                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
//                                val sortedCategories = it[0].categories.sortedByDescending { it?.score }
//
//                                // Ambil nilai prediksi terbesar
//                                val topPrediction = sortedCategories.firstOrNull()
//
//                                // Tampilkan label dan skor prediksi ke dalam TextView
//                                topPrediction?.let { prediction ->
//                                    val trueScore = prediction.score
//                                    val falseScore = 1 - trueScore
//                                    val displayResult = if (trueScore > falseScore) {
//                                        "${prediction.label} " + NumberFormat.getPercentInstance().format(trueScore).trim()
//                                    } else {
//                                        "${prediction.label} " + NumberFormat.getPercentInstance().format(falseScore).trim()
//                                    }
//                                    binding.typeSkin.text = displayResult
//                                } ?: run {
//                                    binding.typeSkin.text = getString(R.string.no_prediction)
//                                }
//                            } else {
//                                binding.typeSkin.text = getString(R.string.no_prediction)
//                            }
//                        }
//                    }
//                }
//            }
//        )
//
//        // Analisis gambar setelah activity dibuat
//        uploadImage(imageUri)

    }

    fun getBitmapFromUri(uri: Uri?): Bitmap? {
        try {
            val contentResolver = contentResolver
            val inputStream = contentResolver.openInputStream(uri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

//    private fun uploadImage(imageUri: Uri) {
//        imageClassifierHelper.classifyStaticImage(imageUri)
//    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedType = parent?.getItemAtPosition(position) as String
        val itemsForSelectedType = when (selectedType) {
            "Face Wash" -> listOf("acne-free", "pore-care", "brightening", "anti-aging", "soothing", "balancing", "moisturizing", "hydrating", "refreshing", "oil-control", "skin-barrier", "acne-spot", "uv-protection", "black-spot")
            "Moisturizer" -> listOf("moisturizing", "brightening", "hydrating", "balancing", "anti-aging", "uv-protection", "acne-free", "oil-control", "black-spot", "pore-care", "soothing", "skin-barrier", "refreshing")
            "Serum" -> listOf("moisturizing", "brightening", "anti-aging", "uv-protection", "acne-free", "pore-care", "soothing", "hydrating", "skin-barrier", "black-spot", "oil-control", "balancing")
            "Toner" -> listOf("soothing", "balancing", "acne-free", "pore-care", "brightening", "anti-aging", "oil-control", "moisturizing", "refreshing", "hydrating", "black-spot", "skin-barrier", "uv-protection")
            "Sunscreen" -> listOf("anti-aging", "uv-protection", "hydrating", "moisturizing", "soothing", "no-whitecast", "skin-barrier", "brightening", "acne-free", "oil-control", "pore-care", "black-spot")
            else -> emptyList()
        }
        Log.d("ResultActivity", "Items for selected type: $itemsForSelectedType")
        adapter.updateItems(itemsForSelectedType) // Update the adapter with the new items
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}