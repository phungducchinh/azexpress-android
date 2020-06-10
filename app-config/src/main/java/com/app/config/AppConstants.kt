package com.app.config

object AppConstants {

    const val TAG = "DEBUG_TAG"
    const val TASK_MODEL_KEY = "TASK_MODEL_KEY"
    const val ID_TASK_MODEL_KEY = "ID_TASK_MODEL_KEY"
    const val BUNDLE = "BUNDLE_TAG"

    const val AZEXPRESS_DEVICE_KEY = "com.app.config.AZEXPRESS_DEVICE_KEY"
    const val DETAIL_TASK_KEY = 101

    const val DAY_SUNDAY = 0
    const val DAY_MONDAY = 1
    const val DAY_TUESDAY = 2
    const val DAY_WEDNESDAY = 3
    const val DAY_THURSDAY = 4
    const val DAY_FRIDAY = 5
    const val DAY_SATURDAY = 6

    object Database {
        const val DB_NAME = "base_db"
    }

    object DateTime {
        const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    object WebView {
        const val WEB_URL_KEY = "com.lib.common.WebView.WEB_URL_KEY"
    }

    object DeepLink {
        const val QUERY_KEY = "com.lib.common.DeepLink.QUERY_KEY"

        object Path {
            const val EMPLOYEE_ACTIVATE = "employee/activate"
        }
    }

}