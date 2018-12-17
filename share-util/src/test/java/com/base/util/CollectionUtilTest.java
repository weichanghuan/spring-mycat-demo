package com.base.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * 集合工具测试类
 */
public class CollectionUtilTest {
    /**
     * 测试isCollection方法
     */
    @Test
    public void testIsMap() {
        assertTrue(CollectionUtil.isCollection(new HashSet<String>()));
        assertTrue(CollectionUtil.isCollection(new ArrayList<String>()));

        assertFalse(CollectionUtil.isCollection(1));
        assertFalse(CollectionUtil.isCollection("1"));
        assertFalse(CollectionUtil.isCollection(new String[]{"1"}));
        assertFalse(CollectionUtil.isCollection(new HashMap<String, String>()));
        assertFalse(CollectionUtil.isCollection(new Object()));
        assertFalse(CollectionUtil.isCollection(null));
    }

    /**
     * 测试size方法
     */
    @Test
    public void testSize() {
        // size(Collection)
        assertEquals(1, CollectionUtil.size(ArrayUtil.asList("1")));
        assertEquals(2, CollectionUtil.size(ArrayUtil.asList("1", "2")));
        assertEquals(0, CollectionUtil.size(new HashSet<String>()));
        assertEquals(0, CollectionUtil.size((Collection<?>) null));

        // size(Collection[])
        assertEquals(3, CollectionUtil.size((Collection<?>) null, ArrayUtil.asList("1"), ArrayUtil.asList("1", "2")));
        assertEquals(0, CollectionUtil.size((Collection<?>[]) null));
        assertEquals(0, CollectionUtil.size());
    }

    /**
     * 测试isEmpty方法
     */
    @Test
    public void testIsEmpty() {
        assertTrue(CollectionUtil.isEmpty(null));
        assertTrue(CollectionUtil.isEmpty(new ArrayList<Object>()));
        assertTrue(CollectionUtil.isEmpty(new HashSet<Object>()));
        assertFalse(CollectionUtil.isEmpty(Arrays.asList(new String[]{""})));
    }

    /**
     * 测试isNotEmpty方法
     */
    @Test
    public void testIsNotEmpty() {
        assertFalse(CollectionUtil.isNotEmpty(null));
        assertFalse(CollectionUtil.isNotEmpty(new ArrayList<Object>()));
        assertFalse(CollectionUtil.isNotEmpty(new HashSet<Object>()));
        assertTrue(CollectionUtil.isNotEmpty(Arrays.asList(new String[]{""})));
    }

    /**
     * 测试contains方法
     */
    @Test
    public void testContains() {
        Collection<String> c = ArrayUtil.asList("1", "2", "3", null);
        assertTrue(CollectionUtil.contains(c, "1"));
        assertTrue(CollectionUtil.contains(c, "3"));
        assertTrue(CollectionUtil.contains(c, null));
        assertFalse(CollectionUtil.contains(c, "4"));
        assertFalse(CollectionUtil.contains(new ArrayList<String>(), ""));
        assertFalse(CollectionUtil.contains(null, ""));
    }

    /**
     * 测试containsAny方法
     */
    @Test
    public void testContainsAny() {
        // List
        List<String> list = ArrayUtil.asList("1", "2", "3", null);
        assertTrue(CollectionUtil.containsAny(list, "1"));
        assertTrue(CollectionUtil.containsAny(list, "3"));
        assertTrue(CollectionUtil.containsAny(list, (String) null));
        assertFalse(CollectionUtil.containsAny(list, "4"));
        assertFalse(CollectionUtil.containsAny(new ArrayList<String>(), ""));
        assertFalse(CollectionUtil.containsAny(null, ""));
        assertTrue(CollectionUtil.containsAny(list, "1", "0"));
        assertTrue(CollectionUtil.containsAny(list, "0", "1"));
        assertTrue(CollectionUtil.containsAny(list, "3", "0"));
        assertTrue(CollectionUtil.containsAny(list, (String) null, "0"));
        assertFalse(CollectionUtil.containsAny(list, "4", "0"));
        assertFalse(CollectionUtil.containsAny(list, "0", "4"));
        assertFalse(CollectionUtil.containsAny(new ArrayList<String>(), "", "0"));
        assertFalse(CollectionUtil.containsAny(null, "", "0"));
        assertFalse(CollectionUtil.containsAny(null));

        // Set
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);
        assertTrue(CollectionUtil.containsAny(set, "1"));
        assertTrue(CollectionUtil.containsAny(set, "3"));
        assertTrue(CollectionUtil.containsAny(set, (String) null));
        assertFalse(CollectionUtil.containsAny(set, "4"));
        assertFalse(CollectionUtil.containsAny(new ArrayList<String>(), ""));
        assertFalse(CollectionUtil.containsAny(null, ""));
        assertTrue(CollectionUtil.containsAny(set, "1", "0"));
        assertTrue(CollectionUtil.containsAny(set, "0", "1"));
        assertTrue(CollectionUtil.containsAny(set, "3", "0"));
        assertTrue(CollectionUtil.containsAny(set, (String) null, "0"));
        assertFalse(CollectionUtil.containsAny(set, "4", "0"));
        assertFalse(CollectionUtil.containsAny(set, "0", "4"));
        assertFalse(CollectionUtil.containsAny(new ArrayList<String>(), "", "0"));
        assertFalse(CollectionUtil.containsAny(null, "", "0"));
        assertFalse(CollectionUtil.containsAny(null));
    }

    /**
     * 测试containsAll方法
     */
    @Test
    public void testContainsAll() {
        // List
        List<String> list = ArrayUtil.asList("1", "2", "3", null);
        assertTrue(CollectionUtil.containsAll(list, "1"));
        assertTrue(CollectionUtil.containsAll(list, "3"));
        assertTrue(CollectionUtil.containsAll(list, (String) null));
        assertFalse(CollectionUtil.containsAll(list, "4"));
        assertFalse(CollectionUtil.containsAll(new ArrayList<String>(), ""));
        assertFalse(CollectionUtil.containsAll(null, ""));
        assertTrue(CollectionUtil.containsAll(list, "1", "2"));
        assertTrue(CollectionUtil.containsAll(list, "2", "1"));
        assertTrue(CollectionUtil.containsAll(list, "3", "2"));
        assertTrue(CollectionUtil.containsAll(list, (String) null, "2"));
        assertFalse(CollectionUtil.containsAll(list, "4", "2"));
        assertFalse(CollectionUtil.containsAll(list, "2", "4"));
        assertFalse(CollectionUtil.containsAll(new ArrayList<String>(), "", "2"));
        assertFalse(CollectionUtil.containsAll(null, "", "2"));
        assertFalse(CollectionUtil.containsAll(null));

        // Set
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);
        assertTrue(CollectionUtil.containsAll(set, "1"));
        assertTrue(CollectionUtil.containsAll(set, "3"));
        assertTrue(CollectionUtil.containsAll(set, (String) null));
        assertFalse(CollectionUtil.containsAll(set, "4"));
        assertFalse(CollectionUtil.containsAll(new ArrayList<String>(), ""));
        assertFalse(CollectionUtil.containsAll(null, ""));
        assertTrue(CollectionUtil.containsAll(set, "1", "2"));
        assertTrue(CollectionUtil.containsAll(set, "2", "1"));
        assertTrue(CollectionUtil.containsAll(set, "3", "2"));
        assertTrue(CollectionUtil.containsAll(set, (String) null, "2"));
        assertFalse(CollectionUtil.containsAll(set, "4", "2"));
        assertFalse(CollectionUtil.containsAll(set, "2", "4"));
        assertFalse(CollectionUtil.containsAll(new ArrayList<String>(), "", "2"));
        assertFalse(CollectionUtil.containsAll(null, "", "2"));
        assertFalse(CollectionUtil.containsAll(null));
    }

    /**
     * 测试getElement方法
     */
    @Test
    public void testGetElement() {
        // ArrayList
        List<String> list = ArrayUtil.asList("1", "2", "3", null);

        // 1 getElement(List)
        assertEquals("1", CollectionUtil.getElement(list, 0));
        assertEquals("3", CollectionUtil.getElement(list, 2));
        assertEquals("3", CollectionUtil.getElement(list, -2));
        assertNull(CollectionUtil.getElement(list, 3));
        assertNull(CollectionUtil.getElement(list, 999));
        assertNull(CollectionUtil.getElement(list, -999));
        assertNull(CollectionUtil.getElement(null, 0));
        assertNull(CollectionUtil.getElement(new ArrayList<String>(), 0));

        // 2
        assertEquals("1", CollectionUtil.getElement(list, 0, "4"));
        assertEquals("3", CollectionUtil.getElement(list, 2, "4"));
        assertEquals("3", CollectionUtil.getElement(list, -2, "4"));
        assertNull(CollectionUtil.getElement(list, 3, "4"));
        assertEquals("4", CollectionUtil.getElement(list, 999, "4"));
        assertEquals("4", CollectionUtil.getElement(list, -999, "4"));
        assertEquals("4", CollectionUtil.getElement((Collection<String>) null, 0, "4"));
        assertEquals("4", CollectionUtil.getElement(new ArrayList<String>(), 0, "4"));

        // LinkedHashSet
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);

        // 1 getElement(Collection)
        assertEquals("1", CollectionUtil.getElement(set, 0));
        assertEquals("3", CollectionUtil.getElement(set, 2));
        assertEquals("3", CollectionUtil.getElement(set, -2));
        assertNull(CollectionUtil.getElement(set, 3));
        assertNull(CollectionUtil.getElement(set, 999));
        assertNull(CollectionUtil.getElement(set, -999));
        assertNull(CollectionUtil.getElement(null, 0));
        assertNull(CollectionUtil.getElement(new ArrayList<String>(), 0));

        // 2
        assertEquals("1", CollectionUtil.getElement(set, 0, "4"));
        assertEquals("3", CollectionUtil.getElement(set, 2, "4"));
        assertEquals("3", CollectionUtil.getElement(set, -2, "4"));
        assertNull(CollectionUtil.getElement(set, 3, "4"));
        assertEquals("4", CollectionUtil.getElement(set, 999, "4"));
        assertEquals("4", CollectionUtil.getElement(set, -999, "4"));
        assertEquals("4", CollectionUtil.getElement(new ArrayList<String>(), 0, "4"));

        // HashSet
        set = new HashSet<String>(ArrayUtil.asSet("1", "2", "3"));

        // 1 getElement(Collection)
        assertNotNull(CollectionUtil.getElement(set, 0));
        assertNotNull(CollectionUtil.getElement(set, 1));
        assertNotNull(CollectionUtil.getElement(set, 2));
        assertNotNull(CollectionUtil.getElement(set, -1));
        assertNotNull(CollectionUtil.getElement(set, -3));
        assertNull(CollectionUtil.getElement(set, 3));
        assertNull(CollectionUtil.getElement(set, 999));
        assertNull(CollectionUtil.getElement(set, -999));
    }

    /**
     * 测试getElements方法
     */
    @Test
    public void testGetElements() {
        // 1 ArrayList
        List<String> list = ArrayUtil.asList("1", "2", "3", null);
        List<String> subList = CollectionUtil.getElements(list, 0, 4);
        assertTrue(subList instanceof List);
        assertEquals(4, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertNull(CollectionUtil.getElement(subList, 3));

        // 2
        subList = CollectionUtil.getElements(list, 0, 10);
        assertEquals(4, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertNull(CollectionUtil.getElement(subList, 3));

        // 3
        subList = CollectionUtil.getElements(list, 1, 2);
        assertEquals(2, subList.size());
        assertEquals("2", CollectionUtil.getElement(subList, 0));
        assertEquals("3", CollectionUtil.getElement(subList, 1));

        // 4
        subList = CollectionUtil.getElements(list, 2, 1);
        assertEquals(1, subList.size());
        assertEquals("3", CollectionUtil.getElement(subList, 0));

        // 5
        subList = CollectionUtil.getElements(list, -2, 2);
        assertEquals(2, subList.size());
        assertEquals("3", CollectionUtil.getElement(subList, 0));
        assertNull(CollectionUtil.getElement(subList, 1));

        // 6
        subList = CollectionUtil.getElements(list, -999, 2);
        assertEquals(2, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertEquals("2", CollectionUtil.getElement(subList, 1));

        // 7
        subList = CollectionUtil.getElements(list, 1, 999);
        assertEquals(3, subList.size());
        assertEquals("2", CollectionUtil.getElement(subList, 0));
        assertNull(CollectionUtil.getElement(subList, 2));

        // 8
        subList = CollectionUtil.getElements(list, -999, 999);
        assertEquals(4, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertNull(CollectionUtil.getElement(subList, 3));

        // 9
        subList = CollectionUtil.getElements(list, 0, 0);
        assertEquals(0, subList.size());

        // 10
        subList = CollectionUtil.getElements(list, -1, 0);
        assertEquals(0, subList.size());

        // 11
        subList = CollectionUtil.getElements(list, 4, 1);
        assertEquals(0, subList.size());

        // 12
        subList = CollectionUtil.getElements(list, 0, -1);
        assertEquals(0, subList.size());

        // 13
        subList = CollectionUtil.getElements((List<String>) null, 0, 4);
        assertNull(subList);

        // 14
        subList = CollectionUtil.getElements((List<String>) null, -1, 10);
        assertNull(subList);

        // 1 LinkedHashSet
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);
        Set<String> subSet = CollectionUtil.getElements(set, 0, 4);
        assertTrue(subSet instanceof Set);
        assertEquals(4, subSet.size());
        assertEquals("1", CollectionUtil.getElement(subSet, 0));
        assertNull(CollectionUtil.getElement(subSet, 3));

        // 2
        subSet = CollectionUtil.getElements(set, 0, 10);
        assertEquals(4, subSet.size());
        assertEquals("1", CollectionUtil.getElement(subSet, 0));
        assertNull(CollectionUtil.getElement(subSet, 3));

        // 3
        subSet = CollectionUtil.getElements(set, 1, 2);
        assertEquals(2, subSet.size());
        assertEquals("2", CollectionUtil.getElement(subSet, 0));
        assertEquals("3", CollectionUtil.getElement(subSet, 1));

        // 4
        subSet = CollectionUtil.getElements(set, 2, 1);
        assertEquals(1, subSet.size());
        assertEquals("3", CollectionUtil.getElement(subSet, 0));

        // 5
        subSet = CollectionUtil.getElements(set, -2, 2);
        assertEquals(2, subSet.size());
        assertEquals("3", CollectionUtil.getElement(subSet, 0));
        assertNull(CollectionUtil.getElement(subSet, 1));

        // 6
        subSet = CollectionUtil.getElements(set, -999, 2);
        assertEquals(2, subSet.size());
        assertEquals("1", CollectionUtil.getElement(subSet, 0));
        assertEquals("2", CollectionUtil.getElement(subSet, 1));

        // 7
        subSet = CollectionUtil.getElements(set, 1, 999);
        assertEquals(3, subSet.size());
        assertEquals("2", CollectionUtil.getElement(subSet, 0));
        assertNull(CollectionUtil.getElement(subSet, 2));

        // 8
        subSet = CollectionUtil.getElements(set, -999, 999);
        assertEquals(4, subSet.size());
        assertEquals("1", CollectionUtil.getElement(subSet, 0));
        assertNull(CollectionUtil.getElement(subSet, 3));

        // 9
        subSet = CollectionUtil.getElements(set, 0, 0);
        assertEquals(0, subSet.size());

        // 10
        subSet = CollectionUtil.getElements(set, -1, 0);
        assertEquals(0, subSet.size());

        // 11
        subSet = CollectionUtil.getElements(set, 4, 1);
        assertEquals(0, subSet.size());

        // 12
        subSet = CollectionUtil.getElements(set, 0, -1);
        assertEquals(0, subSet.size());

        // 1 HashSet
        set = new HashSet<String>(ArrayUtil.asSet("1", "2", "3", null));
        subSet = CollectionUtil.getElements(set, 0, 4);
        assertEquals(4, subSet.size());

        // 2
        subSet = CollectionUtil.getElements(set, -2, 2);
        assertEquals(2, subSet.size());

        // 3
        subSet = CollectionUtil.getElements(set, 1, 2);
        assertEquals(2, subSet.size());

        // 4
        subSet = CollectionUtil.getElements(set, 2, 1);
        assertEquals(1, subSet.size());

        // 5
        subSet = CollectionUtil.getElements(set, -999, 2);
        assertEquals(2, subSet.size());

        // 6
        subSet = CollectionUtil.getElements(set, 0, 999);
        assertEquals(4, subSet.size());

        // 7
        subSet = CollectionUtil.getElements(set, -3, 999);
        assertEquals(3, subSet.size());

        // 8
        subSet = CollectionUtil.getElements(set, 0, 0);
        assertEquals(0, subSet.size());

        // 9
        subSet = CollectionUtil.getElements(set, 0, -1);
        assertEquals(0, subSet.size());
    }

    /**
     * 测试subCollection方法
     */
    @Test
    public void testSubCollection() {
        // ArrayList
        List<String> list = ArrayUtil.asList("1", "2", "3", null);

        // subCollection(list, start, end)
        // 1
        List<String> subList = CollectionUtil.subCollection(list, 0, 3);
        assertTrue(subList instanceof List);
        assertEquals(3, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertEquals("3", CollectionUtil.getElement(subList, 2));

        // 2
        subList = CollectionUtil.subCollection(list, 1, 2);
        assertEquals(1, subList.size());
        assertEquals("2", CollectionUtil.getElement(subList, 0));

        // 3
        subList = CollectionUtil.subCollection(list, 0, 999);
        assertEquals(4, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertNull(CollectionUtil.getElement(subList, 3));

        // 4
        subList = CollectionUtil.subCollection(list, -3, 2);
        assertEquals(1, subList.size());
        assertEquals("2", CollectionUtil.getElement(subList, 0));

        // 5
        subList = CollectionUtil.subCollection(list, 0, -2);
        assertEquals(2, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertEquals("2", CollectionUtil.getElement(subList, 1));

        // 6
        subList = CollectionUtil.subCollection(list, -999, 2);
        assertEquals(2, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertEquals("2", CollectionUtil.getElement(subList, 1));

        // 7
        subList = CollectionUtil.subCollection(list, -4, -1);
        assertEquals(3, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertEquals("3", CollectionUtil.getElement(subList, 2));

        // 8
        subList = CollectionUtil.subCollection(list, -999, 999);
        assertEquals(4, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertNull(CollectionUtil.getElement(subList, 3));

        // 9
        subList = CollectionUtil.subCollection(list, 0, 0);
        assertEquals(4, subList.size());
        assertEquals("1", subList.get(0));
        assertNull(subList.get(3));

        // 10
        subList = CollectionUtil.subCollection(list, 1, 0);
        assertEquals(3, subList.size());
        assertEquals("2", subList.get(0));
        assertNull(subList.get(2));

        // 11
        subList = CollectionUtil.subCollection(list, -1, 0);
        assertEquals(1, subList.size());
        assertNull(subList.get(0));

        // 12
        subList = CollectionUtil.subCollection(list, 4, 1);
        assertEquals(0, subList.size());

        // 13
        subList = CollectionUtil.subCollection(list, 0, -999);
        assertEquals(0, subList.size());

        // 14
        subList = CollectionUtil.subCollection(list, -1, -3);
        assertEquals(0, subList.size());

        // 14
        subList = CollectionUtil.subCollection((List<String>) null, 0, 1);
        assertNull(subList);

        // 15
        subList = CollectionUtil.subCollection((List<String>) null, -999, 999);
        assertNull(subList);

        // subCollection(list, start)
        // 1
        subList = CollectionUtil.subCollection(list, 0);
        assertTrue(subList instanceof List);
        assertEquals(4, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertNull(CollectionUtil.getElement(subList, 3));

        // 2
        subList = CollectionUtil.subCollection(list, 4);
        assertEquals(0, subList.size());

        // 3
        subList = CollectionUtil.subCollection(list, 999);
        assertEquals(0, subList.size());

        // 4
        subList = CollectionUtil.subCollection(list, -3);
        assertEquals(3, subList.size());
        assertEquals("2", CollectionUtil.getElement(subList, 0));
        assertNull(CollectionUtil.getElement(subList, 2));

        // 5
        subList = CollectionUtil.subCollection(list, -999);
        assertEquals(4, subList.size());
        assertEquals("1", CollectionUtil.getElement(subList, 0));
        assertNull(CollectionUtil.getElement(subList, 3));

        // 6
        subList = CollectionUtil.subCollection((List<String>) null, 9);
        assertNull(subList);

        // LinkedHashSet
        // subCollection(set, start, end)
        // 1
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);
        Set<String> subSet = CollectionUtil.subCollection(set, 0, 4);
        assertTrue(subSet instanceof Set);
        assertEquals(4, subSet.size());
        assertEquals("1", CollectionUtil.getElement(subSet, 0));
        assertNull(CollectionUtil.getElement(subSet, 3));

        // 2
        subSet = CollectionUtil.subCollection(set, 1, 2);
        assertEquals(1, subSet.size());
        assertEquals("2", CollectionUtil.getElement(subSet, 0));

        // 3
        subSet = CollectionUtil.subCollection(set, 0, 999);
        assertEquals(4, subSet.size());
        assertEquals("1", CollectionUtil.getElement(subSet, 0));
        assertNull(CollectionUtil.getElement(subSet, 3));

        // 4
        subSet = CollectionUtil.subCollection(set, -3, 2);
        assertEquals(1, subSet.size());
        assertEquals("2", CollectionUtil.getElement(subSet, 0));

        // 5
        subSet = CollectionUtil.subCollection(set, 0, -2);
        assertEquals(2, subSet.size());
        assertEquals("1", CollectionUtil.getElement(subSet, 0));
        assertEquals("2", CollectionUtil.getElement(subSet, 1));

        // 6
        subSet = CollectionUtil.subCollection(set, -999, 2);
        assertEquals(2, subSet.size());
        assertEquals("1", CollectionUtil.getElement(subSet, 0));
        assertEquals("2", CollectionUtil.getElement(subSet, 1));

        // 7
        subSet = CollectionUtil.subCollection(set, -4, -1);
        assertEquals(3, subSet.size());
        assertEquals("1", CollectionUtil.getElement(subSet, 0));
        assertEquals("3", CollectionUtil.getElement(subSet, 2));

        // 8
        subSet = CollectionUtil.subCollection(set, -999, 999);
        assertEquals(4, subSet.size());
        assertEquals("1", CollectionUtil.getElement(subSet, 0));
        assertNull(CollectionUtil.getElement(subSet, 3));

        // 9
        subSet = CollectionUtil.subCollection(set, 0, 0);
        assertEquals(subSet.size(), subSet.size());

        // 10
        subSet = CollectionUtil.subCollection(set, -1, 0);
        assertEquals(1, subSet.size());

        // 11
        subSet = CollectionUtil.subCollection(set, 4, 1);
        assertEquals(0, subSet.size());

        // 12
        subSet = CollectionUtil.subCollection(set, 0, -999);
        assertEquals(0, subSet.size());

        // 13
        subSet = CollectionUtil.subCollection(set, -1, -3);
        assertEquals(0, subSet.size());

        // subCollection(set, start)
        // 1
        Collection<String> subCol = CollectionUtil.subCollection(list, 0);
        assertTrue(subCol instanceof List);
        assertEquals(4, subCol.size());
        assertEquals("1", CollectionUtil.getElement(subCol, 0));
        assertNull(CollectionUtil.getElement(subCol, 3));

        // 2
        subCol = CollectionUtil.subCollection(list, 4);
        assertEquals(0, subCol.size());

        // 3
        subCol = CollectionUtil.subCollection(list, 999);
        assertEquals(0, subCol.size());

        // 4
        subCol = CollectionUtil.subCollection(list, -3);
        assertEquals(3, subCol.size());
        assertEquals("2", CollectionUtil.getElement(subCol, 0));
        assertNull(CollectionUtil.getElement(subCol, 2));

        // 5
        subCol = CollectionUtil.subCollection(list, -999);
        assertEquals(4, subCol.size());
        assertEquals("1", CollectionUtil.getElement(subCol, 0));
        assertNull(CollectionUtil.getElement(subCol, 3));

        // 6
        subCol = CollectionUtil.subCollection((Collection<String>) null, 9);
        assertNull(subCol);

        // 1 HashSet
        set = new HashSet<String>(ArrayUtil.asSet("1", "2", "3", null));
        subSet = CollectionUtil.subCollection(set, 0, 3);
        assertEquals(3, subSet.size());

        // 2
        subSet = CollectionUtil.subCollection(set, 1, 2);
        assertEquals(1, subSet.size());

        // 3
        subSet = CollectionUtil.subCollection(set, -2, 3);
        assertEquals(1, subSet.size());

        // 4
        subSet = CollectionUtil.subCollection(set, -999, 3);
        assertEquals(3, subSet.size());

        // 5
        subSet = CollectionUtil.subCollection(set, 0, -3);
        assertEquals(1, subSet.size());

        // 6
        subSet = CollectionUtil.subCollection(set, 0, 999);
        assertEquals(4, subSet.size());

        // 7
        subSet = CollectionUtil.subCollection(set, -3, 999);
        assertEquals(3, subSet.size());

        // 8
        subSet = CollectionUtil.subCollection(set, -3, -1);
        assertEquals(2, subSet.size());

        // 9
        subSet = CollectionUtil.subCollection(set, 0, 0);
        assertEquals(set.size(), subSet.size());

        // 10
        subSet = CollectionUtil.subCollection(set, 0, -999);
        assertEquals(0, subSet.size());
    }

    /**
     * 测试split方法
     */
    @Test
    public void testSplit() {
        // 1 ArrayList
        List<String> list = ArrayUtil.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        Collection<String>[] sepLists = CollectionUtil.split(list, 10);
        assertTrue(sepLists[0] instanceof List);
        assertEquals(1, sepLists.length);
        assertEquals(10, sepLists[0].size());
        assertEquals("9", CollectionUtil.getElement(sepLists[0], 9));

        // 2
        sepLists = CollectionUtil.split(list, 5);
        assertEquals(2, sepLists.length);
        assertEquals(5, sepLists[0].size());
        assertEquals(5, sepLists[1].size());
        assertEquals("4", CollectionUtil.getElement(sepLists[0], 4));
        assertEquals("9", CollectionUtil.getElement(sepLists[1], 4));

        // 3
        sepLists = CollectionUtil.split(list, 3);
        assertEquals(4, sepLists.length);
        assertEquals(3, sepLists[0].size());
        assertEquals(3, sepLists[1].size());
        assertEquals(3, sepLists[2].size());
        assertEquals(1, sepLists[3].size());
        assertEquals("2", CollectionUtil.getElement(sepLists[0], 2));
        assertEquals("5", CollectionUtil.getElement(sepLists[1], 2));
        assertEquals("8", CollectionUtil.getElement(sepLists[2], 2));
        assertEquals("9", CollectionUtil.getElement(sepLists[3], 0));

        // 4
        sepLists = CollectionUtil.split(list, 20);
        assertEquals(1, sepLists.length);
        assertEquals(10, sepLists[0].size());
        assertEquals("9", CollectionUtil.getElement(sepLists[0], 9));

        // 5
        sepLists = CollectionUtil.split(list, 0);
        assertEquals(1, sepLists.length);
        assertEquals(10, sepLists[0].size());
        assertEquals("9", CollectionUtil.getElement(sepLists[0], 9));

        // 6
        sepLists = CollectionUtil.split(list, -1);
        assertEquals(1, sepLists.length);
        assertEquals(10, sepLists[0].size());
        assertEquals("9", CollectionUtil.getElement(sepLists[0], 9));

        // 7
        sepLists = CollectionUtil.split(new ArrayList<String>(), 10);
        assertEquals(1, sepLists.length);
        assertEquals(0, sepLists[0].size());

        // 8
        sepLists = CollectionUtil.split((List<String>) null, 10);
        assertNull(sepLists);

        // 9
        sepLists = CollectionUtil.split(list, 10);
        list.remove("0"); // c != sepColls[0]
        assertEquals("0", CollectionUtil.getElement(sepLists[0], 0));

        // 1 LinkedHashSet
        Set<String> set = ArrayUtil.asSet("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        Collection<String>[] sepSets = CollectionUtil.split(set, 10);
        assertTrue(sepSets[0] instanceof Set);
        assertEquals(1, sepSets.length);
        assertEquals(10, sepSets[0].size());
        assertEquals("9", CollectionUtil.getElement(sepSets[0], 9));

        // 2
        sepSets = CollectionUtil.split(set, 5);
        assertEquals(2, sepSets.length);
        assertEquals(5, sepSets[0].size());
        assertEquals(5, sepSets[1].size());
        assertEquals("4", CollectionUtil.getElement(sepSets[0], 4));
        assertEquals("9", CollectionUtil.getElement(sepSets[1], 4));

        // 3
        sepSets = CollectionUtil.split(set, 3);
        assertEquals(4, sepSets.length);
        assertEquals(3, sepSets[0].size());
        assertEquals(3, sepSets[1].size());
        assertEquals(3, sepSets[2].size());
        assertEquals(1, sepSets[3].size());
        assertEquals("2", CollectionUtil.getElement(sepSets[0], 2));
        assertEquals("5", CollectionUtil.getElement(sepSets[1], 2));
        assertEquals("8", CollectionUtil.getElement(sepSets[2], 2));
        assertEquals("9", CollectionUtil.getElement(sepSets[3], 0));

        // 4
        sepSets = CollectionUtil.split(set, 20);
        assertEquals(1, sepSets.length);
        assertEquals(10, sepSets[0].size());
        assertEquals("9", CollectionUtil.getElement(sepSets[0], 9));

        // 5
        sepSets = CollectionUtil.split(set, 0);
        assertEquals(1, sepSets.length);
        assertEquals(10, sepSets[0].size());
        assertEquals("9", CollectionUtil.getElement(sepSets[0], 9));

        // 5
        sepSets = CollectionUtil.split(set, -1);
        assertEquals(1, sepSets.length);
        assertEquals(10, sepSets[0].size());
        assertEquals("9", CollectionUtil.getElement(sepSets[0], 9));

        // 6
        sepSets = CollectionUtil.split(new HashSet<String>(), 10);
        assertEquals(1, sepSets.length);
        assertEquals(0, sepSets[0].size());

        // 8
        sepSets = CollectionUtil.split((Set<String>) null, 10);
        assertNull(sepSets);

        // 9
        sepSets = CollectionUtil.split(set, 10);
        set.remove("0"); // c != sepColls[0]
        assertEquals("0", CollectionUtil.getElement(sepSets[0], 0));

        // 1 HashSet
        set = new HashSet<String>(ArrayUtil.asSet("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        sepSets = CollectionUtil.split(set, 3);
        assertEquals(4, sepSets.length);
        assertEquals(3, sepSets[0].size());
        assertEquals(3, sepSets[1].size());
        assertEquals(3, sepSets[2].size());
        assertEquals(1, sepSets[3].size());
    }

    /**
     * 测试newCollection方法
     */
    @Test
    public void testNewCollection() {
        // 1
        List<Object> list = CollectionUtil.newCollection(new ArrayList<String>());
        assertNotNull(list);
        list.add("Test");

        // 2
        list = CollectionUtil.newCollection(ArrayList.class);
        assertNotNull(list);
        list.add("Test");

        // 3
        list = CollectionUtil.newCollection(List.class);
        assertNotNull(list);
        assertEquals(ArrayList.class, list.getClass());
        list.add("Test");

        // 4
        Set<Object> set = CollectionUtil.newCollection(Set.class);
        assertNotNull(set);
        assertEquals(LinkedHashSet.class, set.getClass());
        set.add("Test");

        // 5
        assertNull(CollectionUtil.newCollection(null));
        assertNull(CollectionUtil.newCollection(Object.class));
        assertNull(CollectionUtil.newCollection(new Object()));
        assertNull(CollectionUtil.newCollection(SingletonList.class));
    }

    /**
     * 测试addIfNotNull方法
     */
    @Test
    public void testAddIfNotNull() {
        List<String> list = new ArrayList<String>();
        assertTrue(CollectionUtil.addIfNotNull(list, "1"));
        assertTrue(CollectionUtil.addIfNotNull(list, "2"));
        assertTrue(CollectionUtil.addIfNotNull(list, "1"));
        assertTrue(CollectionUtil.addIfNotNull(list, "2"));
        assertFalse(CollectionUtil.addIfNotNull(list, null));

        Set<String> set = new HashSet<String>();
        assertTrue(CollectionUtil.addIfNotNull(set, "1"));
        assertTrue(CollectionUtil.addIfNotNull(set, "2"));
        assertFalse(CollectionUtil.addIfNotNull(set, "1"));
        assertFalse(CollectionUtil.addIfNotNull(set, "2"));
        assertFalse(CollectionUtil.addIfNotNull(set, null));

        assertFalse(CollectionUtil.addIfNotNull(null, "1"));
        assertFalse(CollectionUtil.addIfNotNull(null, null));
    }

    /**
     * 测试addAllIfNotEmpty方法
     */
    @Test
    public void testAddAllIfNotEmpty() {
        // 1
        List<String> l1 = ArrayUtil.asList("a");
        List<String> l2 = ArrayUtil.asList("1", "2", "3", null);
        assertTrue(CollectionUtil.addAllIfNotEmpty(l1, l2));
        assertEquals("a", l1.get(0));
        assertEquals("1", l1.get(1));
        assertEquals("2", l1.get(2));
        assertEquals("3", l1.get(3));
        assertNull(l1.get(4));

        assertFalse(CollectionUtil.addAllIfNotEmpty(l1, null));
        assertFalse(CollectionUtil.addAllIfNotEmpty(l1, new ArrayList<String>()));

        // 2
        Set<String> s1 = ArrayUtil.asSet("a");
        assertTrue(CollectionUtil.addAllIfNotEmpty(s1, l2));
        assertTrue(s1.contains("a"));
        assertTrue(s1.contains("1"));
        assertTrue(s1.contains("2"));
        assertTrue(s1.contains("3"));
        assertTrue(s1.contains(null));

        assertFalse(CollectionUtil.addAllIfNotEmpty(s1, null));
        assertFalse(CollectionUtil.addAllIfNotEmpty(s1, new HashSet<String>()));

        // 3
        Set<String> s2 = ArrayUtil.asSet("1", "2", "3", null); // LinkedHashSet
        assertFalse(CollectionUtil.addAllIfNotEmpty(s1, s2)); // no-change

        l1 = ArrayUtil.asList("a");
        assertTrue(CollectionUtil.addAllIfNotEmpty(l1, s2));
        assertEquals("a", l1.get(0));
        assertEquals("1", l1.get(1)); // with-orders
        assertEquals("2", l1.get(2));
        assertEquals("3", l1.get(3));
        assertNull(l1.get(4));
    }

    /**
     * 测试addEach方法
     */
    @Test
    public void testAddEach() {
        List<String> list = new ArrayList<String>();
        assertTrue(CollectionUtil.addEach(list, "0"));
        assertEquals("0", list.get(0));
        assertEquals(1, list.size());

        assertTrue(CollectionUtil.addEach(list, "1"));
        assertEquals("1", list.get(1));
        assertEquals(2, list.size());

        assertTrue(CollectionUtil.addEach(list, ArrayUtil.asList("2", "3")));
        assertEquals("2", list.get(2));
        assertEquals("3", list.get(3));
        assertEquals(4, list.size());

        assertTrue(CollectionUtil.addEach(list, ArrayUtil.asSet("4")));
        assertEquals("4", list.get(4));
        assertEquals(5, list.size());

        assertFalse(CollectionUtil.addEach(list, Collections.emptyList()));
        assertFalse(CollectionUtil.addEach(list, Collections.emptySet()));
        assertEquals(5, list.size());

        assertTrue(CollectionUtil.addEach(list, new String[]{"5", "6"}));
        assertEquals("5", list.get(5));
        assertEquals("6", list.get(6));
        assertEquals(7, list.size());

        assertFalse(CollectionUtil.addEach(list, new String[0]));
        assertEquals(7, list.size());

        assertTrue(CollectionUtil.addEach(list, ArrayUtil.asSet("0")));
        assertEquals("0", list.get(7));
        assertEquals(8, list.size());

        assertTrue(CollectionUtil.addEach(list, null));
        assertNull(list.get(8));
        assertEquals(9, list.size());

        Set<String> set = new HashSet<String>();
        assertTrue(CollectionUtil.addEach(set, "0"));
        assertTrue(set.contains("0"));
        assertEquals(1, set.size());

        assertTrue(CollectionUtil.addEach(set, "1"));
        assertTrue(set.contains("1"));
        assertEquals(2, set.size());

        assertTrue(CollectionUtil.addEach(set, ArrayUtil.asList("2", "3")));
        assertTrue(set.contains("2"));
        assertTrue(set.contains("3"));
        assertEquals(4, set.size());

        assertTrue(CollectionUtil.addEach(set, ArrayUtil.asSet("4")));
        assertTrue(set.contains("4"));
        assertEquals(5, set.size());

        assertFalse(CollectionUtil.addEach(set, Collections.emptyList()));
        assertFalse(CollectionUtil.addEach(set, Collections.emptySet()));
        assertEquals(5, set.size());

        assertTrue(CollectionUtil.addEach(set, new String[]{"5", "6"}));
        assertTrue(set.contains("5"));
        assertTrue(set.contains("6"));
        assertEquals(7, set.size());

        assertFalse(CollectionUtil.addEach(set, new String[0]));
        assertEquals(7, set.size());

        assertFalse(CollectionUtil.addEach(set, ArrayUtil.asSet("0", "1", "2")));
        assertEquals(7, set.size());

        assertTrue(CollectionUtil.addEach(set, ArrayUtil.asSet("0", "7")));
        assertTrue(set.contains("7"));
        assertEquals(8, set.size());

        assertTrue(CollectionUtil.addEach(set, null));
        assertTrue(set.contains(null));
        assertEquals(9, set.size());
    }

    /**
     * 测试addEachIfNotNull方法
     */
    @Test
    public void testAddEachIfNotNull() {
        List<String> list = new ArrayList<String>();
        assertTrue(CollectionUtil.addEachIfNotNull(list, "0"));
        assertEquals("0", list.get(0));
        assertEquals(1, list.size());

        assertTrue(CollectionUtil.addEachIfNotNull(list, "1"));
        assertEquals("1", list.get(1));
        assertEquals(2, list.size());

        assertTrue(CollectionUtil.addEachIfNotNull(list, ArrayUtil.asList("2", "3")));
        assertEquals("2", list.get(2));
        assertEquals("3", list.get(3));
        assertEquals(4, list.size());

        assertTrue(CollectionUtil.addEachIfNotNull(list, ArrayUtil.asSet("4")));
        assertEquals("4", list.get(4));
        assertEquals(5, list.size());

        assertFalse(CollectionUtil.addEachIfNotNull(list, Collections.emptyList()));
        assertFalse(CollectionUtil.addEachIfNotNull(list, Collections.emptySet()));
        assertEquals(5, list.size());

        assertTrue(CollectionUtil.addEachIfNotNull(list, new String[]{"5", "6"}));
        assertEquals("5", list.get(5));
        assertEquals("6", list.get(6));
        assertEquals(7, list.size());

        assertFalse(CollectionUtil.addEachIfNotNull(list, new String[0]));
        assertEquals(7, list.size());

        assertTrue(CollectionUtil.addEachIfNotNull(list, ArrayUtil.asSet("0")));
        assertEquals("0", list.get(7));
        assertEquals(8, list.size());

        assertFalse(CollectionUtil.addEachIfNotNull(list, null));
        assertEquals(8, list.size());

        Set<String> set = new HashSet<String>();
        assertTrue(CollectionUtil.addEachIfNotNull(set, "0"));
        assertTrue(set.contains("0"));
        assertEquals(1, set.size());

        assertTrue(CollectionUtil.addEachIfNotNull(set, "1"));
        assertTrue(set.contains("1"));
        assertEquals(2, set.size());

        assertTrue(CollectionUtil.addEachIfNotNull(set, ArrayUtil.asList("2", "3")));
        assertTrue(set.contains("2"));
        assertTrue(set.contains("3"));
        assertEquals(4, set.size());

        assertTrue(CollectionUtil.addEachIfNotNull(set, ArrayUtil.asSet("4")));
        assertTrue(set.contains("4"));
        assertEquals(5, set.size());

        assertFalse(CollectionUtil.addEachIfNotNull(set, Collections.emptyList()));
        assertFalse(CollectionUtil.addEachIfNotNull(set, Collections.emptySet()));
        assertEquals(5, set.size());

        assertTrue(CollectionUtil.addEachIfNotNull(set, new String[]{"5", "6"}));
        assertTrue(set.contains("5"));
        assertTrue(set.contains("6"));
        assertEquals(7, set.size());

        assertFalse(CollectionUtil.addEachIfNotNull(set, new String[0]));
        assertEquals(7, set.size());

        assertFalse(CollectionUtil.addEachIfNotNull(set, ArrayUtil.asSet("0", "1", "2")));
        assertEquals(7, set.size());

        assertTrue(CollectionUtil.addEachIfNotNull(set, ArrayUtil.asSet("0", "7")));
        assertTrue(set.contains("7"));
        assertEquals(8, set.size());

        assertFalse(CollectionUtil.addEachIfNotNull(set, null));
        assertFalse(set.contains(null));
        assertEquals(8, set.size());
    }

    @Test
    public void testFilter() {
        // list
        List<String> list = ArrayUtil.asList("1", "2", "3", null);
        CollectionUtil.filter(list, new CollectionUtil.Filter<String>() {
            public boolean retain(String element) {
                return element != null;
            }
        });

        assertEquals(3, list.size());
        assertEquals("1", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("3", list.get(2));

        CollectionUtil.filter(list, null);
        assertEquals(3, list.size());
        assertEquals("1", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("3", list.get(2));

        // Set
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);
        CollectionUtil.filter(set, new CollectionUtil.Filter<String>() {
            public boolean retain(String element) {
                return element != null;
            }
        });

        assertEquals(3, set.size());
        assertEquals("1", CollectionUtil.getElement(set, 0));
        assertEquals("2", CollectionUtil.getElement(set, 1));
        assertEquals("3", CollectionUtil.getElement(set, 2));

        CollectionUtil.filter(set, null);
        assertEquals(3, set.size());
        assertEquals("1", CollectionUtil.getElement(set, 0));
        assertEquals("2", CollectionUtil.getElement(set, 1));
        assertEquals("3", CollectionUtil.getElement(set, 2));

        // other
        CollectionUtil.filter(null, null);
    }

    /**
     * 测试单例列表类
     */
    static class SingletonList extends ArrayList<String> {
        private static final long serialVersionUID = 1L;

        private static SingletonList instance = new SingletonList();

        private SingletonList() {
        }

        public static SingletonList getInstance() {
            return instance;
        }
    }

    ;
}
