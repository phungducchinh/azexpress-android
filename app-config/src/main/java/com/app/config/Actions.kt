package com.app.config

import android.content.Context
import android.content.Intent

object Actions {

    fun openAuthIntent(context: Context?) =
        internalIntent(
            context,
            "com.azexpress.app.feature.auth.open"
        )

    fun openMainIntent(context: Context?) =
        internalIntent(
            context,
            "com.azexpress.app.feature.main.open"
        )

    private fun internalIntent(context: Context?, action: String) =
        Intent(action).setPackage(context?.packageName)
}