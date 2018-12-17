package com.base.util;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 具备并行访问能力的检查HashMap实现类，当获取Map中的对象时候，检查是否存在，不存在则报指定NotFound异常。
 *
 * @param <K>
 * @param <V>
 */
public class ConcurrentCheckHashMap<K, V> extends ConcurrentHashMap<K, V> {
    private static final long serialVersionUID = 1678614761665986227L;

    private Constructor<? extends RuntimeException> constructor;

    private String elementName;

    public ConcurrentCheckHashMap(Class<? extends RuntimeException> exClass, String elementName) {
        try {
            constructor = exClass.getConstructor(String.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        this.elementName = elementName;
    }

    public ConcurrentCheckHashMap(Class<? extends RuntimeException> exClass) {
        try {
            constructor = exClass.getConstructor(String.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public V get(Object key) {
        V val = super.get(key);
        if (val == null) {
            String msg;
            if (elementName != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Not found the ");
                sb.append(elementName);
                sb.append(", key=[");
                sb.append(key.toString());
                sb.append("].");
                msg = sb.toString();
            } else {
                msg = key.toString();
            }

            RuntimeException e;
            try {
                e = constructor.newInstance(msg);
            } catch (Exception e1) {
                throw new RuntimeException(msg);
            }

            throw e;
        }

        return val;
    }

}
