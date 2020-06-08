package com.lib.utils

import java.util.regex.Pattern

object ValidateUtils {

    fun isEmailValid(email: CharSequence?): Boolean {
        return email != null && isEmailValid(
            email.toString()
        )
    }

    fun isEmailValid(email: String?): Boolean {
        if (email == null) {
            return false
        }
        var isValid = false
        val patternFormat = "[A-Za-z0-9\\._%\\+\\-']+@([\\w\\-]+\\.)+[A-Za-z]{2,}$"
        val pattern = Pattern.compile(patternFormat, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }
}