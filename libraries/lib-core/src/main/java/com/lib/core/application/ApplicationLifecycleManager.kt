package com.lib.core.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.lib.core.activity.splash.BaseSplashActivity
import javax.inject.Inject

class ApplicationLifecycleManager @Inject constructor(
    private val callback: ApplicationLifecycleCallback?
) : Application.ActivityLifecycleCallbacks {

    private val mCreatedActivityList: MutableList<Activity> by lazy { mutableListOf<Activity>() }
    private val mStartedActivityList: MutableList<Activity> by lazy { mutableListOf<Activity>() }
    private val mResumeActivityList: MutableList<Activity> by lazy { mutableListOf<Activity>() }
    private var isAppInBackground: Boolean = false
    private var isBackAllActivity: Boolean = false

    fun isAppAlive(): Boolean {
        return mCreatedActivityList.isNotEmpty()
    }

    fun getForegroundActivity(): Activity? {
        return if (mCreatedActivityList.isEmpty()) {
            null
        } else {
            mCreatedActivityList[mCreatedActivityList.size - 1]
        }
    }

    override
    fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is com.lib.core.activity.BaseActivity) {
            if (mCreatedActivityList.isEmpty()
                && mStartedActivityList.isEmpty()
                && mResumeActivityList.isEmpty()
                && isBackAllActivity
            ) {
                callback?.onAppOpenWhenApplicationAlive()
                isBackAllActivity = false
            }
            if (activity !is BaseSplashActivity) {
                mCreatedActivityList.add(activity)
            }
        }
    }

    override
    fun onActivityStarted(activity: Activity) {
        if (activity is com.lib.core.activity.BaseActivity && activity !is BaseSplashActivity) {
            mStartedActivityList.add(activity)
        }
    }

    override
    fun onActivityResumed(activity: Activity) {
        if (activity is com.lib.core.activity.BaseActivity && activity !is BaseSplashActivity) {
            mResumeActivityList.add(activity)
        }
        if (isAppInBackground && !mResumeActivityList.isNullOrEmpty()) {
            callback?.onAppGoToForeground()
            isAppInBackground = false
        }
    }

    override
    fun onActivityPaused(activity: Activity) {
        if (!mResumeActivityList.isNullOrEmpty() && activity is com.lib.core.activity.BaseActivity) {
            mResumeActivityList.remove(activity)
        }
    }

    override
    fun onActivityStopped(activity: Activity) {
        mStartedActivityList.remove(activity)
        if (activity is com.lib.core.activity.BaseActivity
            && !activity.isBackPressed()
            && mStartedActivityList.isEmpty()
        ) {
            isAppInBackground = true
            callback?.onAppGoToBackgroundViaHomeButton(getForegroundActivity())
        }
    }

    override
    fun onActivityDestroyed(activity: Activity) {
        if (activity is com.lib.core.activity.BaseActivity) {
            mCreatedActivityList.remove(activity)
            if (activity.isBackPressed()) {
                if (mCreatedActivityList.isEmpty()
                    && mStartedActivityList.isEmpty()
                    && mResumeActivityList.isEmpty()
                ) {
                    isAppInBackground = false
                    isBackAllActivity = true
                    callback?.onAppGoToBackgroundViaBackLastActivity(activity)
                }
            }
        }
    }

    override
    fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

}