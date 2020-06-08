package com.feature.main.ui.account.edit_account

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lib.core.activity.navigator.NavigatorActivity
import com.feature.main.R
import com.feature.main.ui.account.edit_account.fragment.EditAccountFragment

class EditAccountActivity : NavigatorActivity(),
    EditAccountActivityContract.View {

    private lateinit var mViewModel: EditAccountActivityViewModel

    override
    fun getLayoutId(): Int {
        return R.layout.activity_edit_account
    }

    override
    fun getContainerId(): Int {
        return R.id.activity_container
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory.get()
        )[EditAccountActivityViewModel::class.java]
    }

    override
    fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)

        pushFragment(EditAccountFragment.newInstance(), EditAccountFragment.TAG, false)
    }
}