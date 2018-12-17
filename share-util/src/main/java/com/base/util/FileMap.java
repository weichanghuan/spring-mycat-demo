package com.base.util;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 文件映射工具类(非线程安全)，用于维护对象和文件的映射。当文件脱离映射时会被自动删除。
 */
public class FileMap<K> implements Map<K, File> {
    /**
     * 文件映射
     */
    private Map<K, File> map = new LinkedHashMap<K, File>();

    /**
     * 创建文件
     *
     * @param key
     * @param filePath
     * @return
     */
    public File createFile(K key, String filePath) {
        File file = FileUtil.createNewFile(filePath);
        put(key, file);

        return file;
    }

    /**
     * 创建临时文件
     *
     * @param key
     * @return
     */
    public File createTempFile(K key) {
        File temp = FileUtil.createTempFile();
        put(key, temp);

        return temp;
    }

    /**
     * 根据Key对象获取文件
     *
     * @param key
     * @return
     */
    public File get(Object key) {
        return map.get(key);
    }

    /**
     * 添加已存在的文件，如存在相同Key对象的文件，则删除文件后返回文件对象
     *
     * @param key
     * @param file
     * @return
     */
    public File put(K key, File file) {
        File old = map.put(key, file);
        if (old != null) {
            old.delete();
        }

        return old;
    }

    /**
     * 添加文件映射数据
     *
     * @param map
     */
    public void putAll(Map<? extends K, ? extends File> map) {
        if (map == null || map.isEmpty()) {
            return;
        }

        for (Map.Entry<? extends K, ? extends File> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 添加文件映射数据
     *
     * @param fileMap
     */
    public void putAll(FileMap<? extends K> fileMap) {
        if (fileMap == null || fileMap.isEmpty()) {
            return;
        }

        for (Map.Entry<? extends K, File> entry : fileMap.map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 获取文件集合
     *
     * @return
     */
    public Collection<File> values() {
        return map.values();
    }

    /**
     * 获取映射对集合
     *
     * @return
     */
    public Set<Map.Entry<K, File>> entrySet() {
        return map.entrySet();
    }

    /**
     * 移除文件
     *
     * @param key
     */
    public File remove(Object key) {
        File file = map.get(key);
        if (file != null) {
            file.delete();
        }

        return file;
    }

    /**
     * 获取键对象集合
     *
     * @return
     */
    public Set<K> keySet() {
        return map.keySet();
    }

    /**
     * 获取对象和文件映射
     *
     * @return
     */
    public Map<K, File> entryMap() {
        return new LinkedHashMap<K, File>(map);
    }

    /**
     * 是否包含指定Key对象
     *
     * @return
     */
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    /**
     * 是否包含指定文件
     *
     * @param file
     * @return
     */
    public boolean containsValue(Object file) {
        return map.containsValue(file);
    }

    /**
     * 映射是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * 获取映射数量
     *
     * @return
     */
    public int size() {
        return map.size();
    }

    /**
     * 清除映射关系，并删除所有关联的文件
     */
    public void clear() {
        if (map.isEmpty()) {
            return;
        }

        for (Map.Entry<K, File> entry : map.entrySet()) {
            File file = entry.getValue();
            if (file != null) {
                file.delete();
            }
        }

        map.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return map.toString();
    }
}
