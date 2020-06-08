package com.chopfit.android.utils

import android.text.TextPaint
import android.text.style.URLSpan

internal class URLSpanNoUnderLineUtils(url: String) : URLSpan(url) {
    override 
    fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
    }
}
