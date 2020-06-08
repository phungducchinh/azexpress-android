package com.lib.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    fun showSoftInput(activity: Activity?) {
        showSoftInput(activity?.currentFocus)
    }

    fun showSoftInput(view: View?) {
        view?.let {
            it.isFocusable = true
            it.isFocusableInTouchMode = true
            it.requestFocus()
            val imm =
                it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(it, InputMethodManager.SHOW_FORCED)
        }
    }

    fun hideSoftInput(activity: Activity?) {
        hideSoftInput(activity?.currentFocus)
    }

    fun hideSoftInput(view: View?) {
        view?.let {
            val imm =
                it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}