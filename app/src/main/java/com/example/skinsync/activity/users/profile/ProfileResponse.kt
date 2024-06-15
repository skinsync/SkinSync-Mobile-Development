package com.example.skinsync.activity.users.profile

import android.os.Parcel
import android.os.Parcelable

data class ProfileResponse(
	val data: DataProfile? = null
)

data class DataProfile(
	val password: String? = null,
	val birthdate: String? = null,
	val gender: String? = null,
	val name: String? = null,
	val profilePicture: String? = null,
	val id: Int? = null,
	val email: String? = null
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(password)
		parcel.writeString(birthdate)
		parcel.writeString(gender)
		parcel.writeString(name)
		parcel.writeString(profilePicture)
		parcel.writeValue(id)
		parcel.writeString(email)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<DataProfile> {
		override fun createFromParcel(parcel: Parcel): DataProfile {
			return DataProfile(parcel)
		}

		override fun newArray(size: Int): Array<DataProfile?> {
			return arrayOfNulls(size)
		}
	}
}

