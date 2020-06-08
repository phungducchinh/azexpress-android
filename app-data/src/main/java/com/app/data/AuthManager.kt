package com.app.data

import com.app.config.di.scope.PerApplication
import com.app.data.local.prefers.PrefersHelper
import javax.inject.Inject

@PerApplication
class AuthManager @Inject constructor(
    private val mPrefersHelper: PrefersHelper
) {
    fun resetUserSession() {
        mPrefersHelper.setToken(null)
    }

    fun saveUserSession(token: String?) {
        mPrefersHelper.setToken(token)
    }
}