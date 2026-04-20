package io.github.ahmedmani.pairipfix.hooks;

import android.os.Bundle;

import de.robv.android.xposed.XposedHelpers;
import io.github.ahmedmani.pairipfix.BaseHook;

public class LicenseClientBypass extends BaseHook {
    String LICENSE_CLIENT = "com.pairip.licensecheck.LicenseClient";
    String LICENSE_CLIENT_V3 = "com.pairip.licensecheck3.LicenseClientV3";
    String LICENSE_CHECK_STATE = "com.pairip.licensecheck.LicenseClient$LicenseCheckState";
    @Override
    public String getName() {
        return "LicenseClientBypass";
    }

    @Override
    public boolean apply() {
        boolean status = false;
        status |= hookDoNothing(LICENSE_CLIENT, "initializeLicenseCheck");
        status |= hookReturnConstant(LICENSE_CLIENT, "performLocalInstallerCheck", true);

        Class<?> stateClass = findClass(LICENSE_CHECK_STATE);
        if (stateClass != null){
            Object fullCheckOk = XposedHelpers.getStaticObjectField(stateClass, "FULL_CHECK_OK");
            status |= setStaticField(LICENSE_CLIENT, "licenseCheckState", fullCheckOk);
        }

        if (classExists(LICENSE_CLIENT_V3)) {
            status |= hookDoNothing(LICENSE_CLIENT_V3, "processResponse", int.class, Bundle.class);
        }

        return status;
    }
}