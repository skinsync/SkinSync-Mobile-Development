package com.example.skinsync.data.articleadmin

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ArticleAdminResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("pages")
	val pages: Int? = null,

	@field:SerializedName("data")
	val data: List<ArticleData> = emptyList(),

	@field:SerializedName("page")
	val page: Int? = null
)

data class ArticleData(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(createdAt)
		parcel.writeString(description)
		parcel.writeValue(id)
		parcel.writeString(title)
		parcel.writeString(picture)
		parcel.writeString(url)
		parcel.writeString(updatedAt)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ArticleData> {
		override fun createFromParcel(parcel: Parcel): ArticleData {
			return ArticleData(parcel)
		}

		override fun newArray(size: Int): Array<ArticleData?> {
			return arrayOfNulls(size)
		}
	}
}
