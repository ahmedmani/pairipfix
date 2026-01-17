package io.github.ahmedmani.pairipfix.utils;

import de.robv.android.xposed.XposedHelpers;

public final class XposedUtils {
    private XposedUtils() {}

    public static Class<?> findClassOrNull(ClassLoader classLoader, String className) {
        try {
            return XposedHelpers.findClass(className, classLoader);
        } catch (Throwable t) {
            return null;
        }
    }

    public static boolean hasClass(ClassLoader classLoader, String className) {
        return findClassOrNull(classLoader, className) != null;
    }

    public static boolean setStaticField(Class<?> clazz, String fieldName, Object value) {
        if (clazz == null) return false;
        try {
            XposedHelpers.setStaticObjectField(clazz, fieldName, value);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public static Object getStaticField(Class<?> clazz, String fieldName) {
        if (clazz == null) return null;
        try {
            return XposedHelpers.getStaticObjectField(clazz, fieldName);
        } catch (Throwable t) {
            return null;
        }
    }
}
