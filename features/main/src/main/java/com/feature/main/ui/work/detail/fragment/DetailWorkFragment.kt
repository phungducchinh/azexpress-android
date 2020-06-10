package com.feature.main.ui.work.detail.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.config.AppConstants
import com.app.data.model.TaskModel
import com.bumptech.glide.Glide
import com.feature.main.R
import com.feature.main.ui.work.detail.take_photo.TakePhotoFragment
import com.lib.core.fragment.BaseFragment
import com.lib.core.widget.*
import java.io.*

class DetailWorkFragment : BaseFragment(),
    DetailWorkFragmentContract.View {

    private lateinit var mViewModel: DetailWorkFragmentViewModel
    private var mStatusTextView: BaseTextView? = null
    private var mCodeTextView: BaseTextView? = null
    private var mNameCustomerTextView: BaseTextView? = null
    private var mNameStaffTextView: BaseTextView? = null
    private var mPhoneTextView: BaseTextView? = null
    private var mAddressTextView: BaseTextView? = null
    private var tvDateCompleted: BaseTextView? = null
    private var mDateCompletedTextView: BaseTextView? = null

    private var mNoteTextView: BaseTextView? = null
    private var mResultImageView: BaseImageView? = null
    private var mCameraConstraintlayout: BaseConstraintLayout? = null
    private var mBackFrameLayout: BaseFrameLayout? = null
    private var mCancelButton: BaseButton? = null

    private var mIdTaskModel: String? = null

    companion object {
        const val TAG = "DetailWorkFragment"

        fun newInstance(bundle: Bundle?): DetailWorkFragment {
            val fragment =
                DetailWorkFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override
    fun getLayoutId(): Int {
        return R.layout.fragment_detail_work
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory
        )[DetailWorkFragmentViewModel::class.java]
    }

    override
    fun init(view: View) {
        super.init(view)

        mCodeTextView = view.findViewById(R.id.code_text_view)
        mNameCustomerTextView = view.findViewById(R.id.name_customer_text_view)
        mNameStaffTextView = view.findViewById(R.id.name_staff_text_view)
        mPhoneTextView = view.findViewById(R.id.phone_text_view)
        mAddressTextView = view.findViewById(R.id.address_text_view)
        tvDateCompleted = view.findViewById(R.id.tv_date_complete)
        mDateCompletedTextView = view.findViewById(R.id.date_complete_text_view)
        mStatusTextView = view.findViewById(R.id.status_text_view)
        mResultImageView = view.findViewById(R.id.result_image_view)

        mNoteTextView = view.findViewById(R.id.note_text_view)
        mCameraConstraintlayout = view.findViewById(R.id.camera_constraint_layout)
        mBackFrameLayout = view.findViewById(R.id.back_frame_layout)
        mCancelButton = view.findViewById(R.id.cancel_button)

        initData()
        initListener()
    }

    private fun initData() {
        arguments?.let {
            mIdTaskModel = it.getString(AppConstants.ID_TASK_MODEL_KEY)
        }

        mViewModel.requestTaskDetailLiveData()?.observe(this, Observer<TaskModel?> {
            hideLoading()
            setDataTask(it)
        })

        mViewModel.requestTaskDetailErrorLiveData()?.observe(this, Observer<String> {
            hideLoading()
            showDefaultErrorDialog(it)
        })
        mIdTaskModel?.let {
            mViewModel.requestTaskDetail(it)
        }
    }

    private fun setDataTask(taskModel: TaskModel?) {
        taskModel?.let {
            mCodeTextView?.text = it.taskCode
            mNameCustomerTextView?.text = "Customer"
            mNameStaffTextView?.text = "Phung Duc Chinh"
            mPhoneTextView?.text = it.phone
            mAddressTextView?.text = it.address
            if (it.status == "New") {
                tvDateCompleted?.visibility = View.GONE
                mDateCompletedTextView?.visibility = View.GONE
                mStatusTextView?.visibility = View.GONE
                mCameraConstraintlayout?.visibility = View.VISIBLE
            } else {
                mStatusTextView?.visibility = View.VISIBLE
                mNoteTextView?.visibility = View.GONE
                mCameraConstraintlayout?.visibility = View.GONE

                tvDateCompleted?.visibility = View.VISIBLE
                mDateCompletedTextView?.visibility = View.VISIBLE
                mResultImageView?.let { it1 -> Glide.with(this).load(it.image_url).into(it1) }
                mDateCompletedTextView?.text = it.delivered_at
            }
        }
    }

    private fun initListener() {
        mCameraConstraintlayout?.setOnClickListener(this)
        mBackFrameLayout?.setOnClickListener(this)
        mCancelButton?.setOnClickListener(this)
    }

    override
    fun onViewClick(v: View) {
        super.onViewClick(v)
        if (v == mBackFrameLayout || v == mCancelButton) {
            getNavigatorActivity()?.finish()
        } else if (v == mCameraConstraintlayout) {
            val mTakePhotoFragment =
                TakePhotoFragment.newInstance(mViewModel.requestTaskDetailLiveData()?.value)
            mTakePhotoFragment.setOnTakePhotoFragmentListener(object :
                TakePhotoFragment.OnTakePhotoFragmentListener {
                override fun onTakePhotoSuccess(taskModel: TaskModel?) {
                    setDataTask(taskModel)
                    showDefaultErrorDialog("Chúc mừng bạn đã hoàn thành công việc")
                }
            })
            getNavigatorActivity()?.pushFragment(
                mTakePhotoFragment,
                TakePhotoFragment.TAG,
                true
            )
        }
    }
}