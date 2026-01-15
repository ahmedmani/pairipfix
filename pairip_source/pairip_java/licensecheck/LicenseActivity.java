package com.pairip.licensecheck;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;

/* loaded from: /home/ark/Downloads/Kuku TV_5.6.3_antisplit/smali_classes2/com/pairip/licensecheck/LicenseActivity.smali */
public class LicenseActivity extends Activity {
    public static final String ACTIVITY_TYPE_ARG_NAME = "activitytype";
    public static final String PAYWALL_INTENT_ARG_NAME = "paywallintent";
    private static final String TAG = "LicenseActivity";

    /* loaded from: /home/ark/Downloads/Kuku TV_5.6.3_antisplit/smali_classes2/com/pairip/licensecheck/LicenseActivity$ActivityType.smali */
    public enum ActivityType {
        PAYWALL,
        ERROR_DIALOG
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        try {
            int iOrdinal = ((ActivityType) getIntent().getSerializableExtra(ACTIVITY_TYPE_ARG_NAME)).ordinal();
            if (iOrdinal == 0) {
                showPaywallAndCloseApp();
            } else {
                if (iOrdinal != 1) {
                    return;
                }
                showErrorDialog();
            }
        } catch (Exception e10) {
            logAndShowErrorDialog("Couldn't process license activity correctly.", e10);
        }
    }

    private void showPaywallAndCloseApp() {
        final PendingIntent pendingIntent = (PendingIntent) getIntent().getParcelableExtra(PAYWALL_INTENT_ARG_NAME);
        if (pendingIntent == null) {
            logAndShowErrorDialog("Paywall intent is not provided.");
        } else {
            runOnUiThread(new Runnable() { // from class: com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws PendingIntent.CanceledException {
                    this.f$0.lambda$showPaywallAndCloseApp$0(pendingIntent);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPaywallAndCloseApp$0(PendingIntent pendingIntent) throws PendingIntent.CanceledException {
        try {
            if (Build.VERSION.SDK_INT >= 34) {
                pendingIntent.send(ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
            } else {
                pendingIntent.send();
            }
            closeApp();
        } catch (PendingIntent.CanceledException e10) {
            logAndShowErrorDialog("Paywall intent unexpectedly cancelled.", e10);
        }
    }

    private void logAndShowErrorDialog(String message, Exception ex) {
        logAndShowErrorDialog(message + " " + Log.getStackTraceString(ex));
    }

    private void logAndShowErrorDialog(String message) {
        Log.e(TAG, message);
        showErrorDialog();
    }

    private void showErrorDialog() {
        runOnUiThread(new Runnable() { // from class: com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showErrorDialog$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showErrorDialog$0() {
        try {
            new AlertDialog.Builder(this).setTitle("Something went wrong").setMessage("Check that Google Play is enabled on your device and that you're using an up-to-date version before opening the app. If the problem persists try reinstalling the app.").setPositiveButton("Close", new DialogInterface.OnClickListener() { // from class: com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i7) {
                    this.f$0.lambda$showErrorDialog$1(dialogInterface, i7);
                }
            }).setCancelable(false).show();
        } catch (RuntimeException e10) {
            Log.d(TAG, "Couldn't show the error dialog. " + Log.getStackTraceString(e10));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showErrorDialog$1(DialogInterface dialogInterface, int i7) {
        closeApp();
    }

    protected void closeApp() {
        finishAndRemoveTask();
        System.exit(0);
    }
}
