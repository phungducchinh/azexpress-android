package com.feature.auth.fragment.sign_up

import android.app.ActivityOptions
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.config.Actions
import com.lib.core.fragment.BaseFragment
import com.feature.auth.R
import com.feature.auth.utils.ValidateUtils
import com.lib.core.widget.BaseEditText
import com.lib.core.widget.BaseTextView

class SignUpFragment : BaseFragment(),
    SignUpFragmentContract.View {

    private lateinit var mViewModel: SignUpFragmentViewModel

    private var mNameEditText: BaseEditText? = null
    private var mEmailAddressNameEditText: BaseEditText? = null
    private var mPasswordEditText: BaseEditText? = null

    private var mLoginTextView: BaseTextView? = null
    private var mSignUpTextView: BaseTextView? = null

    companion object {
        const val TAG = "SignUpFragment"

        fun newInstance(): SignUpFragment {
            val args = Bundle()
            val fragment = SignUpFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override
    fun getLayoutId(): Int {
        return R.layout.fragment_sign_up
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory
        )[SignUpFragmentViewModel::class.java]
    }

    override
    fun init(view: View) {
        super.init(view)

        mLoginTextView = view.findViewById(R.id.login_text_view)

        mNameEditText = view.findViewById(R.id.username_edit_text)
        mEmailAddressNameEditText = view.findViewById(R.id.email_edit_text)
        mPasswordEditText = view.findViewById(R.id.password_edit_text)

        mSignUpTextView = view.findViewById(R.id.sign_up_text_view)

        initData()
        initListener()
    }

    private fun initData() {
        mViewModel.requestSignUpLiveData()?.observe(this, Observer<Boolean?> {
            hideLoading()
            val translateBundle = ActivityOptions
                .makeCustomAnimation(context, R.anim.fade_in, R.anim.fade_out)
                .toBundle()
            startActivity(Actions.openMainIntent(context!!), translateBundle)
            activity?.finishAfterTransition()
        })

        mViewModel.requestSignUpErrorLiveData()?.observe(this, Observer<String> {
            hideLoading()
            showDefaultErrorDialog(it)
        })
    }

    private fun initListener() {
        mLoginTextView?.setOnClickListener(this)
        mSignUpTextView?.setOnClickListener(this)
    }

    override
    fun onViewClick(v: View) {
        super.onViewClick(v)
        if (v == mLoginTextView) {
            getNavigatorActivity()?.popFragment(true)
        } else if (v == mSignUpTextView) {
            checkData()
        }
    }

    private fun checkData() {
        val name = mNameEditText?.text
        val email = mEmailAddressNameEditText?.text
        val password = mPasswordEditText?.text

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(
                context,
                "Vui lòng nhập tên đăng nhập.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(
                context,
                "Vui lòng nhập địa chỉ email.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!ValidateUtils.isEmailValid(email)) {
            Toast.makeText(
                context,
                "Vui lòng nhập đúng email.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(
                context,
                "Vui lòng nhập mật khẩu",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (password != null && password.length < 8) {
            Toast.makeText(
                context,
                "Mật khẩu ít nhất nên có 8 ký tự",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        showLoading()
        mViewModel.requestSignUp(
            name.toString(),
            email.toString(),
            password.toString()
        )
    }
}