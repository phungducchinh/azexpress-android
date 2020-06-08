package com.feature.main.ui.work

import com.feature.main.ui.work.dto.MonthDto
import com.lib.core.fragment.BaseFragmentViewModel
import com.lib.utils.datetime.DateUtils
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class WorkFragmentViewModel @Inject constructor(

) : BaseFragmentViewModel(),
    WorkFragmentContract.ViewModel {
    private var mListDate: ArrayList<MonthDto> = ArrayList()
    private var mMonth: Int = 0
    private var mYear: Int = 0

    init {
        mMonth = DateUtils.getCurrentMonth()
        mYear = DateUtils.getCurrentYear()
    }

    override
    fun getMonth(): Int {
        return mMonth
    }

    override
    fun getYear(): Int {
        return mYear
    }

    override
    fun setMonth(month: Int) {
        this.mMonth = month
    }

    override
    fun setYear(year: Int) {
        this.mYear = year
    }

    override
    fun requestListDate(): ArrayList<MonthDto> {
        mListDate.clear()
        // date in current month
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, mMonth)
        calendar.set(Calendar.YEAR, mYear)
        val minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in minDay..maxDay) {
            val dto = MonthDto(i, mMonth, mYear)
            mListDate.add(dto)
        }

        // before date if exist
        calendar.set(mYear, mMonth, minDay)
        val beforeDay =
            DateUtils.getBeforeRemainDayOfMonth(
                DateUtils.getDayFromCalendarDay(
                    calendar.get(
                        Calendar.DAY_OF_WEEK
                    )
                )
            )
        for (i in 0 until beforeDay) {
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            val day = calendar.get(Calendar.DATE)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            mListDate.add(0, MonthDto(day, month, year))
        }

        // after date if exist
        calendar.set(mYear, mMonth, maxDay)
        val afterDay =
            DateUtils.getAfterRemainDayOfMonth(DateUtils.getDayFromCalendarDay(calendar.get(Calendar.DAY_OF_WEEK)))
        for (i in 0 until afterDay) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            val day = calendar.get(Calendar.DATE)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            mListDate.add(MonthDto(day, month, year))
        }

//        if (mListDate.size < 42) {
//            calendar.set(
//                mListDate.get(mListDate.size - 1).getYear(),
//                mListDate.get(mListDate.size - 1).getMonth(),
//                mListDate.get(mListDate.size - 1).getDay()
//            )
//            for (i in 0 until 7) {
//                calendar.add(Calendar.DAY_OF_MONTH, 1)
//                val day = calendar.get(Calendar.DATE)
//                val month = calendar.get(Calendar.MONTH)
//                val year = calendar.get(Calendar.YEAR)
//                mListDate.add(MonthDto(day, month, year))
//            }
//        }
        return mListDate
    }
}