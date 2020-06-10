package com.feature.auth

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lib.core.activity.navigator.NavigatorActivity
import com.feature.auth.fragment.sign_in.SignInFragment
import com.feature.auth.fragment.sign_up.SignUpFragment

class AuthActivity : NavigatorActivity(),
    AuthActivityContract.View {

    private lateinit var mViewModel: AuthActivityViewModel

    override
    fun getLayoutId(): Int {
        return R.layout.activity_auth
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
        )[AuthActivityViewModel::class.java]
    }

    override
    fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)

        pushFragment(SignUpFragment.newInstance(), SignUpFragment.TAG, false)
    }
}