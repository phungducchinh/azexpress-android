package com.feature.main.ui.work.detail.take_photo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.util.SparseIntArray
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.feature.main.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.lib.core.fragment.BaseFragment
import com.lib.core.widget.BaseConstraintLayout
import com.lib.core.widget.BaseFrameLayout
import com.lib.core.widget.BaseImageView
import com.lib.utils.CommonUtils
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

class TakePhotoFragment : BaseFragment(),
    TakePhotoFragmentContract.View,
    LocationListener {

    private val REQUEST_PERMISSION_LOCATION = 101
    private lateinit var mViewModel: TakePhotoFragmentViewModel

    private var mTextureView: TextureView? = null
    private var mResultImageView: BaseImageView? = null
    private var mMapImageView: BaseImageView? = null
    private var mBackFrameLayout: BaseFrameLayout? = null
    private var mContentConstraintLayout: BaseConstraintLayout? = null
    private var mCameraConstraintLayout: BaseConstraintLayout? = null

    private var isMapReady = false
    private var mMap: GoogleMap? = null

    private var mLocationManager: LocationManager? = null
    private var mLocation: Location? = null

    private val ORIENTATIONS = SparseIntArray()

    private var cameraId: String? = null
    private var cameraDevice: CameraDevice? = null
    private var cameraCaptureSessions: CameraCaptureSession? = null
    private var captureRequestBuilder: CaptureRequest.Builder? = null
    private var imageDimension: Size? = null
    private var imageReader: ImageReader? = null

    private val REQUEST_CAMERA_PERMISSION = 200
    private var mBackgroundHandler: Handler? = null
    private var mBackgroundThread: HandlerThread? = null

    private var mOnTakePhotoFragmentListener: OnTakePhotoFragmentListener? = null

    companion object {
        const val TAG = "TakePhotoFragment"

        fun newInstance(): TakePhotoFragment {
            val args = Bundle()
            val fragment =
                TakePhotoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override
    fun getLayoutId(): Int {
        return R.layout.fragment_take_photo
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory
        )[TakePhotoFragmentViewModel::class.java]
    }

    override
    fun init(view: View) {
        super.init(view)

        mTextureView = view.findViewById(R.id.texture_view)
        mBackFrameLayout = view.findViewById(R.id.back_frame_layout)
        mResultImageView = view.findViewById(R.id.result_image_view)
        mMapImageView = view.findViewById(R.id.map_image_view)
        mContentConstraintLayout = view.findViewById(R.id.content_constraint_layout)
        mCameraConstraintLayout = view.findViewById(R.id.camera_constraint_layout)

        initData()
        initListener()
    }

    private fun initData() {
        mLocationManager =
            activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            mMap = googleMap
            mMap?.setOnMapLoadedCallback {
                mLocation?.let {
                    mMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                it.latitude,
                                it.longitude
                            ), 12f
                        )
                    )

                    val cameraPosition: CameraPosition = CameraPosition.Builder()
                        .target(
                            LatLng(
                                it.latitude,
                                it.longitude
                            )
                        )
                        .zoom(12f)
                        .build()

                    mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
                isMapReady = true
            }
            enableMyLocationIfPermitted()
        }

        mTextureView?.surfaceTextureListener = textureListener
        ORIENTATIONS.append(Surface.ROTATION_0, 90)
        ORIENTATIONS.append(Surface.ROTATION_90, 0)
        ORIENTATIONS.append(Surface.ROTATION_180, 270)
        ORIENTATIONS.append(Surface.ROTATION_270, 180)
    }

    private fun initListener() {
        mBackFrameLayout?.setOnClickListener(this)
        mCameraConstraintLayout?.setOnClickListener(this)
    }

    override
    fun onViewClick(v: View) {
        super.onViewClick(v)
        if (v == mBackFrameLayout) {
            getNavigatorActivity()?.finish()
        } else if (v == mCameraConstraintLayout) {
            captureScreen()
            takePicture()
        }
    }

    private fun enableMyLocationIfPermitted() {
        if (activity == null) return

        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_PERMISSION_LOCATION
            )
        } else if (mMap != null) {
            mMap?.isMyLocationEnabled = true
            mLocationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
            mLocationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (activity == null) return
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                enableMyLocationIfPermitted()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.permission_denied, "Location"),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.permission_denied, "Camera"),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        mLocation = location
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }

    private var textureListener: TextureView.SurfaceTextureListener = object :
        TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(
            surface: SurfaceTexture,
            width: Int,
            height: Int
        ) {
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(
            surface: SurfaceTexture?,
            width: Int,
            height: Int
        ) {

        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    }

    private val stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            createCameraPreview()
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraDevice?.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice?.close()
            cameraDevice = null
        }
    }

    private fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("Camera Background")
        mBackgroundThread?.start()
        mBackgroundThread?.looper?.let {
            mBackgroundHandler = Handler(it)
        }
    }

    private fun stopBackgroundThread() {
        mBackgroundThread?.quitSafely()
        try {
            mBackgroundThread?.join()
            mBackgroundThread = null
            mBackgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun createCameraPreview() {
        try {
            val texture = mTextureView?.surfaceTexture
            imageDimension?.let {
                texture?.setDefaultBufferSize(it.width, it.height)
            }
            val surface = Surface(texture)
            captureRequestBuilder =
                cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder?.addTarget(surface)
            cameraDevice?.createCaptureSession(
                listOf(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(@NonNull cameraCaptureSession: CameraCaptureSession) {
                        //The camera is already closed
                        if (null == cameraDevice) {
                            return
                        }
                        // When the session is ready, we start displaying the preview.
                        cameraCaptureSessions = cameraCaptureSession
                        updatePreview()
                    }

                    override fun onConfigureFailed(@NonNull cameraCaptureSession: CameraCaptureSession) {
                    }
                },
                null
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun openCamera() {
        val manager = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = manager.cameraIdList[0]
            var characteristics: CameraCharacteristics? = null
            cameraId?.let {
                characteristics = manager.getCameraCharacteristics(it)
            }
            val map =
                characteristics?.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            imageDimension = map?.getOutputSizes(SurfaceTexture::class.java)?.get(0)
            cameraId?.let {
                if (ContextCompat.checkSelfPermission(
                        activity!!,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    manager.openCamera(it, stateCallback, null)
                } else {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.CAMERA
                        ),
                        REQUEST_CAMERA_PERMISSION
                    )
                }

            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun updatePreview() {
        if (null == cameraDevice) {
            return
        }
        captureRequestBuilder?.set(
            CaptureRequest.CONTROL_MODE,
            CameraMetadata.CONTROL_MODE_AUTO
        )
        try {
            captureRequestBuilder?.build()?.let {
                cameraCaptureSessions?.setRepeatingRequest(
                    it,
                    null,
                    mBackgroundHandler
                )
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()
        if (mTextureView?.isAvailable == true) {
            openCamera()
        } else {
            mTextureView?.surfaceTextureListener = textureListener
        }
    }

    override fun onPause() {
//        closeCamera()
        stopBackgroundThread()
        super.onPause()
    }

    private fun takePicture() {
        if (null == cameraDevice) {
            return
        }
        try {
            var width = 640
            var height = 480
            mContentConstraintLayout?.let {
                width = it.width
                height = it.height
            }
            val reader =
                ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)
            val outputSurfaces: MutableList<Surface> =
                ArrayList(2)
            outputSurfaces.add(reader.surface)
            outputSurfaces.add(Surface(mTextureView?.surfaceTexture))
            val captureBuilder =
                cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureBuilder?.addTarget(reader.surface)
            captureBuilder?.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
            val rotation = activity?.windowManager?.defaultDisplay?.rotation
            rotation?.let {
                captureBuilder?.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS[rotation])
            }
            val readerListener: ImageReader.OnImageAvailableListener =
                ImageReader.OnImageAvailableListener { reader ->
                    var image: Image? = null
                    try {
                        image = reader.acquireLatestImage()
                        val buffer = image.planes[0].buffer
                        val bytes = ByteArray(buffer.capacity())
                        buffer[bytes]
                        var bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                        activity?.runOnUiThread {
                            mResultImageView?.setImageBitmap(bitmap)
                            mContentConstraintLayout?.let {
                                bitmap = CommonUtils.screenShot(it)
                                mOnTakePhotoFragmentListener?.onTakePhotoSuccess(bitmap)
                            }
                            getNavigatorActivity()?.popFragment(true)
                        }

                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        image?.close()
                    }
                }
            reader.setOnImageAvailableListener(readerListener, mBackgroundHandler)
            val captureListener: CameraCaptureSession.CaptureCallback =
                object : CameraCaptureSession.CaptureCallback() {
                    override fun onCaptureCompleted(
                        session: CameraCaptureSession,
                        request: CaptureRequest,
                        result: TotalCaptureResult
                    ) {
                        super.onCaptureCompleted(session, request, result)
                        createCameraPreview()
                    }
                }
            cameraDevice?.createCaptureSession(
                outputSurfaces,
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        try {
                            captureBuilder?.build()?.let {
                                session.capture(
                                    it,
                                    captureListener,
                                    mBackgroundHandler
                                )
                            }
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {}
                },
                mBackgroundHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun closeCamera() {
        if (null != cameraDevice) {
            cameraDevice?.close()
            cameraDevice = null
        }
        if (null != imageReader) {
            imageReader?.close()
            imageReader = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        closeCamera()
    }

    fun setOnTakePhotoFragmentListener(onTakePhotoFragmentListener: OnTakePhotoFragmentListener) {
        this.mOnTakePhotoFragmentListener = onTakePhotoFragmentListener
    }

    interface OnTakePhotoFragmentListener {
        fun onTakePhotoSuccess(bitmap: Bitmap?)
    }

    private fun captureScreen() {
        val callback =
            SnapshotReadyCallback { snapshot ->
                activity?.runOnUiThread {
                    mMapImageView?.setImageBitmap(snapshot)
                }
            }
        mMap?.snapshot(callback)
    }
}