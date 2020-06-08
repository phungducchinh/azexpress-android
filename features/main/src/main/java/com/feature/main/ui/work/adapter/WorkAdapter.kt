package com.feature.main.ui.Work.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feature.main.R
import com.lib.core.activity.listener.OnActivityCheckFastClickHelper
import com.lib.core.widget.BaseConstraintLayout
import com.lib.core.widget.recyclerview.adapter.BaseRecyclerViewAdapter
import com.lib.core.widget.recyclerview.adapter.BaseViewHolder

class WorkAdapter(
    context: Context?,
    onActivityCheckFastClickHelper: OnActivityCheckFastClickHelper?
) : BaseRecyclerViewAdapter<BaseViewHolder>(
    context,
    onActivityCheckFastClickHelper
) {
    private val NORMAL = 0
    private val TOP = 1

    private val mItems = ArrayList<String>()

    private var mOnWorkAdapterListener: OnWorkAdapterListener? = null

    fun setData(items: ArrayList<String>?) {
        mItems.clear()
        items?.let {
            mItems.addAll(it)
        }
    }

    public fun setOnWorkAdapterListener(onWorkAdapterListener: OnWorkAdapterListener?) {
        this.mOnWorkAdapterListener = onWorkAdapterListener
    }

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == TOP) {
            return BottomHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_work_top, parent, false)
            )
        }
        return WorkHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_work, parent, false)
        )
    }

    override
    fun getItemCount(): Int {
        return 10
    }

    override
    fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is WorkHolder) {
            holder.bindData()
        } else{

        }
    }

    inner class WorkHolder(
        itemView: View
    ) : BaseViewHolder(itemView, mOnActivityCheckFastClickHelper) {

        private var mContentConstraintLayout: BaseConstraintLayout? = null

        init {
            mContentConstraintLayout = itemView.findViewById(R.id.content_constraint_layout)
            mContentConstraintLayout?.setOnClickListener(this)
        }

        fun bindData() {

        }

        override fun onViewClick(v: View) {
            super.onViewClick(v)
            if (v == mContentConstraintLayout) {
                mOnWorkAdapterListener?.onWorkClick()
            }
        }
    }

    inner class BottomHolder(
        itemView: View
    ) : BaseViewHolder(itemView, mOnActivityCheckFastClickHelper) {

        private var mContentConstraintLayout: BaseConstraintLayout? = null

        init {
            mContentConstraintLayout = itemView.findViewById(R.id.content_constraint_layout)
            mContentConstraintLayout?.setOnClickListener(this)
        }

        override fun onViewClick(v: View) {
            super.onViewClick(v)
            if (v == mContentConstraintLayout) {
                mOnWorkAdapterListener?.onWorkClick()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TOP
        }
        return NORMAL
    }

    interface OnWorkAdapterListener {
        fun onWorkClick()
    }
}