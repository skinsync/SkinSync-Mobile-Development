package com.example.skinsync.activity.users.scan.result

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("data")
	val data: List<RecommendationDataItem> = emptyList(),

	@field:SerializedName("message")
	val message: String? = null
)

data class RecommendationDataItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("product_type")
	val productType: String? = null,

	@field:SerializedName("skintype")
	val skintype: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("notable_effects")
	val notableEffects: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("priority")
	val priority: Int? = null,

	@field:SerializedName("brand")
	val brand: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
