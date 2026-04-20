package io.github.ahmedmani.pairipfix;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public abstract  class BaseHook {
        protected ClassLoader classLoader;

        public abstract String getName();
        public abstract boolean apply();

        public BaseHook init(ClassLoader _classLoader) {
            this.classLoader = _classLoader;
            return this;
        }

        protected boolean classExists(String className) {
            Class<?> clazz = XposedHelpers.findClassIfExists(className, this.classLoader);
            return !(clazz == null);
        }

        protected Class<?> findClass(String className) {
            return XposedHelpers.findClassIfExists(className, this.classLoader);
        }

        protected boolean setStaticField(String className, String fieldName, Object value) {
            Class<?> clazz = findClass(className);
            try {
                XposedHelpers.setStaticObjectField(clazz, fieldName, value);
                return true;
            } catch (Throwable t) {
                XposedBridge.log("[PairIPFix] Failed to change static field value for class: " + className + ", fieldName: " + fieldName + ", value to be set: " + value);
                return false;
            }
        }

        protected boolean hookDoNothing(String className, String methodName, Object... paramTypes) {
            try {
                Object[] args = buildArgs(paramTypes, XC_MethodReplacement.DO_NOTHING);
                XposedHelpers.findAndHookMethod(className, this.classLoader, methodName, args);
                return true;
            } catch (Throwable t) {
                XposedBridge.log("[PairIPFix] Failed to hook method with path " + className + "." + methodName);
                return false;
            }
        }

        protected boolean hookReturnConstant(String className, String methodName, Object returnValue, Object... paramTypes) {
            try {
                XC_MethodReplacement replacement = XC_MethodReplacement.returnConstant(returnValue);
                Object[] args = buildArgs(paramTypes, replacement);
                XposedHelpers.findAndHookMethod(className, this.classLoader, methodName, args);
                return true;
            } catch (Throwable t) {
                XposedBridge.log("[PairIPFix] Failed to hook method with path " + className + "." + methodName);
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
                XposedHelpers.findAndHookMethod(className, this.classLoader, methodName, args);
                return true;
            } catch (Throwable t) {
                XposedBridge.log("[PairIPFix] Failed to hook method with path " + className + "." + methodName);
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
                XposedHelpers.findAndHookMethod(className, this.classLoader, methodName, args);
                return true;
            } catch (Throwable t) {
                XposedBridge.log("[PairIPFix] Failed to replace method with path " + className + "." + methodName);
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


        public interface AfterHookCallback {
            void onAfter(XC_MethodHook.MethodHookParam param);
        }

        public interface ReplaceCallback {
            Object onReplace(XC_MethodHook.MethodHookParam param) throws Throwable;
        }

}
