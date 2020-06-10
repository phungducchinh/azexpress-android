package com.lib.utils.datetime

import android.util.Pair
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

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
        return cal.time
    }

    fun getBeforeRemainDayOfMonth(minDayOfMonth: Int): Int {
        val dayOfWeek =
            daysOfWeek(DayOfWeek.MONDAY)
        for (i in dayOfWeek.indices) {
            if (minDayOfMonth == dayOfWeek.get(i)) {
                return i
            }
        }
        return 0
    }

    fun getDayFromCalendarDay(calendarDay: Int): Int {
        when (calendarDay) {
            Calendar.SUNDAY -> return DayOfWeek.SUNDAY
            Calendar.MONDAY -> return DayOfWeek.MONDAY
            Calendar.TUESDAY -> return DayOfWeek.TUESDAY
            Calendar.WEDNESDAY -> return DayOfWeek.WEDNESDAY
            Calendar.THURSDAY -> return DayOfWeek.THURSDAY
            Calendar.FRIDAY -> return DayOfWeek.FRIDAY
            Calendar.SATURDAY -> return DayOfWeek.SATURDAY
        }
        return DayOfWeek.MONDAY
    }

    fun getAfterRemainDayOfMonth(maxDayOfMonth: Int): Int {
        val dayOfWeek =
            daysOfWeek(DayOfWeek.MONDAY)
        for (i in dayOfWeek.indices) {
            if (maxDayOfMonth == dayOfWeek[i]) {
                return dayOfWeek.size - 1 - i
            }
        }
        return dayOfWeek.size - 1
    }

    fun daysOfWeek(startDayOfWeek: Int): List<Int> {
        val defaultList: ArrayList<Int> = ArrayList()
        defaultList.add(DayOfWeek.SUNDAY)
        defaultList.add(DayOfWeek.MONDAY)
        defaultList.add(DayOfWeek.TUESDAY)
        defaultList.add(DayOfWeek.WEDNESDAY)
        defaultList.add(DayOfWeek.THURSDAY)
        defaultList.add(DayOfWeek.FRIDAY)
        defaultList.add(DayOfWeek.SATURDAY)
        var offset = 0
        for (i in defaultList.indices) {
            if (startDayOfWeek == defaultList.get(i)) {
                offset = i
                break
            }
        }
        for (i in 0 until offset) {
            defaultList.add(defaultList[i])
        }
        for (i in 0 until offset) {
            defaultList.removeAt(0)
        }
        return defaultList
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

    fun parseDateToString(
        source: Date?,
        format: String?
    ): String? {
        return parseDateToString(
            source,
            format,
            Locale.US
        )
    }

    private fun parseDateToString(
        source: Date?,
        format: String?,
        locale: Locale?
    ): String? {
        return if (source == null || format == null || locale == null) {
            null
        } else {
            val df: DateFormat = SimpleDateFormat(format, locale)
            return df.format(source)
        }
    }

    fun getCurrentDate(): Date {
        val cal = Calendar.getInstance()
        cal.time = Date()
        return cal.time
    }

    fun isSameDay(leftDate: Date, rightDate: Date): Boolean {
        val startCalendar = Calendar.getInstance()
        startCalendar.time = leftDate
        val endCalendar = Calendar.getInstance()
        endCalendar.time = rightDate
        return startCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)
                && startCalendar.get(Calendar.DAY_OF_YEAR) == endCalendar.get(Calendar.DAY_OF_YEAR)
    }

    // get current month as number
    fun getCurrentMonth(): Int {
        val cal = Calendar.getInstance()
        cal.time = getCurrentDate()
        return cal.get(Calendar.MONTH)
    }

    // get current month as number
    fun getCurrentDay(): Int {
        val cal = Calendar.getInstance()
        cal.time = getCurrentDate()
        return cal.get(Calendar.DAY_OF_MONTH)
    }

    // get current year as number
    fun getCurrentYear(): Int {
        val cal = Calendar.getInstance()
        cal.time = getCurrentDate()
        return cal.get(Calendar.YEAR)
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

    fun getMonthName(index: Int, locale: Locale): String {
        val calendar = Calendar.getInstance(locale)
        calendar.set(Calendar.MONTH, index)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return String.format(locale, "%tb", calendar)
    }

    fun getLastWeek(mCalendar: Calendar): Pair<String, ArrayList<Date>>? {
        mCalendar.add(Calendar.DAY_OF_YEAR, -13)
        val mDateMonday = mCalendar.time

        mCalendar.add(Calendar.DAY_OF_YEAR, 6)
        val mDateSunday = mCalendar.time

        val monday = SimpleDateFormat("MMM dd", Locale.US).format(mDateMonday)
        val sunday = SimpleDateFormat("dd", Locale.US).format(mDateSunday)

        val cal1 = Calendar.getInstance()
        cal1.time = mDateMonday

        val cal2 = Calendar.getInstance()
        cal2.time = mDateSunday

        val listDates = ArrayList<Date>()
        while (!cal1.after(cal2)) {
            listDates.add(cal1.time)
            cal1.add(Calendar.DATE, 1)
        }
        return Pair.create("$monday - $sunday", listDates)
    }

    fun getCurrentWeek(mCalendar: Calendar): Pair<Int, ArrayList<Date>>? {
        var today = -1
        mCalendar.add(Calendar.DAY_OF_YEAR, 1)
        val mDateMonday = mCalendar.time
        mCalendar.add(Calendar.DAY_OF_YEAR, 6)
        val weekSundayDate = mCalendar.time

        val cal1 = Calendar.getInstance()
        cal1.time = mDateMonday

        val cal2 = Calendar.getInstance()
        cal2.time = weekSundayDate

        val listDates = ArrayList<Date>()
        while (!cal1.after(cal2)) {
            listDates.add(cal1.time)
            if(isSameDay(
                    cal1.time,
                    getCurrentDate()
                )
            ){
                today = listDates.size
            }
            cal1.add(Calendar.DATE, 1)
        }
        return Pair.create(today, listDates)
    }

    fun getNextWeek(mCalendar: Calendar): Pair<String, ArrayList<Date>>? {
        mCalendar.add(Calendar.DAY_OF_YEAR, 1)
        val mDateMonday = mCalendar.time
        mCalendar.add(Calendar.DAY_OF_YEAR, 6)
        val weekSundayDate = mCalendar.time

        val monday = SimpleDateFormat("MMM dd", Locale.US).format(mDateMonday)
        val sunday = SimpleDateFormat("dd", Locale.US).format(weekSundayDate)

        val cal1 = Calendar.getInstance()
        cal1.time = mDateMonday

        val cal2 = Calendar.getInstance()
        cal2.time = weekSundayDate
        val listDates = ArrayList<Date>()
        while (!cal1.after(cal2)) {
            listDates.add(cal1.time)
            cal1.add(Calendar.DATE, 1)
        }
        return Pair.create("$monday - $sunday", listDates)
    }

    fun getNameWeek(date: Date): String? {
        val sdf = SimpleDateFormat("EEE", Locale.US)
        return sdf.format(date).substring(0, 1)
    }
}