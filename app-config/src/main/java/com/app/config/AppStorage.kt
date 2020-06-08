package com.app.config

class AppStorage private constructor() {

    private var isAppPlayedIntro = false

    companion object {
        private var mAppStorage: AppStorage =
            AppStorage()

        fun getInstance(): AppStorage {
            return mAppStorage
        }
    }

    fun isAppPlayedIntro(): Boolean {
        return isAppPlayedIntro
    }

    fun setAppPlayedIntro(appPlayedIntro: Boolean): AppStorage {
        isAppPlayedIntro = appPlayedIntro
        return this
    }
}