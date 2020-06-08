package com.azexpress.app.application

import android.util.Log
import com.app.data.remote.interceptor.UnauthorizedInterceptor
import com.azexpress.app.di.component.DaggerApplicationComponent
import com.lib.core.activity.BaseActivity
import com.lib.core.application.BaseApplication
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApplication : BaseApplication(),
    MyApplicationContract.View,
    UnauthorizedInterceptor.OnTokenExpiredListener {

    companion object {
        var TAG = "MyApplication"
    }

    override
    fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .application(this)
            .context(this)
            .build()
    }

    override
    fun onTokenExpired() {
        Log.d(
            TAG,
            String.format(
                "onUnauthorized:top activity => %s",
                mApplicationLifecycleManager.getForegroundActivity()
            )
        )
        when (mApplicationLifecycleManager.getForegroundActivity()) {
            is BaseActivity -> {
                (mApplicationLifecycleManager.getForegroundActivity() as BaseActivity).onTokenExpired()
            }
        }
    }
}