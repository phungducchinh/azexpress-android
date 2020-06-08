package com.lib.core.widget.recyclerview.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.lib.core.activity.listener.OnActivityCheckFastClickHelper

abstract class BaseRecyclerViewAdapter<T : BaseViewHolder>(
    protected val mContext: Context?,
    protected val mOnActivityCheckFastClickHelper: OnActivityCheckFastClickHelper?
) : RecyclerView.Adapter<T>() {
}