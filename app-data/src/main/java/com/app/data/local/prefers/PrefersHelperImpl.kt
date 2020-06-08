package com.app.data.local.prefers

import android.content.SharedPreferences
import com.app.data.model.UserModel
import com.squareup.moshi.Moshi
import javax.inject.Inject

class PrefersHelperImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val moshi: Moshi
) : PrefersHelper {

    companion object {
        const val FILE_NAME = "com.app.data.local.prefers.azexpress_prefers"
        const val USER_TOKEN_KEY: String = "com.app.data.local.prefers.USER_TOKEN_KEY"
        const val USER_KEY: String = "com.app.data.local.prefers.USER_KEY"

    }

    override
    fun getToken(): String? {
        return sharedPreferences.getString(USER_TOKEN_KEY, null)
    }

    override
    fun setToken(token: String?) {
        this.sharedPreferences.edit().putString(USER_TOKEN_KEY, token).apply()
    }

    override
    fun getUser(): UserModel? {
        val json = sharedPreferences.getString(USER_KEY, null)
        val jsonAdapter = moshi.adapter(UserModel::class.java)
        return if (json == null) null else jsonAdapter.fromJson(json)
    }

    override
    fun setUser(userModel: UserModel?) {
        val jsonAdapter = moshi.adapter(UserModel::class.java)
        this.sharedPreferences.edit()
            .putString(USER_KEY, jsonAdapter.toJson(userModel)).apply()
    }
}