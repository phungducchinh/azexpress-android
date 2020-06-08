package com.lib.core.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import com.lib.core.R

class BaseNestedScrollView : ScrollView,
    BaseView {

    companion object {
        val TAG = BaseNestedScrollView::class.java.simpleName
    }

    private var isEnableCheckFastClick: Boolean = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override
    fun init(ctx: Context, attrs: AttributeSet?) {
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.BaseNestedScrollView)
        isEnableCheckFastClick =
            a.getBoolean(R.styleable.BaseNestedScrollView_enableCheckFastClick, false)
        a.recycle()
    }

    override
    fun isEnableCheckFastClick(): Boolean {
        return this.isEnableCheckFastClick
    }

    override
    fun setEnableCheckFastClick(enable: Boolean) {
        this.isEnableCheckFastClick = enable
    }
}