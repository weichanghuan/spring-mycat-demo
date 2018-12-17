package com.base.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;

import com.base.util.MapUtil.Filter;

/**
 * Map工具测试类
 */
public class MapUtilTest {
    /**
     * 测试isMap方法
     */
    @Test
    public void testIsMap() {
        assertTrue(MapUtil.isMap(new HashMap<String, String>()));
        assertTrue(MapUtil.isMap(new ConcurrentHashMap<String, String>()));

        assertFalse(MapUtil.isMap(1));
        assertFalse(MapUtil.isMap("1"));
        assertFalse(MapUtil.isMap(new String[]{"1"}));
        assertFalse(MapUtil.isMap(new HashSet<String>()));
        assertFalse(MapUtil.isMap(new Object()));
        assertFalse(MapUtil.isMap(null));
    }

    /**
     * 测试size方法
     */
    @Test
    public void testSize() {
        // size(Map)
        assertEquals(1, MapUtil.size(ArrayUtil.asMap("Key1")));
        assertEquals(2, MapUtil.size(ArrayUtil.asMap("Key1", "Val1", "Key2")));
        assertEquals(0, MapUtil.size(new HashMap<String, String>()));
        assertEquals(0, MapUtil.size((Map<?, ?>) null));

        // size(Map[])
        assertEquals(3,
                MapUtil.size((Map<?, ?>) null, ArrayUtil.asMap("Key1"), ArrayUtil.asMap("Key1", "Val1", "Key2")));
        assertEquals(0, MapUtil.size((Map<?, ?>[]) null));
        assertEquals(0, MapUtil.size());
    }

    /**
     * 测试put方法
     */
    @Test
    public void testPut() {
        Map<String, String> src = new HashMap<String, String>();
        assertSame(src, MapUtil.put(src, "a", "1"));
        assertEquals("1", src.get("a"));

        src = MapUtil.put(null, "b", "2");
        assertNotNull(src);
        assertEquals("2", src.get("b"));

        assertSame(src, MapUtil.put(src, "c", "3"));
        assertEquals(2, src.size());
        assertEquals("2", src.get("b"));
        assertEquals("3", src.get("c"));
    }

    /**
     * 测试putIfValNotNull方法
     */
    @Test
    public void testPutIfValNotNull() {
        Map<String, String> src = new HashMap<String, String>();
        assertSame(src, MapUtil.putIfValNotNull(src, "a", "1"));
        assertEquals("1", src.get("a"));

        src = MapUtil.putIfValNotNull(null, "b", "2");
        assertNotNull(src);
        assertEquals("2", src.get("b"));

        assertSame(src, MapUtil.putIfValNotNull(src, "c", "3"));
        assertEquals(2, src.size());
        assertEquals("2", src.get("b"));
        assertEquals("3", src.get("c"));

        assertSame(src, MapUtil.putIfValNotNull(src, "d", null));
        assertEquals(2, src.size());
        assertEquals("2", src.get("b"));
        assertEquals("3", src.get("c"));
    }

    /**
     * 测试putOrRmIfValNull方法
     */
    @Test
    public void testPutOrRmIfValNull() {
        Map<String, String> src = new HashMap<String, String>();
        assertSame(src, MapUtil.putOrRmIfValNull(src, "a", "1"));
        assertEquals("1", src.get("a"));

        src = MapUtil.putOrRmIfValNull(null, "b", "2");
        assertNotNull(src);
        assertEquals("2", src.get("b"));

        assertSame(src, MapUtil.putOrRmIfValNull(src, "c", "3"));
        assertEquals(2, src.size());
        assertEquals("2", src.get("b"));
        assertEquals("3", src.get("c"));

        assertSame(src, MapUtil.putOrRmIfValNull(src, "d", null));
        assertEquals(2, src.size());
        assertEquals("2", src.get("b"));
        assertEquals("3", src.get("c"));

        assertSame(src, MapUtil.putOrRmIfValNull(src, "c", null));
        assertEquals(1, src.size());
        assertEquals("2", src.get("b"));
    }

    /**
     * 测试get方法
     */
    @Test
    public void testGet() {
        // 1
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", null);
        src.put("b", "2");
        src.put("c", "3");

        assertNull(MapUtil.get(src, null));
        assertNull(MapUtil.get(src, "a"));
        assertEquals("2", MapUtil.get(src, "b"));
        assertEquals("3", MapUtil.get(src, "c"));
        assertNull(MapUtil.get(null, "b"));
        assertNull(MapUtil.get(null, null));

        // 2
        assertEquals("4", MapUtil.get(src, null, "4"));
        assertEquals("4", MapUtil.get(src, "a", "4"));
        assertEquals("2", MapUtil.get(src, "b", "4"));
        assertEquals("3", MapUtil.get(src, "c", "4"));
        assertEquals("4", MapUtil.get(null, "b", "4"));
        assertEquals("4", MapUtil.get(null, null, "4"));

        // 3
        src = new ConcurrentHashMap<String, String>();
        src.put("a", "1");

        try {
            src.get(null);
            Assert.fail("Expect NullpointerException");
        } catch (NullPointerException ex) {
        }

        assertNull(MapUtil.get(src, null)); // swallow-NullpointerException
        assertEquals("4", MapUtil.get(src, null, "4")); // swallow-NullpointerException
    }

    /**
     * 测试getKeys方法
     */
    @Test
    public void testGetKeys() {
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", "1");
        src.put("b", "2");
        src.put("c", "3");
        src.put("d", "3");
        src.put("e", null);
        src.put("f", null);

        // 1
        Set<String> keys = MapUtil.getKeys(src, "1");
        assertEquals(1, keys.size());
        assertTrue(keys.contains("a"));

        keys = MapUtil.getKeys(src, "3");
        assertEquals(2, keys.size());
        assertTrue(keys.contains("c"));
        assertTrue(keys.contains("d"));

        keys = MapUtil.getKeys(src, null);
        assertEquals(2, keys.size());
        assertTrue(keys.contains("e"));
        assertTrue(keys.contains("f"));

        keys = MapUtil.getKeys(src, null);
        assertEquals(2, keys.size());
        assertTrue(keys.contains("e"));
        assertTrue(keys.contains("f"));

        // 2
        keys = MapUtil.getKeys(src, "g");
        assertEquals(0, keys.size());

        keys = MapUtil.getKeys(src, "a");
        assertEquals(0, keys.size());

        keys = MapUtil.getKeys(null, "1");
        assertEquals(0, keys.size());
    }

    /**
     * 测试getUniqueKey方法
     */
    @Test
    public void testGetUniqueKey() {
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", "1");
        src.put("b", "2");
        src.put("c", "3");
        src.put("d", "3");
        src.put("e", null);
        src.put("f", null);

        // 1 getUniqueKey(map, value)
        String key = MapUtil.getUniqueKey(src, "1");
        assertEquals("a", key);

        key = MapUtil.getUniqueKey(src, "3");
        // 值不唯一的情况下，哪个键会返回不可预计
        assertTrue("c".equals(key) || "d".equals(key));

        key = MapUtil.getUniqueKey(src, null);
        assertTrue("e".equals(key) || "f".equals(key));

        // 2
        assertNull(MapUtil.getUniqueKey(src, "g"));
        assertNull(MapUtil.getUniqueKey(src, "a"));
        assertNull(MapUtil.getUniqueKey(null, "1"));

        // 1 getUniqueKey(map, value, defKey)
        key = MapUtil.getUniqueKey(src, "1", "z");
        assertEquals("a", key);

        key = MapUtil.getUniqueKey(src, "3", "z");
        assertTrue("c".equals(key) || "d".equals(key));

        // 2
        assertEquals("z", MapUtil.getUniqueKey(src, "g", "z"));
        assertEquals("z", MapUtil.getUniqueKey(src, "a", "z"));
        assertEquals("z", MapUtil.getUniqueKey(null, "1", "z"));
    }

    /**
     * 测试containsKey方法
     */
    @Test
    public void testContainsKey() {
        // 1
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", null);
        src.put("b", "2");
        src.put("c", "3");

        assertTrue(MapUtil.containsKey(src, "a"));
        assertTrue(MapUtil.containsKey(src, "b"));
        assertTrue(MapUtil.containsKey(src, "c"));
        assertFalse(MapUtil.containsKey(src, null));
        assertFalse(MapUtil.containsKey(null, "a"));
        assertFalse(MapUtil.containsKey(null, null));
    }

    /**
     * 测试containsAnyKey方法
     */
    @Test
    public void testContainsAnyKey() {
        // 1
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", null);
        src.put("b", "2");
        src.put("c", "3");

        assertTrue(MapUtil.containsAnyKey(src, ArrayUtil.asSet("a")));
        assertTrue(MapUtil.containsAnyKey(src, ArrayUtil.asSet("8", "b")));
        assertTrue(MapUtil.containsAnyKey(src, ArrayUtil.asSet("c", "9")));
        assertFalse(MapUtil.containsAnyKey(src, ArrayUtil.asSet("A", "B", "C")));
        assertFalse(MapUtil.containsAnyKey(src, null));
        assertFalse(MapUtil.containsAnyKey(null, ArrayUtil.asSet("a")));
        assertFalse(MapUtil.containsAnyKey(null, null));
    }

    /**
     * 测试containsValue方法
     */
    @Test
    public void testContainsValue() {
        // 1
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", null);
        src.put("b", "2");
        src.put("c", "3");

        assertTrue(MapUtil.containsValue(src, "2"));
        assertTrue(MapUtil.containsValue(src, "3"));
        assertTrue(MapUtil.containsValue(src, null));
        assertFalse(MapUtil.containsValue(src, "1"));
        assertFalse(MapUtil.containsValue(null, "a"));
        assertFalse(MapUtil.containsValue(null, null));
    }

    /**
     * 测试containsAnyValue方法
     */
    @Test
    public void testContainsAnyValue() {
        // 1
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", null);
        src.put("b", "2");
        src.put("c", "3");

        assertTrue(MapUtil.containsAnyValue(src, ArrayUtil.asSet("2", "B")));
        assertTrue(MapUtil.containsAnyValue(src, ArrayUtil.asSet("3", "C")));
        assertTrue(MapUtil.containsAnyValue(src, ArrayUtil.asSet((String) null)));
        assertFalse(MapUtil.containsAnyValue(src, ArrayUtil.asSet("4", "a", "b", "c")));
        assertFalse(MapUtil.containsAnyValue(src, ArrayUtil.asSet("1")));
        assertFalse(MapUtil.containsAnyValue(null, ArrayUtil.asSet("a")));
        assertFalse(MapUtil.containsAnyValue(null, null));
    }

    /**
     * 测试isEmpty方法
     */
    @Test
    public void testIsEmpty() {
        assertTrue(MapUtil.isEmpty(null));

        Map<Object, Object> map = new HashMap<Object, Object>();
        assertTrue(MapUtil.isEmpty(map));

        map.put("", "");
        assertFalse(MapUtil.isEmpty(map));
    }

    /**
     * 测试isNotEmpty方法
     */
    @Test
    public void testIsNotEmpty() {
        assertFalse(MapUtil.isNotEmpty(null));

        Map<Object, Object> map = new HashMap<Object, Object>();
        assertFalse(MapUtil.isNotEmpty(map));

        map.put("", "");
        assertTrue(MapUtil.isNotEmpty(map));
    }

    /**
     * 测试Entry类
     */
    @Test
    public void testToEntry() {
        // 1
        Entry<String, Object> entry = MapUtil.toEntry(null, null);
        assertNotNull(entry);
        assertNull(entry.getKey());
        assertNull(entry.getValue());
        assertEquals("null=null", entry.toString());

        // 2
        entry = MapUtil.toEntry("k", (Object) "v");
        assertNotNull(entry);
        assertEquals("k", entry.getKey());
        assertEquals("v", entry.getValue());
        assertEquals("k=v", entry.toString());

        // 3
        Entry<String, Object> entry1 = MapUtil.toEntry(null, null);
        Entry<String, Object> entry2 = MapUtil.toEntry(null, null);
        assertEquals(0, entry1.hashCode());
        assertTrue(entry1.equals(entry2));
        assertTrue(entry1.hashCode() == entry2.hashCode());

        // 4
        entry1 = MapUtil.toEntry("k", (Object) "v");
        entry2 = MapUtil.toEntry("k", (Object) "v");
        assertTrue(entry1.equals(entry2));
        assertTrue(entry1.hashCode() == entry2.hashCode());

        // 5
        entry1 = MapUtil.toEntry("k1", (Object) "v1");
        entry2 = MapUtil.toEntry("k2", (Object) "v2");
        assertFalse(entry1.equals(entry2));
        assertFalse(entry1.hashCode() == entry2.hashCode());
    }

    /**
     * 测试newMap方法
     */
    @Test
    public void testNewMap() {
        // 1
        Map<String, Number> map = MapUtil.newMap(new HashMap<String, Number>());
        assertNotNull(map);
        map.put("key", Integer.valueOf(1));

        // 2
        map = MapUtil.newMap(HashMap.class);
        assertNotNull(map);
        map.put("key", Integer.valueOf(1));

        // 3
        map = MapUtil.newMap(Map.class);
        assertNotNull(map);
        assertEquals(LinkedHashMap.class, map.getClass());
        map.put("key", Integer.valueOf(1));

        // 4
        assertNull(MapUtil.newMap(null));
        assertNull(MapUtil.newMap(Object.class));
        assertNull(MapUtil.newMap(new Object()));
        assertNull(MapUtil.newMap(SingletonMap.class));
    }

    @Test
    public void testFilter() {
        // 1
        Map<String, String> src = new LinkedHashMap<String, String>();
        src.put("a", null);
        src.put("b", "2");
        src.put("c", "3");

        MapUtil.filter(src, new Filter<String, String>() {
            public boolean retain(String key, String value) {
                return value != null;
            }
        });

        assertEquals(2, src.size());
        assertFalse(src.containsKey("a"));
        assertEquals("2", src.get("b"));
        assertEquals("3", src.get("c"));

        MapUtil.filter(src, null);
        assertEquals(2, src.size());
        assertEquals("2", src.get("b"));
        assertEquals("3", src.get("c"));

        // 2
        Map<String, String> src2 = new ConcurrentHashMap<String, String>();
        src2.put("a", "1");
        src2.put("b", "2");
        src2.put("c", "3");

        MapUtil.filter(src2, new Filter<String, String>() {
            public boolean retain(String key, String value) {
                return key.equals("a") == false;
            }
        });

        assertEquals(2, src2.size());
        assertFalse(src2.containsKey("a"));
        assertEquals("2", src2.get("b"));
        assertEquals("3", src2.get("c"));

        MapUtil.filter(src2, null);
        assertEquals(2, src2.size());
        assertEquals("2", src2.get("b"));
        assertEquals("3", src2.get("c"));

        // 3
        MapUtil.filter(null, null);
    }

    /**
     * 测试单例列表类
     */
    static class SingletonMap extends HashMap<String, String> {
        private static final long serialVersionUID = 1L;

        private static SingletonMap instance = new SingletonMap();

        private SingletonMap() {
        }

        public static SingletonMap getInstance() {
            return instance;
        }
    }

    ;
}
