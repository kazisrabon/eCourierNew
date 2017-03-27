package com.ps.ecourier.utils;

/**
 * Created by Prokash Sarkar on 1/21/2017.
 */

/**
 * Created by Prokash Sarkar on 12/11/2016.
 */

public class LogUtils {
    static final boolean log = false;

    public static void i(String tag, String string) {
        if (log) android.util.Log.i(tag, string);
    }

    public static void e(String tag, String string) {
        if (log) android.util.Log.e(tag, string);
    }

    public static void d(String tag, String string) {
        if (log) android.util.Log.d(tag, string);
    }

    public static void v(String tag, String string) {
        if (log) android.util.Log.v(tag, string);
    }

    public static void w(String tag, String string) {
        if (log) android.util.Log.w(tag, string);
    }
}