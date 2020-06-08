package com.lib.core.fragment.dialog

import android.os.Bundle
import com.lib.core.R

class DefaultLoadingDialog : BaseDialogFragment(),
    BaseDialogFragment.OnBackPressedListener {

    companion object {
        const val TAG = "DefaultLoadingDialog"

        fun newInstance(): DefaultLoadingDialog {
            val args = Bundle()
            val dialog = DefaultLoadingDialog()
            dialog.arguments = args
            return dialog
        }
    }

    override
    fun getLayoutId(): Int {
        return R.layout.dialog_loading
    }

    override
    fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnBackPressedListener(this)
    }

    override
    fun onHandleBackPressed(): Boolean {
        return true
    }
}