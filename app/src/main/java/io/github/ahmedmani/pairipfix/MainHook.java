package io.github.ahmedmani.pairipfix;
import io.github.ahmedmani.pairipfix.hooks.BaseHook;
import io.github.ahmedmani.pairipfix.hooks.LicenseActivityBypass;
import io.github.ahmedmani.pairipfix.hooks.LicenseClientBypass;
import io.github.ahmedmani.pairipfix.hooks.LicenseClientV3Bypass;
import io.github.ahmedmani.pairipfix.hooks.LicenseContentProviderBypass;
import io.github.ahmedmani.pairipfix.hooks.LicenseResponseBypass;
import io.github.ahmedmani.pairipfix.hooks.SignatureBypass;
import io.github.ahmedmani.pairipfix.hooks.SystemExitBypass;
import io.github.ahmedmani.pairipfix.hooks.VMRunnerBypass;
import io.github.ahmedmani.pairipfix.utils.Logger;
import io.github.ahmedmani.pairipfix.utils.PairIPClasses;
import io.github.ahmedmani.pairipfix.utils.XposedUtils;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.util.Arrays;
import java.util.List;

public class MainHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        // Early debug logging - helps diagnose LSPosed loading issues
        XposedBridge.log("[PairIPFix] Module loaded for package: " + lpparam.packageName);

        if (!hasPairIPProtection(lpparam)) {
            return;
        }

        Logger.i("PairIPFix - " + lpparam.packageName);

        List<BaseHook> hooks = Arrays.asList(
                new SignatureBypass(),
                new LicenseContentProviderBypass(),
                new LicenseClientBypass(),
                new LicenseActivityBypass(),
                new LicenseResponseBypass(),
                new LicenseClientV3Bypass(),
                new VMRunnerBypass(),
                new SystemExitBypass()
        );

        int successCount = 0;
        for (BaseHook hook : hooks) {
            try {
                boolean success = hook.init(lpparam).apply();
                if (success) {
                    successCount++;
                    Logger.i(hook.getName() + " - applied successfully");
                }
            } catch (Throwable t) {
                Logger.e(hook.getName() + " error", t);
            }
        }

        Logger.i("Done: " + successCount + "/" + hooks.size() + " hooks applied");
    }

    private boolean hasPairIPProtection(XC_LoadPackage.LoadPackageParam lpparam) {
        for (String className : PairIPClasses.ALL_CLASSES) {
            if (XposedUtils.hasClass(lpparam.classLoader, className)) {
                Logger.i("Found PairIP class: " + className);
                return true;
            }
        }
        return false;
    }
}