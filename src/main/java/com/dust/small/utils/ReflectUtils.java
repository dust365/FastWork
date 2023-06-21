package com.dust.small.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {

    public static Object getFieldValue(Object object, String fieldName) {
        if (object == null) return null;
        Field field = findFile(object.getClass(), fieldName);
        if (field == null) return null;
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Field findFile(Class clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    public static Method findMethod(Class clazz, String methodName, Class<?>... parameterTypes) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
}
