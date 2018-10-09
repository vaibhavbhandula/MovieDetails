package com.vaibhav.moviedetails.commons;

import android.util.Log;

import com.vaibhav.moviedetails.BuildConfig;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class LogUtils {

    private static final boolean isLogEnabled = BuildConfig.DEBUG;

    public static void printLog(String tag, String message) {
        if (isLogEnabled) {
            Log.v(tag, message);
        }
    }

    public static void printStackTrace(Throwable throwable) {
        if (isLogEnabled) {
            throwable.printStackTrace();
        }
    }
}
