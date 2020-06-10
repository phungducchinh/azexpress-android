package com.feature.main.ui.work.detail.take_photo

import androidx.lifecycle.MutableLiveData
import com.app.data.model.TaskModel
import com.lib.core.fragment.BaseFragmentContract
import java.io.File

interface TakePhotoFragmentContract {

    interface View : BaseFragmentContract.View {
    }

    interface ViewModel : BaseFragmentContract.ViewModel {
        fun requestUpdateTaskLiveData(): MutableLiveData<TaskModel>?

        fun requestUpdateTaskErrorLiveData(): MutableLiveData<String>?

        fun requestUpdateTask(idTask:String?,  bitmap: File?)
    }
}