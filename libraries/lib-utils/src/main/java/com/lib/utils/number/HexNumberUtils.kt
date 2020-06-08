package com.lib.utils.number

object HexNumberUtils {

    fun decimalToHex(decimal: Int): String {
        var value = decimal
        var rem: Int
        var hex = ""
        val hexCharConst = charArrayOf(
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F'
        )

        while (value > 0) {
            rem = value % 16
            hex = hexCharConst[rem].toString() + hex
            value /= 16
        }
        return hex
    }

    fun formatHex2Digits(hex: String): String {
        var value = hex
        while (value.length < 2) {
            value = "0$value"
        }
        return value
    }

    fun hexStringToByteArray(source: String?): ByteArray? {
        return if (source.isNullOrEmpty()) {
            null
        } else {
            val length = source.length
            val data = ByteArray(length / 2)
            var i = 0
            while (i < length) {
                data[i / 2] = ((Character.digit(source[i], 16) shl 4)
                        + Character.digit(source[i + 1], 16)).toByte()
                i += 2
            }
            data
        }
    }
}