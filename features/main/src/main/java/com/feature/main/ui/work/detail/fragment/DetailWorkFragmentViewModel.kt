package com.feature.main.ui.work.detail.fragment

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

import retrofit2.Response
import javax.inject.Inject


class DetailWorkFragmentViewModel @Inject constructor(
) : BaseFragmentViewModel(),
    DetailWorkFragmentContract.ViewModel {

    @Inject
    lateinit var mTaskApi: TaskApi

    private val mRequestTaskDetailLiveData: MutableLiveData<TaskModel> by lazy {
        MutableLiveData<TaskModel>()
    }

    private val mRequestTaskDetailLiveDataError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override
    fun requestTaskDetailLiveData(): MutableLiveData<TaskModel>? {
        return mRequestTaskDetailLiveData
    }

    override
    fun requestTaskDetailErrorLiveData(): MutableLiveData<String>? {
        return mRequestTaskDetailLiveDataError
    }

    override
    fun requestTaskDetail(taskId: String) {
        mTaskApi.getTaskDetail(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<TaskDetailResponse>> {
                override
                fun onSubscribe(d: Disposable) {
                }

                override
                fun onNext(response: Response<TaskDetailResponse>) {
                    if (response.isSuccessful) {
                        requestTaskDetailLiveData()?.postValue(response.body()?.data)
                    } else {
//                        val jsonAdapter = mMoshi.adapter(ErrorBody::class.java)
//                        var event: ErrorBody? = null
//                        response.errorBody()?.string()?.let {
//                            event = jsonAdapter.fromJson(it)
//                        }
//                        requestTaskDetailErrorLiveData()?.postValue(event?.message)
                    }
                }

                override
                fun onError(e: Throwable) {
                    requestTaskDetailErrorLiveData()?.postValue(e.message.toString())
                }

                override
                fun onComplete() {
                    Log.d("TAGA", "Done")
                }
            })
    }
}
