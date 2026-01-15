package com.pairip;

import android.content.Context;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: /home/ark/Downloads/Kuku TV_5.6.3_antisplit/smali_classes8/com/pairip/InitContextProvider.smali */
final class InitContextProvider {
    static Context tryToCreateContextForBindingApp() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName("android.app.LoadedApk");
            Class<?> cls2 = Class.forName("android.app.ActivityThread");
            Class[] clsArr = new Class[0];
            Method method = cls2.getMethod("currentActivityThread", null);
            Field declaredField = cls2.getDeclaredField("mBoundApplication");
            declaredField.setAccessible(true);
            Field declaredField2 = Class.forName("android.app.ActivityThread$AppBindData").getDeclaredField("info");
            declaredField2.setAccessible(true);
            Method declaredMethod = Class.forName("android.app.ContextImpl").getDeclaredMethod("createAppContext", cls2, cls);
            declaredMethod.setAccessible(true);
            Object objInvoke = method.invoke(null, null);
            Object objInvoke2 = declaredMethod.invoke(null, objInvoke, declaredField2.get(declaredField.get(objInvoke)));
            tryToInitializeBasicInstrumentationForStartActivity(cls2, objInvoke);
            return (Context) objInvoke2;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException unused) {
            return null;
        }
    }

    private static void tryToInitializeBasicInstrumentationForStartActivity(Class<?> activityThreadClass, Object activityThread) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        try {
            Field declaredField = activityThreadClass.getDeclaredField("mInstrumentation");
            declaredField.setAccessible(true);
            Class[] clsArr = new Class[0];
            declaredField.set(activityThread, Class.forName("android.app.Instrumentation").getDeclaredConstructor(null).newInstance(null));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException unused) {
        }
    }

    private InitContextProvider() {
    }
}
