package com.feature.auth

import com.app.data.remote.api.app.user.UserApi
import com.lib.core.activity.navigator.NavigatorActivityViewModel
import javax.inject.Inject

class AuthActivityViewModel @Inject constructor(

) : NavigatorActivityViewModel(),
    AuthActivityContract.ViewModel {

    @Inject
    lateinit var mUserApi: UserApi

    var value: Int? = null

}