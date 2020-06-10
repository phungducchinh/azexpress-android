package com.azexpress.app.splash

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.app.config.Actions
import com.app.config.AppConstants
import com.lib.core.callback.OnTaskCompleteCallback
import com.app.config.AppStorage
import com.lib.core.activity.splash.BaseSplashActivity
import com.lib.core.application.ApplicationLifecycleManager
import com.azexpress.app.R
import javax.inject.Inject

class SplashActivity : BaseSplashActivity(), SplashActivityContract.View {

    companion object {
        var TAG = AppConstants.TAG
    }

    @Inject
    lateinit var mApplicationLifecycleManager: ApplicationLifecycleManager

    private lateinit var mViewModel: SplashActivityViewModel

    override
    fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory.get()
        )[SplashActivityViewModel::class.java]
    }

    override
    fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        startIntroAnimInNeed(object :
            OnTaskCompleteCallback<Long> {
            override
            fun onTaskCompleted(response: Long) {
                AppStorage.getInstance().setAppPlayedIntro(true)
                handleIntent()
            }
        })
    }

    override
    fun startIntroAnimInNeed(callback: OnTaskCompleteCallback<Long>?) {
        if (mApplicationLifecycleManager.isAppAlive()) {
            callback?.onTaskCompleted(System.currentTimeMillis())
        } else {
            Handler().postDelayed({
                callback?.onTaskCompleted(System.currentTimeMillis())
            }, 1000)
        }
    }

    override
    fun handleIntent() {
        if (intent?.action == Intent.ACTION_VIEW && intent?.data?.encodedPath.isNullOrBlank()) {
            if (intent?.data?.encodedPath?.endsWith(AppConstants.DeepLink.Path.EMPLOYEE_ACTIVATE) == true) {
                //todo handle deep link
            } else {
                handleIntentDefault()
            }
        } else {
            handleIntentDefault()
        }
    }

    override
    fun handleIntentDefault() {
        Log.d(
            TAG,
            String.format(
                "handleDefault: isAppAlive => %s, foregroundActivity => %s, %s",
                mApplicationLifecycleManager.isAppAlive(),
                mApplicationLifecycleManager.getForegroundActivity(),
                this
            )
        )
        if (mApplicationLifecycleManager.isAppAlive() && mApplicationLifecycleManager.getForegroundActivity() != null) {
            bringAppToForeground(mApplicationLifecycleManager.getForegroundActivity())
        } else {
            if (mViewModel.isLogin()) {
                startMainFlow()
            } else {
                startAuthenticateFlow()
            }
        }
        finish()
    }

    override
    fun bringAppToForeground(activity: Activity?) {
        Log.d(
            TAG,
            String.format("startBringAppToForegroundFlow: foregroundActivity => %s", activity)
        )
        val intent = Intent(this, activity?.javaClass)
        //todo set flags
        startActivity(intent)
    }

    override
    fun startAuthenticateFlow() {
        Log.d(TAG, "startAuthenticateFlow")
        val translateBundle = ActivityOptions
            .makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
            .toBundle()
        startActivity(Actions.openAuthIntent(this), translateBundle)
    }

    override
    fun startMainFlow() {
        Log.d(TAG, "startMainFlow")
        val translateBundle = ActivityOptions
            .makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
            .toBundle()
        startActivity(Actions.openMainIntent(this), translateBundle)
    }
}