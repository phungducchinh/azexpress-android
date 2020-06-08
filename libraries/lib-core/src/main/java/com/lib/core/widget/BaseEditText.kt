package com.lib.core.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import com.lib.core.R
import com.lib.core.utils.KeyboardUtils

class BaseEditText : AppCompatEditText,
    TextWatcher,
    BaseView {

    companion object {
        val TAG = BaseEditText::class.java.simpleName
    }

    private var isEnableCheckFastClick: Boolean = false
    private var mOnTextChangedListener: OnTextChangedListener? = null

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
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.BaseEditText)
        isEnableCheckFastClick = a.getBoolean(R.styleable.BaseEditText_enableCheckFastClick, false)
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

    fun addTextChangedListener(onTextChangedListener: OnTextChangedListener) {
        addTextChangedListener(this)
        this.mOnTextChangedListener = onTextChangedListener
    }

    fun removeTextChangedListener() {
        removeTextChangedListener(this)
        this.mOnTextChangedListener = null
    }

    //region TextWatcher
    override
    fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        Log.d(TAG, "beforeTextChanged: ")
    }

    override
    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        mOnTextChangedListener?.onTextChanged(this, s, start, before, count)
    }

    override
    fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (isFocusable && event?.keyCode == KeyEvent.KEYCODE_BACK) {
            clearFocus()
            KeyboardUtils.hideSoftInput(this)
            return true
        }
        return super.onKeyPreIme(keyCode, event)
    }

    override
    fun afterTextChanged(s: Editable) {
        Log.d(TAG, "afterTextChanged: ")
    }
    //endregion TextWatcher

    interface OnTextChangedListener {
        fun onTextChanged(
            editText: BaseEditText,
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        )
    }
}