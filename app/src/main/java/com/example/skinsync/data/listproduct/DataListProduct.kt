package com.example.skinsync.data.listproduct

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataListProduct (
    val type: String,
    val name: String,
    val photo: Int
): Parcelable