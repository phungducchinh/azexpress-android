package com.lib.utils

import androidx.fragment.app.DialogFragment

object DialogUtils {

    fun isShowing(dialog: DialogFragment?): Boolean {
        return dialog?.isAdded == true &&  dialog.dialog?.isShowing == true
    }
}