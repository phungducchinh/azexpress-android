package com.lib.core.activity.listener

interface OnActivityCheckFastClickHelper {
     fun isFastClick(clickTime: Long): Boolean
     fun resetClickTime()
}