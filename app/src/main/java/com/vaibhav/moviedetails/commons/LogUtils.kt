package com.vaibhav.moviedetails.commons

import android.util.Log
import com.vaibhav.moviedetails.BuildConfig

/**
 * @author Vaibhav Bhandula on 08/04/19.
 */
object LogUtils {
    private val isLogEnabled = BuildConfig.DEBUG

    fun printLog(tag: String, message: String) {
        if (isLogEnabled) {
            Log.v(tag, message)
        }
    }

    fun printStackTrace(throwable: Throwable) {
        if (isLogEnabled) {
            throwable.printStackTrace()
        }
    }
}