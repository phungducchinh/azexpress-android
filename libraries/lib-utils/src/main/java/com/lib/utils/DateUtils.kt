package com.lib.utils

import com.app.config.AppConstants
import java.math.BigDecimal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DateUtils {
    companion object {
        fun createDateFromDMY(date: Int, month: Int, year: Int): Date {
            val cal = Calendar.getInstance()
            // reset time
            cal.set(Calendar.AM_PM, Calendar.AM)
            cal.set(Calendar.HOUR, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.MILLISECOND, 0)
            cal.set(Calendar.DATE, date)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.YEAR, year)
            return cal.getTime()
        }

        fun convertTimeZone(date: Date, fromTZ: TimeZone, toTZ: TimeZone): Date {
            var fromTZDst: Int = 0
            if (fromTZ.inDaylightTime(date)) {
                fromTZDst = fromTZ.dstSavings
            }

            val fromTZOffset = fromTZ.rawOffset + fromTZDst

            var toTZDst: Int = 0
            if (toTZ.inDaylightTime(date)) {
                toTZDst = toTZ.dstSavings
            }
            val toTZOffset = toTZ.rawOffset + toTZDst

            return Date(date.time + (toTZOffset - fromTZOffset))
        }

        fun getCurrentDate(): Date {
            val cal = Calendar.getInstance()
            return convertTimeZone(cal.time, TimeZone.getDefault(), TimeZone.getDefault())
        }

        fun getTimeCurrentDate(): Long {
            val c = Calendar.getInstance()
            c.time = Date()
            c.get(Calendar.MILLISECOND)
            return c.timeInMillis
        }

        fun getSecondCurrentDate(): BigDecimal {
            return BigDecimal(getTimeCurrentDate()).divide(BigDecimal(1000))
        }

        fun getBigDecimalDate(date: Date?): BigDecimal {
            val c = Calendar.getInstance()
            date?.let {
                c.time = date
            }
            return BigDecimal(c.timeInMillis).divide(BigDecimal(1000))
        }

        fun getDateByMiliseconds(time: BigDecimal): Date {
            val c = Calendar.getInstance()
            c.timeInMillis = time.multiply(BigDecimal(1000)).toLong()
            return c.time
        }

        fun isSameDay(leftDate: Date, rightDate: Date): Boolean {
            val startCalendar = Calendar.getInstance()
            startCalendar.time = leftDate
            val endCalendar = Calendar.getInstance()
            endCalendar.time = rightDate
            return startCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)
                    && startCalendar.get(Calendar.DAY_OF_YEAR) == endCalendar.get(Calendar.DAY_OF_YEAR)
        }

        fun daysOfWeek(startDayOfWeek: Int): List<Int> {
            val defaultList: ArrayList<Int> = ArrayList()
            defaultList.add(AppConstants.DAY_SUNDAY)
            defaultList.add(AppConstants.DAY_MONDAY)
            defaultList.add(AppConstants.DAY_TUESDAY)
            defaultList.add(AppConstants.DAY_WEDNESDAY)
            defaultList.add(AppConstants.DAY_THURSDAY)
            defaultList.add(AppConstants.DAY_FRIDAY)
            defaultList.add(AppConstants.DAY_SATURDAY)
            var offset = 0
            for (i in defaultList.indices) {
                if (startDayOfWeek == defaultList.get(i)) {
                    offset = i
                    break
                }
            }
            for (i in 0 until offset) {
                defaultList.add(defaultList.get(i))
            }
            for (i in 0 until offset) {
                defaultList.removeAt(0)
            }
            return defaultList
        }

        fun getBeforeRemainDayOfMonth(minDayOfMonth: Int): Int {
            val dayOfWeek = daysOfWeek(AppConstants.DAY_SUNDAY)
            for (i in dayOfWeek.indices) {
                if (minDayOfMonth == dayOfWeek.get(i)) {
                    return i
                }
            }
            return 0
        }

        fun getAfterRemainDayOfMonth(maxDayOfMonth: Int): Int {
            val dayOfWeek = daysOfWeek(AppConstants.DAY_SUNDAY)
            for (i in dayOfWeek.indices) {
                if (maxDayOfMonth == dayOfWeek[i]) {
                    return dayOfWeek.size - 1 - i
                }
            }
            return dayOfWeek.size - 1
        }

        fun getDayFromCalendarDay(calendarDay: Int): Int {
            when (calendarDay) {
                Calendar.SUNDAY -> return AppConstants.DAY_SUNDAY
                Calendar.MONDAY -> return AppConstants.DAY_MONDAY
                Calendar.TUESDAY -> return AppConstants.DAY_TUESDAY
                Calendar.WEDNESDAY -> return AppConstants.DAY_WEDNESDAY
                Calendar.THURSDAY -> return AppConstants.DAY_THURSDAY
                Calendar.FRIDAY -> return AppConstants.DAY_FRIDAY
                Calendar.SATURDAY -> return AppConstants.DAY_SATURDAY
            }
            return AppConstants.DAY_SUNDAY
        }

        fun formatDateDefault(date: Date): String {
            val simpleDateFormat = SimpleDateFormat(AppConstants.DateTime.DEFAULT_DATE_FORMAT, Locale.US)
            return simpleDateFormat.format(date)
        }

        fun getMonthByName(date: Date): String {
            val simpleDateFormat = SimpleDateFormat("MMM")
            return simpleDateFormat.format(date)
        }

        // get current month as number
        fun getMonth(date: Date): Int {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal.get(Calendar.MONTH)
        }

        fun getCurrentMonth(): Int {
            return getMonth(getCurrentDate())
        }

        // get current month as number
        fun getDay(date: Date): Int {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal.get(Calendar.DAY_OF_MONTH)
        }

        fun getCurrentDay(): Int {
            return getDay(getCurrentDate())
        }

        // get current year as number
        fun getYear(date: Date): Int {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal.get(Calendar.YEAR)
        }

        fun getCurrentYear(): Int {
            return getYear(getCurrentDate())
        }

        fun getMonthName(index: Int, locale: Locale): String {
            val calendar = Calendar.getInstance(locale)
            calendar.set(Calendar.MONTH, index)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            return String.format(locale, "%tb", calendar)
        }

        fun parseStringToDate(
            source: String?,
            format: String?
        ): Date? {
            return parseStringToDate(source, format, Locale.US)
        }

        fun parseStringToDate(
            source: String?,
            format: String?,
            locale: Locale?
        ): Date? {
            return if (source == null || format == null || locale == null) {
                null
            } else {
                val simpleDateFormat = SimpleDateFormat(format, locale)
                try {
                    simpleDateFormat.parse(source)
                } catch (e: ParseException) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}