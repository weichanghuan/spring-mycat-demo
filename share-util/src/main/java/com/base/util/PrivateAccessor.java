package com.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 私有属性、方法访问器类。
 */
public class PrivateAccessor {
    public static Object getField(Object obj, String field) {
        return getField(obj.getClass(), obj, field);
    }

    public static Object getField(Class<?> clazz, Object obj, String field) {
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void setField(Object obj, String field, Object value) {
        setField(obj.getClass(), obj, field, value);
    }

    public static void setField(Class<?> clazz, Object obj, String field, Object value) {
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static class MethodDescription {
        private String method;

        private Class<?>[] paraTypes;
    }

    public static MethodDescription newMethod(String method, Class<?>... paraTypes) {
        MethodDescription methodDesc = new MethodDescription();
        methodDesc.method = method;
        methodDesc.paraTypes = paraTypes;

        return methodDesc;
    }

    public static Object invokeMethod(Object obj, MethodDescription methodDesc, Object... args) {
        return invokeMethod(obj.getClass(), obj, methodDesc, args);
    }

    public static Object invokeMethod(Class<?> clazz, Object obj, MethodDescription methodDesc, Object... args) {
        try {
            Method m = clazz.getDeclaredMethod(methodDesc.method, methodDesc.paraTypes);
            m.setAccessible(true);
            return m.invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
