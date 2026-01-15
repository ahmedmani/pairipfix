package com.pairipfix.hooks

import com.pairipfix.utils.Logger
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

/**
 * Bypasses System.exit() calls to prevent app termination
 * 
 * Target class: java.lang.System
 * 
 * PairIP calls System.exit(0) in several places:
 * - LicenseActivity.closeApp()
 * - LicenseClient.exitAction (static Runnable)
 * - Background exit when app not in foreground
 * 
 * This is a safety net to catch any exit calls that slip through
 * other bypasses.
 * 
 * Methods hooked:
 * - System.exit(int): Blocks all exit calls
 * - Runtime.exit(int): Alternative exit method
 */
class SystemExitBypass : BaseHook() {

    override val name = "SystemExitBypass"

    override fun apply(): Boolean {
        logApplying()
        
        var success = false
        
        // Hook 1: System.exit(int)
        success = hookSystemExit() || success
        
        // Hook 2: Runtime.exit(int)
        success = hookRuntimeExit() || success
        
        logResult(success)
        return success
    }
    
    /**
     * Hook System.exit(int) to prevent app termination
     */
    private fun hookSystemExit(): Boolean = runCatching {
        XposedHelpers.findAndHookMethod(
            System::class.java,
            "exit",
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val exitCode = param.args[0] as Int
                    Logger.i { "System.exit($exitCode) blocked!" }
                    
                    // Log stack trace to identify PairIP callers
                    if (exitCode == 0) {
                        Logger.d { "Exit call stack trace:" }
                        Thread.currentThread().stackTrace
                            .filter { it.className.contains("pairip") }
                            .forEach { Logger.d { "  $it" } }
                    }
                    
                    // Prevent exit
                    param.result = null
                }
            }
        )
        Logger.hookSuccess("java.lang.System", "exit")
        true
    }.getOrElse {
        Logger.hookFailed("java.lang.System", "exit", it)
        false
    }
    
    /**
     * Hook Runtime.exit(int) to prevent app termination
     */
    private fun hookRuntimeExit(): Boolean = runCatching {
        XposedHelpers.findAndHookMethod(
            Runtime::class.java,
            "exit",
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val exitCode = param.args[0] as Int
                    Logger.i { "Runtime.exit($exitCode) blocked!" }
                    param.result = null
                }
            }
        )
        Logger.hookSuccess("java.lang.Runtime", "exit")
        true
    }.getOrElse {
        Logger.hookFailed("java.lang.Runtime", "exit", it)
        false
    }
}
