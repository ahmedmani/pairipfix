package com.pairipfix.utils

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Kotlin extension functions for Xposed API
 * Provides DSL-like syntax for hooking methods
 */

/**
 * Result type for hook operations
 */
sealed class HookResult {
    data class Success(val className: String, val methodName: String) : HookResult()
    data class Failure(val className: String, val methodName: String, val error: Throwable) : HookResult()
    
    val isSuccess get() = this is Success
    val isFailure get() = this is Failure
}

/**
 * Extension to safely find a class
 */
fun ClassLoader.findClassOrNull(className: String): Class<*>? = try {
    XposedHelpers.findClass(className, this)
} catch (e: Throwable) {
    null
}

/**
 * Extension to check if a class exists
 */
fun ClassLoader.hasClass(className: String): Boolean = 
    findClassOrNull(className) != null

/**
 * Extension to get static field value safely
 */
inline fun <reified T> Class<*>.getStaticFieldOrNull(fieldName: String): T? = try {
    XposedHelpers.getStaticObjectField(this, fieldName) as? T
} catch (e: Throwable) {
    null
}

/**
 * Extension to set static field value safely
 */
fun Class<*>.setStaticFieldSafe(fieldName: String, value: Any?): Boolean = try {
    XposedHelpers.setStaticObjectField(this, fieldName, value)
    true
} catch (e: Throwable) {
    Logger.e("Failed to set field $fieldName", e)
    false
}

/**
 * DSL Builder for method hooks
 */
class MethodHookBuilder(
    private val classLoader: ClassLoader,
    private val className: String,
    private val methodName: String
) {
    private var parameterTypes: Array<out Any> = emptyArray()
    private var beforeHook: ((XC_MethodHook.MethodHookParam) -> Unit)? = null
    private var afterHook: ((XC_MethodHook.MethodHookParam) -> Unit)? = null
    private var replaceWith: ((XC_MethodHook.MethodHookParam) -> Any?)? = null
    private var returnValue: Any? = null
    private var doNothing: Boolean = false
    
    fun params(vararg types: Any) {
        parameterTypes = types
    }
    
    fun before(block: (XC_MethodHook.MethodHookParam) -> Unit) {
        beforeHook = block
    }
    
    fun after(block: (XC_MethodHook.MethodHookParam) -> Unit) {
        afterHook = block
    }
    
    fun replace(block: (XC_MethodHook.MethodHookParam) -> Any?) {
        replaceWith = block
    }
    
    fun returnConstant(value: Any?) {
        returnValue = value
        replaceWith = { value }
    }
    
    fun doNothing() {
        doNothing = true
    }
    
    fun build(): HookResult {
        return try {
            val callback: XC_MethodHook = when {
                doNothing -> XC_MethodReplacement.DO_NOTHING
                
                replaceWith != null -> object : XC_MethodReplacement() {
                    override fun replaceHookedMethod(param: MethodHookParam): Any? {
                        Logger.bypassed(className, methodName)
                        return replaceWith?.invoke(param)
                    }
                }
                
                else -> object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        beforeHook?.invoke(param)
                    }
                    
                    override fun afterHookedMethod(param: MethodHookParam) {
                        afterHook?.invoke(param)
                    }
                }
            }
            
            val args = parameterTypes.toMutableList().apply { add(callback) }.toTypedArray()
            XposedHelpers.findAndHookMethod(className, classLoader, methodName, *args)
            
            Logger.hookSuccess(className, methodName)
            HookResult.Success(className, methodName)
        } catch (t: Throwable) {
            Logger.hookFailed(className, methodName, t)
            HookResult.Failure(className, methodName, t)
        }
    }
}

/**
 * DSL entry point for hooking methods
 */
fun ClassLoader.hook(
    className: String,
    methodName: String,
    block: MethodHookBuilder.() -> Unit
): HookResult {
    return MethodHookBuilder(this, className, methodName).apply(block).build()
}

/**
 * Convenience extension for XC_LoadPackage.LoadPackageParam
 */
fun XC_LoadPackage.LoadPackageParam.hook(
    className: String,
    methodName: String,
    block: MethodHookBuilder.() -> Unit
): HookResult = classLoader.hook(className, methodName, block)

/**
 * Batch hook multiple methods and collect results
 */
class HookBatch(private val classLoader: ClassLoader) {
    private val results = mutableListOf<HookResult>()
    
    fun hook(className: String, methodName: String, block: MethodHookBuilder.() -> Unit) {
        results.add(classLoader.hook(className, methodName, block))
    }
    
    val successCount get() = results.count { it.isSuccess }
    val failureCount get() = results.count { it.isFailure }
    val hasAnySuccess get() = results.any { it.isSuccess }
    val allSucceeded get() = results.all { it.isSuccess }
}

/**
 * DSL for batch hooking
 */
fun ClassLoader.hookBatch(block: HookBatch.() -> Unit): HookBatch {
    return HookBatch(this).apply(block)
}

fun XC_LoadPackage.LoadPackageParam.hookBatch(block: HookBatch.() -> Unit): HookBatch {
    return classLoader.hookBatch(block)
}
