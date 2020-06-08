package com.lib.core.view.animation.scale

import android.view.View

class BaseScaleYAnimation(
    targetView: View?,
    fromValue: Float,
    toValue: Float
) : BaseScaleAnimation(targetView, fromValue, toValue) {

    override
    fun applyScaleTransformation(progress: Float) {
        getTargetView()?.scaleY = progress
    }
}