package io.github.ahmedmani.pairipfix.hooks;

import io.github.ahmedmani.pairipfix.utils.PairIPClasses;

public class LicenseClientBypass extends BaseHook {
    @Override
    public String getName() {
        return "LicenseClientBypass";
    }

    @Override
    public boolean apply() {
        boolean hook1 = hookDoNothing(PairIPClasses.LICENSE_CLIENT, "initializeLicenseCheck");
        boolean hook2 = hookReturnConstant(PairIPClasses.LICENSE_CLIENT, "performLocalInstallerCheck", true);
        return hook1 || hook2;
    }
}
