package com.feature.auth.fragment.sign_in

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
import com.feature.auth.fragment.sign_up.SignUpFragment
import com.feature.auth.utils.ValidateUtils
import com.lib.core.utils.KeyboardUtils
import com.lib.core.widget.BaseEditText
import com.lib.core.widget.BaseTextView

class SignInFragment : BaseFragment(),
    SignInFragmentContract.View {

    private lateinit var mViewModel: SignInFragmentViewModel
    private var mSignUpTextView: BaseTextView? = null
    private var mEmailEditText: BaseEditText? = null
    private var mPasswordEditText: BaseEditText? = null
    private var mLoginTextView: BaseTextView? = null
    private var mForgotPasswordTextView: BaseTextView? = null

    companion object {
        const val TAG = "SignInFragment"

        fun newInstance(): SignInFragment {
            val args = Bundle()
            val fragment = SignInFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override
    fun getLayoutId(): Int {
        return R.layout.fragment_sign_in
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory
        )[SignInFragmentViewModel::class.java]
    }

    override
    fun init(view: View) {
        super.init(view)

        mSignUpTextView = view.findViewById(R.id.sign_up_text_view)
        mEmailEditText = view.findViewById(R.id.email_edit_text)
        mPasswordEditText = view.findViewById(R.id.password_edit_text)
        mLoginTextView = view.findViewById(R.id.login_text_view)
        mForgotPasswordTextView = view.findViewById(R.id.forgot_pass_text_view)

        initData()
        initListener()
    }

    private fun initData() {


        mViewModel.requestSignInLiveData()?.observe(this, Observer<Boolean?> {
            hideLoading()
            val translateBundle = ActivityOptions
                .makeCustomAnimation(context, R.anim.fade_in, R.anim.fade_out)
                .toBundle()
            startActivity(Actions.openMainIntent(context), translateBundle)
            activity?.finishAfterTransition()
        })

        mViewModel.requestSignInErrorLiveData()?.observe(this, Observer<String?> {
            hideLoading()
            showDefaultErrorDialog(it)
        })
    }

    private fun initListener() {
        mSignUpTextView?.setOnClickListener(this)
        mLoginTextView?.setOnClickListener(this)
        mForgotPasswordTextView?.setOnClickListener(this)
    }

    override
    fun onViewClick(v: View) {
        super.onViewClick(v)
        if (v == mSignUpTextView) {
            getNavigatorActivity()?.pushFragment(
                SignUpFragment.newInstance(),
                SignUpFragment.TAG,
                true
            )
        } else if (v == mLoginTextView) {
            checkData()
        } else if(v == mForgotPasswordTextView){
            Toast.makeText(
                context,
                "Coming soon",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun checkData() {
        val email = mEmailEditText?.text
        val password = mPasswordEditText?.text

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
        mViewModel.requestLogin(email.toString(), password.toString())
    }
}