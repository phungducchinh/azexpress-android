package com.lib.utils

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*

object AssetsUtils {
    fun readJsonArrayFromFile(context: Context, path: String): JSONArray? {
        var result: JSONArray? = null
        var inputStream: InputStream? = null
        try {
            val sw = StringWriter()
            inputStream = context.assets.open(path)
            val reader = BufferedReader(InputStreamReader(inputStream, "ISO-8859-1"))
            var c = reader.read()
            while (c >= 0) {
                sw.append(c.toChar())
                c = reader.read()
            }
            inputStream.close()
            inputStream = null
            sw.flush()
            result = JSONArray(sw.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        return result
    }

    fun readJsonFromFile(context: Context, path: String): JSONObject? {
        var result: JSONObject? = null
        var inputStream: InputStream? = null
        try {
            val sw = StringWriter()
            inputStream = context.assets.open(path)
            val reader = BufferedReader(InputStreamReader(inputStream, "ISO-8859-1"))
            var c = reader.read()
            while (c >= 0) {
                sw.append(c.toChar())
                c = reader.read()
            }
            inputStream.close()
            inputStream = null
            sw.flush()
            result = JSONObject(sw.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        return result
    }
}