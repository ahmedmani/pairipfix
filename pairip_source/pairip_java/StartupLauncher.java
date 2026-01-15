package com.pairip;

/* loaded from: /home/ark/Downloads/Kuku TV_5.6.3_antisplit/smali_classes8/com/pairip/StartupLauncher.smali */
public final class StartupLauncher {
    private static boolean launchCalled = false;
    private static String startupProgramName = "aKja6rD08SHDQfuc";

    public static synchronized void launch() {
        if (launchCalled) {
            return;
        }
        launchCalled = true;
        VMRunner.invoke(startupProgramName, null);
    }

    private StartupLauncher() {
    }
}
