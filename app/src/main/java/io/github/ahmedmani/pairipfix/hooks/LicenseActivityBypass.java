package io.github.ahmedmani.pairipfix.hooks;


import io.github.ahmedmani.pairipfix.BaseHook;

public class LicenseActivityBypass extends BaseHook {
    String LICENSE_ACTIVITY = "com.pairip.licensecheck.LicenseActivity";
    @Override
    public String getName() {
        return "LicenseActivityBypass";
    }

    @Override
    public boolean apply() {
        boolean success = false;

        success |= hookDoNothing(LICENSE_ACTIVITY, "closeApp");
        success |= hookDoNothing(LICENSE_ACTIVITY, "exitApp");
        success |= hookDoNothing(LICENSE_ACTIVITY, "showErrorDialog");
        success |= hookDoNothing(LICENSE_ACTIVITY, "showPaywallAndCloseApp");
        success |= hookDoNothing(LICENSE_ACTIVITY, "logAndShowErrorDialog", String.class);
        success |= hookDoNothing(LICENSE_ACTIVITY, "logAndShowErrorDialog", String.class, Exception.class);

        return success;
    }
}