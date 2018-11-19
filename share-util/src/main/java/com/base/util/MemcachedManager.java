package com.base.util;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description memcached类---拷用以前方法
 */
public class MemcachedManager {

    private Logger logger = LoggerFactory.getLogger(MemcachedManager.class);
    private static final String SEPARATOR = "-";

    private static MemcachedManager instance;
    private MemcachedClient client;
    private String channel;
    private String addresses;
    public static String offerUrl = "http://www.51offer.com";


    private MemcachedManager() {
        logger.info("initClient memcached");
        Properties config = new Properties();
        InputStream input = null;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            input = cl.getResourceAsStream("memcache.properties");
            Reader reader = new InputStreamReader(input, "UTF-8");
            config.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("memcache.properties load failed!", e);
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        addresses = config.getProperty("addresses");
        channel = config.getProperty("channel");
        try {
            client = new MemcachedClient(AddrUtil.getAddresses(addresses));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("new MemcachedClient failed!", e);
        }
    }

    /**
     * 单例
     * spy MemcachedClient 是一种nio方式的连接，为避免原则性操作问题，整个应用只允许打开一个连接
     *
     * @return
     */
    public static MemcachedManager getInstance() {
        if (null == instance) {
            instance = new MemcachedManager();
        }
        return instance;
    }

    @PreDestroy
    public void destroy() {
        client.shutdown();
        logger.info("destroy memcached");
    }

    public Object get(String key) {
        org.springframework.util.Assert.notNull(key, "访问memcache的key不可为空");
        String realKey = composeKey(key);
        Object value = client.get(realKey);
        logger.debug("get value from memcache key:{}", realKey);
        if (value == null) {
            return null;
        }
        return value;

    }

    /**
     * 添加其他类型
     *
     * @param key1
     * @param key2
     * @return
     */
    public Object get(String key1, String key2) {
        String realKey = key1 + SEPARATOR + key2;
        Object value = client.get(realKey);
        logger.debug("get value from memcache key:{}", realKey);
        if (value == null) {
            return null;
        }
        return value;

    }


    public void set(String key, Object value) throws IOException {
        set(key, 0, value);
    }

    public void delete(String key) {
        org.springframework.util.Assert.notNull(key, "删除memcache的key不可为空");
        String realKey = composeKey(key);
        logger.debug("begin to delete value from memcache key:{}", realKey);
        client.delete(realKey);
    }

    /**
     * @param key
     * @param expre 单位 秒
     * @param value
     * @throws IOException
     */
    public void set(String key, int expre, Object value) throws IOException {
        org.springframework.util.Assert.notNull(key, "设置memcache的key不可为空");
        org.springframework.util.Assert.isTrue(expre >= 0, "expre time must >= 0");
        String realKey = composeKey(key);
        logger.debug("begin to set value to memcache key:{}", realKey);
        client.set(realKey, expre, value);
    }

    /**
     * 一个key只允许包含一条记录
     * 不等待结果集返回
     * 内部串行实现
     *
     * @param key
     * @param expre 秒
     * @param value
     * @throws IOException
     */
    public void addNoWait(String key, int expre, Object value) throws IOException {
        org.springframework.util.Assert.notNull(key, "设置memcache的key不可为空");
        org.springframework.util.Assert.isTrue(expre >= 0, "expre time must >= 0");
        String realKey = composeKey(key);
        logger.debug("begin to addNoWait value to memcache key:{}", realKey);
        client.add(realKey, expre, value);
    }

    /**
     * 一个key只允许包含一条记录
     * 等待结果集返回
     * 内部串行实现
     *
     * @param key
     * @param expre 秒
     * @param value
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public boolean addWait(String key, int expre, Object value) throws IOException, InterruptedException, ExecutionException {
        org.springframework.util.Assert.notNull(key, "设置memcache的key不可为空");
        Assert.isTrue(expre >= 0, "expre time must >= 0");
        String realKey = composeKey(key);
        logger.debug("begin to addWait value to memcache key:{}", realKey);
        Future<Boolean> b = client.add(realKey, expre, value);
        return b.get();
    }

    protected String composeKey(String key) {
        String result = this.channel + SEPARATOR + key;
        return result;
    }

    public boolean add(String key, Object value) {
        try {
            set(key, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean add(String key, int secs, Object value) {
        try {
            set(key, secs, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
