package com.feature.main.ui.account.edit_account.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.feature.main.R
import com.lib.core.fragment.BaseFragment
import com.lib.core.widget.BaseButton
import com.lib.core.widget.BaseFrameLayout

class EditAccountFragment : BaseFragment(),
    EditAccountFragmentContract.View {

    private lateinit var mViewModel: EditAccountFragmentViewModel
    private var mBackFrameLayout: BaseFrameLayout? = null
    private var mCancelButton: BaseButton? = null

    companion object {
        const val TAG = "EditAccountFragment"

        fun newInstance(): EditAccountFragment {
            val args = Bundle()
            val fragment =
                EditAccountFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override
    fun getLayoutId(): Int {
        return R.layout.fragment_edit_account
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory
        )[EditAccountFragmentViewModel::class.java]
    }

    override
    fun init(view: View) {
        super.init(view)

        mBackFrameLayout = view.findViewById(R.id.back_frame_layout)
        mCancelButton = view.findViewById(R.id.cancel_button)

        initData()
        initListener()
    }

    private fun initData() {

    }

    private fun initListener() {
        mBackFrameLayout?.setOnClickListener(this)
        mCancelButton?.setOnClickListener(this)
    }

    override
    fun onViewClick(v: View) {
        super.onViewClick(v)
        if (v == mBackFrameLayout || v == mCancelButton) {
            getNavigatorActivity()?.finish()
        }
    }
}