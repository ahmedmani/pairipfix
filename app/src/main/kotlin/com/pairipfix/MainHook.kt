package com.pairipfix

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import com.pairipfix.hooks.*
import com.pairipfix.utils.Logger
import com.pairipfix.utils.PairIPClasses
import com.pairipfix.utils.hasClass
import com.pairipfix.BuildConfig

/**
 * Main entry point for PairIP bypass Xposed module
 * 
 * This module bypasses Google's PairIP (Play Integrity API) protection
 * which consists of three protection layers:
 * 
 * Layer 1: Signature Verification (CRITICAL)
 *   - SignatureCheck.verifyIntegrity() - Blocks modified APKs
 * 
 * Layer 2: License Verification (CRITICAL)
 *   - LicenseContentProvider.onCreate() - Auto-init on app start
 *   - LicenseClient - Google Play license check
 *   - LicenseActivity - Error/Paywall display
 *   - LicenseResponseHelper - JWS response validation
 * 
 * Layer 3: VM Protection (Optional)
 *   - StartupLauncher.launch() - One-time VM execution
 *   - VMRunner - Native VM bytecode execution
 * 
 * Safety Net:
 *   - System.exit() blocking - Prevents forced app termination
 * 
 * Uses Kotlin features:
 * - Object declarations for singleton pattern
 * - Sealed classes for hook results
 * - Extension functions for cleaner API
 * - DSL builder for hook configuration
 * - Inline functions for performance
 * - Lambda expressions throughout
 */
class MainHook : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        // Check if this package has PairIP protection
        if (!hasPairIPProtection(lpparam)) {
            return
        }
        
        printBanner(lpparam.packageName)
        
        // Initialize and apply all bypass hooks
        val hooks = createHooks()
        val results = hooks.map { hook ->
            runCatching {
                hook.init(lpparam).apply()
            }.getOrElse { t ->
                Logger.e("Error applying ${hook.name}", t)
                false
            }
        }
        
        val successCount = results.count { it }
        val failCount = results.size - successCount
        
        printSummary(successCount, failCount)
    }
    
    /**
     * Check if the target package has PairIP protection
     * Uses Kotlin's any() for clean iteration
     */
    private fun hasPairIPProtection(lpparam: XC_LoadPackage.LoadPackageParam): Boolean {
        return PairIPClasses.allClasses.any { className ->
            val exists = lpparam.classLoader.hasClass(className)
            if (exists) {
                Logger.i { "PairIP protection detected in: ${lpparam.packageName}" }
                Logger.d { "Found class: $className" }
            }
            exists
        }
    }
    
    /**
     * Create all bypass hook instances
     * Order matters - hooks are applied in this order
     * Uses Kotlin's listOf for immutable list
     */
    private fun createHooks(): List<BaseHook> = listOf(
        // ==================== Layer 1: Signature Verification ====================
        // Must be first - blocks app if signature is invalid
        SignatureBypass(),
        ApplicationBypass(),
        
        // ==================== Layer 2: License Verification ====================
        // Core license check bypasses
        LicenseContentProviderBypass(),
        LicenseClientBypass(),
        LicenseActivityBypass(),
        LicenseResponseBypass(),
        
        // V3 compatibility (older PairIP versions)
        LicenseClientV3Bypass(),
        
        // ==================== Layer 3: VM Protection ====================
        // Native bytecode VM protection
        VMRunnerBypass(),
        
        // ==================== Safety Net ====================
        // Block System.exit() as last resort
        SystemExitBypass()
    )
    
    private fun printBanner(packageName: String) {
        Logger.i { "========================================" }
        Logger.i { "PairIPFix v${BuildConfig.VERSION_NAME}" }
        Logger.i { "Target package: $packageName" }
        Logger.i { "========================================" }
    }
    
    private fun printSummary(successCount: Int, failCount: Int) {
        Logger.i { "========================================" }
        Logger.i { "Bypass complete: $successCount succeeded, $failCount failed" }
        Logger.i { "========================================" }
    }
}
