package com.azexpress.app.splash

import com.app.config.AppStorage
import com.app.data.local.prefers.PrefersHelper
import com.lib.core.activity.BaseActivityViewModel
import javax.inject.Inject

class SplashActivityViewModel @Inject constructor(

) : BaseActivityViewModel(),
    SplashActivityContract.ViewModel {

    @Inject
    lateinit var mPrefersHelper: PrefersHelper

    override
    fun isLogin(): Boolean {
        return mPrefersHelper.getToken() != null
    }

    override
    fun shouldStartIntroAnim(): Boolean {
        return !AppStorage.getInstance().isAppPlayedIntro()
    }
}