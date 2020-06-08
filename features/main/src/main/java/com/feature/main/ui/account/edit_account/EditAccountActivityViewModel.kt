package com.feature.main.ui.account.edit_account

import com.app.data.remote.api.app.user.UserApi
import com.lib.core.activity.navigator.NavigatorActivityViewModel
import javax.inject.Inject

class EditAccountActivityViewModel @Inject constructor(

) : NavigatorActivityViewModel(),
    EditAccountActivityContract.ViewModel {

    @Inject
    lateinit var mUserApi: UserApi

    var value: Int? = null

}