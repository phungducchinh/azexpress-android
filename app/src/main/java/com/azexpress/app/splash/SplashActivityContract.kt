package com.azexpress.app.splash

import android.app.Activity
import com.lib.core.activity.splash.BaseSplashActivityContract
import com.lib.core.callback.OnTaskCompleteCallback

interface SplashActivityContract {
    interface View : BaseSplashActivityContract.View {

        fun startIntroAnimInNeed(callback: OnTaskCompleteCallback<Long>?)

        fun handleIntent()

        fun handleIntentDefault()

        fun bringAppToForeground(activity: Activity?)

        fun startAuthenticateFlow()

        fun startMainFlow()
    }

    interface ViewModel : BaseSplashActivityContract.ViewModel {
        fun isLogin(): Boolean

        fun shouldStartIntroAnim(): Boolean
    }
}