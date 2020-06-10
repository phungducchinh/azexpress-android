package com.lib.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Looper
import android.os.Process
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View


object CommonUtils {

    fun isFastDoubleClick(clickTime: Long, lastClickTime: Long, awaitTime: Int): Boolean {
        return getAwaitTime(
            clickTime,
            lastClickTime,
            awaitTime
        ) < 0
    }

    fun getAwaitTime(compareTime: Long, needToCheckTime: Long, awaitTime: Int): Long {
        return compareTime - (needToCheckTime + awaitTime)
    }

    fun clearFocusCurrentView(context: Context?) {
        if (context is Activity) {
            clearFocusCurrentView(context)
        }
    }

    fun clearFocusCurrentView(activity: Activity?) {
        val current = activity?.currentFocus
        current?.clearFocus()
    }

    fun checkThread(tag: String) {
        Log.d(
            tag,
            String.format(
                "checkThread: currentThread => %d, myPid => %d",
                Thread.currentThread().id,
                Process.myPid()
            )
        )
        Log.d(
            tag,
            "checkThread: Looper.myLooper() == Looper.getMainLooper() => " + (Looper.myLooper() == Looper.getMainLooper())
        )
        Log.d(
            tag,
            "checkThread: Looper.getMainLooper().getThread() == Thread.currentThread() => " + (Looper.getMainLooper().thread === Thread.currentThread())
        )
    }

    fun getScreenWidth(act: Activity): Int {
        val displaymetrics = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(displaymetrics)
        return displaymetrics.widthPixels
    }

    fun getScreenHeight(act: Activity): Int {
        val displaymetrics = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(displaymetrics)
        return displaymetrics.heightPixels
    }

    fun convertDpToPx(act: Activity, dip: Float): Float {
        val r: Resources = act.resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
        return px
    }

    fun getHeightNavigationBar(act: Activity): Int {
        val resources: Resources = act.getResources()
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

    fun getStatusBarHeight(act: Activity): Int {
        val resources: Resources = act.resources
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }


    fun screenShot(view: View): Bitmap? {
        if (view.width == 0 || view.height == 0) {
            return null
        }
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_4444
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun getAndroidId(context: Context?): String {
        return Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
    }
}