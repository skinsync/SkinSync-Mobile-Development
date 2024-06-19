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
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skinsync.activity.users.listproduct.ListProductAdapter
//import com.anurag.multiselectionspinner.MultiSelectionSpinnerDialog
//import com.anurag.multiselectionspinner.MultiSpinner
import com.example.skinsync.helper.MultiSelectAdapter
import com.example.skinsync.viewmodel.ArticleUserViewModel
import com.example.skinsync.viewmodel.ListProductViewModel
import com.example.skinsync.viewmodel.LoadingViewModel
import com.example.skinsync.viewmodel.ViewModelFactory
import java.util.Arrays

class ResultActivity : AppCompatActivity(){
    private val viewModelRecommendation by viewModels<RecommendationViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var adapter: MultiSelectAdapter
    private lateinit var defaultCategory: String // Tambahkan variabel defaultCategory

    //
    private lateinit var spinner: TextView
    private var langArray = arrayOf("Java", "C++", "Kotlin", "C", "Python", "Javascript")
    private var selectedLanguage = BooleanArray(langArray.size)
    private val langList = ArrayList<Int>()

    private lateinit var skinType: String
    private lateinit var productType: String
    private lateinit var notableEffect: String

    private lateinit var loadingViewModel: LoadingViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

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

        // Inisialisasi ViewModel
        loadingViewModel = ViewModelProvider(this).get(LoadingViewModel::class.java)

        // Observer untuk isLoading
        loadingViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        val listTipeProduct = listOf("Face Wash", "Moisturizer", "Serum", "Toner", "Sunscreen")
        val adapter1 = ArrayAdapter(this, R.layout.list_tipe_product, listTipeProduct)
        binding.actDropdownTipeProduk.setAdapter(adapter1)


        // Inisialisasi array list untuk isi spinner
        val contentList: MutableList<String> = ArrayList()

        binding.actDropdownTipeProduk.setOnItemClickListener { parent, view, position, id ->
            val selectedType = parent.getItemAtPosition(position) as String
            // Lakukan sesuatu dengan selectedType
            println("Selected Type: $selectedType")
            Log.d("ResultActivity", "Selected Type: $selectedType")

            if (selectedType == "Face Wash"){
                langArray = arrayOf("acne-free", "pore-care", "brightening", "anti-aging", "soothing", "balancing", "moisturizing", "hydrating", "refreshing", "oil-control", "skin-barrier", "acne-spot", "uv-protection", "black-spot")
                selectedLanguage = BooleanArray(langArray.size)
                setSpinner()
            }else if (selectedType == "Moisturizer"){
                langArray = arrayOf("moisturizing", "brightening", "hydrating", "balancing", "anti-aging", "uv-protection", "acne-free", "oil-control", "black-spot", "pore-care", "soothing", "skin-barrier", "refreshing")
                selectedLanguage = BooleanArray(langArray.size)
                setSpinner()
            }else if (selectedType == "Serum"){
                langArray = arrayOf("moisturizing", "brightening", "anti-aging", "uv-protection", "acne-free", "pore-care", "soothing", "hydrating", "skin-barrier", "black-spot", "oil-control", "balancing")
                selectedLanguage = BooleanArray(langArray.size)
                setSpinner()
            }else if(selectedType == "Toner"){
                langArray = arrayOf("soothing", "balancing", "acne-free", "pore-care", "brightening", "anti-aging", "oil-control", "moisturizing", "refreshing", "hydrating", "black-spot", "skin-barrier", "uv-protection")
                selectedLanguage = BooleanArray(langArray.size)
                setSpinner()
            }else if (selectedType == "Sunscreen"){
                langArray = arrayOf("anti-aging", "uv-protection", "hydrating", "moisturizing", "soothing", "no-whitecast", "skin-barrier", "brightening", "acne-free", "oil-control", "pore-care", "black-spot")
                selectedLanguage = BooleanArray(langArray.size)
                setSpinner()
            }else{
                Toast.makeText(this, "Masukkan Notable Terlebih dahulu !", Toast.LENGTH_SHORT).show()
            }
        }
//
//        val spinner: MultiSpinner = findViewById(R.id.spinner)
//
//        val contentList : MutableList<String> = ArrayList()
//        contentList.add("One")
//        contentList.add("Two")
//        contentList.add("Three")
//        contentList.add("Four")
//        contentList.add("Five")
//
//        spinner.setAdapterWithOutImage(this,contentList,this)


        // Set defaultCategory dengan kategori pertama dari listTipeProduct
        //defaultCategory = listTipeProduct.first()

        //val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        //val items = listOf<String>()
//        val items = when (defaultCategory) {
//            "Face Wash" -> listOf("acne-free", "pore-care", "brightening", "anti-aging", "soothing", "balancing", "moisturizing", "hydrating", "refreshing", "oil-control", "skin-barrier", "acne-spot", "uv-protection", "black-spot")
//            "Moisturizer" -> listOf("moisturizing", "brightening", "hydrating", "balancing", "anti-aging", "uv-protection", "acne-free", "oil-control", "black-spot", "pore-care", "soothing", "skin-barrier", "refreshing")
//            "Serum" -> listOf("moisturizing", "brightening", "anti-aging", "uv-protection", "acne-free", "pore-care", "soothing", "hydrating", "skin-barrier", "black-spot", "oil-control", "balancing")
//            "Toner" -> listOf("soothing", "balancing", "acne-free", "pore-care", "brightening", "anti-aging", "oil-control", "moisturizing", "refreshing", "hydrating", "black-spot", "skin-barrier", "uv-protection")
//            "Sunscreen" -> listOf("anti-aging", "uv-protection", "hydrating", "moisturizing", "soothing", "no-whitecast", "skin-barrier", "brightening", "acne-free", "oil-control", "pore-care", "black-spot")
//            else -> emptyList()
//        }
        //adapter = MultiSelectAdapter(this, R.layout.item_multiselect, items)


//        spinner.adapter = adapter
//        spinner.onItemSelectedListener = this
//
//        // Manually call onItemSelected for initial selection
//        if (spinner.selectedItem != null) {
//            val position = spinner.selectedItemPosition
//            onItemSelected(spinner, spinner.selectedView, position, 0)
//        }

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
                    viewModel.setSaveSkinType(label[prediction])
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

        // Set up RecyclerView
        val recommendationAdapter = RecommendationAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ResultActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendationAdapter
        }

        binding.buttonGetRecommendation.setOnClickListener{
            skinType = binding.typeSkin.text.toString()
            productType = binding.actDropdownTipeProduk.text.toString()
            notableEffect = binding.spinner.text.toString()

            loadingViewModel.setLoadingStatus(true)

            if (skinType.isEmpty() || productType.isEmpty() || notableEffect.isEmpty()) {
                loadingViewModel.setLoadingStatus(false)
                Toast.makeText(this, "Skin Type, Product Type, dan Notable Effect tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                viewModelRecommendation.getRecommendedSkincare(skinType, productType, listOf(notableEffect))
                loadingViewModel.setLoadingStatus(false)
            }
        }

        // Observe data from ViewModel and submit to adapter
        Log.d("Luar Observe View Model", "Luar")
        viewModelRecommendation.recommendedSkincare.observe(this) { pagingData ->
            Log.d("ResultActivity", "Submitting data to adapter: $pagingData")
            recommendationAdapter.submitData(lifecycle, pagingData)
        }

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

//    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        val selectedType = parent?.getItemAtPosition(position) as String
////        val itemsForSelectedType = when (selectedType) {
////            "Face Wash" -> listOf("acne-free", "pore-care", "brightening", "anti-aging", "soothing", "balancing", "moisturizing", "hydrating", "refreshing", "oil-control", "skin-barrier", "acne-spot", "uv-protection", "black-spot")
////            "Moisturizer" -> listOf("moisturizing", "brightening", "hydrating", "balancing", "anti-aging", "uv-protection", "acne-free", "oil-control", "black-spot", "pore-care", "soothing", "skin-barrier", "refreshing")
////            "Serum" -> listOf("moisturizing", "brightening", "anti-aging", "uv-protection", "acne-free", "pore-care", "soothing", "hydrating", "skin-barrier", "black-spot", "oil-control", "balancing")
////            "Toner" -> listOf("soothing", "balancing", "acne-free", "pore-care", "brightening", "anti-aging", "oil-control", "moisturizing", "refreshing", "hydrating", "black-spot", "skin-barrier", "uv-protection")
////            "Sunscreen" -> listOf("anti-aging", "uv-protection", "hydrating", "moisturizing", "soothing", "no-whitecast", "skin-barrier", "brightening", "acne-free", "oil-control", "pore-care", "black-spot")
////            else -> emptyList()
////        }
//        println(selectedType)
//        Log.d("ResultActivity", "Items for selected type: $selectedType")
//        //adapter.updateItems(itemsForSelectedType) // Update the adapter with the new items
//    }
//
//    override fun onNothingSelected(parent: AdapterView<*>?) {
//        TODO("Not yet implemented")
//    }

//    override fun OnMultiSpinnerItemSelected(chosenItems: MutableList<String>?) {
//
//        //This is where you get all your items selected from the Multi Selection Spinner :)
//        for (i in chosenItems!!.indices){
//
//            Log.e("chosenItems",chosenItems[i])
//        }
//    }

    fun setSpinner(){
        // assign variable
        spinner = findViewById(R.id.spinner)

        // initialize selected language array
        Arrays.fill(selectedLanguage, false)

        spinner.setOnClickListener {
            // Initialize alert dialog
            val builder = AlertDialog.Builder(this)

            // set title
            builder.setTitle("Select Language")

            // set dialog non cancelable
            builder.setCancelable(false)

            builder.setMultiChoiceItems(langArray, selectedLanguage) { _, i, b ->
                // check condition
                if (b) {
                    // when checkbox selected
                    // Add position in lang list
                    langList.add(i)
                    // Sort array list
                    langList.sort()
                } else {
                    // when checkbox unselected
                    // Remove position from langList
                    langList.remove(i)
                }
            }

            builder.setPositiveButton("OK") { _, _ ->
                // Initialize string builder
                val stringBuilder = StringBuilder()
                // use for loop
                for (j in langList.indices) {
                    // concat array value
                    stringBuilder.append(langArray[langList[j]])
                    // check condition
                    if (j != langList.size - 1) {
                        // When j value not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(", ")
                    }
                }
                // set text on textView
                spinner.text = stringBuilder.toString()
            }

            builder.setNegativeButton("Cancel") { dialogInterface, _ ->
                // dismiss dialog
                dialogInterface.dismiss()
            }

            builder.setNeutralButton("Clear All") { _, _ ->
                // use for loop
                for (j in selectedLanguage.indices) {
                    // remove all selection
                    selectedLanguage[j] = false
                }
                // clear language list
                langList.clear()
                // clear text view value
                spinner.text = ""
            }

            // show dialog
            builder.show()
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