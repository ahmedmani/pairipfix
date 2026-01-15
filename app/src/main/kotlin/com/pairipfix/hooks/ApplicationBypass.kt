package com.pairipfix.hooks

import android.content.Context
import com.pairipfix.utils.Logger
import com.pairipfix.utils.PairIPClasses

/**
 * Bypasses PairIP Application class hooks
 * 
 * Target class: com.pairip.application.Application
 * 
 * PairIP extends the app's Application class and overrides attachBaseContext()
 * to perform signature verification before the app starts.
 * 
 * Flow:
 * 1. Application.attachBaseContext(Context) is called by Android
 * 2. PairIP calls VMRunner.setContext(context)
 * 3. PairIP calls SignatureCheck.verifyIntegrity(context) - BLOCKING
 * 4. If signature invalid, throws SignatureTamperedException - APP CRASHES
 * 5. If valid, calls super.attachBaseContext(context)
 * 
 * IMPORTANT: We do NOT need to hook attachBaseContext() because:
 * - SignatureCheck.verifyIntegrity() is already hooked to do nothing
 * - The original attachBaseContext() will run normally and call super
 * - Hooking it would break the super call chain
 * 
 * Instead, we just log when it's called for debugging purposes.
 */
class ApplicationBypass : BaseHook() {

    override val name = "ApplicationBypass"

    override fun apply(): Boolean {
        // Check if the PairIP Application class exists
        if (!classExists(PairIPClasses.APPLICATION)) {
            Logger.d { "PairIP Application class not found, app may use different entry point" }
            return false
        }
        
        logApplying()
        
        // We DON'T replace attachBaseContext - just observe it
        // The SignatureBypass already handles verifyIntegrity()
        val result = hook(PairIPClasses.APPLICATION, "attachBaseContext") {
            params(Context::class.java)
            before { _ ->
                Logger.d { "Application.attachBaseContext() called - SignatureCheck will be bypassed" }
            }
        }
        
        return result.isSuccess.also { logResult(it) }
    }
}
