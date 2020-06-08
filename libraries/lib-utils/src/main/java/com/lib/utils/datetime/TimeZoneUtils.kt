package com.lib.utils.datetime

import kotlin.math.abs

object TimeZoneUtils {

    fun parseTimeZoneToString(
        source: Double?,
        format: String?
    ): String? {
        return if (source == null || format == null) {
            null
        } else {
            val timeZone = source.toInt()
            val number = abs(timeZone)
            val signedStr = if (timeZone < 0) {
                "-"
            } else {
                "+"
            }
            return String.format(format, signedStr, number)
        }
    }
}


