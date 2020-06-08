package com.lib.utils

import android.bluetooth.BluetoothAdapter

object DeviceUtils {

    fun isSupportBluetooth(): Boolean {
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        return bluetoothAdapter != null
    }
}