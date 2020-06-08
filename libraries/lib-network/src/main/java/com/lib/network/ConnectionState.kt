package com.lib.network

import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    ConnectionState.UNKNOWN,
    ConnectionState.WIFI_CONNECTED,
    ConnectionState.MOBILE_CONNECTED
)
annotation class ConnectionState {
    companion object {
        const val UNKNOWN = "unknown"
        const val WIFI_CONNECTED = "connected to WiFi"
        const val MOBILE_CONNECTED = "connected to mobile network"
    }
}