package com.feature.main.ui.work

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
    }
}