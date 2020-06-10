package com.app.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

class TaskModel() :Parcelable{
    @field: Json(name = "task_code")
    var taskCode: String? = null
    @field: Json(name = "house_number")
    var houseNumber: String? = null
    @field: Json(name = "street_name")
    var streetName: String? = null
    @field: Json(name = "ward")
    var ward: String? = null
    @field: Json(name = "district")
    var district: String? = null
    @field: Json(name = "province")
    var province: String? = null
    @field: Json(name = "address")
    var address: String? = null
    @field: Json(name = "phone")
    var phone: String? = null
    @field: Json(name = "status")
    var status: String? = null
    @field: Json(name = "delivered_at")
    var delivered_at: String? = null
    @field: Json(name = "image_url")
    var image_url: String? = null

    constructor(parcel: Parcel) : this() {
        taskCode = parcel.readString()
        houseNumber = parcel.readString()
        streetName = parcel.readString()
        ward = parcel.readString()
        district = parcel.readString()
        province = parcel.readString()
        address = parcel.readString()
        phone = parcel.readString()
        status = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(taskCode)
        parcel.writeString(houseNumber)
        parcel.writeString(streetName)
        parcel.writeString(ward)
        parcel.writeString(district)
        parcel.writeString(province)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskModel> {
        override fun createFromParcel(parcel: Parcel): TaskModel {
            return TaskModel(parcel)
        }

        override fun newArray(size: Int): Array<TaskModel?> {
            return arrayOfNulls(size)
        }
    }
}