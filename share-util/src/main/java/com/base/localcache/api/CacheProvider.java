package com.base.localcache.api;

import com.base.localcache.exception.NotFoundCachedException;
import com.base.localcache.model.ObjectHolder;


import java.lang.reflect.Type;


/**
 * 缓存服务接口定义类。
 */
public interface CacheProvider {
    /**
     * 获得缓存返回对象
     *
     * @param entity
     * @param key
     * @param expectClasses
     * @return
     * @throws NotFoundCachedException
     */
    ObjectHolder getCachedObject(String entity, String key, Type[] expectClasses) throws NotFoundCachedException;

    /**
     * 保存缓存对象
     *
     * @param entity
     * @param key
     * @param obj
     * @param ttl    生存周期（秒）
     */
    void cacheObject(String entity, String key, ObjectHolder obj, int ttl);

    /**
     * 驱逐缓存对象
     *
     * @param entity
     * @param key
     * @return
     */
    ObjectHolder evictObject(String entity, String key);

    /**
     * 驱逐缓存entity下的所有对象
     *
     * @param entity
     * @return 被驱逐的对象数量
     */
    long evictObjects(String entity);
}
