package io.github.ahmedmani.pairipfix.hooks;

import io.github.ahmedmani.pairipfix.utils.PairIPClasses;

public class VMRunnerBypass extends BaseHook {
    @Override
    public String getName() {
        return "VMRunnerBypass";
    }

    @Override
    public boolean apply() {
        boolean result = hookDoNothing(PairIPClasses.STARTUP_LAUNCHER, "launch");
        setStaticField(PairIPClasses.STARTUP_LAUNCHER, "launchCalled", true);
        return result;
    }
}
