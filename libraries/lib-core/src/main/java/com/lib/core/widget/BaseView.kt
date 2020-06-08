package com.lib.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View

interface BaseView {
    fun init(ctx: Context, attrs: AttributeSet?)

    fun isEnableCheckFastClick(): Boolean

    fun setEnableCheckFastClick(enable: Boolean)

    interface OnViewClickListener {
        fun onViewClick(v: View)

        fun onViewFastClick(v: View)
    }
}
