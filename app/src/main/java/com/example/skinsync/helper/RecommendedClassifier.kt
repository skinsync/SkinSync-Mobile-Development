package com.example.skinsync.helper

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class RecommendedClassifier(context: Context) {
    private lateinit var tflite: Interpreter

    init {
        tflite = Interpreter(loadModelFile(context))
    }

    private fun loadModelFile(context: Context): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("model_Recommendation.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun convertTextToByteBuffer(text: String): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(text.length * 4)
        byteBuffer.order(ByteOrder.nativeOrder())
        for (char in text) {
            byteBuffer.putFloat(char.code.toFloat())
        }
        return byteBuffer
    }

    fun getRecommendations(inputSkinType: String, inputProductType: String, inputNotableEffects: String, topK: Int = 5): List<String> {
        val inputSkinTypeBuffer = convertTextToByteBuffer(inputSkinType)
        val inputProductTypeBuffer = convertTextToByteBuffer(inputProductType)
        val inputNotableEffectsBuffer = convertTextToByteBuffer(inputNotableEffects)

        // Assuming the model expects the inputs in a specific order
        val inputs = arrayOf(inputSkinTypeBuffer, inputProductTypeBuffer, inputNotableEffectsBuffer)
        val output = Array(1) { ByteArray(256) } // Adjust size as needed for your model

        tflite.runForMultipleInputsOutputs(inputs, mapOf(0 to output))

        val recommendations = output[0].toString(Charsets.UTF_8).split(",").take(topK)
        return recommendations
    }
}