package com.app.data.local.prefers

import com.app.data.model.UserModel

interface PrefersHelper {

    fun getToken(): String?
    fun setToken(token: String?)

    fun getUser(): UserModel?
    fun setUser(userModel: UserModel?)

}