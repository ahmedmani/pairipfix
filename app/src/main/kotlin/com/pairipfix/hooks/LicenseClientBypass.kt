package com.pairipfix.hooks

import android.app.PendingIntent
import android.os.Bundle
import android.os.IBinder
import com.pairipfix.utils.PairIPClasses

/**
 * Bypasses PairIP LicenseClient license verification
 * 
 * Target class: com.pairip.licensecheck.LicenseClient
 * 
 * This class handles the core license verification logic including:
 * - Connecting to Google Play licensing service
 * - Processing license responses
 * - Handling errors and showing dialogs
 * - Retrying failed checks
 * 
 * Methods hooked:
 * - initializeLicenseCheck(): Main entry point for license verification
 * - handleError(LicenseCheckException): Called when license check fails
 * - connectToLicensingService(): Binds to Google Play service
 * - checkLicenseInternal(IBinder): Performs actual license check
 * - processResponse(int, Bundle): Processes license response from Google
 * - performLocalInstallerCheck(): Checks if installed from Play Store (SDK 30+)
 * - startErrorDialogActivity(): Shows error dialog
 * - startPaywallActivity(PendingIntent): Shows paywall
 * - isForeground(): Checks if app is in foreground
 * - retryOrThrow(LicenseCheckException): Handles retry logic
 * - retryOrThrow(LicenseCheckException, boolean): Handles retry with ignore flag
 */
class LicenseClientBypass : BaseHook() {

    override val name = "LicenseClientBypass"

    override fun apply(): Boolean {
        logApplying()
        
        val exceptionClass = findClass(PairIPClasses.LICENSE_CHECK_EXCEPTION)
        
        val batch = hookBatch {
            // Hook 1: initializeLicenseCheck() - Skip entire license check
            hook(PairIPClasses.LICENSE_CLIENT, "initializeLicenseCheck") {
                doNothing()
            }
            
            // Hook 2: handleError(LicenseCheckException) - Prevent error handling
            if (exceptionClass != null) {
                hook(PairIPClasses.LICENSE_CLIENT, "handleError") {
                    params(exceptionClass)
                    doNothing()
                }
            }
            
            // Hook 3: connectToLicensingService() - Skip service connection
            hook(PairIPClasses.LICENSE_CLIENT, "connectToLicensingService") {
                doNothing()
            }
            
            // Hook 4: checkLicenseInternal(IBinder) - Skip license check
            hook(PairIPClasses.LICENSE_CLIENT, "checkLicenseInternal") {
                params(IBinder::class.java)
                doNothing()
            }
            
            // Hook 5: processResponse(int, Bundle) - Skip entirely
            // CRITICAL: We can't just change responseCode to 0 because
            // the method would still call LicenseResponseHelper.validateResponse()
            // which throws LicenseCheckException with invalid Bundle data
            hook(PairIPClasses.LICENSE_CLIENT, "processResponse") {
                params(Int::class.javaPrimitiveType!!, Bundle::class.java)
                doNothing()
            }
            
            // Hook 6: performLocalInstallerCheck() - Always return true
            hook(PairIPClasses.LICENSE_CLIENT, "performLocalInstallerCheck") {
                returnConstant(true)
            }
            
            // Hook 7: startErrorDialogActivity() - Prevent error dialog
            hook(PairIPClasses.LICENSE_CLIENT, "startErrorDialogActivity") {
                doNothing()
            }
            
            // Hook 8: startPaywallActivity(PendingIntent) - Prevent paywall
            hook(PairIPClasses.LICENSE_CLIENT, "startPaywallActivity") {
                params(PendingIntent::class.java)
                doNothing()
            }
            
            // Hook 9: isForeground() - Always return true
            hook(PairIPClasses.LICENSE_CLIENT, "isForeground") {
                returnConstant(true)
            }
            
            // Hook 10 & 11: retryOrThrow overloads
            if (exceptionClass != null) {
                hook(PairIPClasses.LICENSE_CLIENT, "retryOrThrow") {
                    params(exceptionClass)
                    doNothing()
                }
                
                hook(PairIPClasses.LICENSE_CLIENT, "retryOrThrow") {
                    params(exceptionClass, Boolean::class.javaPrimitiveType!!)
                    doNothing()
                }
            }
        }
        
        // Note: License state is set by LicenseContentProviderBypass which runs first
        // (ContentProvider.onCreate runs before Application.onCreate)
        // We don't duplicate it here to avoid race conditions
        
        return batch.hasAnySuccess.also { logResult(it) }
    }
}
