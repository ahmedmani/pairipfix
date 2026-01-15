package com.pairipfix.utils

/**
 * Constants for PairIP class and method names
 * Uses Kotlin object for singleton constants
 */
object PairIPClasses {
    // ==================== PAIRIP V2 (Current Version) ====================
    
    // Signature Check
    const val SIGNATURE_CHECK = "com.pairip.SignatureCheck"
    
    // Application
    const val APPLICATION = "com.pairip.application.Application"
    
    // License Check
    const val LICENSE_CLIENT = "com.pairip.licensecheck.LicenseClient"
    const val LICENSE_CONTENT_PROVIDER = "com.pairip.licensecheck.LicenseContentProvider"
    const val LICENSE_ACTIVITY = "com.pairip.licensecheck.LicenseActivity"
    const val LICENSE_RESPONSE_HELPER = "com.pairip.licensecheck.LicenseResponseHelper"
    const val LICENSE_CHECK_EXCEPTION = "com.pairip.licensecheck.LicenseCheckException"
    const val LICENSE_CHECK_STATE = "com.pairip.licensecheck.LicenseClient\$LicenseCheckState"
    
    // VM Protection
    const val VM_RUNNER = "com.pairip.VMRunner"
    const val STARTUP_LAUNCHER = "com.pairip.StartupLauncher"
    const val VM_DECRYPTOR = "com.pairip.VmDecryptor"
    
    // ==================== PAIRIP V3 (Older Version) ====================
    
    const val LICENSE_CLIENT_V3 = "com.pairip.licensecheck3.LicenseClientV3"
    const val RESPONSE_VALIDATOR_V3 = "com.pairip.licensecheck3.ResponseValidator"
    
    // ==================== PAIRIP Latest (ResponseValidator) ====================
    
    const val RESPONSE_VALIDATOR = "com.pairip.licensecheck.ResponseValidator"
    
    /**
     * All known PairIP classes for detection
     */
    val allClasses = listOf(
        SIGNATURE_CHECK,
        APPLICATION,
        LICENSE_CLIENT,
        LICENSE_CONTENT_PROVIDER,
        VM_RUNNER,
        STARTUP_LAUNCHER,
        LICENSE_CLIENT_V3,
        RESPONSE_VALIDATOR,
        RESPONSE_VALIDATOR_V3
    )
}

/**
 * License check states enum
 */
object LicenseStates {
    const val CHECK_REQUIRED = "CHECK_REQUIRED"
    const val FULL_CHECK_OK = "FULL_CHECK_OK"
    const val LOCAL_CHECK_OK = "LOCAL_CHECK_OK"
    const val LOCAL_CHECK_REPORTED = "LOCAL_CHECK_REPORTED"
    const val REPEATED_CHECK_REQUIRED = "REPEATED_CHECK_REQUIRED"
}

/**
 * License response codes
 */
object ResponseCodes {
    const val LICENSED = 0
    const val NOT_LICENSED = 2
    const val ERROR_INVALID_PACKAGE_NAME = 3
}
