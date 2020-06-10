package com.feature.main.ui.barcode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.config.AppConstants
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.feature.main.R
import com.feature.main.ui.work.detail.DetailWorkActivity
import com.lib.core.fragment.BaseFragment
import com.lib.core.widget.BaseTextView

class ScanBarCodeFragment : BaseFragment(),
    ScanBarCodeFragmentContract.View {

    private lateinit var mViewModel: ScanBarCodeFragmentViewModel
    private val REQUEST_CAMERA_PERMISSION = 201

//    private var mScanBarCodeTextView: BaseTextView? = null

    private var mCodeScanner: CodeScanner? = null
    private var mCodeScannerView: CodeScannerView? = null

    companion object {
        const val TAG = "ScanBarCodeFragment"

        fun newInstance(): ScanBarCodeFragment {
            val args = Bundle()
            val fragment = ScanBarCodeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override
    fun getLayoutId(): Int {
        return R.layout.fragment_scan_bar_code
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory
        )[ScanBarCodeFragmentViewModel::class.java]
    }

    override
    fun init(view: View) {
        super.init(view)

        mCodeScannerView = view.findViewById(R.id.scanner_view)
//        mScanBarCodeTextView = view.findViewById(R.id.scan_barcode_text_view)

        initData()
        initListener()
    }

    private fun initData() {
        mCodeScannerView?.let {
            mCodeScanner = CodeScanner(context!!, it)
        }
        mCodeScanner?.decodeCallback = DecodeCallback { result ->
            activity?.runOnUiThread {
                val intent = Intent(context, DetailWorkActivity::class.java)
                val bundle = Bundle()
                bundle.putString(AppConstants.ID_TASK_MODEL_KEY, result.text)
                intent.putExtra(AppConstants.BUNDLE, bundle)
                startActivityForResult(intent, AppConstants.DETAIL_TASK_KEY)
            }
        }

        checkPermission()
    }

    private fun initListener() {
//        mScanBarCodeTextView?.setOnClickListener(this)
    }

    override fun onPause() {
        super.onPause()
        mCodeScanner?.releaseResources()
    }


    override
    fun onViewClick(v: View) {
        super.onViewClick(v)
//        if (v == mScanBarCodeTextView) {
//            checkPermission()
//        }
    }

    private fun checkPermission() {
        if (activity == null) return
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mCodeScanner?.startPreview()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION && activity != null) {
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mCodeScanner?.startPreview()
            } else {
                Toast.makeText(
                    context!!,
                    "Camera Permission is Required.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity == null) return
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mCodeScanner?.startPreview()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}