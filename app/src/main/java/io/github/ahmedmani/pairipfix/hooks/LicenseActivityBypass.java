package io.github.ahmedmani.pairipfix.hooks;

import android.app.Activity;

import io.github.ahmedmani.pairipfix.utils.PairIPClasses;

public class LicenseActivityBypass extends BaseHook {
    @Override
    public String getName() {
        return "LicenseActivityBypass";
    }

    @Override
    public boolean apply() {
        boolean success = false;

        success |= hookDoNothing(PairIPClasses.LICENSE_ACTIVITY, "closeApp");

        success |= hookAfter(PairIPClasses.LICENSE_ACTIVITY, "onStart", param -> {
            Object thisObject = param.thisObject;
            if (thisObject instanceof Activity) {
                ((Activity) thisObject).finish();
            }
        });

        success |= hookDoNothing(PairIPClasses.LICENSE_ACTIVITY, "showErrorDialog");
        success |= hookDoNothing(PairIPClasses.LICENSE_ACTIVITY, "showPaywallAndCloseApp");
        success |= hookDoNothing(PairIPClasses.LICENSE_ACTIVITY, "logAndShowErrorDialog", String.class);
        success |= hookDoNothing(PairIPClasses.LICENSE_ACTIVITY, "logAndShowErrorDialog", String.class, Exception.class);

        return success;
    }
}
