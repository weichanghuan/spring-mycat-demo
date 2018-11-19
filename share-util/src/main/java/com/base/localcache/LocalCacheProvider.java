package com.base.localcache;

import com.base.localcache.api.CacheProvider;

import com.base.localcache.exception.NotFoundCachedException;
import com.base.localcache.model.ObjectHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Wch
 * <p>
 * 本地缓存提供者
 * 主要功能：支持lru，ttl定时清除
 * @Date 2018/6/12
 */
public class LocalCacheProvider implements CacheProvider {

    private final static int limit = 100;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static class Item {
        private String entity;

        private String key;

        private String objectVersion;
    }

    private SortedMap<Long, List<Item>> itemsByTtl = new TreeMap<Long, List<Item>>();

    private Map<String, LRUMap<String, ObjectHolder>> cache = new ConcurrentHashMap<String, LRUMap<String, ObjectHolder>>();

    private final ScheduledExecutorService scheduledExecutorService;

    private class CleanTask implements Runnable {
        @Override
        public void run() {
            clean();
        }
    }

    synchronized private void clean() {
        logger.debug("Start to clean expired cachedObject.");

        long ts = System.currentTimeMillis();
        Map<Long, List<Item>> expiredItems = itemsByTtl.headMap(ts);
        Set<Long> removeKeys = new HashSet<Long>();
        for (Map.Entry<Long, List<Item>> entry : expiredItems.entrySet()) {
            removeKeys.add(entry.getKey());
            for (LocalCacheProvider.Item item : entry.getValue()) {
                Map<String, ObjectHolder> map = cache.get(item.entity);
                if (map == null) {
                    continue;
                }

                ObjectHolder objHolder = map.get(item.key);
                if (objHolder != null) {
                    if (item.objectVersion.equals(objHolder.getObjectVersion())) {
                        map.remove(item.key);
                        logger.debug("Clean the cachedObject, entity=[" + item.entity + "], key=[" + item.key + "].");
                    } else {
                        logger.debug("Found newer version entity=[" + item.entity + "], key=[" + item.key + "].");
                    }
                }
            }
        }

        for (Long rts : removeKeys) {
            itemsByTtl.remove(rts);
        }

        logger.debug("Finish clean expired cachedObject.");
    }

    public LocalCacheProvider() {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);

        //当定时任务执行完成以后，延迟给定时间后才执行下一个定时任务
        scheduledExecutorService.scheduleWithFixedDelay(new CleanTask(), 5 * 60, 30, TimeUnit.SECONDS);
    }

    public LocalCacheProvider(long initialDelay, long delay) {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(new CleanTask(), initialDelay, delay, TimeUnit.SECONDS);

    }

    @PreDestroy
    public void stop() {
        if (scheduledExecutorService == null) {
            return;
        }
        if(!scheduledExecutorService.isShutdown()){
            scheduledExecutorService.shutdown();
        }
    }

    @Override
    synchronized public ObjectHolder getCachedObject(String entity, String key, Type[] expectClasses) throws NotFoundCachedException {
        LRUMap<String, ObjectHolder> map = cache.get(entity);
        if (map == null) {
            throw new NotFoundCachedException();
        }

        if (map.containsKey(key) == false) {
            throw new NotFoundCachedException();
        }

        return (ObjectHolder) map.get(key);
    }

    @Override
    synchronized public void cacheObject(String entity, String key, ObjectHolder objHolder, int ttl) {
        LRUMap<String, ObjectHolder> map = cache.get(entity);
        if (map == null) {
            map = new LRUMap<String, ObjectHolder>((int) (limit * 1.4f));
            cache.put(entity, map);
        }

        map.put(key, objHolder);

        LocalCacheProvider.Item item = new LocalCacheProvider.Item();
        item.entity = entity;
        item.key = key;
        item.objectVersion = objHolder.getObjectVersion();

        long expiredTime = System.currentTimeMillis() + ttl * 1000;
        List<Item> items = itemsByTtl.get(expiredTime);
        if (items == null) {
            items = new ArrayList<Item>();
            itemsByTtl.put(expiredTime, items);
        }

        items.add(item);
    }

    @Override
    synchronized public ObjectHolder evictObject(String entity, String key) {
        Map<String, ObjectHolder> map = cache.get(entity);
        if (map == null) {
            return null;
        }

        return map.remove(key);
    }

    @Override
    public synchronized long evictObjects(String entity) {
        Map<String, ObjectHolder> map = cache.remove(entity);
        if (map != null) {
            return map.size();
        } else {
            return 0;
        }
    }

    final class LRUMap<K, V> extends LinkedHashMap {

        private final int max;

        public LRUMap(int max) {
            super((int) (max * 1.4f), 0.75f, true);
            this.max = max;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > max;
        }

    }


}
