package io.github.ahmedmani.pairipfix.utils;

import java.util.Arrays;
import java.util.List;

public final class PairIPClasses {
    private PairIPClasses() {}

    // Signature
    public static final String SIGNATURE_CHECK = "com.pairip.SignatureCheck";

    // License (V2)
    public static final String LICENSE_CLIENT = "com.pairip.licensecheck.LicenseClient";
    public static final String LICENSE_CONTENT_PROVIDER = "com.pairip.licensecheck.LicenseContentProvider";
    public static final String LICENSE_ACTIVITY = "com.pairip.licensecheck.LicenseActivity";
    public static final String LICENSE_RESPONSE_HELPER = "com.pairip.licensecheck.LicenseResponseHelper";
    public static final String LICENSE_CHECK_STATE = "com.pairip.licensecheck.LicenseClient$LicenseCheckState";

    // License (V3)
    public static final String LICENSE_CLIENT_V3 = "com.pairip.licensecheck3.LicenseClientV3";
    public static final String RESPONSE_VALIDATOR_V3 = "com.pairip.licensecheck3.ResponseValidator";

    // License (Latest)
    public static final String RESPONSE_VALIDATOR = "com.pairip.licensecheck.ResponseValidator";

    // VM
    public static final String STARTUP_LAUNCHER = "com.pairip.StartupLauncher";

    public static final List<String> ALL_CLASSES = Arrays.asList(
            SIGNATURE_CHECK,
            LICENSE_CLIENT,
            LICENSE_CONTENT_PROVIDER,
            STARTUP_LAUNCHER,
            LICENSE_CLIENT_V3,
            RESPONSE_VALIDATOR,
            RESPONSE_VALIDATOR_V3
    );
}
