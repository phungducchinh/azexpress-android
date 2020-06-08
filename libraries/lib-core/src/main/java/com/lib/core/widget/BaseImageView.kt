package com.lib.core.widget

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import com.lib.core.R

class BaseImageView : AppCompatImageView,
    BaseView {

    companion object {
        val TAG = BaseImageView::class.java.simpleName
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
    ) {
        init(context, attrs)
    }

    override
    fun init(ctx: Context, attrs: AttributeSet?) {
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.BaseImageView)
        isEnableCheckFastClick = a.getBoolean(R.styleable.BaseImageView_enableCheckFastClick, false)
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