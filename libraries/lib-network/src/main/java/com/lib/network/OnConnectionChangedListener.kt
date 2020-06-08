package com.lib.network

interface OnConnectionChangedListener {
    fun onConnectionChanged(@ConnectionState state: String?, connected: Boolean)
}