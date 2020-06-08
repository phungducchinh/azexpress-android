package com.lib.core.widget.recyclerview.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lib.core.activity.listener.OnActivityCheckFastClickHelper
import com.lib.core.widget.BaseView

abstract class BaseViewHolder(
    itemView: View,
    private val mOnActivityCheckFastClickHelper: OnActivityCheckFastClickHelper?
) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener,
    BaseView.OnViewClickListener {

    companion object {
        val TAG = BaseViewHolder::class.java.simpleName
    }

    override
    fun onClick(v: View) {
        if (v is BaseView && v.isEnableCheckFastClick() && mOnActivityCheckFastClickHelper != null) {
            if (mOnActivityCheckFastClickHelper.isFastClick(System.currentTimeMillis())) {
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