package com.base.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 数组工具测试类
 */
public class SizeLimitedHashMapTest {

    @Test
    public void testCache() {

        SizeLimitedHashMap<Integer, Integer> cache = new SizeLimitedHashMap<Integer, Integer>(10);

        for (int i = 0; i < 1000; i++) {
            cache.put(i, i);
            Assert.assertEquals(i < 10 ? i + 1 : 10, cache.size());
            Assert.assertEquals((Integer) i, cache.get(i));

            if (i >= 10) {
                Assert.assertNull(cache.get(i - 10));
            }
        }
    }

}
