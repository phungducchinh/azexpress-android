package com.lib.core.activity

import com.lib.core.viewmodel.BaseViewModel

abstract class BaseActivityViewModel : BaseViewModel(),
    BaseActivityContract.ViewModel {

    override
    fun onCleared() {
        /*Log.d(
            AppConstants.TAG,
            String.format("onCleared: %s, %s", mActivityComposite, this)
        )*/
        super.onCleared()
    }
}