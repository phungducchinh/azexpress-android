package com.app.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

class UserModel() : Parcelable {
    @field: Json(name = "unid")
    var unid: String? = null

    @field: Json(name = "createdAt")
    var createdAt: String? = null

    @field: Json(name = "updatedAt")
    var updatedAt: String? = null

    @field: Json(name = "email")
    var email: String? = null

    @field: Json(name = "firstName")
    var firstName: String? = null

    @field: Json(name = "lastName")
    var lastName: String? = null

    @field: Json(name = "phoneNumber")
    var phoneNumber: String? = null

    @field: Json(name = "temperatureUnit")
    var temperatureUnit: String? = null

    constructor(parcel: Parcel) : this() {
        unid = parcel.readString()
        createdAt = parcel.readString()
        updatedAt = parcel.readString()
        email = parcel.readString()
        firstName = parcel.readString()
        lastName = parcel.readString()
        phoneNumber = parcel.readString()
        temperatureUnit = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(unid)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
        parcel.writeString(email)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(phoneNumber)
        parcel.writeString(temperatureUnit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }

}