package com.feature.main.ui.work.detail.fragment

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.app.data.model.TaskModel
import com.lib.core.fragment.BaseFragmentContract
import java.io.File

interface DetailWorkFragmentContract {

    interface View : BaseFragmentContract.View {
    }

    interface ViewModel : BaseFragmentContract.ViewModel {

        fun requestTaskDetailLiveData(): MutableLiveData<TaskModel>?

        fun requestTaskDetailErrorLiveData(): MutableLiveData<String>?

        fun requestTaskDetail(taskId:String)
    }
}