package com.base.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * 数组工具测试类
 * 
 *
 */
public class ArrayUtilTest {
	/**
	 * 测试isArray方法
	 */
	@Test
	public void testIsArray() {
		assertTrue(ArrayUtil.isArray(new int[0]));
		assertTrue(ArrayUtil.isArray(new long[0]));
		assertTrue(ArrayUtil.isArray(new float[0]));
		assertTrue(ArrayUtil.isArray(new double[0]));
		assertTrue(ArrayUtil.isArray(new short[0]));
		assertTrue(ArrayUtil.isArray(new boolean[0]));
		assertTrue(ArrayUtil.isArray(new char[0]));
		assertTrue(ArrayUtil.isArray(new Integer[0]));
		assertTrue(ArrayUtil.isArray(new Long[0]));
		assertTrue(ArrayUtil.isArray(new Float[0]));
		assertTrue(ArrayUtil.isArray(new Double[0]));
		assertTrue(ArrayUtil.isArray(new Short[0]));
		assertTrue(ArrayUtil.isArray(new Boolean[0]));
		assertTrue(ArrayUtil.isArray(new Character[0]));
		assertTrue(ArrayUtil.isArray(new String[0]));
		assertTrue(ArrayUtil.isArray(new Date[0]));
		assertTrue(ArrayUtil.isArray(new Object[0]));
		assertTrue(ArrayUtil.isArray(new int[0][0]));
		assertTrue(ArrayUtil.isArray(new Object[0][0]));

		assertFalse(ArrayUtil.isArray(1));
		assertFalse(ArrayUtil.isArray("1"));
		assertFalse(ArrayUtil.isArray(new Object()));
		assertFalse(ArrayUtil.isArray(new HashSet<String>()));
		assertFalse(ArrayUtil.isArray(new ArrayList<String>()));
		assertFalse(ArrayUtil.isArray(new HashMap<String, String>()));
		assertFalse(ArrayUtil.isArray(null));
	}

	/**
	 * 测试length方法
	 */
	@Test
	public void testLength() {
		// length(Object[])
		assertEquals(1, ArrayUtil.length(new String[] { "1" }));
		assertEquals(2, ArrayUtil.length(new String[] { "1", "2" }));
		assertEquals(0, ArrayUtil.length(new String[0]));
		assertEquals(0, ArrayUtil.length((String[]) null));

		// length(Object[][])
		assertEquals(3, ArrayUtil.length((String[]) null, new String[] { "1" }, new String[] { "1", "2" }));
		assertEquals(0, ArrayUtil.length((Object[][]) null));

		// length(int[])
		assertEquals(1, ArrayUtil.length(new int[] { 1 }));
		assertEquals(2, ArrayUtil.length(new int[] { 1, 2 }));
		assertEquals(0, ArrayUtil.length(new int[0]));
		assertEquals(0, ArrayUtil.length((int[]) null));

		// length(int[][])
		assertEquals(3, ArrayUtil.length((int[]) null, new int[] { 1 }, new int[] { 1, 2 }));
		assertEquals(0, ArrayUtil.length((int[][]) null));
	}

	/**
	 * 测试isEmpty方法
	 */
	@Test
	public void testIsEmpty() {
		// isEmpty(Object[])
		assertTrue(ArrayUtil.isEmpty((String[]) null));
		assertTrue(ArrayUtil.isEmpty(new String[0]));
		assertFalse(ArrayUtil.isEmpty(new String[] { "" }));

		// isEmpty(Object[])
		assertTrue(ArrayUtil.isEmpty((int[]) null));
		assertTrue(ArrayUtil.isEmpty(new int[0]));
		assertFalse(ArrayUtil.isEmpty(new int[] { 0 }));
	}

	/**
	 * 测试isNotEmpty方法
	 */
	@Test
	public void testIsNotEmpty() {
		// isNotEmpty(Object[])
		assertFalse(ArrayUtil.isNotEmpty((String[]) null));
		assertFalse(ArrayUtil.isNotEmpty(new String[0]));
		assertTrue(ArrayUtil.isNotEmpty(new String[] { "" }));

		// isNotEmpty(int[])
		assertFalse(ArrayUtil.isNotEmpty((int[]) null));
		assertFalse(ArrayUtil.isNotEmpty(new int[0]));
		assertTrue(ArrayUtil.isNotEmpty(new int[] { 0 }));
	}

	/**
	 * 测试contains方法
	 */
	@Test
	public void testContains() {
		// contains(Object[])
		String[] sArr = { "1", "2", "3", null };
		assertTrue(ArrayUtil.contains(sArr, "1"));
		assertTrue(ArrayUtil.contains(sArr, "3"));
		assertTrue(ArrayUtil.contains(sArr, null));
		assertFalse(ArrayUtil.contains(sArr, "4"));
		assertFalse(ArrayUtil.contains(new String[0], ""));
		assertFalse(ArrayUtil.contains(null, ""));

		// contains(int[])
		int[] iArr = { 1, 2, 3, 0 };
		assertTrue(ArrayUtil.contains(iArr, 1));
		assertTrue(ArrayUtil.contains(iArr, 3));
		assertTrue(ArrayUtil.contains(iArr, 0));
		assertFalse(ArrayUtil.contains(iArr, 4));
		assertFalse(ArrayUtil.contains(new int[0], 0));
		assertFalse(ArrayUtil.contains(null, 0));
	}

	/**
	 * 测试containsAny方法
	 */
	@Test
	public void testContainsAny() {
		// containsAny(Object[])
		String[] sArr = { "1", "2", "3", null };
		assertTrue(ArrayUtil.containsAny(sArr, "1"));
		assertTrue(ArrayUtil.containsAny(sArr, "3"));
		assertTrue(ArrayUtil.containsAny(sArr, (String) null));
		assertFalse(ArrayUtil.containsAny(sArr, "4"));
		assertFalse(ArrayUtil.containsAny(new String[0], ""));
		assertFalse(ArrayUtil.containsAny(null, ""));
		assertTrue(ArrayUtil.containsAny(sArr, "1", "0"));
		assertTrue(ArrayUtil.containsAny(sArr, "0", "1"));
		assertTrue(ArrayUtil.containsAny(sArr, "3", "0"));
		assertTrue(ArrayUtil.containsAny(sArr, (String) null, "0"));
		assertFalse(ArrayUtil.containsAny(sArr, "4", "0"));
		assertFalse(ArrayUtil.containsAny(sArr, "0", "4"));
		assertFalse(ArrayUtil.containsAny(new String[0], "", "0"));
		assertFalse(ArrayUtil.containsAny(null, "", "0"));
		assertFalse(ArrayUtil.containsAny((String[]) null));

		// containsAny(int[])
		int[] iArr = { 1, 2, 3, 0 };
		assertTrue(ArrayUtil.containsAny(iArr, 1));
		assertTrue(ArrayUtil.containsAny(iArr, 3));
		assertTrue(ArrayUtil.containsAny(iArr, 0));
		assertFalse(ArrayUtil.containsAny(iArr, 4));
		assertFalse(ArrayUtil.containsAny(new int[0], 0));
		assertFalse(ArrayUtil.containsAny((int[]) null, 0));
		assertTrue(ArrayUtil.containsAny(iArr, 1, 0));
		assertTrue(ArrayUtil.containsAny(iArr, 0, 1));
		assertTrue(ArrayUtil.containsAny(iArr, 3, 0));
		assertTrue(ArrayUtil.containsAny(iArr, 0, 0));
		assertFalse(ArrayUtil.containsAny(iArr, 4, -1));
		assertFalse(ArrayUtil.containsAny(iArr, -1, 4));
		assertFalse(ArrayUtil.containsAny(new int[0], 0, 0));
		assertFalse(ArrayUtil.containsAny((int[]) null, 0, 0));
		assertFalse(ArrayUtil.containsAny((int[]) null));
	}

	/**
	 * 测试containsAll方法
	 */
	@Test
	public void testContainsAll() {
		// containsAll(Object[])
		String[] sArr = { "1", "2", "3", null };
		assertTrue(ArrayUtil.containsAll(sArr, "1"));
		assertTrue(ArrayUtil.containsAll(sArr, "3"));
		assertTrue(ArrayUtil.containsAll(sArr, (String) null));
		assertFalse(ArrayUtil.containsAll(sArr, "4"));
		assertFalse(ArrayUtil.containsAll(new String[0], ""));
		assertFalse(ArrayUtil.containsAll(null, ""));
		assertTrue(ArrayUtil.containsAll(sArr, "1", "2"));
		assertTrue(ArrayUtil.containsAll(sArr, "2", "1"));
		assertTrue(ArrayUtil.containsAll(sArr, "3", "2"));
		assertTrue(ArrayUtil.containsAll(sArr, (String) null, "2"));
		assertFalse(ArrayUtil.containsAll(sArr, "4", "2"));
		assertFalse(ArrayUtil.containsAll(sArr, "2", "4"));
		assertFalse(ArrayUtil.containsAll(new String[0], "", "2"));
		assertFalse(ArrayUtil.containsAll(null, "", "2"));
		assertFalse(ArrayUtil.containsAll((String[]) null));

		// containsAll(int[])
		int[] iArr = { 1, 2, 3, 0 };
		assertTrue(ArrayUtil.containsAll(iArr, 1));
		assertTrue(ArrayUtil.containsAll(iArr, 3));
		assertTrue(ArrayUtil.containsAll(iArr, 0));
		assertFalse(ArrayUtil.containsAll(iArr, 4));
		assertFalse(ArrayUtil.containsAll(new int[0], 0));
		assertFalse(ArrayUtil.containsAll((int[]) null, 0));
		assertTrue(ArrayUtil.containsAll(iArr, 1, 2));
		assertTrue(ArrayUtil.containsAll(iArr, 2, 1));
		assertTrue(ArrayUtil.containsAll(iArr, 3, 2));
		assertTrue(ArrayUtil.containsAll(iArr, 0, 2));
		assertFalse(ArrayUtil.containsAll(iArr, 4, 2));
		assertFalse(ArrayUtil.containsAll(iArr, 2, 4));
		assertFalse(ArrayUtil.containsAll(new int[0], 0, 2));
		assertFalse(ArrayUtil.containsAll((int[]) null, 0, 2));
		assertFalse(ArrayUtil.containsAll((int[]) null));
	}

	/**
	 * 测试getElement方法
	 */
	@Test
	public void testGetElement() {
		// getElement(Object[])
		// 1
		String[] sArr = { "1", "2", "3", null };
		assertEquals("1", ArrayUtil.getElement(sArr, 0));
		assertEquals("3", ArrayUtil.getElement(sArr, 2));
		assertEquals("2", ArrayUtil.getElement(sArr, -3));
		assertNull(ArrayUtil.getElement(sArr, 3));
		assertNull(ArrayUtil.getElement(sArr, -999));
		assertNull(ArrayUtil.getElement(sArr, 999));
		assertNull(ArrayUtil.getElement((String[]) null, 0));
		assertNull(ArrayUtil.getElement(new String[0], 0));

		// 2
		assertEquals("1", ArrayUtil.getElement(sArr, 0, "4"));
		assertEquals("3", ArrayUtil.getElement(sArr, 2, "4"));
		assertEquals("2", ArrayUtil.getElement(sArr, -3, "4"));
		assertNull(ArrayUtil.getElement(sArr, -1, "4")); // 只有下标越界时才会返回defElement
		assertEquals("4", ArrayUtil.getElement(sArr, -999, "4"));
		assertNull(ArrayUtil.getElement(sArr, 3, "4"));
		assertEquals("4", ArrayUtil.getElement(sArr, 999, "4"));
		assertEquals("4", ArrayUtil.getElement(null, 3, "4"));
		assertEquals("4", ArrayUtil.getElement(new String[0], 0, "4"));

		// getElement(int[])
		// 1
		int[] iArr = { 1, 2, 3, 0 };
		assertEquals(1, ArrayUtil.getElement(iArr, 0));
		assertEquals(3, ArrayUtil.getElement(iArr, 2));
		assertEquals(2, ArrayUtil.getElement(iArr, -3));
		assertEquals(0, ArrayUtil.getElement(iArr, 3));
		assertEquals(0, ArrayUtil.getElement(iArr, -999));
		assertEquals(0, ArrayUtil.getElement(iArr, 999));
		assertEquals(0, ArrayUtil.getElement((int[]) null, 0));
		assertEquals(0, ArrayUtil.getElement(new int[0], 0));

		// 2
		assertEquals(1, ArrayUtil.getElement(iArr, 0, 4));
		assertEquals(3, ArrayUtil.getElement(iArr, 2, 4));
		assertEquals(2, ArrayUtil.getElement(iArr, -3, 4));
		assertEquals(0, ArrayUtil.getElement(iArr, -1, 4)); // 只有下标越界时才会返回defElement
		assertEquals(4, ArrayUtil.getElement(iArr, -999, 4));
		assertEquals(0, ArrayUtil.getElement(iArr, 3, 4));
		assertEquals(4, ArrayUtil.getElement(iArr, 999, 4));
		assertEquals(4, ArrayUtil.getElement(null, 3, 4));
		assertEquals(4, ArrayUtil.getElement(new String[0], 0, 4));
	}

	/**
	 * 测试getElements方法
	 */
	@Test
	public void testGetElements() {
		// 1 getElements(Object[], offset, length)
		final String[] sArr = { "1", "2", "3", null };
		String[] subArr = ArrayUtil.getElements(sArr, 0, 4);

		assertEquals(4, subArr.length);
		assertEquals("1", subArr[0]);
		assertNull(subArr[3]);

		// 2
		subArr = ArrayUtil.getElements(sArr, 0, 10);
		assertEquals(4, subArr.length);
		assertEquals("1", subArr[0]);
		assertNull(subArr[3]);

		// 3
		subArr = ArrayUtil.getElements(sArr, -999, 2);
		assertEquals(2, subArr.length);
		assertEquals("1", subArr[0]);
		assertEquals("2", subArr[1]);

		// 4
		subArr = ArrayUtil.getElements(sArr, -2, 1);
		assertEquals(1, subArr.length);
		assertEquals("3", subArr[0]);

		// 5
		subArr = ArrayUtil.getElements(sArr, -2, 999);
		assertEquals(2, subArr.length);
		assertEquals("3", subArr[0]);
		assertNull(subArr[1]);

		// 6
		subArr = ArrayUtil.getElements(sArr, 0, 0);
		assertEquals(0, subArr.length);

		// 7
		subArr = ArrayUtil.getElements(sArr, -1, 0);
		assertEquals(0, subArr.length);

		// 8
		subArr = ArrayUtil.getElements(sArr, 4, 1);
		assertEquals(0, subArr.length);

		// 9
		subArr = ArrayUtil.getElements(sArr, 0, -1);
		assertEquals(0, subArr.length);

		// 10
		subArr = ArrayUtil.getElements((String[]) null, 0, 4);
		assertNull(subArr);

		// 11
		subArr = ArrayUtil.getElements((String[]) null, -1, 10);
		assertNull(subArr);

		// 1 getElements(int[], offset, length)
		final int[] iArr = { 1, 2, 3, 0 };
		int[] iSubArr = ArrayUtil.getElements(iArr, 0, 4);

		assertEquals(4, iSubArr.length);
		assertEquals(1, iSubArr[0]);
		assertEquals(0, iSubArr[3]);

		// 2
		iSubArr = ArrayUtil.getElements(iArr, 0, 10);
		assertEquals(4, iSubArr.length);
		assertEquals(1, iSubArr[0]);
		assertEquals(0, iSubArr[3]);

		// 3
		iSubArr = ArrayUtil.getElements(iArr, -999, 2);
		assertEquals(2, iSubArr.length);
		assertEquals(1, iSubArr[0]);
		assertEquals(2, iSubArr[1]);

		// 4
		iSubArr = ArrayUtil.getElements(iArr, -2, 1);
		assertEquals(1, iSubArr.length);
		assertEquals(3, iSubArr[0]);

		// 5
		iSubArr = ArrayUtil.getElements(iArr, -2, 999);
		assertEquals(2, iSubArr.length);
		assertEquals(3, iSubArr[0]);
		assertEquals(0, iSubArr[1]);

		// 6
		iSubArr = ArrayUtil.getElements(iArr, 0, 0);
		assertEquals(0, iSubArr.length);

		// 7
		iSubArr = ArrayUtil.getElements(iArr, -1, 0);
		assertEquals(0, iSubArr.length);

		// 8
		iSubArr = ArrayUtil.getElements(iArr, 4, 1);
		assertEquals(0, iSubArr.length);

		// 9
		iSubArr = ArrayUtil.getElements(iArr, 0, -1);
		assertEquals(0, iSubArr.length);

		// 10
		iSubArr = ArrayUtil.getElements((int[]) null, 0, 4);
		assertNull(iSubArr);

		// 11
		iSubArr = ArrayUtil.getElements((int[]) null, -1, 10);
		assertNull(iSubArr);
	}

	/**
	 * 测试subArray方法
	 */
	@Test
	public void testSubArray() {
		final String[] sArr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		// subArray(Object[], start, end[exclude])
		// 1
		String[] subArr = ArrayUtil.subArray(sArr, 1, 3);
		assertEquals(2, subArr.length);
		assertEquals(sArr[1], subArr[0]);
		assertEquals(sArr[2], subArr[1]);

		// 2
		subArr = ArrayUtil.subArray(sArr, 1, 999);
		assertEquals(9, subArr.length);
		assertEquals(sArr[1], subArr[0]);
		assertEquals(sArr[9], subArr[8]);

		// 3
		subArr = ArrayUtil.subArray(sArr, -3, 9);
		assertEquals(2, subArr.length);
		assertEquals(sArr[7], subArr[0]);
		assertEquals(sArr[8], subArr[1]);

		// 4
		subArr = ArrayUtil.subArray(sArr, -999, 1);
		assertEquals(1, subArr.length);
		assertEquals(sArr[0], subArr[0]);

		// 5
		subArr = ArrayUtil.subArray(sArr, -999, 999);
		assertEquals(10, subArr.length);
		assertEquals(sArr[0], subArr[0]);
		assertEquals(sArr[9], subArr[9]);

		// 6
		subArr = ArrayUtil.subArray(sArr, 5, -2);
		assertEquals(3, subArr.length);
		assertEquals(sArr[5], subArr[0]);
		assertEquals(sArr[7], subArr[2]);

		// 7
		subArr = ArrayUtil.subArray(sArr, -4, -2);
		assertEquals(2, subArr.length);
		assertEquals(sArr[6], subArr[0]);
		assertEquals(sArr[7], subArr[1]);

		// 8
		subArr = ArrayUtil.subArray(sArr, 0, 0);
		assertArrayEquals(sArr, subArr);

		// 9
		subArr = ArrayUtil.subArray(sArr, 1, 0);
		assertEquals(9, subArr.length);
		assertEquals(sArr[1], subArr[0]);
		assertEquals(sArr[9], subArr[8]);

		// 10
		subArr = ArrayUtil.subArray(sArr, -1, 0);
		assertEquals(1, subArr.length);
		assertEquals(sArr[9], subArr[0]);

		// 11
		subArr = ArrayUtil.subArray(sArr, 4, 2);
		assertEquals(0, subArr.length);

		// 12
		subArr = ArrayUtil.subArray(sArr, 100, 102);
		assertEquals(0, subArr.length);

		// 13
		subArr = ArrayUtil.subArray(sArr, -1, -2);
		assertEquals(0, subArr.length);

		// 14
		subArr = ArrayUtil.subArray(sArr, 0, -999);
		assertEquals(0, subArr.length);

		// 15
		subArr = ArrayUtil.subArray(new String[0], 0, 1);
		assertEquals(0, subArr.length);

		// 16
		assertNull(ArrayUtil.subArray((String[]) null, 0, 1));

		// subArray(Object[], start)
		// 1
		subArr = ArrayUtil.subArray(sArr, 1);
		assertEquals(9, subArr.length);
		assertEquals(sArr[1], subArr[0]);
		assertEquals(sArr[9], subArr[8]);

		// 2
		subArr = ArrayUtil.subArray(sArr, 10);
		assertEquals(0, subArr.length);

		// 3
		subArr = ArrayUtil.subArray(sArr, 999);
		assertEquals(0, subArr.length);

		// 4
		subArr = ArrayUtil.subArray(sArr, -9);
		assertEquals(9, subArr.length);
		assertEquals(sArr[1], subArr[0]);
		assertEquals(sArr[9], subArr[8]);

		// 5
		subArr = ArrayUtil.subArray(sArr, -10);
		assertEquals(10, subArr.length);
		assertEquals(sArr[0], subArr[0]);
		assertEquals(sArr[9], subArr[9]);

		// 6
		subArr = ArrayUtil.subArray(sArr, -999);
		assertEquals(10, subArr.length);
		assertEquals(sArr[0], subArr[0]);
		assertEquals(sArr[9], subArr[9]);

		// 7
		assertNull(ArrayUtil.subArray((String[]) null, 0));

		final int[] iArr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		// iSubArray(int[], start, end[exclude])
		// 1
		int[] iSubArr = ArrayUtil.subArray(iArr, 1, 3);
		assertEquals(2, iSubArr.length);
		assertEquals(iArr[1], iSubArr[0]);
		assertEquals(iArr[2], iSubArr[1]);

		// 2
		iSubArr = ArrayUtil.subArray(iArr, 1, 999);
		assertEquals(9, iSubArr.length);
		assertEquals(iArr[1], iSubArr[0]);
		assertEquals(iArr[9], iSubArr[8]);

		// 3
		iSubArr = ArrayUtil.subArray(iArr, -3, 9);
		assertEquals(2, iSubArr.length);
		assertEquals(iArr[7], iSubArr[0]);
		assertEquals(iArr[8], iSubArr[1]);

		// 4
		iSubArr = ArrayUtil.subArray(iArr, -999, 1);
		assertEquals(1, iSubArr.length);
		assertEquals(iArr[0], iSubArr[0]);

		// 5
		iSubArr = ArrayUtil.subArray(iArr, -999, 999);
		assertEquals(10, iSubArr.length);
		assertEquals(iArr[0], iSubArr[0]);
		assertEquals(iArr[9], iSubArr[9]);

		// 6
		iSubArr = ArrayUtil.subArray(iArr, 5, -2);
		assertEquals(3, iSubArr.length);
		assertEquals(iArr[5], iSubArr[0]);
		assertEquals(iArr[7], iSubArr[2]);

		// 7
		iSubArr = ArrayUtil.subArray(iArr, -4, -2);
		assertEquals(2, iSubArr.length);
		assertEquals(iArr[6], iSubArr[0]);
		assertEquals(iArr[7], iSubArr[1]);

		// 8
		iSubArr = ArrayUtil.subArray(iArr, 0, 0);
		assertArrayEquals(iArr, iSubArr);

		// 9
		iSubArr = ArrayUtil.subArray(iArr, 1, 0);
		assertEquals(9, iSubArr.length);
		assertEquals(iArr[1], iSubArr[0]);
		assertEquals(iArr[9], iSubArr[8]);

		// 10
		iSubArr = ArrayUtil.subArray(iArr, -1, 0);
		assertEquals(1, iSubArr.length);
		assertEquals(iArr[9], iSubArr[0]);

		// 11
		iSubArr = ArrayUtil.subArray(iArr, 4, 2);
		assertEquals(0, iSubArr.length);

		// 12
		iSubArr = ArrayUtil.subArray(iArr, 100, 102);
		assertEquals(0, iSubArr.length);

		// 13
		iSubArr = ArrayUtil.subArray(iArr, -1, -2);
		assertEquals(0, iSubArr.length);

		// 14
		iSubArr = ArrayUtil.subArray(iArr, 0, -999);
		assertEquals(0, iSubArr.length);

		// 15
		iSubArr = ArrayUtil.subArray(new int[0], 0, 1);
		assertEquals(0, iSubArr.length);

		// 16
		assertNull(ArrayUtil.subArray((int[]) null, 0, 1));

		// iSubArray(int[], start)
		// 1
		iSubArr = ArrayUtil.subArray(iArr, 1);
		assertEquals(9, iSubArr.length);
		assertEquals(iArr[1], iSubArr[0]);
		assertEquals(iArr[9], iSubArr[8]);

		// 2
		iSubArr = ArrayUtil.subArray(iArr, 10);
		assertEquals(0, iSubArr.length);

		// 3
		iSubArr = ArrayUtil.subArray(iArr, 999);
		assertEquals(0, iSubArr.length);

		// 4
		iSubArr = ArrayUtil.subArray(iArr, -9);
		assertEquals(9, iSubArr.length);
		assertEquals(iArr[1], iSubArr[0]);
		assertEquals(iArr[9], iSubArr[8]);

		// 5
		iSubArr = ArrayUtil.subArray(iArr, -10);
		assertEquals(10, iSubArr.length);
		assertEquals(iArr[0], iSubArr[0]);
		assertEquals(iArr[9], iSubArr[9]);

		// 6
		iSubArr = ArrayUtil.subArray(iArr, -999);
		assertEquals(10, iSubArr.length);
		assertEquals(iArr[0], iSubArr[0]);
		assertEquals(iArr[9], iSubArr[9]);

		// 7
		assertNull(ArrayUtil.subArray((int[]) null, 0));
	}

	/**
	 * 测试split方法
	 */
	@Test
	public void testSplit() {
		// split(Object[])
		// 1
		final String[] sArr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[][] sepArrs = ArrayUtil.split(sArr, 10);
		assertEquals(1, sepArrs.length);
		assertEquals(10, sepArrs[0].length);
		assertEquals("9", sepArrs[0][9]);

		// 2
		sepArrs = ArrayUtil.split(sArr, 5);
		assertEquals(2, sepArrs.length);
		assertEquals(5, sepArrs[0].length);
		assertEquals(5, sepArrs[1].length);
		assertEquals("4", sepArrs[0][4]);
		assertEquals("9", sepArrs[1][4]);

		// 3
		sepArrs = ArrayUtil.split(sArr, 3);
		assertEquals(4, sepArrs.length);
		assertEquals(3, sepArrs[0].length);
		assertEquals(3, sepArrs[1].length);
		assertEquals(3, sepArrs[2].length);
		assertEquals(1, sepArrs[3].length);
		assertEquals("2", sepArrs[0][2]);
		assertEquals("5", sepArrs[1][2]);
		assertEquals("8", sepArrs[2][2]);
		assertEquals("9", sepArrs[3][0]);

		// 4
		sepArrs = ArrayUtil.split(sArr, 3, true);
		assertEquals(4, sepArrs.length);
		assertEquals(3, sepArrs[0].length);
		assertEquals(3, sepArrs[1].length);
		assertEquals(3, sepArrs[2].length);
		assertEquals(3, sepArrs[3].length);
		assertEquals("2", sepArrs[0][2]);
		assertEquals("5", sepArrs[1][2]);
		assertEquals("8", sepArrs[2][2]);
		assertEquals("9", sepArrs[3][0]);
		assertEquals(null, sepArrs[3][2]);

		// 5
		sepArrs = ArrayUtil.split(sArr, 20);
		assertEquals(1, sepArrs.length);
		assertEquals(10, sepArrs[0].length);
		assertEquals("9", sepArrs[0][9]);

		// 6
		sepArrs = ArrayUtil.split(sArr, 20, true);
		assertEquals(1, sepArrs.length);
		assertEquals(20, sepArrs[0].length);
		assertEquals("9", sepArrs[0][9]);
		assertEquals(null, sepArrs[0][19]);

		// 7
		sepArrs = ArrayUtil.split(sArr, 0);
		assertEquals(1, sepArrs.length);
		assertEquals(10, sepArrs[0].length);
		assertEquals("9", sepArrs[0][9]);

		// 8
		sepArrs = ArrayUtil.split(sArr, -1);
		assertEquals(1, sepArrs.length);
		assertEquals(10, sepArrs[0].length);
		assertEquals("9", sepArrs[0][9]);

		// 9
		sepArrs = ArrayUtil.split(new String[0], 10);
		assertEquals(1, sepArrs.length);
		assertEquals(0, sepArrs[0].length);

		// 10
		sepArrs = ArrayUtil.split((String[]) null, 10);
		assertNull(sepArrs);

		// 11
		sepArrs = ArrayUtil.split(sArr, 10);
		sArr[0] = "00"; // sArr != sArr[0]
		assertEquals("0", sepArrs[0][0]);

		// split(int[])
		// 1
		final int[] iArr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[][] iSepArrs = ArrayUtil.split(iArr, 10);
		assertEquals(1, iSepArrs.length);
		assertEquals(10, iSepArrs[0].length);
		assertEquals(9, iSepArrs[0][9]);

		// 2
		iSepArrs = ArrayUtil.split(iArr, 5);
		assertEquals(2, iSepArrs.length);
		assertEquals(5, iSepArrs[0].length);
		assertEquals(5, iSepArrs[1].length);
		assertEquals(4, iSepArrs[0][4]);
		assertEquals(9, iSepArrs[1][4]);

		// 3
		iSepArrs = ArrayUtil.split(iArr, 3);
		assertEquals(4, iSepArrs.length);
		assertEquals(3, iSepArrs[0].length);
		assertEquals(3, iSepArrs[1].length);
		assertEquals(3, iSepArrs[2].length);
		assertEquals(1, iSepArrs[3].length);
		assertEquals(2, iSepArrs[0][2]);
		assertEquals(5, iSepArrs[1][2]);
		assertEquals(8, iSepArrs[2][2]);
		assertEquals(9, iSepArrs[3][0]);

		// 4
		iSepArrs = ArrayUtil.split(iArr, 3, true);
		assertEquals(4, iSepArrs.length);
		assertEquals(3, iSepArrs[0].length);
		assertEquals(3, iSepArrs[1].length);
		assertEquals(3, iSepArrs[2].length);
		assertEquals(3, iSepArrs[3].length);
		assertEquals(2, iSepArrs[0][2]);
		assertEquals(5, iSepArrs[1][2]);
		assertEquals(8, iSepArrs[2][2]);
		assertEquals(9, iSepArrs[3][0]);
		assertEquals(0, iSepArrs[3][2]);

		// 5
		iSepArrs = ArrayUtil.split(iArr, 20);
		assertEquals(1, iSepArrs.length);
		assertEquals(10, iSepArrs[0].length);
		assertEquals(9, iSepArrs[0][9]);

		// 6
		iSepArrs = ArrayUtil.split(iArr, 20, true);
		assertEquals(1, iSepArrs.length);
		assertEquals(20, iSepArrs[0].length);
		assertEquals(9, iSepArrs[0][9]);
		assertEquals(0, iSepArrs[0][19]);

		// 7
		iSepArrs = ArrayUtil.split(iArr, 0);
		assertEquals(1, iSepArrs.length);
		assertEquals(10, iSepArrs[0].length);
		assertEquals(9, iSepArrs[0][9]);

		// 8
		iSepArrs = ArrayUtil.split(iArr, -1);
		assertEquals(1, iSepArrs.length);
		assertEquals(10, iSepArrs[0].length);
		assertEquals(9, iSepArrs[0][9]);

		// 9
		iSepArrs = ArrayUtil.split(new int[0], 10);
		assertEquals(1, iSepArrs.length);
		assertEquals(0, iSepArrs[0].length);

		// 10
		iSepArrs = ArrayUtil.split((int[]) null, 10);
		assertNull(iSepArrs);

		// 11
		iSepArrs = ArrayUtil.split(iArr, 10);
		iArr[0] = 00; // iArr != iArr[0]
		assertEquals(0, iSepArrs[0][0]);

		// split(byte[])
		// 1
		final byte[] bArr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		byte[][] bSepArrs = ArrayUtil.split(bArr, 10);
		assertEquals(1, bSepArrs.length);
		assertEquals(10, bSepArrs[0].length);
		assertEquals(9, bSepArrs[0][9]);

		// 2
		bSepArrs = ArrayUtil.split(bArr, 5);
		assertEquals(2, bSepArrs.length);
		assertEquals(5, bSepArrs[0].length);
		assertEquals(5, bSepArrs[1].length);
		assertEquals(4, bSepArrs[0][4]);
		assertEquals(9, bSepArrs[1][4]);

		// 3
		bSepArrs = ArrayUtil.split(bArr, 3);
		assertEquals(4, bSepArrs.length);
		assertEquals(3, bSepArrs[0].length);
		assertEquals(3, bSepArrs[1].length);
		assertEquals(3, bSepArrs[2].length);
		assertEquals(1, bSepArrs[3].length);
		assertEquals(2, bSepArrs[0][2]);
		assertEquals(5, bSepArrs[1][2]);
		assertEquals(8, bSepArrs[2][2]);
		assertEquals(9, bSepArrs[3][0]);

		// 4
		bSepArrs = ArrayUtil.split(bArr, 3, true);
		assertEquals(4, bSepArrs.length);
		assertEquals(3, bSepArrs[0].length);
		assertEquals(3, bSepArrs[1].length);
		assertEquals(3, bSepArrs[2].length);
		assertEquals(3, bSepArrs[3].length);
		assertEquals(2, bSepArrs[0][2]);
		assertEquals(5, bSepArrs[1][2]);
		assertEquals(8, bSepArrs[2][2]);
		assertEquals(9, bSepArrs[3][0]);
		assertEquals(0, bSepArrs[3][2]);

		// 5
		bSepArrs = ArrayUtil.split(bArr, 20);
		assertEquals(1, bSepArrs.length);
		assertEquals(10, bSepArrs[0].length);
		assertEquals(9, bSepArrs[0][9]);

		// 6
		bSepArrs = ArrayUtil.split(bArr, 20, true);
		assertEquals(1, bSepArrs.length);
		assertEquals(20, bSepArrs[0].length);
		assertEquals(9, bSepArrs[0][9]);
		assertEquals(0, bSepArrs[0][19]);

		// 7
		bSepArrs = ArrayUtil.split(bArr, 0);
		assertEquals(1, bSepArrs.length);
		assertEquals(10, bSepArrs[0].length);
		assertEquals(9, bSepArrs[0][9]);

		// 8
		bSepArrs = ArrayUtil.split(bArr, -1);
		assertEquals(1, bSepArrs.length);
		assertEquals(10, bSepArrs[0].length);
		assertEquals(9, bSepArrs[0][9]);

		// 9
		bSepArrs = ArrayUtil.split(new byte[0], 10);
		assertEquals(1, bSepArrs.length);
		assertEquals(0, bSepArrs[0].length);

		// 10
		bSepArrs = ArrayUtil.split((byte[]) null, 10);
		assertNull(bSepArrs);

		// 11
		bSepArrs = ArrayUtil.split(bArr, 10);
		bArr[0] = 00; // bArr != bArr[0]
		assertEquals(0, bSepArrs[0][0]);
	}

	/**
	 * 测试concat方法
	 */
	@Test
	public void testConcat() {
		// concat(Object[][])
		final Object[] arr1 = { "a", 'a', (byte) 1 };
		final Object[] arr2 = ArrayUtil.asArray(null, 1, new Object());
		final String[] arr3 = { "a", "测试", "C" };
		final Object[] arr4 = ArrayUtil.concat((Integer[]) null, arr1, arr2, arr3);
		assertEquals(arr1[0], arr4[0]);
		assertEquals(arr1[1], arr4[1]);
		assertEquals(arr1[2], arr4[2]);
		assertEquals(arr2[0], arr4[3]);
		assertEquals(arr2[1], arr4[4]);
		assertEquals(arr2[2], arr4[5]);
		assertEquals(arr3[0], arr4[6]);
		assertEquals(arr3[1], arr4[7]);
		assertEquals(arr3[2], arr4[8]);

		assertEquals(0, ArrayUtil.concat((Object[]) null, new Integer[0]).length);
		assertEquals(1, ArrayUtil.concat((Object[]) null, new Object[] { null }).length);
		assertNull(ArrayUtil.concat((Object[][]) null));

		// concat(int[][])
		final int[] iArr1 = { 1, 2, 3 };
		final int[] iArr2 = { -1, 0, 1 };
		final int[] iArr3 = { -3, -2, -1 };
		final int[] iArr4 = ArrayUtil.concat((int[]) null, iArr1, iArr2, iArr3);
		assertEquals(iArr1[0], iArr4[0]);
		assertEquals(iArr1[1], iArr4[1]);
		assertEquals(iArr1[2], iArr4[2]);
		assertEquals(iArr2[0], iArr4[3]);
		assertEquals(iArr2[1], iArr4[4]);
		assertEquals(iArr2[2], iArr4[5]);
		assertEquals(iArr3[0], iArr4[6]);
		assertEquals(iArr3[1], iArr4[7]);
		assertEquals(iArr3[2], iArr4[8]);

		assertEquals(0, ArrayUtil.concat((int[]) null, new int[0]).length);
		assertEquals(1, ArrayUtil.concat((int[]) null, new int[] { 0 }).length);
		assertNull(ArrayUtil.concat((int[][]) null));
	}

	/**
	 * 测试newArray方法
	 */
	@Test
	public void testNewArray() {
		// 1
		Object[] array = ArrayUtil.newArray(new Object[0], new int[] { 5 });
		assertEquals(5, array.length);
		array[0] = new Object();

		// 2
		array = ArrayUtil.newArray(Integer.class, 5);
		assertEquals(5, array.length);
		array[0] = Integer.valueOf(1);
		try {
			array[1] = new Object(); // ClassCastException
			Assert.fail();
		} catch (Exception e) {
		}

		// 3
		array = ArrayUtil.newArray(new Long[0], new int[] { 2 });
		assertEquals(2, array.length);
		array[0] = Long.valueOf(1L);
		try {
			array[1] = new Object(); // ClassCastException
			Assert.fail();
		} catch (Exception e) {
		}

		// 4
		array = ArrayUtil.newArray(Double.valueOf(1D), 5);
		assertEquals(5, array.length);
		array[0] = Double.valueOf(1L);
		try {
			array[1] = new Object(); // ClassCastException
			Assert.fail();
		} catch (Exception e) {
		}

		// 5
		array = ArrayUtil.newArray(new Void[0], 0);
		assertEquals(0, array.length);

		// 6
		try {
			array = ArrayUtil.newArray(Integer.TYPE, 5); // ClassCastException
			Assert.fail();
		} catch (Exception e) {
		}

		// 7
		String[][] sArr = ArrayUtil.newArray(new String[0], new int[] { 5, 5 });
		assertEquals(5, sArr.length);
		assertEquals(5, sArr[0].length);
		sArr[0][0] = "Test";

		// 8
		assertNull(ArrayUtil.newArray(new Object[0], new int[] { 1, -1 }));
		assertNull(ArrayUtil.newArray(new Object(), new int[] { -1 }));
		assertNull(ArrayUtil.newArray(null, new int[] { 1 }));

		// 9
		assertNull(ArrayUtil.newArray(new Object(), -1));
		assertNull(ArrayUtil.newArray(null, 1));

		// 10
		long[] lArr = ArrayUtil.newArray(Long.TYPE, 1);
		assertEquals(1, lArr.length);
		lArr[0] = 1L;

		// 11
		int[] dimensions = new int[256];
		for (int i = 0; i < dimensions.length; i++) {
			dimensions[i] = 1;
		}
		// 数组维数最高为255
		assertNull(ArrayUtil.newArray(new Object[0], dimensions));
	}

	/**
	 * 测试asArray方法
	 */
	@Test
	@SuppressWarnings("all")
	public void testAsArray() {
		String[] sArr = ArrayUtil.asArray("1", "2", "3");
		assertEquals(3, sArr.length);
		assertEquals("3", sArr[2]);

		Object[] iArr = ArrayUtil.asArray();
		assertEquals(0, iArr.length);

		iArr = ArrayUtil.asArray((Integer) null);
		assertEquals(1, iArr.length);

		Object[] oArr = ArrayUtil.asArray(null);
		assertNull(oArr);

		oArr = ArrayUtil.asArray(new Object[0]);
		assertEquals(0, oArr.length);
	}

	/**
	 * 测试asMap
	 */
	@Test
	public void testAsMap() {
		/*
		 * Map<T, T> asMap(T[][] objs)
		 */
		String[][] arrayA = new String[][] { { "a", "1" }, { "b", "2" } };
		Map<String, String> map = ArrayUtil.asMap(arrayA);

		Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
		Map.Entry<String, String> entry = iterator.next();
		assertEquals("a", entry.getKey());
		assertEquals("1", entry.getValue());

		entry = iterator.next();
		assertEquals("b", entry.getKey());
		assertEquals("2", entry.getValue());

		Object[][] arrayB = new Object[][] { { "a", "1" }, { "b", "2" } };
		Map<Object, Object> mapB = (Map<Object, Object>) ArrayUtil.asMap(arrayB);

		Iterator<Map.Entry<Object, Object>> iteratorB = mapB.entrySet().iterator();
		Map.Entry<Object, Object> entryB = iteratorB.next();
		assertEquals("a", entryB.getKey());
		assertEquals("1", entryB.getValue());

		entryB = iteratorB.next();
		assertEquals("b", entryB.getKey());
		assertEquals("2", entryB.getValue());

		map = ArrayUtil.asMap(new String[0][0]);
		assertEquals(0, map.size());
		map = ArrayUtil.asMap((String[][]) null);
		assertEquals(0, map.size());

		/*
		 * Map<T, T> asMap(T... objs)
		 */
		map = ArrayUtil.asMap("a", "1");
		iterator = map.entrySet().iterator();
		entry = iterator.next();
		assertEquals("a", entry.getKey());
		assertEquals("1", entry.getValue());

		map = ArrayUtil.asMap("a", "1", "b");
		iterator = map.entrySet().iterator();
		entry = iterator.next();
		assertEquals("a", entry.getKey());
		assertEquals("1", entry.getValue());

		entry = iterator.next();
		assertEquals("b", entry.getKey());
		assertNull(entry.getValue());

		map = ArrayUtil.asMap(new String[0]);
		assertEquals(0, map.size());
		map = ArrayUtil.asMap((String[]) null);
		assertEquals(0, map.size());

		/*
		 * Map<K, V> asMultiTypeMap(T... objs)
		 */
		Map<String, Object> mapKV = ArrayUtil.asMultiTypeMap("a", "b");
		Iterator<Map.Entry<String, Object>> iteratorKV = mapKV.entrySet().iterator();
		Map.Entry<String, Object> entryKV = iteratorKV.next();
		assertEquals("c", entryKV.getKey().replaceAll("a", "c"));
		assertEquals("d", ((String) entryKV.getValue()).replaceAll("b", "d"));

		mapKV = ArrayUtil.asMultiTypeMap(new Object[0]);
		assertEquals(0, mapKV.size());
		mapKV = ArrayUtil.asMultiTypeMap((Object[]) null);
		assertEquals(0, mapKV.size());

		/*
		 * Map<K, V> asMultiTypeMap(T[][] objs)
		 */
		Object[][] arrayC = new Object[][] { { "a", "1" }, { "b", "2" } };
		mapKV = ArrayUtil.asMultiTypeMap(arrayC);
		iteratorKV = mapKV.entrySet().iterator();
		entryKV = iteratorKV.next();
		assertEquals("c", entryKV.getKey().replaceAll("a", "c"));
		assertEquals("2", ((String) entryKV.getValue()).replaceAll("1", "2"));
		entryKV = iteratorKV.next();
		assertEquals("d", entryKV.getKey().replaceAll("b", "d"));
		assertEquals("3", ((String) entryKV.getValue()).replaceAll("2", "3"));

		mapKV = ArrayUtil.asMultiTypeMap(new Object[0][0]);
		assertEquals(0, mapKV.size());
		mapKV = ArrayUtil.asMultiTypeMap((Object[][]) null);
		assertEquals(0, mapKV.size());
	}

	/**
	 * 测试asList方法
	 */
	@Test
	@SuppressWarnings("all")
	public void testAsList() {
		// asList
		List<String> list = ArrayUtil.asList("1", null, "3");
		assertEquals(3, list.size());
		assertEquals(null, list.get(1));
		assertEquals("3", list.get(2));

		list = ArrayUtil.asList((String) null);
		assertEquals(1, list.size());
		assertEquals(null, list.get(0));

		list = ArrayUtil.asList(null, null);
		assertEquals(2, list.size());
		assertEquals(null, list.get(1));

		list = ArrayUtil.asList((String[]) null);
		assertNull(list);

		assertNull(ArrayUtil.asList(null));
		assertTrue(ArrayUtil.asList(new Object[0]).isEmpty());
		assertTrue(ArrayUtil.asList().isEmpty());

		// asListNoNull
		list = ArrayUtil.asListNoNull("1", null, "3");
		assertEquals(2, list.size());
		assertEquals("1", list.get(0));
		assertEquals("3", list.get(1));

		list = ArrayUtil.asListNoNull(null, null);
		assertNull(list);

		assertNull(ArrayUtil.asListNoNull(null));
		assertNull(ArrayUtil.asListNoNull(new Object[0]));
		assertNull(ArrayUtil.asListNoNull());
	}

	/**
	 * 测试asSet方法
	 */
	@Test
	@SuppressWarnings("all")
	public void testAsSet() {
		// asSet
		Set<String> set = ArrayUtil.asSet("1", null, "3");
		assertEquals(3, set.size());
		assertEquals(null, set.toArray()[1]);
		assertEquals("3", set.toArray()[2]);

		set = ArrayUtil.asSet((String) null);
		assertEquals(1, set.size());
		assertEquals(null, set.toArray()[0]);

		set = ArrayUtil.asSet(null, null);
		assertEquals(1, set.size()); // same object
		assertEquals(null, set.toArray()[0]);

		set = ArrayUtil.asSet((String[]) null);
		assertNull(set);

		assertNull(ArrayUtil.asSet(null));
		assertTrue(ArrayUtil.asSet(new Object[0]).isEmpty());
		assertTrue(ArrayUtil.asSet().isEmpty());

		// asSetNoNull
		set = ArrayUtil.asSetNoNull("1", null, "3");
		assertEquals(2, set.size());
		assertEquals("1", set.toArray()[0]);
		assertEquals("3", set.toArray()[1]);

		set = ArrayUtil.asSetNoNull(null, null);
		assertNull(set);

		assertNull(ArrayUtil.asSetNoNull(null));
		assertNull(ArrayUtil.asSetNoNull(new Object[0]));
		assertNull(ArrayUtil.asSetNoNull());
	}

	@Test
	public void toArray() {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(BigDecimal.ONE);
		BigDecimal[] array = ArrayUtil.toArray(list, BigDecimal.class);
		assertEquals(BigDecimal.ONE, array[0]);

		list.clear();
		array = ArrayUtil.toArray(list, BigDecimal.class);
	}

	@Test
	public void testFirstNotNull() {
		assertEquals("1", ArrayUtil.firstNotNull("1"));
		assertEquals("2", ArrayUtil.firstNotNull("2", null, null, "3"));
		assertEquals("3", ArrayUtil.firstNotNull(null, null, "3", "4"));
		assertNull(ArrayUtil.firstNotNull((String) null));
		assertNull(ArrayUtil.firstNotNull());
	}
}
