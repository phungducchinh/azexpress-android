package com.app.data.local.db.user

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "name")
    var name: String?

) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserEntity> =
            object : Parcelable.Creator<UserEntity> {
                override
    fun createFromParcel(source: Parcel): UserEntity =
                    UserEntity(source)
                override
    fun newArray(size: Int): Array<UserEntity?> = arrayOfNulls(size)
            }
    }

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()
    )

    override
    fun describeContents() = 0

    override
    fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
    }
}