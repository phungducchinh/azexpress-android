package com.feature.main.ui.work.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.feature.main.R
import com.feature.main.ui.work.dto.MonthDto
import com.lib.core.widget.BaseTextView

class CalendarAdapter(
    private val mContext: Context,
    private var mMonth: Int,
    private var mYear: Int,
    private var mOnCalendarClickListener: OnCalendarClickListener
) : RecyclerView.Adapter<CalendarAdapter.CalendarHolder>() {

    private var mItems: ArrayList<MonthDto>? = null
    private var mCurrentPosition: Int = -1

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        return CalendarHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.item_calendar,
                parent,
                false
            )
        )
    }

    override
    fun getItemCount(): Int {
        mItems?.let {
            return it.size
        }
        return 0
    }

    fun setMonthAndYear(month: Int, year: Int) {
        this.mMonth = month
        this.mYear = year
    }

    fun setItems(list: List<MonthDto>): CalendarAdapter {
        if (mItems == null) {
            mItems = ArrayList()
        }
        mItems?.let {
            it.clear()
            it.addAll(list)
        }
        return this
    }

    fun setCurrentPosition(pos: Int) {
        this.mCurrentPosition = pos
    }

    fun getCurrentPosition(): Int {
        return this.mCurrentPosition
    }

    fun getCurrentItem(): MonthDto? {
        return mItems?.get(mCurrentPosition)
    }

    override
    fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        val monthDto = mItems?.get(position)

        monthDto?.let {
            holder.mDateTextView.text = it.getDay().toString()
            holder.mDayTextView.text = it.getNameDay()
            // set date text color
            if (mMonth == it.getMonth() && (it.isCurrentDay() || mCurrentPosition == position)) {
                holder.mDateTextView.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                holder.mDateTextView.setBackgroundResource(R.drawable.bg_circle_ecstasy)
            } else if (mMonth == it.getMonth() && mYear == it.getYear()) {
                holder.mDateTextView.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                holder.mDateTextView.setBackgroundResource(0)
            } else {
                holder.mDateTextView.setTextColor(
                    ContextCompat.getColor(mContext, R.color.black_20)
                )
                holder.mDateTextView.setBackgroundResource(0)
            }
        }
    }

    inner class CalendarHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var mDateTextView: BaseTextView
        var mDayTextView: BaseTextView

        init {
            mDayTextView = itemView.findViewById(R.id.day_text_view)
            mDateTextView = itemView.findViewById(R.id.date_text_view)
            mDateTextView.setOnClickListener(this)
        }

        override
        fun onClick(v: View?) {
            if (v == mDateTextView) {
                if (mCurrentPosition == adapterPosition) {
                    mCurrentPosition = -1
                    notifyDataSetChanged()
                    mOnCalendarClickListener.onUnClick()
                } else {
                    val monthDto = mItems?.get(adapterPosition)
                    monthDto?.let {
                        if (mMonth == it.getMonth() && mYear == it.getYear()) {
                            mCurrentPosition = adapterPosition
                            notifyDataSetChanged()
                            mOnCalendarClickListener.onClick(it)
                        }
                    }
                }
            }
        }
    }

    public interface OnCalendarClickListener {
        fun onClick(monthDto: MonthDto)
        fun onUnClick()
    }
}