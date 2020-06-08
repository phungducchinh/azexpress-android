package com.lib.core.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lib.core.activity.listener.OnActivityCheckFastClickHelper
import com.lib.core.activity.navigator.NavigatorActivity
import com.lib.core.fragment.dialog.DefaultErrorDialog
import com.lib.core.viewmodel.ViewModelProviderFactory
import com.lib.core.widget.BaseView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment(),
    BaseFragmentContract.View,
    View.OnClickListener,
    BaseView.OnViewClickListener {

    companion object {
        const val TAG = "BaseFragment"
    }

    private var mNavigatorActivity: NavigatorActivity? = null

    @Inject
    lateinit var mViewModelFactory: ViewModelProviderFactory

    protected var mOnActivityCheckFastClickHelper: com.lib.core.activity.listener.OnActivityCheckFastClickHelper? = null

    override
    fun onAttachFragment(childFragment: Fragment) {
        Log.d(TAG, String.format("onAttachFragment: %s, childFragment => %s", this, childFragment))
        super.onAttachFragment(childFragment)
    }

    override
    fun onAttach(context: Context) {
        Log.d(TAG, String.format("onAttach: %s, context => %s", this, context))
        super.onAttach(context)
        if (context is NavigatorActivity) {
            mNavigatorActivity = context
        }
        if (context is OnActivityCheckFastClickHelper) {
            mOnActivityCheckFastClickHelper = context
            mOnActivityCheckFastClickHelper?.resetClickTime()
        }
    }

    override
    fun onCreate(savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            String.format("onCreate: %s, savedInstanceState => %s", this, savedInstanceState)
        )
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override
    fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        Log.d(
            TAG,
            String.format(
                "onGetLayoutInflater: %s, savedInstanceState => %s",
                this,
                savedInstanceState
            )
        )
        return super.onGetLayoutInflater(savedInstanceState)
    }

    override
    fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            String.format(
                "onInflate: %s, context => %s, savedInstanceState => %s",
                this,
                context,
                savedInstanceState
            )
        )
        super.onInflate(context, attrs, savedInstanceState)
    }

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(
            TAG,
            String.format(
                "onCreateView: %s, inflater => %s, container => %s, savedInstanceState => %s",
                this,
                inflater,
                container,
                savedInstanceState
            )
        )
        return inflater.inflate(getLayoutId(), container, false)
    }

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            String.format(
                "onViewCreated: %s, view => %s, savedInstanceState => %s",
                this,
                view,
                savedInstanceState
            )
        )
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    override
    fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            String.format(
                "onActivityCreated: %s, savedInstanceState => %s",
                this,
                savedInstanceState
            )
        )
        super.onActivityCreated(savedInstanceState)
    }

    override
    fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            String.format(
                "onViewStateRestored: %s, savedInstanceState => %s",
                this,
                savedInstanceState
            )
        )
        super.onViewStateRestored(savedInstanceState)
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
    fun onHiddenChanged(hidden: Boolean) {
        Log.d(TAG, String.format("onHiddenChanged: %s, hidden => %s", this, hidden))
        super.onHiddenChanged(hidden)
    }

    override
    fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, String.format("onSaveInstanceState: %s, outState => %s", this, outState))
        super.onSaveInstanceState(outState)
    }

    override
    fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        Log.d(
            TAG,
            String.format(
                "onMultiWindowModeChanged: %s, isInMultiWindowMode => %s",
                this,
                isInMultiWindowMode
            )
        )
        super.onMultiWindowModeChanged(isInMultiWindowMode)
    }

    override
    fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        Log.d(
            TAG,
            String.format(
                "onPictureInPictureModeChanged: %s, isInPictureInPictureMode => %s",
                this,
                isInPictureInPictureMode
            )
        )
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
    }

    override
    fun onConfigurationChanged(newConfig: Configuration) {
        Log.d(TAG, String.format("onConfigurationChanged: %s, newConfig => %s", this, newConfig))
        super.onConfigurationChanged(newConfig)
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
    fun onLowMemory() {
        Log.d(TAG, String.format("onLowMemory: %s", this))
        super.onLowMemory()
    }

    override
    fun onDestroyView() {
        Log.d(TAG, String.format("onDestroyView: %s", this))
        super.onDestroyView()
    }

    override
    fun onDestroy() {
        Log.d(TAG, String.format("onDestroy: %s", this))
        super.onDestroy()
    }

    override
    fun onDetach() {
        Log.d(TAG, String.format("onDetach: %s", this))
        super.onDetach()
    }

    override
    fun getNavigatorActivity(): NavigatorActivity? {
        return mNavigatorActivity
    }

    override
    fun init(view: View) {
        performDependencyInjection()
    }

    override
    fun showLoading() {
        mNavigatorActivity?.showLoading()
    }

    override
    fun hideLoading() {
        mNavigatorActivity?.hideLoading()
    }

    override
    fun showDefaultErrorDialog(message: String?) {
        mNavigatorActivity?.showDefaultErrorDialog(message)
    }

    override
    fun showDefaultErrorDialog(
        title: String?,
        message: String?,
        action: String?,
        onErrorListener: DefaultErrorDialog.OnActionClickListener?
    ) {
        mNavigatorActivity?.showDefaultErrorDialog(title, message, action, onErrorListener)
    }

    override
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d(
            TAG,
            String.format(
                "onRequestPermissionsResult: %s, requestCode => %d, permissions => %s, grantResults => %s",
                this,
                requestCode,
                permissions.contentToString(),
                grantResults.contentToString()
            )
        )
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(
            TAG,
            String.format(
                "onActivityResult: %s, requestCode => %d, resultCode => %d, data => %s",
                this,
                requestCode,
                resultCode,
                data
            )
        )
        super.onActivityResult(requestCode, resultCode, data)
    }

    override
    fun onClick(v: View) {
        if (v is BaseView && v.isEnableCheckFastClick() && mOnActivityCheckFastClickHelper != null) {
            if (mOnActivityCheckFastClickHelper!!.isFastClick(System.currentTimeMillis())) {
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
}