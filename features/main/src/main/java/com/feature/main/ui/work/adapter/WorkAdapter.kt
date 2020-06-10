package com.feature.main.ui.Work.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.data.model.TaskModel
import com.feature.main.R
import com.lib.core.activity.listener.OnActivityCheckFastClickHelper
import com.lib.core.widget.BaseConstraintLayout
import com.lib.core.widget.BaseTextView
import com.lib.core.widget.recyclerview.adapter.BaseRecyclerViewAdapter
import com.lib.core.widget.recyclerview.adapter.BaseViewHolder

class WorkAdapter(
    context: Context?,
    onActivityCheckFastClickHelper: OnActivityCheckFastClickHelper?,
    var mOnWorkAdapterListener: OnWorkAdapterListener
) : BaseRecyclerViewAdapter<BaseViewHolder>(
    context,
    onActivityCheckFastClickHelper
) {
    private val NORMAL = 0
    private val TOP = 1

    private val mItems = ArrayList<TaskModel>()

    fun setData(items: List<TaskModel>?) {
        mItems.clear()
        items?.let {
            mItems.addAll(it)
        }
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
        return mItems.size
    }

    override
    fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is WorkHolder) {
            holder.bindData()
        } else {
            (holder as BottomHolder).bindData()
        }
    }

    inner class WorkHolder(
        itemView: View
    ) : BaseViewHolder(itemView, mOnActivityCheckFastClickHelper) {

        private var mContentConstraintLayout: BaseConstraintLayout? = null
        private var mStatusTextView: BaseTextView? = null
        private var mNameTextView: BaseTextView? = null
        private var mPhoneTextView: BaseTextView? = null
        private var mAddressTextView: BaseTextView? = null

        init {
            mContentConstraintLayout = itemView.findViewById(R.id.content_constraint_layout)
            mStatusTextView = itemView.findViewById(R.id.status_text_view)
            mNameTextView = itemView.findViewById(R.id.name_text_view)
            mPhoneTextView = itemView.findViewById(R.id.phone_text_view)
            mAddressTextView = itemView.findViewById(R.id.address_text_view)
            mContentConstraintLayout?.setOnClickListener(this)
        }

        fun bindData() {
            val taskModel = mItems[adapterPosition]
            val status: String
            if (taskModel.status == "New") {
                status = "Mới"
                mContentConstraintLayout?.isSelected = false
            } else {
                status = "Hoàn thành"
                mContentConstraintLayout?.isSelected = true
            }
            mStatusTextView?.text = String.format(
                "Mã QRCode: %s - %s - %s",
                taskModel.taskCode,
                taskModel.district,
                status
            )
            mNameTextView?.text = "Phung Duc Chinh"
            mPhoneTextView?.text = taskModel.phone
            mAddressTextView?.text = taskModel.address
        }

        override fun onViewClick(v: View) {
            super.onViewClick(v)
            if (v == mContentConstraintLayout) {
                mOnWorkAdapterListener.onWorkClick(mItems[adapterPosition])
            }
        }
    }

    inner class BottomHolder(
        itemView: View
    ) : BaseViewHolder(itemView, mOnActivityCheckFastClickHelper) {

        private var mContentConstraintLayout: BaseConstraintLayout? = null
        private var mStatusTextView: BaseTextView? = null
        private var mNameTextView: BaseTextView? = null
        private var mPhoneTextView: BaseTextView? = null
        private var mAddressTextView: BaseTextView? = null

        init {
            mContentConstraintLayout = itemView.findViewById(R.id.content_constraint_layout)
            mStatusTextView = itemView.findViewById(R.id.status_text_view)
            mNameTextView = itemView.findViewById(R.id.name_text_view)
            mPhoneTextView = itemView.findViewById(R.id.phone_text_view)
            mAddressTextView = itemView.findViewById(R.id.address_text_view)
            mContentConstraintLayout?.setOnClickListener(this)
        }

        fun bindData() {
            val taskModel = mItems[adapterPosition]
            var status = ""
            if (taskModel.status == "New") {
                status = "Mới"
                mContentConstraintLayout?.isSelected = false
            } else {
                status = "Hoàn thành"
                mContentConstraintLayout?.isSelected = true
            }
            mStatusTextView?.text = String.format(
                "Mã QRCode: %s - %s - %s",
                taskModel.taskCode,
                taskModel.district,
                status
            )
            mNameTextView?.text = "Phung Duc Chinh"
            mPhoneTextView?.text = taskModel.phone
            mAddressTextView?.text = taskModel.address
        }

        override fun onViewClick(v: View) {
            super.onViewClick(v)
            if (v == mContentConstraintLayout) {
                mOnWorkAdapterListener.onWorkClick(mItems[adapterPosition])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TOP
        }
        return NORMAL
    }

    public interface OnWorkAdapterListener {
        fun onWorkClick(taskModel: TaskModel)
    }
}