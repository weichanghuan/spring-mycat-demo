package com.base.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Map工具类
 */
public final class MapUtil {
    /**
     * 是否为键值对类型，为null或其他类型返回false
     *
     * @param obj
     * @return
     */
    public static boolean isMap(Object obj) {
        return (obj instanceof Map);
    }

    /**
     * 获得Map对象包含的Key数量
     *
     * @param map 键值对
     * @return 键值对大小，如果map为null，则返回0
     */
    public static int size(Map<?, ?> map) {
        return (map == null ? 0 : map.size());
    }

    /**
     * 获得Map对象集包含的Key总数量
     *
     * @param maps 键值对集
     * @return 键值对集大小，如果maps为null，则返回0
     */
    public static int size(Map<?, ?>... maps) {
        int size = 0;
        if (maps != null) {
            for (Map<?, ?> m : maps) {
                if (m != null) {
                    size += m.size();
                }
            }
        }

        return size;
    }

    /**
     * 判断Map对象是否为null或空
     *
     * @param map 键值对
     * @return 是否为null或空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 判断Map对象是否不为null或空
     *
     * @param map 键值对
     * @return 是否不为null或空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return (map != null && !map.isEmpty());
    }

    /**
     * 根据Key从Map中获取指定元素，如果Map为空或Key不存在则返回null
     *
     * @param map 键值对
     * @param key 键
     * @return 值
     */
    public static <K, V> V get(Map<K, V> map, K key) {
        return get(map, key, null);
    }

    /**
     * 根据Key从Map中获取指定元素，如果Map为空或Key不存在则返回defVal
     *
     * @param map    键值对
     * @param key    键
     * @param defVal 默认值
     * @return 值
     */
    public static <K, V> V get(Map<K, V> map, K key, V defVal) {
        if (isEmpty(map)) {
            return defVal;
        }

        V val = null;
        try {
            val = map.get(key);
        } catch (NullPointerException e) {
            // The map whose key cannot be null, such as ConcurrentHashMap
        }

        return (val != null ? val : defVal);
    }

    /**
     * 根据Value从Map中获取第一个值相同的Key，如果Map为空或Value不存在则返回null(用于value唯一的情况)
     *
     * @param map   键值对
     * @param value 值
     * @return 键
     */
    public static <K, V> K getUniqueKey(Map<K, V> map, V value) {
        return getUniqueKey(map, value, null);
    }

    /**
     * 根据Value从Map中获取第一个值相同的Key，如果Map为空或Value不存在则返回defKey(用于value唯一的情况)
     *
     * @param map    键值对
     * @param value  值
     * @param defKey 默认键
     * @return 键
     */
    public static <K, V> K getUniqueKey(Map<K, V> map, V value, K defKey) {
        if (isEmpty(map)) {
            return defKey;
        }

        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value == null) {
                if (entry.getValue() == null) {
                    return entry.getKey();
                }
            } else if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        return defKey;
    }

    /**
     * 根据Value从Map中获取所有值相同的Key集合，如果Map为空或Value不存在则返回空集合
     *
     * @param map   键值对
     * @param value 值
     * @return 键集合
     */
    public static <K, V> Set<K> getKeys(Map<K, V> map, V value) {
        if (isEmpty(map)) {
            return Collections.emptySet();
        }

        Set<K> keys = new LinkedHashSet<K>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value == null) {
                if (entry.getValue() == null) {
                    keys.add(entry.getKey());
                }
            } else if (value.equals(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }

        return keys;
    }

    /**
     * 判断指定键是否在键值对中存在
     *
     * @param <K>
     * @param <V>
     * @param map 键值对
     * @param key 键
     * @return 是否存在
     */
    public static <K, V> boolean containsKey(Map<K, V> map, K key) {
        if (isEmpty(map)) {
            return false;
        }

        return map.containsKey(key);
    }

    /**
     * 判断指定键集是否任一一个在键值对中存在
     *
     * @param map
     * @param keys
     * @return
     */
    public static <K, V> boolean containsAnyKey(Map<K, V> map, Collection<K> keys) {
        if (isEmpty(map) || CollectionUtil.isEmpty(keys)) {
            return false;
        }

        for (K key : keys) {
            if (map.containsKey(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断指定值是否在键值对中存在
     *
     * @param <K>
     * @param <V>
     * @param map   键值对
     * @param value 值
     * @return 是否存在
     */
    public static <K, V> boolean containsValue(Map<K, V> map, V value) {
        if (isEmpty(map)) {
            return false;
        }

        return map.containsValue(value);
    }

    /**
     * 判断指定值集是否任一一个值在键值对中存在
     *
     * @param <K>
     * @param <V>
     * @param map   键值对
     * @param value 值
     * @return 是否存在
     */
    public static <K, V> boolean containsAnyValue(Map<K, V> map, Collection<V> values) {
        if (isEmpty(map) || CollectionUtil.isEmpty(values)) {
            return false;
        }

        for (V value : values) {
            if (map.containsValue(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 向map对中添加元素，如果map为null，则新建一个并返回新建的map对象，否则返回原始map
     *
     * @param map
     * @param key
     * @param val
     * @return
     */
    public static <K, V> Map<K, V> put(Map<K, V> map, K key, V val) {
        if (map == null) {
            map = new LinkedHashMap<K, V>();
        }

        map.put(key, val);
        return map;
    }

    /**
     * 向map对中添加元素，如果val为null，则直接返回map；如果map为null，则新建一个并返回新建的map对象，否则返回原始map
     *
     * @param map
     * @param key
     * @param val
     * @return
     */
    public static <K, V> Map<K, V> putIfValNotNull(Map<K, V> map, K key, V val) {
        if (val == null) {
            return map;
        }

        return put(map, key, val);
    }

    /**
     * 向map对中添加元素，如果val为null，则尝试从map中删除指定key的元素，并返回map；如果val不为null且map为null，
     * 则新建一个并返回新建的map对象
     *
     * @param map
     * @param key
     * @param val
     * @return
     */
    public static <K, V> Map<K, V> putOrRmIfValNull(Map<K, V> map, K key, V val) {
        if (val != null) {
            return put(map, key, val);
        }

        if (map != null) {
            map.remove(key);
        }

        return map;
    }

    /**
     * 利用反射根据指定类型建立Map对象
     *
     * @param reference 参考对象
     * @return 新建的Map对象. 返回null, 如果参考对象为空或者不是Map类型或构建失败.
     */
    @SuppressWarnings("unchecked")
    public static <M extends Map<?, ?>> M newMap(Object reference) {
        Class<?> clazz = null;
        if (reference instanceof Map<?, ?>) {
            clazz = (Class<?>) reference.getClass();

        } else if (reference instanceof Class<?> && Map.class.isAssignableFrom((Class<?>) reference)) {
            clazz = (Class<?>) reference;

            if (clazz.equals(Map.class)) {
                clazz = (Class<?>) LinkedHashMap.class;
            }
        }

        if (clazz != null) {
            try {
                return (M) clazz.newInstance();
            } catch (Exception e) {
            }
        }

        return null;
    }

    /**
     * 创建Entry的实例对象
     *
     * @param <K>   键的类型
     * @param <V>   值的类型
     * @param key   键
     * @param value 值
     * @return Entry对象
     */
    public static <K, V> Map.Entry<K, V> toEntry(K key, V value) {
        return new Entry<K, V>(key, value);
    }

    /**
     * 过滤键值对，保留满足指定条件的元素
     *
     * @param map
     * @param filter
     */
    public static <K, V> void filter(Map<K, V> map, Filter<K, V> filter) {
        if (isEmpty(map) || filter == null) {
            return;
        }

        for (Iterator<Map.Entry<K, V>> i = map.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry<K, V> entry = i.next();
            if (filter.retain(entry.getKey(), entry.getValue()) == false) {
                i.remove();
            }
        }
    }

    /**
     * 默认无参构造函数
     */
    private MapUtil() {
    }

    /**
     *
     */
    static class Entry<K, V> implements Map.Entry<K, V> {
        /**
         * 键
         */
        private K key;

        /**
         * 值
         */
        private V value;

        /**
         * 默认无参构造函数
         */
        public Entry() {
        }

        /**
         * 由键与值作为参数的构造函数
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        public K getKey() {
            return key;
        }

        /**
         * {@inheritDoc}
         */
        public K setKey(K key) {
            K originalKey = this.key;
            this.key = key;

            return originalKey;
        }

        /**
         * {@inheritDoc}
         */
        public V getValue() {
            return value;
        }

        /**
         * {@inheritDoc}
         */
        public V setValue(V value) {
            V originalValue = this.value;
            this.value = value;

            return originalValue;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }

            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2))) {
                    return true;
                }
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /**
     * 过滤器接口
     */
    public interface Filter<K, V> {
        /**
         * 过滤Map内容，返回是否保留该元素
         *
         * @param key
         * @param value
         * @return true 表示保留，false表示移除
         */
        boolean retain(K key, V value);
    }
}
