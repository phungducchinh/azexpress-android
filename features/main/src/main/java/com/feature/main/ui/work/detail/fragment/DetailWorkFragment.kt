package com.feature.main.ui.work.detail.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.feature.main.R
import com.feature.main.ui.work.detail.take_photo.TakePhotoFragment
import com.lib.core.fragment.BaseFragment
import com.lib.core.widget.BaseButton
import com.lib.core.widget.BaseConstraintLayout
import com.lib.core.widget.BaseFrameLayout
import com.lib.core.widget.BaseImageView

class DetailWorkFragment : BaseFragment(),
    DetailWorkFragmentContract.View,
    TakePhotoFragment.OnTakePhotoFragmentListener {

    private lateinit var mViewModel: DetailWorkFragmentViewModel
    private var mResultImageView: BaseImageView? = null
    private var mCameraConstraintlayout: BaseConstraintLayout? = null
    private var mBackFrameLayout: BaseFrameLayout? = null
    private var mCancelButton: BaseButton? = null

    companion object {
        const val TAG = "DetailWorkFragment"

        fun newInstance(): DetailWorkFragment {
            val args = Bundle()
            val fragment =
                DetailWorkFragment()
            fragment.arguments = args
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

        mResultImageView = view.findViewById(R.id.result_image_view)
        mCameraConstraintlayout = view.findViewById(R.id.camera_constraint_layout)
        mBackFrameLayout = view.findViewById(R.id.back_frame_layout)
        mCancelButton = view.findViewById(R.id.cancel_button)

        initData()
        initListener()
    }

    private fun initData() {

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
            val mTakePhotoFragment = TakePhotoFragment.newInstance()
            mTakePhotoFragment.setOnTakePhotoFragmentListener(this)
            getNavigatorActivity()?.pushFragment(
                mTakePhotoFragment,
                TakePhotoFragment.TAG,
                true
            )
        }
    }

    override fun onTakePhotoSuccess(bitmap: Bitmap?) {
        mResultImageView?.setImageBitmap(bitmap)
    }
}