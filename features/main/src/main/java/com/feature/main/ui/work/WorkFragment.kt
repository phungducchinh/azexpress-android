package com.feature.main.ui.work

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feature.main.R
import com.feature.main.ui.Work.adapter.WorkAdapter
import com.feature.main.ui.work.adapter.CalendarAdapter
import com.feature.main.ui.work.detail.DetailWorkActivity
import com.feature.main.ui.work.dto.MonthDto
import com.lib.core.fragment.BaseFragment
import com.lib.core.widget.BaseFrameLayout
import com.lib.core.widget.BaseTextView
import com.lib.core.widget.recyclerview.BaseRecyclerView

class WorkFragment : BaseFragment(),
    WorkFragmentContract.View,
    CalendarAdapter.OnCalendarClickListener {

    private lateinit var mViewModel: WorkFragmentViewModel

    private var mWorkRecyclerView: BaseRecyclerView? = null

    private var mMonthTextView: BaseTextView? = null
    private var mPreviousFrameLayout: BaseFrameLayout? = null
    private var mNextFrameLayout: BaseFrameLayout? = null
    private var mCalendarRecyclerView: BaseRecyclerView? = null
    private var mCalendarAdapter: CalendarAdapter? = null

    companion object {
        const val TAG = "WorkFragment"

        fun newInstance(): WorkFragment {
            val args = Bundle()
            val fragment = WorkFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override
    fun getLayoutId(): Int {
        return R.layout.fragment_work
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory
        )[WorkFragmentViewModel::class.java]
    }

    override
    fun init(view: View) {
        super.init(view)

        mMonthTextView = view.findViewById(R.id.month_text_view)
        mPreviousFrameLayout = view.findViewById(R.id.previous_frame_layout)
        mNextFrameLayout = view.findViewById(R.id.next_frame_layout)
        mCalendarRecyclerView = view.findViewById(R.id.calendar_recycler_view)
        mWorkRecyclerView = view.findViewById(R.id.works_recycler_view)

        initData()
        initListener()
    }

    private fun initData() {
        mMonthTextView?.text = String.format("Tháng %d", mViewModel.getMonth() + 1)

        val workAdapter = WorkAdapter(context, mOnActivityCheckFastClickHelper)
        workAdapter.setOnWorkAdapterListener(object : WorkAdapter.OnWorkAdapterListener {
            override fun onWorkClick() {
                val intent = Intent(context, DetailWorkActivity::class.java)
                startActivity(intent)
            }
        })
        mWorkRecyclerView?.layoutManager = LinearLayoutManager(context)
        mWorkRecyclerView?.adapter = workAdapter

        mCalendarAdapter = CalendarAdapter(
            context!!,
            mViewModel.getMonth(),
            mViewModel.getYear(),
            this
        )
        val mLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mCalendarRecyclerView?.layoutManager = mLayoutManager
        mCalendarRecyclerView?.adapter = mCalendarAdapter

        mCalendarAdapter?.setItems(mViewModel.requestListDate())?.notifyDataSetChanged()
    }

    private fun initListener() {
        mNextFrameLayout?.setOnClickListener(this)
        mPreviousFrameLayout?.setOnClickListener(this)
    }


    private fun showPreviousMonth() {
        if (mCalendarAdapter?.getCurrentPosition() != -1) {
//            mViewModel.setCurrentSelected(mAdapterCalendar.getCurrentItem())
        }

        if (mViewModel.getMonth() == 0) {
            mViewModel.setMonth(11)
            mViewModel.setYear(mViewModel.getYear() - 1)
        } else {
            mViewModel.setMonth(mViewModel.getMonth() - 1)
        }
        handleNextPreviousMonth()
    }

    private fun showNextMonth() {
        if (mCalendarAdapter?.getCurrentPosition() != -1) {
//            mViewModel.setCurrentSelected(mAdapterCalendar.getCurrentItem())
        }

        if (mViewModel.getMonth() == 11) {
            mViewModel.setMonth(0)
            mViewModel.setYear(mViewModel.getYear() + 1)
        } else {
            mViewModel.setMonth(mViewModel.getMonth() + 1)
        }

        handleNextPreviousMonth()
    }

    private fun handleNextPreviousMonth() {
        updateHeader()
        mCalendarAdapter?.setMonthAndYear(mViewModel.getMonth(), mViewModel.getYear())
        mCalendarAdapter?.setItems(mViewModel.requestListDate())?.notifyDataSetChanged()
    }

    private fun updateHeader() {
        mMonthTextView?.text = String.format("Tháng %d", mViewModel.getMonth() + 1)
    }

    override
    fun onViewClick(v: View) {
        super.onViewClick(v)
        if (v == mNextFrameLayout) {
            showNextMonth()
        } else if (v == mPreviousFrameLayout) {
            showPreviousMonth()
        }
    }

    override fun onClick(monthDto: MonthDto) {

    }

    override fun onUnClick() {

    }
}