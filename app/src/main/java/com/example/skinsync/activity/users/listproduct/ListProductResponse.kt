package com.example.skinsync.activity.users.listproduct

import com.google.gson.annotations.SerializedName

data class ListProductResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("pages")
	val pages: Int? = null,

	@field:SerializedName("data")
	val data: List<ProductDataItem> = emptyList(),

	@field:SerializedName("page")
	val page: Int? = null
)

data class Brand(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ProductType(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ProductDataItem(

	@field:SerializedName("skintype")
	val skintype: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("product_type_id")
	val productTypeId: Int? = null,

	@field:SerializedName("priority")
	val priority: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("brand_id")
	val brandId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("product_type")
	val productType: ProductType? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("notable_effects")
	val notableEffects: String? = null,

	@field:SerializedName("isSavedByUser")
	val isSavedByUser: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("brand")
	val brand: Brand? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
