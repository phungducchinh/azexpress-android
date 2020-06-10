package com.feature.auth.fragment.sign_in

import androidx.lifecycle.MutableLiveData
import com.lib.core.fragment.BaseFragmentContract

interface SignInFragmentContract {

    interface View : BaseFragmentContract.View {
    }

    interface ViewModel : BaseFragmentContract.ViewModel {

        fun requestLogin(email: String?, password: String?)

        fun requestSignInLiveData(): MutableLiveData<Boolean>?

        fun requestSignInErrorLiveData(): MutableLiveData<String>?

        fun setToken()
    }
}