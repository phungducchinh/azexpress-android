package com.feature.main.ui.account

import com.app.data.local.prefers.PrefersHelper
import com.lib.core.fragment.BaseFragmentViewModel
import javax.inject.Inject

class AccountFragmentViewModel @Inject constructor(
) : BaseFragmentViewModel(),
    AccountFragmentContract.ViewModel {


    @Inject
    lateinit var mPrefersHelper: PrefersHelper

    override
    fun logout() {
        mPrefersHelper.setToken(null)
        mPrefersHelper.setUser(null)
    }
}
