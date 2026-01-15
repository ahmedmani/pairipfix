package com.pairipfix.hooks

import android.os.Bundle
import com.pairipfix.utils.Logger
import com.pairipfix.utils.PairIPClasses

/**
 * Bypasses PairIP V3 (older version) license verification
 * 
 * Target class: com.pairip.licensecheck3.LicenseClientV3
 * 
 * This is the older version of PairIP's license client. Some apps
 * may still use this version, so we need to support it for compatibility.
 * 
 * Methods hooked:
 * - processResponse(int, Bundle): Process license response
 */
class LicenseClientV3Bypass : BaseHook() {

    override val name = "LicenseClientV3Bypass"

    override fun apply(): Boolean {
        // Only apply if the V3 class exists
        if (!classExists(PairIPClasses.LICENSE_CLIENT_V3)) {
            Logger.d { "LicenseClientV3 not found, skipping V3 bypass" }
            return false
        }
        
        Logger.i { "Found LicenseClientV3 (older PairIP version)" }
        logApplying()
        
        // Skip processResponse entirely - just modifying args still runs the method
        // which would call validateResponse() and throw exceptions
        val result = hook(PairIPClasses.LICENSE_CLIENT_V3, "processResponse") {
            params(Int::class.javaPrimitiveType!!, Bundle::class.java)
            doNothing()
        }
        
        return result.isSuccess.also { logResult(it) }
    }
}
