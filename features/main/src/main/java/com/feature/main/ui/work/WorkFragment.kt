package com.feature.main.ui.work

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.config.AppConstants
import com.app.data.model.TaskModel
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

    private var mHelloTextView: BaseTextView? = null
    private var mMonthTextView: BaseTextView? = null
    private var mPreviousFrameLayout: BaseFrameLayout? = null
    private var mNextFrameLayout: BaseFrameLayout? = null
    private var mCalendarRecyclerView: BaseRecyclerView? = null
    private var mCalendarAdapter: CalendarAdapter? = null
    private var mWorkAdapter: WorkAdapter? = null

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

        mHelloTextView = view.findViewById(R.id.hello_text_view)
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
        mHelloTextView?.text = getString(R.string.work_chao_mung, "Chính")

        mWorkAdapter = WorkAdapter(
            context,
            mOnActivityCheckFastClickHelper,
            object : WorkAdapter.OnWorkAdapterListener {
                override fun onWorkClick(taskModel: TaskModel) {
                    val intent = Intent(context, DetailWorkActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString(AppConstants.ID_TASK_MODEL_KEY, taskModel.taskCode)
                    intent.putExtra(AppConstants.BUNDLE, bundle)
                    startActivityForResult(intent, AppConstants.DETAIL_TASK_KEY)
                }
            })
        mWorkRecyclerView?.layoutManager = LinearLayoutManager(context)
        mWorkRecyclerView?.adapter = mWorkAdapter

        mCalendarAdapter = CalendarAdapter(
            context!!,
            mViewModel.getMonth(),
            mViewModel.getYear(),
            this
        )
        mCalendarRecyclerView?.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mCalendarRecyclerView?.adapter = mCalendarAdapter

        mCalendarAdapter?.setItems(mViewModel.requestListDate())
        mCalendarAdapter?.setCurrentPosition(mViewModel.getCurrentDay())
        mCalendarAdapter?.notifyDataSetChanged()
        mCalendarRecyclerView?.scrollToPosition(mViewModel.getCurrentDay() - 3)


        mViewModel.requestGetListTaskLiveData()?.observe(this, Observer<List<TaskModel>?> {
            hideLoading()
            mWorkAdapter?.setData(it)
            mWorkAdapter?.notifyDataSetChanged()
        })

        mViewModel.requestGetListTaskErrorLiveData()?.observe(this, Observer<String> {
            hideLoading()
            showDefaultErrorDialog(it)
        })

        showLoading()
        mViewModel.requestGetListTask()

    }

    private fun initListener() {
        mNextFrameLayout?.setOnClickListener(this)
        mPreviousFrameLayout?.setOnClickListener(this)
    }

    private fun showPreviousMonth() {
        if (mViewModel.getMonth() == 0) {
            mViewModel.setMonth(11)
            mViewModel.setYear(mViewModel.getYear() - 1)
        } else {
            mViewModel.setMonth(mViewModel.getMonth() - 1)
        }
        handleNextPreviousMonth()
    }

    private fun showNextMonth() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == AppConstants.DETAIL_TASK_KEY){
            mViewModel.requestGetListTask()
        }
    }
}