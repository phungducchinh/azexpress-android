package com.lib.core.application

import android.app.Activity
import android.util.Log
import com.lib.network.NetworkHelper
import com.lib.network.OnConnectionChangedListener
import com.lib.network.OnInternetConnectionChangedListener
import dagger.Lazy
import dagger.android.support.DaggerApplication
import javax.inject.Inject

abstract class BaseApplication : DaggerApplication(),
    BaseApplicationContract.View,
    ApplicationLifecycleCallback,
    OnConnectionChangedListener,
    OnInternetConnectionChangedListener {

    companion object {
        var TAG = "BaseApplication"
    }

    @Inject
    lateinit var mApplicationLifecycleManager: ApplicationLifecycleManager

    @Inject
    lateinit var mNetworkHelper: Lazy<NetworkHelper>

    override
    fun onCreate() {
        super.onCreate()
        onAppOpenWhenApplicationDied()
        registerActivityLifecycleCallbacks(mApplicationLifecycleManager)
    }

    override
    fun isAlive(): Boolean {
        return mApplicationLifecycleManager.isAppAlive()
    }

    override
    fun onAppOpenWhenApplicationDied() {
        Log.d(TAG, "onAppOpenWhenApplicationDied")
        mNetworkHelper.get().registerNetworkCallback()
    }

    override
    fun onAppOpenWhenApplicationAlive() {
        Log.d(TAG, "onAppOpenWhenApplicationAlive")
    }

    override
    fun onAppGoToForeground() {
        Log.d(TAG, "onAppGoToForeground")
    }

    override
    fun onAppGoToBackgroundViaHomeButton(foregroundActivity: Activity?) {
        Log.d(TAG, String.format("onAppGoToBackgroundViaHomeButton => %s", foregroundActivity))
    }

    override
    fun onAppGoToBackgroundViaBackLastActivity(lastActivity: Activity?) {
        Log.d(TAG, String.format("onAppGoToBackgroundViaBackLastActivity => %s", lastActivity))
    }

    override
    fun onConnectionChanged(state: String?, connected: Boolean) {
        Log.d(
            TAG,
            String.format("onConnectionChanged: state => %s, connected => %s", state, connected)
        )
    }

    override
    fun onInternetConnectionChanged(state: String?, online: Boolean) {
        Log.d(
            TAG,
            String.format("onConnectionChanged: state => %s, online => %s", state, online)
        )
    }

}