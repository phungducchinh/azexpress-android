package com.lib.core.application

interface BaseApplicationContract {
    interface View {
        fun isAlive(): Boolean
        fun onAppOpenWhenApplicationDied()
    }
}