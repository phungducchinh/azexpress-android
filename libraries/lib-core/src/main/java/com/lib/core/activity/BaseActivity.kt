package com.lib.core.activity

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import com.app.config.Actions
import com.lib.core.R
import com.lib.core.activity.listener.OnActivityCheckFastClickHelper
import com.lib.core.fragment.dialog.BaseDialogFragment
import com.lib.core.fragment.dialog.DefaultErrorDialog
import com.lib.core.fragment.dialog.DefaultLoadingDialog
import com.lib.core.fragment.BaseFragment
import com.lib.core.utils.DialogUtils
import com.lib.core.viewmodel.ViewModelProviderFactory
import com.lib.core.widget.BaseView
import com.app.data.AuthManager
import com.lib.utils.CommonUtils
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(),
    BaseActivityContract.View,
    View.OnClickListener,
    BaseView.OnViewClickListener,
    OnActivityCheckFastClickHelper {

    companion object {
        const val TAG = BaseFragment.TAG
    }

    protected var mIsBackPressed: Boolean = false

    private var mLastTimeClicked: Long? = null
    private val mCheckFastDoubleClickAwaitTime = 800

    private val mDefaultLoadingDialog: DefaultLoadingDialog by lazy { DefaultLoadingDialog.newInstance() }
    private val mDefaultErrorDialog: DefaultErrorDialog by lazy { DefaultErrorDialog.newInstance() }

    @Inject
    protected lateinit var mViewModelFactory: Lazy<ViewModelProviderFactory>

    @Inject
    lateinit var mAuthManager: Lazy<AuthManager>

    override
    fun onAttachedToWindow() {
        Log.d(TAG, String.format("onAttachedToWindow: %s", this))
        super.onAttachedToWindow()
    }

    override
    fun onCreate(savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            String.format("onCreate: %s, savedInstanceState => %s", this, savedInstanceState)
        )
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(getLayoutId())
        init(savedInstanceState)
    }

    override
    fun init(savedInstanceState: Bundle?) {
        performDependencyInjection()
    }

    override
    fun onConfigurationChanged(newConfig: Configuration) {
        Log.d(TAG, String.format("onConfigurationChanged: %s", this))
        super.onConfigurationChanged(newConfig)
    }

    override
    fun onContentChanged() {
        Log.d(TAG, String.format("onContentChanged: %s", this))
        super.onContentChanged()
    }

    override
    fun onStart() {
        Log.d(TAG, String.format("onStart: %s", this))
        super.onStart()
    }

    override
    fun onResume() {
        Log.d(TAG, String.format("onResume: %s", this))
        super.onResume()
    }

    override
    fun onPause() {
        Log.d(TAG, String.format("onPause: %s", this))
        super.onPause()
    }

    override
    fun onStop() {
        Log.d(TAG, String.format("onStop: %s", this))
        super.onStop()
    }

    override
    fun onDestroy() {
        Log.d(TAG, String.format("onDestroy: %s", this))
        super.onDestroy()
    }

    override
    fun onNewIntent(intent: Intent?) {
        Log.d(TAG, String.format("onNewIntent: %s, intent => %s", this, intent))
        super.onNewIntent(intent)
    }

    override
    fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, String.format("onSaveInstanceState: %s, outState => %s", this, outState))
        super.onSaveInstanceState(outState)
    }

    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(
            TAG,
            String.format(
                "onRestoreInstanceState: %s, savedInstanceState => %s",
                this,
                savedInstanceState
            )
        )
        super.onRestoreInstanceState(savedInstanceState)
    }

    override
    fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.d(
            TAG,
            String.format(
                "onSaveInstanceState: %s, outPersistentState => %s",
                this,
                outPersistentState
            )
        )
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override
    fun onDetachedFromWindow() {
        Log.d(TAG, String.format("onDetachedFromWindow: %s", this))
        super.onDetachedFromWindow()
    }

    override
    fun onBackPressed() {
        Log.d(TAG, String.format("handleBackPressed: %s", this))
        this.mIsBackPressed = true
        super.onBackPressed()
    }

    override
    fun isBackPressed(): Boolean {
        return this.mIsBackPressed
    }

    override
    fun showLoading() {
        if (!mDefaultLoadingDialog.isAdded) {
            mDefaultLoadingDialog.show(supportFragmentManager, DefaultLoadingDialog.TAG)
        }
    }

    override
    fun hideLoading() {
        if (mDefaultLoadingDialog.isAdded && mDefaultLoadingDialog.dialog!!.isShowing) {
            mDefaultLoadingDialog.dismiss()
        }
    }


    override
    fun showDefaultErrorDialog(message: String?) {
        showDefaultErrorDialog(message, null)
    }

    override
    fun showDefaultErrorDialog(
        message: String?,
        onErrorListener: DefaultErrorDialog.OnActionClickListener?
    ) {
        showDefaultErrorDialog(null, message, getString(R.string.action_ok), onErrorListener)
    }

    override
    fun showDefaultErrorDialog(
        title: String?,
        message: String?,
        action: String?,
        onErrorListener: DefaultErrorDialog.OnActionClickListener?
    ) {
        if (!DialogUtils.isShowing(mDefaultErrorDialog)) {
            mDefaultErrorDialog.setData(title, message, action, onErrorListener)
                .show(supportFragmentManager, DefaultErrorDialog.TAG)
        }
    }

    override
    fun onClick(v: View) {
        if (v is BaseView && v.isEnableCheckFastClick()) {
            if (isFastClick(System.currentTimeMillis())) {
                onViewFastClick(v)
            } else {
                onViewClick(v)
            }
        } else {
            onViewClick(v)
        }
    }

    override
    fun onViewClick(v: View) {
        Log.d(TAG, String.format("onViewClick: %s, v => %s", this, v))
    }

    override
    fun onViewFastClick(v: View) {
        Log.d(TAG, String.format("onViewFastClick: %s, v => %s", this, v))
    }

    override
    fun isFastClick(clickTime: Long): Boolean {
        Log.d(
            TAG, String.format(
                "isFastDoubleClick: clickTime => %d, mLastTimeClicked => %s," + " mCheckFastDoubleClickAwaitTime => %d, getAwaitTime => %s",
                clickTime,
                mLastTimeClicked,
                mCheckFastDoubleClickAwaitTime,
                if (mLastTimeClicked == null) null else
                    mLastTimeClicked?.let {
                        CommonUtils.getAwaitTime(
                            clickTime,
                            it,
                            mCheckFastDoubleClickAwaitTime
                        )
                    }
            )
        )

        return if (mLastTimeClicked != null
            && CommonUtils.isFastDoubleClick(
                clickTime,
                mLastTimeClicked!!.toLong(),
                mCheckFastDoubleClickAwaitTime
            )
        ) {
            true
        } else {
            this.mLastTimeClicked = clickTime
            false
        }
    }

    override
    fun resetClickTime() {
        Log.d(TAG, "resetClickTime")
        mLastTimeClicked = null
    }

    override
    fun onTokenExpired() {
        Log.d(TAG, "onTokenExpired")
        mAuthManager.get().resetUserSession()
        hideLoading()
        DefaultErrorDialog.newInstance().let {
            it.setMessage(getString(R.string.common_error_token_expired))
                .setOnActionClickListener(object : DefaultErrorDialog.OnActionClickListener {
                    override
                    fun onActionClick() {
                        it.dismiss()
                        openLoginScreen()
                    }
                })
                .setOnBackPressedListener(object : BaseDialogFragment.OnBackPressedListener {
                    override
                    fun onHandleBackPressed(): Boolean {
                        it.dismiss()
                        openLoginScreen()
                        return true
                    }
                })
                .show(supportFragmentManager, "TokenExpiredDialog")
        }
    }

    fun openLoginScreen() {
        val translateBundle = ActivityOptions
            .makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
            .toBundle()
        val intent = Actions.openAuthIntent(this)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent/*, translateBundle*/)
    }
}