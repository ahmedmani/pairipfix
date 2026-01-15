package com.pairipfix.hooks

import android.content.Context
import com.pairipfix.utils.PairIPClasses

/**
 * Bypasses PairIP APK signature verification
 * 
 * Target class: com.pairip.SignatureCheck
 * 
 * This is a CRITICAL bypass - without it, the app will crash on startup
 * if the APK signature doesn't match the expected signature.
 * 
 * Methods hooked:
 * - verifyIntegrity(Context): Main signature check, throws SignatureTamperedException if invalid
 * - verifySignatureMatches(String): Compares signature against expected values
 */
class SignatureBypass : BaseHook() {

    override val name = "SignatureBypass"

    override fun apply(): Boolean {
        logApplying()
        
        val batch = hookBatch {
            // Hook 1: verifyIntegrity(Context) - Main entry point
            // This is called from Application.attachBaseContext()
            // It throws SignatureTamperedException if signature is invalid
            hook(PairIPClasses.SIGNATURE_CHECK, "verifyIntegrity") {
                params(Context::class.java)
                doNothing()
            }
            
            // Hook 2: verifySignatureMatches(String) - Returns true if signature matches
            // Called by verifyIntegrity() to check against expected signatures
            hook(PairIPClasses.SIGNATURE_CHECK, "verifySignatureMatches") {
                params(String::class.java)
                returnConstant(true)  // Always return true (signature matches)
            }
        }
        
        return batch.hasAnySuccess.also { logResult(it) }
    }
}
