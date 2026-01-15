package com.pairip.application;

import android.content.Context;
import android.content.pm.PackageManager;
import com.pairip.SignatureCheck;
import com.pairip.VMRunner;
import com.vlv.aravali.KukuFMApplication;

/* loaded from: /home/ark/Downloads/Kuku TV_5.6.3_antisplit/smali_classes8/com/pairip/application/Application.smali */
public class Application extends KukuFMApplication {
    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context context) throws PackageManager.NameNotFoundException {
        VMRunner.setContext(context);
        SignatureCheck.verifyIntegrity(context);
        super.attachBaseContext(context);
    }
}
