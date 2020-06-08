package com.lib.network

import android.content.Context
import android.content.Intent
import android.net.*
import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject

class NetworkHelperImpl @Inject constructor(
    private val mContext: Context?,
    private val onConnectionChangedListener: OnConnectionChangedListener?,
    private val onInternetConnectionChangedListener: OnInternetConnectionChangedListener?

) : NetworkHelper,
    ConnectivityManager.NetworkCallback() {

    companion object {
        var TAG = "NetworkObserverImpl"

        private const val DEFAULT_PING_HOST = "www.google.com"
        private const val DEFAULT_PING_PORT = 80
        private const val DEFAULT_PING_TIMEOUT_IN_MS = 2000
    }

    @ConnectionState
    private var mConnectionState: String? = null
    private var isInitChecked: Boolean = false
    private var isOnline: Boolean? = null

    private var mPingThread: Thread? = null
    private var mPingHost: String? = null
    private var mPingPort = 0
    private var mPingTimeout = 0

    init {
        mPingHost = DEFAULT_PING_HOST
        mPingPort = DEFAULT_PING_PORT
        mPingTimeout = DEFAULT_PING_TIMEOUT_IN_MS
    }

    private val mConnectivityManager: ConnectivityManager? by lazy {
        mContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    }

    override
    fun onLost(network: Network) {
        super.onLost(network)
        Log.d(
            TAG, String.format(
                "onLost: %s, wifi => %s, cellular => %s",
                network,
                mConnectivityManager?.getNetworkCapabilities(network)?.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                ),
                mConnectivityManager?.getNetworkCapabilities(network)?.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                )
            )
        )
        onConnectionChanged(false)
    }

    override
    fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.d(
            TAG, String.format(
                "onAvailable: %s, wifi => %s, cellular => %s",
                network,
                mConnectivityManager?.getNetworkCapabilities(network)?.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                ),
                mConnectivityManager?.getNetworkCapabilities(network)?.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                )
            )
        )
        mConnectionState = when {
            mConnectivityManager?.getNetworkCapabilities(network)?.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) == true -> {
                ConnectionState.MOBILE_CONNECTED
            }
            mConnectivityManager?.getNetworkCapabilities(network)?.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            ) == true -> {
                ConnectionState.WIFI_CONNECTED
            }
            else -> {
                ConnectionState.UNKNOWN
            }
        }
        onConnectionChanged(true)
        checkInternet()
    }

    override
    fun isConnected(): Boolean? {
        return if (isInitChecked) {
            mConnectionState == ConnectionState.WIFI_CONNECTED
                    || mConnectionState == ConnectionState.MOBILE_CONNECTED
        } else {
            null
        }
    }

    override
    fun isOnline(): Boolean? {
        return isOnline
    }

    override
    fun registerNetworkCallback() {
        Log.d(TAG, "registerNetworkCallback")
        val networkBuilder = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

        mConnectivityManager?.registerNetworkCallback(networkBuilder.build(), this)
    }

    override
    fun unregisterNetworkCallback() {
        Log.d(TAG, "unregisterNetworkCallback")
        mConnectivityManager?.unregisterNetworkCallback(this)
    }

    override
    fun checkInternet() {
        Log.d(TAG, "checkInternet")
        mPingThread = Thread {
            var online = false
            val socket = Socket()
            try {
                socket.connect(InetSocketAddress(mPingHost, mPingPort), mPingTimeout)
                online = socket.isConnected
            } catch (e: IOException) {
                Log.d(TAG, String.format("socket connect exception %s", e))
            } finally {
                try {
                    socket.close()
                } catch (e: IOException) {
                    Log.d(TAG, String.format("socket close exception %s", e))
                }
                onInternetChanged(online)
                mPingThread?.interrupt()
                mPingThread = null
            }
        }
        mPingThread?.start()
    }

    override
    fun onConnectionChanged(connected: Boolean) {
        isInitChecked = true
        onConnectionChangedListener?.onConnectionChanged(mConnectionState, connected)
        sendBroadcast(NetworkHelper.CONNECTION_ACTION, connected)
    }

    override
    fun onInternetChanged(online: Boolean) {
        isOnline = online
        onInternetConnectionChangedListener?.onInternetConnectionChanged(mConnectionState, online)
        sendBroadcast(NetworkHelper.INTERNET_AVAILABLE_ACTION, online)
    }

    override
    fun sendBroadcast(action: String, enable: Boolean) {
        Log.d(TAG, String.format("sendBroadcast: isOnline => %s, online => %s", isOnline, enable))
        if (enable != isOnline) {
            isOnline = enable
            val intent = Intent()
            intent.action = action
            intent.putExtra(NetworkHelper.IS_ONLINE_KEY, enable)
            mContext?.sendBroadcast(intent)
        }
    }
}