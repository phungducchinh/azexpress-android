package com.lib.core.application

import android.app.Activity

interface ApplicationLifecycleCallback {
    fun onAppOpenWhenApplicationAlive()
    fun onAppGoToForeground()
    fun onAppGoToBackgroundViaHomeButton(foregroundActivity: Activity?)
    fun onAppGoToBackgroundViaBackLastActivity(lastActivity: Activity?)
}