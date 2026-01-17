package io.github.ahmedmani.pairipfix.hooks;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class SystemExitBypass extends BaseHook {
    @Override
    public String getName() {
        return "SystemExitBypass";
    }

    @Override
    public boolean apply() {
        boolean systemExit = hookExit(System.class);
        boolean runtimeExit = hookExit(Runtime.class);
        return systemExit || runtimeExit;
    }

    private boolean hookExit(Class<?> clazz) {
        try {
            XposedHelpers.findAndHookMethod(clazz, "exit", int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    param.setResult(null);
                }
            });
            return true;
        } catch (Throwable t) {
            return false;
        }
    }
}
