package com.lib.core.fragment.dialog

import android.os.Bundle
import android.view.View
import com.lib.core.R
import com.lib.core.widget.BaseTextView

class DefaultErrorDialog : BaseDialogFragment() {

    private lateinit var mTitleTextView: BaseTextView
    private lateinit var mMessageTextView: BaseTextView
    private lateinit var mActionTextView: BaseTextView

    private var mTitle: String? = null
    private var mMessage: String? = null
    private var mAction: String? = null
    private var mOnErrorListener: OnActionClickListener? = null

    companion object {
        const val TAG = "DefaultErrorDialog"

        fun newInstance(): DefaultErrorDialog {
            val args = Bundle()
            val dialog = DefaultErrorDialog()
            dialog.arguments = args
            return dialog
        }
    }

    override
    fun getLayoutId(): Int {
        return R.layout.dialog_error
    }

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTitleTextView = view.findViewById(R.id.title_text_view)
        mMessageTextView = view.findViewById(R.id.message_text_view)
        mActionTextView = view.findViewById(R.id.action_text_view)

        initData()
        initListener()
    }

    private fun initData() {
        mTitleTextView.visibility = if (mTitle.isNullOrEmpty()) View.GONE else View.VISIBLE
        mTitleTextView.text = mTitle
        mMessageTextView.text = mMessage
    }

    private fun initListener() {
        mActionTextView.setOnClickListener(this)
    }

    fun setData(
        title: String?,
        message: String?,
        action: String?,
        onErrorListener: OnActionClickListener?
    ): DefaultErrorDialog {

        this.mTitle = title
        this.mMessage = message
        this.mAction = action
        this.mOnErrorListener = onErrorListener

        return this
    }

    fun setTitle(title: String?): DefaultErrorDialog {
        this.mTitle = title
        return this
    }

    fun setMessage(message: String?): DefaultErrorDialog {
        this.mMessage = message
        return this
    }

    fun setAction(action: String?): DefaultErrorDialog {
        this.mAction = action
        return this
    }

    fun setOnActionClickListener(onErrorListener: OnActionClickListener?): DefaultErrorDialog {
        this.mOnErrorListener = onErrorListener
        return this
    }

    override
    fun onViewClick(v: View) {
        super.onViewClick(v)
        dismiss()
        if (v == mActionTextView) {
            mOnErrorListener?.onActionClick()
        }
    }

    interface OnActionClickListener {
        fun onActionClick()
    }
}