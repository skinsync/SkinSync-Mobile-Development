package com.example.skinsync.activity.users.listproduct

data class ListProductResponse(
	val total: Int? = null,
	val pages: Int? = null,
	val data: List<ProductDataItem> = emptyList(),
	val page: Int? = null
)

data class Brand(
	val createdAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val id: Int? = null,
	val updatedAt: String? = null
)

data class ProductType(
	val createdAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val id: Int? = null,
	val updatedAt: String? = null
)

data class ProductDataItem(
	val skintype: String? = null,
	val description: String? = null,
	val productTypeId: Int? = null,
	val priority: Int? = null,
	val url: String? = null,
	val picture: String? = null,
	val brandId: Int? = null,
	val createdAt: String? = null,
	val productType: ProductType? = null,
	val name: String? = null,
	val isSavedByUser: Boolean? = null,
	val id: Int? = null,
	val brand: Brand? = null,
	val updatedAt: String? = null
)

