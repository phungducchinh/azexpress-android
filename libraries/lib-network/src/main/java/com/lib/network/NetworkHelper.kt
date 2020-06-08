package com.lib.network

interface NetworkHelper {

    companion object {
        const val INTERNET_AVAILABLE_ACTION = "com.lib.network.internet_available_action"
        const val CONNECTION_ACTION = "com.lib.network.connection_action"
        const val IS_ONLINE_KEY = "com.lib.network.is_online_key"
    }

    fun isConnected(): Boolean?
    fun isOnline(): Boolean?
    fun registerNetworkCallback()
    fun unregisterNetworkCallback()
    fun checkInternet()
    fun onConnectionChanged(connected: Boolean)
    fun onInternetChanged(online: Boolean)
    fun sendBroadcast(action: String, enable: Boolean)
}