package com.lib.core.utils

import android.bluetooth.BluetoothAdapter

object DeviceUtils {

    fun isSupportBluetooth(): Boolean {
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        return bluetoothAdapter != null
    }
}