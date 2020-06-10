package com.feature.main.ui.work.dto

import com.lib.utils.datetime.DateUtils
import java.util.*

class MonthDto(private var day: Int, private var month: Int, private var year: Int) {
    fun getDay(): Int {
        return day
    }

    fun setDay(date: Int) {
        this.day = date
    }

    fun getMonth(): Int {
        return month
    }

    fun setMonth(month: Int) {
        this.month = month
    }

    fun getYear(): Int {
        return year
    }

    fun setYear(year: Int) {
        this.year = year
    }

    fun getNameDay() : String?{
        val date  = DateUtils.createDateFromDMY(day, month,  year)
        val  calendar  = Calendar.getInstance()
        calendar.time = date
        val dayOfWeek: Int = calendar.get(Calendar.DAY_OF_WEEK)

        return if (Calendar.MONDAY == dayOfWeek) {
             "T2"
        } else if (Calendar.TUESDAY === dayOfWeek) {
             "T3"
        } else if (Calendar.WEDNESDAY === dayOfWeek) {
             "T4"
        } else if (Calendar.THURSDAY === dayOfWeek) {
             "T5"
        } else if (Calendar.FRIDAY === dayOfWeek) {
             "T6"
        } else if (Calendar.SATURDAY === dayOfWeek) {
             "T7"
        } else if (Calendar.SUNDAY === dayOfWeek) {
             "CN"
        } else{
            ""
        }
    }

    fun isCurrentDay(): Boolean {
        val dateObj = DateUtils.createDateFromDMY(day, month, year)
        return DateUtils.isSameDay(dateObj, DateUtils.getCurrentDate())
    }
}