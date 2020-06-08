package com.feature.main.ui.work.detail

import com.app.data.remote.api.app.user.UserApi
import com.lib.core.activity.navigator.NavigatorActivityViewModel
import javax.inject.Inject

class DetailWorkActivityViewModel @Inject constructor(

) : NavigatorActivityViewModel(),
    DetailWorkActivityContract.ViewModel {

    @Inject
    lateinit var mUserApi: UserApi

    var value: Int? = null

}