package com.base.util;

import java.util.LinkedHashMap;

/**
 * @param <K>
 * @param <V>
 */
public class SizeLimitedHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = 1L;

    private int maxSize;

    public SizeLimitedHashMap(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}