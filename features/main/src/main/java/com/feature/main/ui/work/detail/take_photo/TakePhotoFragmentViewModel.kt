package com.feature.main.ui.work.detail.take_photo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.data.model.TaskModel
import com.app.data.remote.api.app.task.TaskApi
import com.app.data.remote.api.app.task.work.TaskDetailResponse
import com.lib.core.fragment.BaseFragmentViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject
class TakePhotoFragmentViewModel @Inject constructor(
) : BaseFragmentViewModel(),
    TakePhotoFragmentContract.ViewModel {

    @Inject
    lateinit var mTaskApi: TaskApi

    private val mRequestUpdateTaskLiveData: MutableLiveData<TaskModel> by lazy {
        MutableLiveData<TaskModel>()
    }

    private val mRequestUpdateTaskLiveDataError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override
    fun requestUpdateTaskLiveData(): MutableLiveData<TaskModel>? {
        return mRequestUpdateTaskLiveData
    }

    override
    fun requestUpdateTaskErrorLiveData(): MutableLiveData<String>? {
        return mRequestUpdateTaskLiveDataError
    }

    override fun requestUpdateTask(idTask: String?, bitmap: File?) {
        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), bitmap)
        val body =
            MultipartBody.Part.createFormData("image", "image", requestFile)
        mTaskApi.updateTask(idTask, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<TaskDetailResponse>> {
                override
                fun onSubscribe(d: Disposable) {
                }

                override
                fun onNext(response: Response<TaskDetailResponse>) {
                    if (response.isSuccessful) {
                        requestUpdateTaskLiveData()?.postValue(response.body()?.data)
                    } else {
                        Log.d("TAGA", response.errorBody()?.string())
//                        val jsonAdapter = mMoshi.adapter(ErrorBody::class.java)
//                        var event: ErrorBody? = null
//                        response.errorBody()?.string()?.let {
//                            event = jsonAdapter.fromJson(it)
//                        }
//                        requestUpdateTaskErrorLiveData()?.postValue(event?.message)
                    }
                }

                override
                fun onError(e: Throwable) {
                    requestUpdateTaskErrorLiveData()?.postValue(e.message.toString())
                }

                override
                fun onComplete() {
                    Log.d("TAGA", "Done")
                }
            })
    }
}
