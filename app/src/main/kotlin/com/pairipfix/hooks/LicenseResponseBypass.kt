package com.pairipfix.hooks

import android.os.Bundle
import com.pairipfix.utils.Logger
import com.pairipfix.utils.PairIPClasses
import java.security.PublicKey

/**
 * Bypasses PairIP license response validation
 * 
 * Target class: com.pairip.licensecheck.LicenseResponseHelper
 * Also targets: com.pairip.licensecheck.ResponseValidator (newer versions)
 * 
 * This class validates the JWS (JSON Web Signature) response from Google Play
 * licensing service, verifying:
 * - Algorithm is RS256
 * - Signature is valid using Google's public key
 * - Package name matches
 * 
 * Methods hooked:
 * - validateResponse(Bundle, String): Main validation entry point
 * - verifySignature(String, String, String, PublicKey): RSA signature verification
 * - getPublicKey(): Returns Google's public key for verification
 * - getRepeatedCheckMetadata(Bundle): Extracts repeated check timing info
 */
class LicenseResponseBypass : BaseHook() {

    override val name = "LicenseResponseBypass"

    override fun apply(): Boolean {
        logApplying()
        
        val batch = hookBatch {
            // ==================== LicenseResponseHelper (Current Version) ====================
            
            // Hook 1: validateResponse(Bundle, String) - Skip all validation
            hook(PairIPClasses.LICENSE_RESPONSE_HELPER, "validateResponse") {
                params(Bundle::class.java, String::class.java)
                doNothing()
            }
            
            // Hook 2: verifySignature - Skip RSA signature verification
            hook(PairIPClasses.LICENSE_RESPONSE_HELPER, "verifySignature") {
                params(
                    String::class.java,     // signedData
                    String::class.java,     // signature
                    String::class.java,     // signatureAlgorithm
                    PublicKey::class.java   // publicKey
                )
                doNothing()
            }
            
            // Hook 3: getPublicKey() - Return null
            hook(PairIPClasses.LICENSE_RESPONSE_HELPER, "getPublicKey") {
                returnConstant(null)
            }
            
            // Hook 4: getRepeatedCheckMetadata - Return null to disable repeated checks
            hook(PairIPClasses.LICENSE_RESPONSE_HELPER, "getRepeatedCheckMetadata") {
                params(Bundle::class.java)
                returnConstant(null)
            }
            
            // ==================== ResponseValidator (Newer Version) ====================
            
            if (classExists(PairIPClasses.RESPONSE_VALIDATOR)) {
                Logger.i { "Found ResponseValidator class (newer PairIP version)" }
                
                hook(PairIPClasses.RESPONSE_VALIDATOR, "validateResponse") {
                    params(Bundle::class.java, String::class.java)
                    doNothing()
                }
                
                hook(PairIPClasses.RESPONSE_VALIDATOR, "verifySignature") {
                    params(
                        String::class.java,
                        String::class.java,
                        String::class.java,
                        PublicKey::class.java
                    )
                    doNothing()
                }
            }
            
            // ==================== ResponseValidator V3 (Older Version) ====================
            
            if (classExists(PairIPClasses.RESPONSE_VALIDATOR_V3)) {
                Logger.i { "Found ResponseValidator V3 class (older PairIP version)" }
                
                hook(PairIPClasses.RESPONSE_VALIDATOR_V3, "validateResponse") {
                    params(Bundle::class.java, String::class.java)
                    doNothing()
                }
            }
        }
        
        return batch.hasAnySuccess.also { logResult(it) }
    }
}
