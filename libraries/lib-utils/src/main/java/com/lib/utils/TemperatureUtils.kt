package com.lib.utils

object TemperatureUtils {

    fun fahrenheitToCelsius(fahrenheit: Float): Float {
        return ((fahrenheit - 32) * 5 / 9)
    }

    fun celsiusToFahrenheit(celsius: Float): Float {
        return ((celsius * 9) / 5) + 32
    }
}