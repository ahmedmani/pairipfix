package io.github.ahmedmani.pairipfix.hooks;

import android.os.Bundle;

import io.github.ahmedmani.pairipfix.utils.PairIPClasses;

public class LicenseClientV3Bypass extends BaseHook {
    @Override
    public String getName() {
        return "LicenseClientV3Bypass";
    }

    @Override
    public boolean apply() {
        if (!classExists(PairIPClasses.LICENSE_CLIENT_V3)) {
            return false;
        }
        return hookDoNothing(PairIPClasses.LICENSE_CLIENT_V3, "processResponse", int.class, Bundle.class);
    }
}
