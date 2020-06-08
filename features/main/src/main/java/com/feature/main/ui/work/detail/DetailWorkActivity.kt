package com.feature.main.ui.work.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lib.core.activity.navigator.NavigatorActivity
import com.feature.main.R
import com.feature.main.ui.work.detail.fragment.DetailWorkFragment
import com.feature.main.ui.work.detail.take_photo.TakePhotoFragment

class DetailWorkActivity : NavigatorActivity(),
    DetailWorkActivityContract.View {

    private lateinit var mViewModel: DetailWorkActivityViewModel

    override
    fun getLayoutId(): Int {
        return R.layout.activity_detail_work
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
        )[DetailWorkActivityViewModel::class.java]
    }

    override
    fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)

        pushFragment(DetailWorkFragment.newInstance(), DetailWorkFragment.TAG, false)
    }
}