package com.lib.core.activity

import android.os.Bundle
import com.lib.core.fragment.dialog.DefaultErrorDialog

interface BaseActivityContract {
    interface View {
        fun getLayoutId(): Int

        fun performDependencyInjection()

        fun init(savedInstanceState: Bundle?)

        fun isBackPressed(): Boolean

        fun showLoading()

        fun hideLoading()

        fun onTokenExpired()

        fun showDefaultErrorDialog(
            message: String?
        )

        fun showDefaultErrorDialog(
            message: String?,
            onErrorListener: DefaultErrorDialog.OnActionClickListener?
        )

        fun showDefaultErrorDialog(
            title: String?,
            message: String?,
            action: String?,
            onErrorListener: DefaultErrorDialog.OnActionClickListener?
        )

    }

    interface ViewModel
}