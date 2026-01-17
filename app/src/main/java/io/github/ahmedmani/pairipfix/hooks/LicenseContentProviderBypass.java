package io.github.ahmedmani.pairipfix.hooks;

import io.github.ahmedmani.pairipfix.utils.PairIPClasses;

import de.robv.android.xposed.XposedHelpers;

public class LicenseContentProviderBypass extends BaseHook {
    @Override
    public String getName() {
        return "LicenseContentProviderBypass";
    }

    @Override
    public boolean apply() {
        return hookReplace(PairIPClasses.LICENSE_CONTENT_PROVIDER, "onCreate", param -> {
            setLicenseStateFullCheckOK();
            return true;
        });
    }

    private void setLicenseStateFullCheckOK() {
        try {
            Class<?> stateClass = findClass(PairIPClasses.LICENSE_CHECK_STATE);
            if (stateClass == null) return;

            Object fullCheckOk = XposedHelpers.getStaticObjectField(stateClass, "FULL_CHECK_OK");
            setStaticField(PairIPClasses.LICENSE_CLIENT, "licenseCheckState", fullCheckOk);
        } catch (Throwable ignored) {
        }
    }
}
