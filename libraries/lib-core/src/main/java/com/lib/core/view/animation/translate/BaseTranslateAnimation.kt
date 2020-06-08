package com.lib.core.view.animation.translate

import android.view.View
import android.view.animation.Transformation
import com.lib.core.view.animation.BaseAnimation

abstract class BaseTranslateAnimation(
    targetView: View?,
    fromValue: Float,
    toValue: Float
) : BaseAnimation(targetView, fromValue, toValue) {

    override
    fun applyTransformation(
        interpolatedTime: Float,
        t: Transformation?
    ) {
        if (isStarted() && !isEnded()) {
            val progress = mStartValue + interpolatedTime * mRangeValue
            applyTranslateTransformation(progress)
            for (updateListener in mOnAnimationUpdateListenerList) {
                updateListener.onAnimationUpdate(this, getTargetView(), interpolatedTime, progress)
            }
        }
    }

    abstract fun applyTranslateTransformation(progress: Float)
}