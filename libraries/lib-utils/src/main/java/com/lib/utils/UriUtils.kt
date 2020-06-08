package com.lib.utils

object UriUtils {

    fun getQueryMap(queryStr: String?): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        return if (queryStr == null) {
            map
        } else {
            val queryEntryList = queryStr.split("&")
            for (query in queryEntryList) {
                val keyValue = query.split("=")
                map[keyValue[0]] = keyValue[1]
            }
            map
        }
    }
}