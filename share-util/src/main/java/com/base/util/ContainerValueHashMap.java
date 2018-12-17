package com.base.util;

import java.util.HashMap;

/**
 * 容器值HashMap类。
 *
 * @param <K>
 * @param <V>
 */
public class ContainerValueHashMap<K, Container> extends HashMap<K, Container> {
    private static final long serialVersionUID = 2208939254913772012L;

    private Class<?> containerClass;

    public ContainerValueHashMap(Class<?> containerClass) {
        try {
            containerClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        this.containerClass = containerClass;
    }

    @SuppressWarnings("unchecked")
    public Container get(Object key) {
        Container container = super.get(key);
        if (container == null) {
            try {
                container = (Container) containerClass.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage(), e);
            }

            super.put((K) key, container);
        }

        return container;
    }
}
