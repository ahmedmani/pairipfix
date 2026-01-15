package com.pairipfix.hooks

import android.app.Activity
import com.pairipfix.utils.Logger
import com.pairipfix.utils.PairIPClasses
import de.robv.android.xposed.XposedBridge

/**
 * Bypasses PairIP LicenseActivity error/paywall display and app exit
 * 
 * Target class: com.pairip.licensecheck.LicenseActivity
 * 
 * This Activity is displayed when:
 * - License check fails (ERROR_DIALOG)
 * - User needs to purchase/subscribe (PAYWALL)
 * 
 * Both scenarios result in app termination via closeApp() which calls
 * finishAndRemoveTask() and System.exit(0)
 * 
 * Methods hooked:
 * - closeApp(): Prevents app termination (finishAndRemoveTask + System.exit)
 * - onStart(): Prevents activity from showing error/paywall dialogs
 * - showErrorDialog(): Prevents error dialog display
 * - showPaywallAndCloseApp(): Prevents paywall display and app close
 * - logAndShowErrorDialog(String): Prevents error logging and dialog
 * - logAndShowErrorDialog(String, Exception): Prevents error logging and dialog
 */
class LicenseActivityBypass : BaseHook() {

    override val name = "LicenseActivityBypass"

    override fun apply(): Boolean {
        logApplying()
        
        val batch = hookBatch {
            // Hook 1: closeApp() - CRITICAL - Prevents System.exit(0)
            hook(PairIPClasses.LICENSE_ACTIVITY, "closeApp") {
                doNothing()
            }
            
            // Hook 2: onStart() - Prevent activity from processing intents
            // We use after hook to let the super.onStart() be called properly,
            // then immediately finish the activity before it shows dialogs
            hook(PairIPClasses.LICENSE_ACTIVITY, "onStart") {
                after { param ->
                    // The original onStart already called super.onStart()
                    // Now finish the activity before it shows any dialogs
                    val activity = param.thisObject as? Activity
                    if (activity != null) {
                        Logger.d { "LicenseActivity.onStart() - finishing immediately" }
                        activity.finish()
                    }
                }
            }
            
            // Hook 3: showErrorDialog() - Prevent error dialog
            hook(PairIPClasses.LICENSE_ACTIVITY, "showErrorDialog") {
                doNothing()
            }
            
            // Hook 4: showPaywallAndCloseApp() - Prevent paywall
            hook(PairIPClasses.LICENSE_ACTIVITY, "showPaywallAndCloseApp") {
                doNothing()
            }
            
            // Hook 5: logAndShowErrorDialog(String)
            hook(PairIPClasses.LICENSE_ACTIVITY, "logAndShowErrorDialog") {
                params(String::class.java)
                doNothing()
            }
            
            // Hook 6: logAndShowErrorDialog(String, Exception)
            hook(PairIPClasses.LICENSE_ACTIVITY, "logAndShowErrorDialog") {
                params(String::class.java, Exception::class.java)
                doNothing()
            }
        }
        
        return batch.hasAnySuccess.also { logResult(it) }
    }
}
