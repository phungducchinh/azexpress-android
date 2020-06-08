package com.feature.auth

import com.lib.core.activity.navigator.NavigatorActivityContract

interface AuthActivityContract {
    interface View : NavigatorActivityContract.View {

    }

    interface ViewModel : NavigatorActivityContract.ViewModel {
    }
}