package com.app.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

class ErrorBody() : Parcelable {

    @field: Json(name = "message")
    var message: String? = null

    @field: Json(name = "code")
    var code: Int? = null

    @field: Json(name = "api_version")
    var api_version: Double? = null

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ErrorBody> = object : Parcelable.Creator<ErrorBody> {
            override fun createFromParcel(source: Parcel): ErrorBody = ErrorBody(source)
            override fun newArray(size: Int): Array<ErrorBody?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel): this(
    ){
        this.message = source.readString();
        this.code = source.readValue(Int::class.java.classLoader) as Int?;
        this.api_version = source.readValue(Double::class.java.classLoader) as Double?;
    }


    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(message)
        writeValue(code)
        writeValue(api_version)
    }
}