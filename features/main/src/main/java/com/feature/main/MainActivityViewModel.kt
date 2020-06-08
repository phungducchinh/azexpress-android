package com.feature.main

import com.app.data.remote.api.app.user.UserApi
import com.lib.core.activity.navigator.NavigatorActivityViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(

) : NavigatorActivityViewModel(),
    MainActivityContract.ViewModel {

    @Inject
    lateinit var mUserApi: UserApi

    var value: Int? = null

}