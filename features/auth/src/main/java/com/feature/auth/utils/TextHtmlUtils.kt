package com.chopfit.android.utils

import android.content.Context
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.feature.auth.R

class TextHtmlUtils {
    companion object {
        protected fun makeLinkClickable(
            strBuilder: SpannableStringBuilder, span: URLSpan?,
            listener: OnLinkClicked<String>?, haveUnderline: Boolean) {

            val start = strBuilder.getSpanStart(span)
            val end = strBuilder.getSpanEnd(span)
            val flags = strBuilder.getSpanFlags(span)
            val clickable = object : ClickableSpan() {
                override 
    fun onClick(view: View) {
                    // Do something with span.getURL() to handle the link click...
                    if (span != null && span.url != null && span.url != "") {
                        if (span.url.contains("tel")) {
                        }
                        if (span.url.contains("www") || span.url.contains("http")
                            || span.url.contains("https") || span.url.contains("html")
                        ) {
                            listener?.onLinkClicked(span.url)
                        }
                    }
                }
            }
            strBuilder.setSpan(clickable, start, end, flags)
            if (haveUnderline) {
                strBuilder.setSpan(URLSpanUnderlineUtils(span!!.url), start, end, flags)
            }else {
                strBuilder.setSpan(URLSpanNoUnderLineUtils(span!!.url), start, end, flags)
            }
            strBuilder.removeSpan(span)
        }

        fun setTextViewHTML(
            context: Context, text: TextView, html: String?,
            listener: OnLinkClicked<String>, haveUnderline: Boolean) {
            if (html != null && html != "") {
                val sequence = Html.fromHtml(html)
                val strBuilder = SpannableStringBuilder(sequence)
                val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
                for (span in urls) {
                    makeLinkClickable(strBuilder, span, listener, haveUnderline)
                }
                text.text = strBuilder
//                text.setLinkTextColor(ContextCompat.getColor(context, R.color.whisper_80))
                text.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }
    public interface OnLinkClicked<LINK> {
        fun onLinkClicked(link: LINK)
    }
}
