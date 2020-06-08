package com.lib.core.callback

interface OnTaskCompleteCallback<S> {
    fun onTaskCompleted(response: S)
}
