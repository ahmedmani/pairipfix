package com.pairipfix.hooks

import com.pairipfix.utils.Logger
import com.pairipfix.utils.PairIPClasses
import de.robv.android.xposed.XposedHelpers

/**
 * Bypasses PairIP LicenseContentProvider initialization
 * 
 * Target class: com.pairip.licensecheck.LicenseContentProvider
 * 
 * ContentProvider onCreate() is called BEFORE Application.onCreate(),
 * making it a very early entry point for license checks.
 * 
 * Original flow:
 * 1. onCreate() creates new LicenseClient(context)
 * 2. Calls initializeLicenseCheck() which starts Google Play license check
 * 3. Returns true
 * 
 * Bypass strategy:
 * - Skip the LicenseClient creation and initializeLicenseCheck() call
 * - Set licenseCheckState to FULL_CHECK_OK (so any state checks pass)
 * - Return true (required for ContentProvider to work)
 */
class LicenseContentProviderBypass : BaseHook() {

    override val name = "LicenseContentProviderBypass"

    override fun apply(): Boolean {
        logApplying()
        
        val result = hook(PairIPClasses.LICENSE_CONTENT_PROVIDER, "onCreate") {
            replace {
                Logger.d { "LicenseContentProvider.onCreate() - Bypassing license initialization" }
                
                // Set license state to FULL_CHECK_OK to fool any code that checks the state
                setLicenseStateFullCheckOK()
                
                // Return true - required for ContentProvider to register properly
                true
            }
        }
        
        return result.isSuccess.also { logResult(it) }
    }
    
    /**
     * Set licenseCheckState to FULL_CHECK_OK
     * This ensures any code checking the license state thinks verification passed
     */
    private fun setLicenseStateFullCheckOK() {
        runCatching {
            val stateClass = findClass(PairIPClasses.LICENSE_CHECK_STATE)
            if (stateClass != null) {
                val fullCheckOk = XposedHelpers.getStaticObjectField(stateClass, "FULL_CHECK_OK")
                setStaticField(PairIPClasses.LICENSE_CLIENT, "licenseCheckState", fullCheckOk)
                Logger.d { "licenseCheckState set to FULL_CHECK_OK" }
            } else {
                Logger.d { "LicenseCheckState enum not found, skipping state set" }
            }
        }.onFailure {
            Logger.e("Failed to set licenseCheckState", it)
        }
    }
}
