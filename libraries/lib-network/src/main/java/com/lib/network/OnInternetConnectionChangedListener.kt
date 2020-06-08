package com.lib.network

interface OnInternetConnectionChangedListener {
    fun onInternetConnectionChanged(@ConnectionState state: String?, online: Boolean)
}