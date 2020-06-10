package com.feature.auth.fragment.sign_up

import androidx.lifecycle.MutableLiveData
import com.lib.core.fragment.BaseFragmentContract

interface SignUpFragmentContract {

    interface View : BaseFragmentContract.View {
    }

    interface ViewModel : BaseFragmentContract.ViewModel {
        fun requestSignUpLiveData(): MutableLiveData<Boolean>?
        fun requestSignUp(name: String?, email: String?, password: String?)
        fun requestSignUpErrorLiveData(): MutableLiveData<String>?

        fun setToken()
    }
}