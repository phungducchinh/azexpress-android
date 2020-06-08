package com.feature.main.ui.account

import com.lib.core.fragment.BaseFragmentContract

interface AccountFragmentContract {

    interface View : BaseFragmentContract.View {
    }

    interface ViewModel : BaseFragmentContract.ViewModel {
        fun logout()
    }
}