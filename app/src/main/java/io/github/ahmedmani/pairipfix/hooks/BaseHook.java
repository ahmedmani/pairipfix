package io.github.ahmedmani.pairipfix.hooks;

import io.github.ahmedmani.pairipfix.utils.XposedUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHook {
    protected XC_LoadPackage.LoadPackageParam lpparam;

    public abstract String getName();
    public abstract boolean apply();

    public BaseHook init(XC_LoadPackage.LoadPackageParam lpparam) {
        this.lpparam = lpparam;
        return this;
    }

    protected ClassLoader getClassLoader() {
        return lpparam.classLoader;
    }

    protected boolean classExists(String className) {
        return XposedUtils.hasClass(getClassLoader(), className);
    }

    protected Class<?> findClass(String className) {
        return XposedUtils.findClassOrNull(getClassLoader(), className);
    }

    protected boolean setStaticField(String className, String fieldName, Object value) {
        Class<?> clazz = findClass(className);
        return XposedUtils.setStaticField(clazz, fieldName, value);
    }

    protected boolean hookDoNothing(String className, String methodName, Object... paramTypes) {
        try {
            Object[] args = buildArgs(paramTypes, XC_MethodReplacement.DO_NOTHING);
            XposedHelpers.findAndHookMethod(className, getClassLoader(), methodName, args);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    protected boolean hookReturnConstant(String className, String methodName, Object returnValue, Object... paramTypes) {
        try {
            XC_MethodReplacement replacement = XC_MethodReplacement.returnConstant(returnValue);
            Object[] args = buildArgs(paramTypes, replacement);
            XposedHelpers.findAndHookMethod(className, getClassLoader(), methodName, args);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    protected boolean hookBefore(String className, String methodName, BeforeHookCallback callback, Object... paramTypes) {
        try {
            XC_MethodHook hook = new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    callback.onBefore(param);
                }
            };
            Object[] args = buildArgs(paramTypes, hook);
            XposedHelpers.findAndHookMethod(className, getClassLoader(), methodName, args);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    protected boolean hookAfter(String className, String methodName, AfterHookCallback callback, Object... paramTypes) {
        try {
            XC_MethodHook hook = new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    callback.onAfter(param);
                }
            };
            Object[] args = buildArgs(paramTypes, hook);
            XposedHelpers.findAndHookMethod(className, getClassLoader(), methodName, args);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    protected boolean hookReplace(String className, String methodName, ReplaceCallback callback, Object... paramTypes) {
        try {
            XC_MethodReplacement replacement = new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return callback.onReplace(param);
                }
            };
            Object[] args = buildArgs(paramTypes, replacement);
            XposedHelpers.findAndHookMethod(className, getClassLoader(), methodName, args);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    private Object[] buildArgs(Object[] paramTypes, XC_MethodHook callback) {
        List<Object> args = new ArrayList<>();
        for (Object type : paramTypes) {
            args.add(type);
        }
        args.add(callback);
        return args.toArray();
    }

    public interface BeforeHookCallback {
        void onBefore(XC_MethodHook.MethodHookParam param);
    }

    public interface AfterHookCallback {
        void onAfter(XC_MethodHook.MethodHookParam param);
    }

    public interface ReplaceCallback {
        Object onReplace(XC_MethodHook.MethodHookParam param) throws Throwable;
    }
}
