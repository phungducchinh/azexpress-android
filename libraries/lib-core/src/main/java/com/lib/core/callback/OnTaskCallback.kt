package com.lib.core.callback

interface OnTaskCallback<S, V> {
    fun onTaskCompleted(response: S)
    fun onTaskFailed(ex: V)
}
