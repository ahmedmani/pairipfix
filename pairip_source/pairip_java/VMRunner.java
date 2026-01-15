package com.pairip;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: /home/ark/Downloads/Kuku TV_5.6.3_antisplit/smali_classes8/com/pairip/VMRunner.smali */
public class VMRunner {
    private static final int PACKAGE_MANAGER_TRIES = 5;
    private static final String TAG = "VMRunner";
    private static String apkPath = null;
    private static Context context = null;
    private static String loggingEnabled = "false";
    private static int versionCode = 5060300;

    public static native Object executeVM(byte[] vmCode, Object[] args);

    static {
        System.loadLibrary("pairipcore");
    }

    /* loaded from: /home/ark/Downloads/Kuku TV_5.6.3_antisplit/smali_classes8/com/pairip/VMRunner$VMRunnerException.smali */
    private static class VMRunnerException extends RuntimeException {
        public VMRunnerException(String message) {
            super(message);
        }

        public VMRunnerException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static void setContext(Context context2) {
        context = context2;
    }

    public static Context getContext() {
        return context;
    }

    public static Object invoke(String vmByteCodeFile, Object[] args) {
        if (context == null) {
            setContext(InitContextProvider.tryToCreateContextForBindingApp());
        }
        if (isDebuggingEnabled()) {
            Log.i(TAG, "Executing " + vmByteCodeFile);
        }
        try {
            byte[] vmByteCode = getVmByteCode(vmByteCodeFile);
            long jCurrentTimeMillis = System.currentTimeMillis();
            Object objExecuteVM = executeVM(vmByteCode, args);
            if (isDebuggingEnabled()) {
                Log.i(TAG, String.format("Finished executing %s after %d ms.", vmByteCodeFile, Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis)));
            }
            return objExecuteVM;
        } catch (IOException e10) {
            throw new VMRunnerException("Error while loading bytecode.", e10);
        }
    }

    public static Runnable createInvokeRunnable(final String vmByteCodeFile, final Object[] args) {
        return new Runnable() { // from class: com.pairip.VMRunner.1
            @Override // java.lang.Runnable
            public void run() {
                VMRunner.invoke(vmByteCodeFile, args);
            }
        };
    }

    private static byte[] getVmByteCode(String vmByteCodeFile) throws IOException {
        return new VmDecryptor().decrypt(readByteCode(vmByteCodeFile), versionCode, isDebuggingEnabled());
    }

    private static byte[] readByteCode(String vmByteCodeFile) throws IOException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ZipFile zipFileOpenBaseApk = openBaseApk();
        try {
            ZipEntry entry = zipFileOpenBaseApk.getEntry("assets/" + vmByteCodeFile);
            if (entry == null) {
                throw new IOException(vmByteCodeFile + " not found.");
            }
            InputStream inputStream = zipFileOpenBaseApk.getInputStream(entry);
            try {
                int size = (int) entry.getSize();
                byte[] bArr = new byte[size];
                readFullByteArrayFromStream(inputStream, bArr);
                if (isDebuggingEnabled()) {
                    Log.i(TAG, String.format("Finished loading %s (%d kB) after %d ms.", vmByteCodeFile, Integer.valueOf(size / 1024), Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis)));
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (zipFileOpenBaseApk != null) {
                    zipFileOpenBaseApk.close();
                }
                return bArr;
            } finally {
            }
        } catch (Throwable th2) {
            if (zipFileOpenBaseApk != null) {
                try {
                    zipFileOpenBaseApk.close();
                } catch (Throwable th3) {
                    th2.addSuppressed(th3);
                }
            }
            throw th2;
        }
    }

    private static ZipFile openBaseApk() {
        ZipFile zipFileTryOpen = tryOpen(apkPath);
        if (zipFileTryOpen != null) {
            return zipFileTryOpen;
        }
        ZipFile zipFileTryOpen2 = tryOpen(getApkPathFromResource());
        if (zipFileTryOpen2 != null) {
            return zipFileTryOpen2;
        }
        ZipFile apkFromContextPath = getApkFromContextPath();
        if (apkFromContextPath != null) {
            return apkFromContextPath;
        }
        throw new VMRunnerException("Could not open base apk.");
    }

    private static ZipFile getApkFromContextPath() {
        ZipFile zipFileTryOpen = null;
        if (context == null) {
            return null;
        }
        for (int i7 = 0; i7 < 5; i7++) {
            try {
                zipFileTryOpen = tryOpen(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir);
                if (zipFileTryOpen != null) {
                    return zipFileTryOpen;
                }
            } catch (PackageManager.NameNotFoundException e10) {
                throw new VMRunnerException("Error while opening base apk: " + e10.getMessage(), e10);
            }
        }
        return zipFileTryOpen;
    }

    private static ZipFile tryOpen(String path) {
        if (path == null) {
            return null;
        }
        try {
            ZipFile zipFile = new ZipFile(path);
            apkPath = path;
            return zipFile;
        } catch (IOException e10) {
            if (isDebuggingEnabled()) {
                Log.i(TAG, "Cannot open zipfile " + path + " error: " + e10.getMessage());
            }
            return null;
        }
    }

    private static byte[] readFullByteArrayFromStream(InputStream is, byte[] byteArray) throws IOException {
        int i7 = 0;
        while (true) {
            int i10 = is.read(byteArray, i7, byteArray.length - i7);
            if (i10 <= 0) {
                break;
            }
            i7 += i10;
        }
        if (i7 == byteArray.length) {
            return byteArray;
        }
        throw new IOException("Read " + i7 + MqttTopic.TOPIC_LEVEL_SEPARATOR + byteArray.length + " bytes.");
    }

    private static synchronized String getApkPathFromResource() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        URL resource = VMRunner.class.getResource("/AndroidManifest.xml");
        if (resource == null) {
            if (isDebuggingEnabled()) {
                Log.i(TAG, "Cannot load resource!");
            }
            return null;
        }
        if (isDebuggingEnabled()) {
            Log.i(TAG, "Resource URL is " + String.valueOf(resource));
        }
        String string = resource.toString();
        String strSubstring = string.substring(9, string.lastIndexOf(33));
        if (isDebuggingEnabled()) {
            Log.i(TAG, String.format("Found APK path %s after %d ms.", strSubstring, Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis)));
        }
        return strSubstring;
    }

    private static boolean isDebuggingEnabled() {
        return "true".equals(loggingEnabled);
    }

    private VMRunner() {
    }
}
