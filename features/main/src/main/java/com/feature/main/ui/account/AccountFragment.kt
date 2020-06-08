package com.feature.main.ui.account

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.app.config.Actions
import com.feature.main.R
import com.feature.main.ui.account.edit_account.EditAccountActivity
import com.feature.main.ui.account.edit_account.fragment.EditAccountFragment
import com.lib.core.fragment.BaseFragment
import com.lib.core.widget.BaseImageView
import com.lib.core.widget.BaseTextView

class AccountFragment : BaseFragment(),
    AccountFragmentContract.View {

    private lateinit var mViewModel: AccountFragmentViewModel
    private var mDayTextView: BaseTextView? = null
    private var mMonthTextView: BaseTextView? = null
    private var mYearTextView: BaseTextView? = null
    private var mCustomTextView: BaseTextView? = null
    private var mLogoutImageView: BaseImageView? = null
    private var mEditImageView: BaseImageView? = null

    companion object {
        const val TAG = "AccountFragment"

        fun newInstance(): AccountFragment {
            val args = Bundle()
            val fragment = AccountFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override
    fun getLayoutId(): Int {
        return R.layout.fragment_account
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory
        )[AccountFragmentViewModel::class.java]
    }

    override
    fun init(view: View) {
        super.init(view)

        mDayTextView = view.findViewById(R.id.day_text_view)
        mMonthTextView = view.findViewById(R.id.month_text_view)
        mYearTextView = view.findViewById(R.id.year_text_view)
        mCustomTextView = view.findViewById(R.id.custom_text_view)
        mLogoutImageView = view.findViewById(R.id.logout_image_view)
        mEditImageView = view.findViewById(R.id.edit_image_view)

        initData()
        initListener()
    }

    private fun initData() {
        mDayTextView?.isSelected = true
    }

    private fun initListener() {
        mDayTextView?.setOnClickListener(this)
        mMonthTextView?.setOnClickListener(this)
        mYearTextView?.setOnClickListener(this)
        mCustomTextView?.setOnClickListener(this)
        mLogoutImageView?.setOnClickListener(this)
        mEditImageView?.setOnClickListener(this)
    }

    override
    fun onViewClick(v: View) {
        super.onViewClick(v)
        if (v == mLogoutImageView) {
            mViewModel.logout()
            val translateBundle = ActivityOptions
                .makeCustomAnimation(context!!, R.anim.fade_in, R.anim.fade_out)
                .toBundle()
            startActivity(Actions.openAuthIntent(context), translateBundle)
            activity?.finishAfterTransition()
        } else if (v == mDayTextView) {
            mDayTextView?.isSelected = true
            mMonthTextView?.isSelected = false
            mYearTextView?.isSelected = false
            mCustomTextView?.isSelected = false
        } else if (v == mMonthTextView) {
            mDayTextView?.isSelected = false
            mMonthTextView?.isSelected = true
            mYearTextView?.isSelected = false
            mCustomTextView?.isSelected = false
        } else if (v == mYearTextView) {
            mDayTextView?.isSelected = false
            mMonthTextView?.isSelected = false
            mYearTextView?.isSelected = true
            mCustomTextView?.isSelected = false
        } else if (v == mCustomTextView) {
            mDayTextView?.isSelected = false
            mMonthTextView?.isSelected = false
            mYearTextView?.isSelected = false
            mCustomTextView?.isSelected = true
        } else if (v == mEditImageView) {
            val intent = Intent(context, EditAccountActivity::class.java)
            startActivity(intent)
        }
    }
}