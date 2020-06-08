package com.lib.utils.datetime

import androidx.annotation.IntDef
import androidx.annotation.StringDef
import java.util.*

object MonthUtils {

    private val mNumberNameMap: Map<Int, String> by lazy {
        mapOf(
            Number.JANUARY to Name.JANUARY,
            Number.FEBRUARY to Name.FEBRUARY,
            Number.MARCH to Name.MARCH,
            Number.APRIL to Name.APRIL,
            Number.MAY to Name.MAY,
            Number.JUNE to Name.JUNE,
            Number.JULY to Name.JULY,
            Number.AUGUST to Name.AUGUST,
            Number.SEPTEMBER to Name.SEPTEMBER,
            Number.OCTOBER to Name.OCTOBER,
            Number.NOVEMBER to Name.NOVEMBER,
            Number.DECEMBER to Name.DECEMBER
        )
    }

    private val mNumberAbbreviationMap: Map<Int, String> by lazy {
        mapOf(
            Number.JANUARY to Abbreviation.JANUARY,
            Number.FEBRUARY to Abbreviation.FEBRUARY,
            Number.MARCH to Abbreviation.MARCH,
            Number.APRIL to Abbreviation.APRIL,
            Number.MAY to Abbreviation.MAY,
            Number.JUNE to Abbreviation.JUNE,
            Number.JULY to Abbreviation.JULY,
            Number.AUGUST to Abbreviation.AUGUST,
            Number.SEPTEMBER to Abbreviation.SEPTEMBER,
            Number.OCTOBER to Abbreviation.OCTOBER,
            Number.NOVEMBER to Abbreviation.NOVEMBER,
            Number.DECEMBER to Abbreviation.DECEMBER
        )
    }

    private val mNameNumberMap: Map<String, Int> by lazy {
        mapOf(
            Name.JANUARY to Number.JANUARY,
            Name.FEBRUARY to Number.FEBRUARY,
            Name.MARCH to Number.MARCH,
            Name.APRIL to Number.APRIL,
            Name.MAY to Number.MAY,
            Name.JUNE to Number.JUNE,
            Name.JULY to Number.JULY,
            Name.AUGUST to Number.AUGUST,
            Name.SEPTEMBER to Number.SEPTEMBER,
            Name.OCTOBER to Number.OCTOBER,
            Name.NOVEMBER to Number.NOVEMBER,
            Name.DECEMBER to Number.DECEMBER
        )
    }

    private val mAbbreviationNumberMap: Map<String, Int> by lazy {
        mapOf(
            Abbreviation.JANUARY to Number.JANUARY,
            Abbreviation.FEBRUARY to Number.FEBRUARY,
            Abbreviation.MARCH to Number.MARCH,
            Abbreviation.APRIL to Number.APRIL,
            Abbreviation.MAY to Number.MAY,
            Abbreviation.JUNE to Number.JUNE,
            Abbreviation.JULY to Number.JULY,
            Abbreviation.AUGUST to Number.AUGUST,
            Abbreviation.SEPTEMBER to Number.SEPTEMBER,
            Abbreviation.OCTOBER to Number.OCTOBER,
            Abbreviation.NOVEMBER to Number.NOVEMBER,
            Abbreviation.DECEMBER to Number.DECEMBER
        )
    }

    fun getNameByNumber(number: Int?): String? {
        return mNumberNameMap[number]
    }

    fun getAbbreviationByNumber(number: Int?): String? {
        return mNumberAbbreviationMap[number]
    }

    fun getNumberByName(name: String?): Int? {
        return if (name == null) {
            null
        } else {
            mNameNumberMap[name.toLowerCase(Locale.US)]
        }
    }

    fun getNumberByAbbreviation(abbreviation: String?): Int? {
        return if (abbreviation == null) {
            null
        } else {
            mAbbreviationNumberMap[abbreviation.toLowerCase(Locale.US)]
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(
        Abbreviation.JANUARY,
        Abbreviation.FEBRUARY,
        Abbreviation.MARCH,
        Abbreviation.APRIL,
        Abbreviation.MAY,
        Abbreviation.JUNE,
        Abbreviation.JULY,
        Abbreviation.AUGUST,
        Abbreviation.SEPTEMBER,
        Abbreviation.OCTOBER,
        Abbreviation.NOVEMBER,
        Abbreviation.DECEMBER
    )
    annotation class Abbreviation {
        companion object {
            const val JANUARY = "jan"
            const val FEBRUARY = "feb"
            const val MARCH = "mar"
            const val APRIL = "apr"
            const val MAY = "may"
            const val JUNE = "jun"
            const val JULY = "jul"
            const val AUGUST = "aug"
            const val SEPTEMBER = "sep"
            const val OCTOBER = "oct"
            const val NOVEMBER = "nov"
            const val DECEMBER = "dec"
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(
        Name.JANUARY,
        Name.FEBRUARY,
        Name.MARCH,
        Name.APRIL,
        Name.MAY,
        Name.JUNE,
        Name.JULY,
        Name.AUGUST,
        Name.SEPTEMBER,
        Name.OCTOBER,
        Name.NOVEMBER,
        Name.DECEMBER
    )
    annotation class Name {
        companion object {
            const val JANUARY = "january"
            const val FEBRUARY = "february"
            const val MARCH = "march"
            const val APRIL = "april"
            const val MAY = "may"
            const val JUNE = "june"
            const val JULY = "july"
            const val AUGUST = "august"
            const val SEPTEMBER = "september"
            const val OCTOBER = "october"
            const val NOVEMBER = "november"
            const val DECEMBER = "december"
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(
        Number.JANUARY,
        Number.FEBRUARY,
        Number.MARCH,
        Number.APRIL,
        Number.MAY,
        Number.JUNE,
        Number.JULY,
        Number.AUGUST,
        Number.SEPTEMBER,
        Number.OCTOBER,
        Number.NOVEMBER,
        Number.DECEMBER
    )
    annotation class Number {
        companion object {
            const val JANUARY = Calendar.JANUARY
            const val FEBRUARY = Calendar.FEBRUARY
            const val MARCH = Calendar.MARCH
            const val APRIL = Calendar.APRIL
            const val MAY = Calendar.MAY
            const val JUNE = Calendar.JUNE
            const val JULY = Calendar.JULY
            const val AUGUST = Calendar.AUGUST
            const val SEPTEMBER = Calendar.SEPTEMBER
            const val OCTOBER = Calendar.OCTOBER
            const val NOVEMBER = Calendar.NOVEMBER
            const val DECEMBER = Calendar.DECEMBER
        }
    }

}


