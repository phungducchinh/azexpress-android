package com.lib.core.fragment

import com.lib.core.activity.navigator.NavigatorActivity
import com.lib.core.fragment.dialog.DefaultErrorDialog

interface BaseFragmentContract {
    interface View {
        fun getLayoutId(): Int

        fun getNavigatorActivity(): NavigatorActivity?

        fun init(view: android.view.View)

        fun performDependencyInjection()

        fun showLoading()

        fun hideLoading()

        fun showDefaultErrorDialog(
            message: String?
        )

        fun showDefaultErrorDialog(
            title: String?,
            message: String?,
            action: String?,
            onErrorListener: DefaultErrorDialog.OnActionClickListener?
        )
    }

    interface ViewModel {
    }
}