package com.pairipfix.utils

import de.robv.android.xposed.XposedBridge

/**
 * Centralized logging utility for PairIP bypass module
 * Uses Kotlin features: object singleton, extension functions, inline functions
 */
object Logger {
    const val TAG = "PairIPFix"
    
    @Volatile
    var debugEnabled = true
    
    // Log levels as inline functions for better performance
    inline fun i(message: () -> String) {
        XposedBridge.log("[$TAG] ${message()}")
    }
    
    inline fun d(message: () -> String) {
        if (debugEnabled) {
            XposedBridge.log("[$TAG/DEBUG] ${message()}")
        }
    }
    
    inline fun e(message: () -> String) {
        XposedBridge.log("[$TAG/ERROR] ${message()}")
    }
    
    fun e(message: String, throwable: Throwable) {
        XposedBridge.log("[$TAG/ERROR] $message")
        XposedBridge.log(throwable)
    }
    
    // Convenience overloads for direct string logging
    fun i(message: String) = i { message }
    fun d(message: String) = d { message }
    fun e(message: String) = e { message }
    
    // Hook-specific logging
    fun hookSuccess(className: String, methodName: String) {
        i { "Hooked: $className.$methodName()" }
    }
    
    fun hookFailed(className: String, methodName: String, t: Throwable) {
        e("Failed to hook: $className.$methodName()", t)
    }
    
    fun bypassed(className: String, methodName: String) {
        d { "Bypassed: $className.$methodName()" }
    }
}

/**
 * Extension function to log any object
 */
fun Any?.logDebug(prefix: String = "") {
    Logger.d { "$prefix${this?.toString() ?: "null"}" }
}