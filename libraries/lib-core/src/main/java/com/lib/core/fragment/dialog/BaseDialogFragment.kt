package com.lib.core.fragment.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import android.util.Log
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.widget.RelativeLayout
import com.lib.core.activity.BaseActivity
import com.lib.core.activity.listener.OnActivityCheckFastClickHelper
import com.lib.core.widget.BaseView

abstract class BaseDialogFragment : DialogFragment(),
    View.OnClickListener,
    BaseView.OnViewClickListener {

    companion object {
        const val TAG = "BaseDialogFragment"
    }

    private var mBaseActivity: BaseActivity? = null

    private var mOnActivityCheckFastClickHelper: OnActivityCheckFastClickHelper? = null
    private var mOnBackPressedListener: OnBackPressedListener? = null

    abstract fun getLayoutId(): Int

    fun setOnBackPressedListener(onBackPressedListener: OnBackPressedListener?): BaseDialogFragment {
        this.mOnBackPressedListener = onBackPressedListener
        return this
    }

    override
    fun onAttach(context: Context) {
        Log.d(TAG, String.format("onAttach: %s, context => %s", this, context))
        super.onAttach(context)
        if (context is BaseActivity) {
            this.mBaseActivity = context
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
    fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(
            TAG,
            String.format("onCreateDialog: %s, savedInstanceState => %s", this, savedInstanceState)
        )
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // creating the fullscreen dialog
        val newDialog = Dialog(context!!)
        newDialog.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                mOnBackPressedListener?.onHandleBackPressed() == true
            } else false
        }

        newDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        newDialog.setContentView(root)
        if (newDialog.window != null) {
            newDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            newDialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        newDialog.setCanceledOnTouchOutside(false)

        return newDialog
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
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(getLayoutId(), container, false)
    }

    override
    fun onStart() {
        Log.d(TAG, String.format("onStart: %s", this))
        super.onStart()
    }

    override
    fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, String.format("onSaveInstanceState: %s, outState => %s", this, outState))
        super.onSaveInstanceState(outState)
    }

    override
    fun onCancel(dialog: DialogInterface) {
        Log.d(TAG, String.format("onCancel: %s, dialog => %s", this, dialog))
        super.onCancel(dialog)
    }

    override
    fun onDismiss(dialog: DialogInterface) {
        Log.d(TAG, String.format("onDismiss: %s, dialog => %s", this, dialog))
        super.onDismiss(dialog)
    }

    override
    fun onStop() {
        Log.d(TAG, String.format("onStop: %s", this))
        super.onStop()
    }

    override
    fun onDestroyView() {
        Log.d(TAG, String.format("onDestroyView: %s", this))
        super.onDestroyView()
    }

    override
    fun onDetach() {
        Log.d(TAG, String.format("onDetach: %s", this))
        super.onDetach()
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

    override
    fun show(fragmentManager: FragmentManager, tag: String?) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    fun startAnimationBackground(view: View) {
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 300
        animation.startOffset = 0
        animation.fillAfter = true
        animation.interpolator = AccelerateDecelerateInterpolator()
        view.startAnimation(animation)
    }

    interface OnBackPressedListener {
        fun onHandleBackPressed(): Boolean
    }
}