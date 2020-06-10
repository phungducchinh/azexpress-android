package com.feature.main.ui.work

import androidx.lifecycle.MutableLiveData
import com.app.data.model.TaskModel
import com.feature.main.ui.work.dto.MonthDto
import com.lib.core.fragment.BaseFragmentContract

interface WorkFragmentContract {

    interface View : BaseFragmentContract.View {
    }

    interface ViewModel : BaseFragmentContract.ViewModel {
        fun getMonth(): Int

        fun getYear(): Int

        fun setMonth(month: Int)

        fun setYear(year: Int)

        fun requestListDate(): ArrayList<MonthDto>

        fun getCurrentDay() : Int

        fun requestGetListTaskLiveData(): MutableLiveData<List<TaskModel>>?

        fun requestGetListTaskErrorLiveData(): MutableLiveData<String>?

        fun requestGetListTask()
    }
}