package com.lib.core.utils

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager

object BluetoothUtils {

    @SuppressLint("MissingPermission")
    fun isEnabled(): Boolean {
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        return bluetoothAdapter?.isEnabled == true
    }

    fun isBLESupported(context: Context?): Boolean {
        context?.packageManager?.let {
            return it.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
        }
        return false
    }
}
