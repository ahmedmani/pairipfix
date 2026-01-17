package io.github.ahmedmani.pairipfix.utils;

import de.robv.android.xposed.XposedBridge;

public final class Logger {
    private static final String TAG = "PairIPFix";

    private Logger() {}

    public static void i(String message) {
        XposedBridge.log("[" + TAG + "] " + message);
    }

    public static void e(String message) {
        XposedBridge.log("[" + TAG + "] " + message);
    }

    public static void e(String message, Throwable t) {
        XposedBridge.log("[" + TAG + "] " + message);
        if (t != null) {
            XposedBridge.log(t);
        }
    }
}
