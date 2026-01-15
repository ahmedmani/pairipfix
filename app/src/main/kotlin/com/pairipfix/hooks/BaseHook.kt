package com.pairipfix.hooks

import de.robv.android.xposed.callbacks.XC_LoadPackage
import com.pairipfix.utils.*

/**
 * Base class for all PairIP bypass hooks
 * Uses Kotlin features: abstract class, lateinit, extension functions
 */
abstract class BaseHook {
    
    protected lateinit var lpparam: XC_LoadPackage.LoadPackageParam
    protected val classLoader: ClassLoader get() = lpparam.classLoader
    
    /**
     * Initialize the hook with package parameters
     */
    fun init(lpparam: XC_LoadPackage.LoadPackageParam): BaseHook {
        this.lpparam = lpparam
        return this
    }
    
    /**
     * Apply all hooks for this bypass module
     * @return true if at least one hook was successful
     */
    abstract fun apply(): Boolean
    
    /**
     * Get the name of this hook module for logging
     */
    abstract val name: String
    
    /**
     * DSL helper for hooking methods
     */
    protected fun hook(
        className: String,
        methodName: String,
        block: MethodHookBuilder.() -> Unit
    ): HookResult = classLoader.hook(className, methodName, block)
    
    /**
     * Batch hook helper
     */
    protected fun hookBatch(block: HookBatch.() -> Unit): HookBatch =
        classLoader.hookBatch(block)
    
    /**
     * Check if a class exists
     */
    protected fun classExists(className: String): Boolean =
        classLoader.hasClass(className)
    
    /**
     * Find class or null
     */
    protected fun findClass(className: String): Class<*>? =
        classLoader.findClassOrNull(className)
    
    /**
     * Set static field value
     */
    protected fun setStaticField(className: String, fieldName: String, value: Any?): Boolean {
        return findClass(className)?.setStaticFieldSafe(fieldName, value) ?: false
    }
    
    /**
     * Get static field value
     */
    protected inline fun <reified T> getStaticField(className: String, fieldName: String): T? {
        return findClass(className)?.getStaticFieldOrNull<T>(fieldName)
    }
    
    /**
     * Log hook application start
     */
    protected fun logApplying() {
        Logger.i { "Applying $name..." }
    }
    
    /**
     * Log hook application result
     */
    protected fun logResult(success: Boolean) {
        if (success) {
            Logger.i { "$name applied successfully" }
        } else {
            Logger.e { "$name failed to apply" }
        }
    }
}

/**
 * Sealed class for hook module types
 */
sealed class HookLayer {
    object Signature : HookLayer()
    object License : HookLayer()
    object VM : HookLayer()
    object Safety : HookLayer()
}
