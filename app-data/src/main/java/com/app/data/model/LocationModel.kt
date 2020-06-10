package com.app.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

class LocationModel() : Parcelable {
    @field: Json(name = "date")
    var date: Long? = null
    @field: Json(name = "lat")
    var lat: Double? = null
    @field: Json(name = "long")
    var long: Double? = null

    constructor(parcel: Parcel) : this() {
        date = parcel.readValue(Long::class.java.classLoader) as? Long
        lat = parcel.readValue(Double::class.java.classLoader) as? Double
        long = parcel.readValue(Double::class.java.classLoader) as? Double
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(date)
        parcel.writeValue(lat)
        parcel.writeValue(long)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationModel> {
        override fun createFromParcel(parcel: Parcel): LocationModel {
            return LocationModel(parcel)
        }

        override fun newArray(size: Int): Array<LocationModel?> {
            return arrayOfNulls(size)
        }
    }

}