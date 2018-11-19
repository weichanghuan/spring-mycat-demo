package com.base.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * BeanUtil测试类
 * 
 *
 */
public class BeanUtilTest {
	/**
	 * 测试getPropertyNames方法
	 */
	@Test
	public void testGetPropertyNames() {
		// 1
		List<String> propNames = BeanUtil.getPropertyNames(BeanA.class);
		List<String> expPropNames = ArrayUtil.asList("p", "p0");

		assertEquals(expPropNames.size(), propNames.size());
		assertTrue(propNames.containsAll(expPropNames));

		// 2
		propNames = BeanUtil.getPropertyNames(BeanB.class);
		expPropNames = ArrayUtil.asList("p", "p0", "p1", "p2", "p3");

		assertEquals(expPropNames.size(), propNames.size());
		assertTrue(propNames.containsAll(expPropNames));

		// 3
		propNames = BeanUtil.getPropertyNames(BeanB.class, "p", "p2");
		expPropNames = ArrayUtil.asList("p0", "p1", "p3");

		assertEquals(expPropNames.size(), propNames.size());
		assertTrue(propNames.containsAll(expPropNames));

		// 4
		assertNull(BeanUtil.getPropertyNames(null, "p", "p2"));
	}

	/**
	 * 测试getPropertyNamesWithDiffValue方法
	 */
	@Test
	public void testGetPropertyNamesWithDiffValue() {
		ObjectUtilTest.Person p1 = new ObjectUtilTest.Person("Mr.X", 30);
		ObjectUtilTest.Person p2 = new ObjectUtilTest.Person("Mr.X", 60);
		ObjectUtilTest.Animal a1 = new ObjectUtilTest.Animal(30);
		ObjectUtilTest.Animal a2 = new ObjectUtilTest.Animal(10);

		// person vs person
		List<String> propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p1);
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p1, "age", "name", "sex"); // sex-ignored
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p1, false, "age", "name", "sex"); // sex-ignored
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p1, true, "age", "name", "sex"); // excluded-all
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2);
		assertEquals(1, propNames.size());
		assertEquals("age", propNames.get(0));

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, "name");
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, false, "name");
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, true, "name"); // name-excluded
		assertEquals(1, propNames.size());
		assertEquals("age", propNames.get(0));

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, "age");
		assertEquals(1, propNames.size());
		assertEquals("age", propNames.get(0));

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, false, "age"); // age-included
		assertEquals(1, propNames.size());
		assertEquals("age", propNames.get(0));

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, true, "age"); // age-excluded
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, "sex"); // sex-ignored
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, true, "age", "name"); // excluded
		assertEquals(0, propNames.size());

		// animal vs animal
		propNames = BeanUtil.getPropertyNamesWithDiffValue(a1, a2); // no-propNames
		assertEquals(1, propNames.size());
		assertEquals("age", propNames.get(0));

		propNames = BeanUtil.getPropertyNamesWithDiffValue(a1, a2, true); // no-propNames
		assertEquals(1, propNames.size());
		assertEquals("age", propNames.get(0));

		propNames = BeanUtil.getPropertyNamesWithDiffValue(a1, a2, false); // no-propNames
		assertEquals(1, propNames.size());
		assertEquals("age", propNames.get(0));

		// person vs animal
		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, a1);
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, a1, "name", "age", null); // name-ignored
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, a2);
		assertEquals(1, propNames.size());
		assertEquals("age", propNames.get(0));

		// null
		propNames = BeanUtil.getPropertyNamesWithDiffValue(null, a1);
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(a1, null);
		assertEquals(0, propNames.size());

		propNames = BeanUtil.getPropertyNamesWithDiffValue(null, null);
		assertEquals(0, propNames.size());
	}

	/**
	 * 测试getPropertiesWithDiffValue方法
	 */
	@Test
	public void testGetPropertyValuesWithDiffValue() {
		ObjectUtilTest.Person p1 = new ObjectUtilTest.Person("Mr.X", 30);
		ObjectUtilTest.Person p2 = new ObjectUtilTest.Person("Mr.X", 60);
		ObjectUtilTest.Animal a1 = new ObjectUtilTest.Animal(30);
		ObjectUtilTest.Animal a2 = new ObjectUtilTest.Animal(10);

		// person vs person
		Map<String, Object[]> props = BeanUtil.getPropertyValuesWithDiffValue(p1, p1);
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p1, "age", "name", "sex"); // sex-ignored
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p1, false, "age", "name", "sex"); // all-included
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p1, true, "age", "name", "sex"); // all-excluded
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p2);
		assertEquals(1, props.size());
		assertArrayEquals(new Object[] { 30, 60 }, props.get("age"));

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p2, "name");
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p2, false, "name"); // name-included
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p2, true, "name"); // name-excluded
		assertEquals(1, props.size());
		assertArrayEquals(new Object[] { 30, 60 }, props.get("age"));

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p2, "age");
		assertEquals(1, props.size());
		assertArrayEquals(new Object[] { 30, 60 }, props.get("age"));

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p2, false, "age"); // age-included
		assertEquals(1, props.size());
		assertArrayEquals(new Object[] { 30, 60 }, props.get("age"));

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p2, true, "age"); // age-excluded
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p2, true, "age", "name"); // age-excluded
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, p2, "sex"); // sex-ignored
		assertEquals(0, props.size());

		// animal vs animal
		props = BeanUtil.getPropertyValuesWithDiffValue(a1, a2);
		assertEquals(1, props.size());
		assertArrayEquals(new Object[] { 30, 10 }, props.get("age"));

		props = BeanUtil.getPropertyValuesWithDiffValue(a1, a2, false); // no-propNames
		assertEquals(1, props.size());
		assertArrayEquals(new Object[] { 30, 10 }, props.get("age"));

		props = BeanUtil.getPropertyValuesWithDiffValue(a1, a2, true); // no-propNames
		assertEquals(1, props.size());
		assertArrayEquals(new Object[] { 30, 10 }, props.get("age"));

		// person vs animal
		props = BeanUtil.getPropertyValuesWithDiffValue(p1, a1);
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, a1, "name", "age", null); // name-ignored
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(p1, a2);
		assertEquals(1, props.size());
		assertArrayEquals(new Object[] { 30, 10 }, props.get("age"));

		// null
		props = BeanUtil.getPropertyValuesWithDiffValue(null, a1);
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(a1, null);
		assertEquals(0, props.size());

		props = BeanUtil.getPropertyValuesWithDiffValue(null, null);
		assertEquals(0, props.size());
	}

	/**
	 * 测试asMap方法
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testAsMap() {
		BeanC bean = genBean();

		// asMap(obj, ignoredProps)
		Map<String, Object> propMap = BeanUtil.asMap(bean);
		assertEquals(14, propMap.size());
		assertEquals((BeanA) propMap.get("p"), bean.getP());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
		assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
		assertEquals((String) propMap.get("p3"), bean.p3);
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);
		assertTrue(propMap.containsKey("p12"));

		// asMap(mode, obj, ignoredProps)
		propMap = BeanUtil.asMap(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, bean);
		assertEquals(13, propMap.size());
		assertEquals((BeanA) propMap.get("p"), bean.getP());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
		assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
		assertEquals((String) propMap.get("p3"), bean.p3);
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);
		assertFalse(propMap.containsKey("p12"));

		// asMap(obj, excludedProps)
		propMap = BeanUtil.asMap(bean, "p0", "p1", "p2", "p3");
		assertEquals(10, propMap.size());
		assertEquals((BeanA) propMap.get("p"), bean.getP());
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);
		assertTrue(propMap.containsKey("p12"));

		// asMap(mode, obj, excludedProps)
		propMap = BeanUtil.asMap(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, bean, "p0", "p1", "p2", "p3");
		assertEquals(9, propMap.size());
		assertEquals((BeanA) propMap.get("p"), bean.getP());
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);
		assertFalse(propMap.containsKey("p12"));

		// asMap(obj, excludedProps)
		propMap = BeanUtil.asMap(bean, ArrayUtil.asList("p0", "p1", "p2", "p3"));
		assertEquals(10, propMap.size());
		assertEquals((BeanA) propMap.get("p"), bean.getP());
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);
		assertTrue(propMap.containsKey("p12"));

		// asMap(mode, obj, excludedProps)
		propMap = BeanUtil.asMap(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, bean, ArrayUtil.asList("p0", "p1", "p2", "p3"));
		assertEquals(9, propMap.size());
		assertEquals((BeanA) propMap.get("p"), bean.getP());
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);
		assertFalse(propMap.containsKey("p12"));

		assertNull(BeanUtil.asMap(null));
	}

	/**
	 * 测试asMap方法
	 */
	@Test
	public void testAsMapWithAdjustedPropDesc() {
		// 1
		BoolBean bb = new BoolBean();

		Map<String, Object> bbMap = BeanUtil.asMap(bb);
		assertEquals(4, bbMap.size());
		assertEquals(bb.isA(), bbMap.get("a"));
		assertEquals(bb.isB(), bbMap.get("b"));
		assertFalse(bbMap.containsKey("c"));
		assertEquals(bb.getD(), bbMap.get("d"));
		assertEquals(bb.isE(), bbMap.get("e"));
		
		bbMap = BeanUtil.asMap(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, bb);
		assertEquals(3, bbMap.size());
		assertEquals(bb.isA(), bbMap.get("a"));
		assertFalse(bbMap.containsKey("b"));
		assertFalse(bbMap.containsKey("c"));
		assertEquals(bb.getD(), bbMap.get("d"));
		assertEquals(bb.isE(), bbMap.get("e"));
		
		// 2
		bb = new BoolBean();
		bb.setA(true);
		bb.setB(true);
		bb.setC(true);
		bb.setD(true);
		bb.setE(true);
		
		bbMap = BeanUtil.asMap(bb);
		assertEquals(4, bbMap.size());
		assertEquals(bb.isA(), bbMap.get("a"));
		assertEquals(bb.isB(), bbMap.get("b"));
		assertFalse(bbMap.containsKey("c"));
		assertEquals(bb.getD(), bbMap.get("d"));
		assertEquals(bb.isE(), bbMap.get("e"));
		
		bbMap = BeanUtil.asMap(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, bb);
		assertEquals(4, bbMap.size());
		assertEquals(bb.isA(), bbMap.get("a"));
		assertEquals(bb.isB(), bbMap.get("b"));
		assertFalse(bbMap.containsKey("c"));
		assertEquals(bb.getD(), bbMap.get("d"));
		assertEquals(bb.isE(), bbMap.get("e"));
	}

	/**
	 * 测试asMapWithProps方法
	 */
	@Test
	public void testAsMapWithProps() {
		BeanC bean = genBean();

		// asMapWithProps(obj, includedProps)
		Map<String, Object> propMap = BeanUtil.asMapWithProps(bean, "p0", "p1", "p2", "p3", "p12");
		assertEquals(5, propMap.size());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
		assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
		assertEquals((String) propMap.get("p3"), bean.p3);
		assertTrue(propMap.containsKey("p12"));

		// asMapWithProps(mode, obj, includedProps)
		propMap = BeanUtil.asMapWithProps(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, bean, "p0", "p1", "p2", "p3", "p12");
		assertEquals(4, propMap.size());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
		assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
		assertEquals((String) propMap.get("p3"), bean.p3);
		assertFalse(propMap.containsKey("p12"));

		// asMapWithProps(obj, includedProps)
		propMap = BeanUtil.asMapWithProps(bean, ArrayUtil.asList("p0", "p1", "p2", "p3", "p12"));
		assertEquals(5, propMap.size());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
		assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
		assertEquals((String) propMap.get("p3"), bean.p3);
		assertTrue(propMap.containsKey("p12"));

		// asMapWithProps(mode, obj, includedProps)
		propMap = BeanUtil.asMapWithProps(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, bean,
				ArrayUtil.asList("p0", "p1", "p2", "p3", "p12"));
		assertEquals(4, propMap.size());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
		assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
		assertEquals((String) propMap.get("p3"), bean.p3);
		assertFalse(propMap.containsKey("p12"));

		propMap = BeanUtil.asMapWithProps(bean);
		assertEquals(0, propMap.size()); // no-props

		propMap = BeanUtil.asMapWithProps(bean, new String[0]);
		assertEquals(0, propMap.size()); // no-props

		assertNull(BeanUtil.asMapWithProps(null));
	}

	/**
	 * 测试asMapWithAnnos方法
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testAsMapWithAnnos() {
		// asMapWithAnnos(obj, annos)
		BeanA beanA = new BeanA();
		beanA.setP(beanA);
		beanA.setP0(true);
		BeanB beanB = new BeanB();
		beanB.setP(beanB);
		beanB.setP0(false);
		beanB.setP1(1);
		beanB.setP2(new Byte[0]);
		BeanC beanC = new BeanC();
		beanC.setP(beanC);
		beanC.setP0(false); // BeanC
		beanC.setP0(Boolean.TRUE); // BeanA
		beanC.setP1(2);
		beanC.setP2(new byte[2]);
		beanC.setP3("c");
		beanC.setP4(BigDecimal.ZERO);

		// 1
		Map<String, Object> propMap = BeanUtil.asMapWithAnnos(beanA, AnnoA.class);
		assertEquals(2, propMap.size());
		assertEquals(((BeanA) propMap.get("p")), beanA.p);
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), beanA.p0);

		// 2
		propMap = BeanUtil.asMapWithAnnos(beanA, AnnoA.class, AnnoB.class);
		assertEquals(2, propMap.size());
		assertEquals(((BeanA) propMap.get("p")), beanA.p);
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), beanA.p0);

		// 3
		propMap = BeanUtil.asMapWithAnnos(beanA, AnnoB.class);
		assertEquals(0, propMap.size());

		// 4
		propMap = BeanUtil.asMapWithAnnos(beanB, AnnoB.class);
		assertEquals(4, propMap.size());
		assertEquals(beanB.getP0(), (Boolean) propMap.get("p0"));
		assertEquals(beanB.getP1(), (Integer) propMap.get("p1"));
		assertArrayEquals(beanB.getP2(), (Byte[]) propMap.get("p2"));
		assertEquals(beanB.getP3(), (BeanA) propMap.get("p3"));

		// 5
		propMap = BeanUtil.asMapWithAnnos(beanB, AnnoA.class);
		assertEquals(1, propMap.size());
		assertEquals(beanB.getP(), (BeanA) propMap.get("p"));

		// 6
		propMap = BeanUtil.asMapWithAnnos(beanB, AnnoA.class, AnnoB.class);
		assertEquals(5, propMap.size());
		assertEquals(beanB.getP(), (BeanA) propMap.get("p"));
		assertEquals(beanB.getP0(), (Boolean) propMap.get("p0"));
		assertEquals(beanB.getP1(), (Integer) propMap.get("p1"));
		assertArrayEquals(beanB.getP2(), (Byte[]) propMap.get("p2"));
		assertTrue(propMap.containsKey("p3"));
		assertNull(propMap.get("p3"));

		// 7
		propMap = BeanUtil.asMapWithAnnos(beanC, AnnoA.class);
		assertEquals(1, propMap.size());
		assertEquals(beanC.getP(), (BeanA) propMap.get("p"));

		// 8
		propMap = BeanUtil.asMapWithAnnos(beanC, AnnoB.class);
		assertEquals(0, propMap.size());

		// 9
		propMap = BeanUtil.asMapWithAnnos(beanA);
		assertEquals(0, propMap.size());

		// 10
		assertNull(BeanUtil.asMapWithAnnos(null, AnnoA.class));

		// asMapWithAnnos(mode, obj, annos)
		// 1
		propMap = BeanUtil.asMapWithAnnos(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, beanA, AnnoA.class, AnnoB.class);
		assertEquals(2, propMap.size());
		assertEquals(((BeanA) propMap.get("p")), beanA.p);
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), beanA.p0);

		// 2
		propMap = BeanUtil.asMapWithAnnos(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, beanB, AnnoA.class, AnnoB.class);
		assertEquals(4, propMap.size());
		assertEquals(beanB.getP(), (BeanA) propMap.get("p"));
		assertEquals(beanB.getP0(), (Boolean) propMap.get("p0"));
		assertEquals(beanB.getP1(), (Integer) propMap.get("p1"));
		assertArrayEquals(beanB.getP2(), (Byte[]) propMap.get("p2"));
		assertFalse(propMap.containsKey("p3"));

		// 3
		propMap = BeanUtil.asMapWithAnnos(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, beanC, AnnoA.class, AnnoB.class);
		assertEquals(1, propMap.size());
		assertEquals(beanC.getP(), (BeanA) propMap.get("p"));

		// 4
		propMap = BeanUtil.asMapWithAnnos(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, beanA);
		assertEquals(0, propMap.size());

		// 5
		assertNull(BeanUtil.asMapWithAnnos(BeanUtil.AsMapModes.PROP_VALUE_NOT_NULL, (Object) null, AnnoA.class));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCopyProperties() {
		// copyProperties(Map, Object, String[])
		BeanC rawBean = genBean();
		Map<String, Object> propMap = BeanUtil.asMap(rawBean);

		BeanC bean = new BeanC();
		assertSame(bean, BeanUtil.copyProperties(propMap, bean));

		assertEquals(14, propMap.size());
		assertEquals((BeanA) propMap.get("p"), bean.getP());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
		assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
		assertEquals((String) propMap.get("p3"), bean.p3);
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

		bean = new BeanC();
		BeanUtil.copyProperties(propMap, bean, "p", "p0", "p1", "p2", "p3");

		assertNull(bean.getP());
		assertEquals(false, bean.p0);
		assertEquals(0, bean.p1);
		assertNull(bean.p2);
		assertNull(bean.p3);
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

		// copyProperties(Map, Object, boolean, String[])
		bean = new BeanC();
		BeanUtil.copyProperties(propMap, bean, true, "p", "p0", "p1", "p2", "p3"); // excluded

		assertNull(bean.getP());
		assertEquals(false, bean.p0);
		assertEquals(0, bean.p1);
		assertNull(bean.p2);
		assertNull(bean.p3);
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

		bean = new BeanC();
		BeanUtil.copyProperties(propMap, bean, false, "p", "p0", "p1", "p2", "p3"); // included

		assertEquals((BeanA) propMap.get("p"), bean.getP());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
		assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
		assertEquals((String) propMap.get("p3"), bean.p3);
		assertNull(bean.p4);
		assertNull(bean.p5);
		assertNull(bean.p6);
		assertNull(bean.p7);
		assertNull(bean.p8);
		assertNull(bean.p9);
		assertEquals(0, bean.p10);
		assertEquals(0L, bean.p11);

		// copyProperties(SOURCE_VALUE_NOT_NULL, Map, Object, String[])
		bean = new BeanC();
		bean.p2 = new byte[0];
		bean.p3 = "abc";

		propMap.put("p2", null);
		propMap.put("p3", null);

		BeanUtil.copyProperties(BeanUtil.CopyPropModes.SOURCE_VALUE_NOT_NULL, propMap, bean, "p");

		assertNull(bean.getP());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
		assertArrayEquals(new byte[0], bean.p2); // no change
		assertEquals("abc", bean.p3); // no change
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

		// copyProperties(SOURCE_VALUE_NOT_NULL, Map, Object, boolean, String[])
		bean = new BeanC();
		bean.p2 = new byte[0];
		bean.p3 = "abc";

		BeanUtil.copyProperties(BeanUtil.CopyPropModes.SOURCE_VALUE_NOT_NULL, propMap, bean, true, "p"); // excluded

		assertNull(bean.getP());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
		assertArrayEquals(new byte[0], bean.p2); // no change
		assertEquals("abc", bean.p3); // no change
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

		bean = new BeanC();
		bean.p2 = new byte[0];
		bean.p3 = "abc";

		BeanUtil.copyProperties(BeanUtil.CopyPropModes.SOURCE_VALUE_NOT_NULL, propMap, bean, false, "p"); // included

		assertEquals((BeanA) propMap.get("p"), bean.getP());
		assertEquals(false, bean.p0);
		assertEquals(0, bean.p1);
		assertArrayEquals(new byte[0], bean.p2); // no change
		assertEquals("abc", bean.p3); // no change
		assertNull(bean.p4);
		assertNull(bean.p5);
		assertNull(bean.p6);
		assertNull(bean.p7);
		assertNull(bean.p8);
		assertNull(bean.p9);
		assertEquals(0, bean.p10);
		assertEquals(0L, bean.p11);

		// copyProperties(TARGET_VALUE_NOT_SET, Map, Object, String[])
		bean = new BeanC();
		bean.p1 = 2;
		bean.p2 = new byte[0];
		bean.p3 = "abc";

		propMap.put("p2", new byte[1]);
		propMap.put("p3", "cba");

		BeanUtil.copyProperties(BeanUtil.CopyPropModes.TARGET_VALUE_NOT_SET, propMap, bean, "p");

		assertNull(bean.getP());
		assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
		assertEquals(2, bean.p1); // no change
		assertArrayEquals(new byte[0], bean.p2); // no change
		assertEquals("abc", bean.p3); // no change
		assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
		assertEquals((Date) propMap.get("p5"), bean.p5);
		assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
		assertEquals((Class<String>) propMap.get("p7"), bean.p7);
		assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
		assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
		assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10); // changed-when-default-value
		assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11); // changed-when-default-value

		// copyProperties(Object, Object, String[])
		bean = new BeanC();
		assertSame(bean, BeanUtil.copyProperties(rawBean, bean, "p", "p0", "p1", "p2", "p3"));

		assertNull(bean.getP());
		assertEquals(false, bean.p0);
		assertEquals(0, bean.p1);
		assertNull(bean.p2);
		assertNull(bean.p3);
		assertEquals(rawBean.p4, bean.p4);
		assertEquals(rawBean.p5, bean.p5);
		assertArrayEquals(rawBean.p6, bean.p6);
		assertEquals(rawBean.p7, bean.p7);
		assertEquals(rawBean.p8, bean.p8);
		assertEquals(rawBean.p9, bean.p9);
		assertEquals(rawBean.p10, bean.p10);
		assertEquals(rawBean.p11, bean.p11);

		bean = new BeanC();
		BeanUtil.copyProperties(rawBean, bean);

		assertEquals(rawBean.getP(), bean.getP());
		assertEquals(rawBean.p1, bean.p1);
		assertArrayEquals(rawBean.p2, bean.p2);
		assertEquals(rawBean.p3, bean.p3);
		assertEquals(rawBean.p4, bean.p4);
		assertEquals(rawBean.p5, bean.p5);
		assertArrayEquals(rawBean.p6, bean.p6);
		assertEquals(rawBean.p7, bean.p7);
		assertEquals(rawBean.p8, bean.p8);
		assertEquals(rawBean.p9, bean.p9);
		assertEquals(rawBean.p10, bean.p10);
		assertEquals(rawBean.p11, bean.p11);

		BeanB beanB = new BeanB();
		BeanUtil.copyProperties(rawBean, beanB, "p2", "p3"); // p2,p3-argument-type-mismatch

		assertEquals(rawBean.getP(), beanB.getP());
		assertEquals(rawBean.p1, beanB.p1.intValue());

		// copyProperties(Object, Object, boolean, String[])
		bean = new BeanC();
		assertSame(bean, BeanUtil.copyProperties(rawBean, bean, true, "p", "p0", "p1", "p2", "p3")); // excluded

		assertNull(bean.getP());
		assertEquals(false, bean.p0);
		assertEquals(0, bean.p1);
		assertNull(bean.p2);
		assertNull(bean.p3);
		assertEquals(rawBean.p4, bean.p4);
		assertEquals(rawBean.p5, bean.p5);
		assertArrayEquals(rawBean.p6, bean.p6);
		assertEquals(rawBean.p7, bean.p7);
		assertEquals(rawBean.p8, bean.p8);
		assertEquals(rawBean.p9, bean.p9);
		assertEquals(rawBean.p10, bean.p10);
		assertEquals(rawBean.p11, bean.p11);

		bean = new BeanC();
		assertSame(bean, BeanUtil.copyProperties(rawBean, bean, false, "p", "p0", "p1", "p2", "p3")); // included

		assertEquals(rawBean.getP(), bean.getP());
		assertEquals(rawBean.p1, bean.p1);
		assertArrayEquals(rawBean.p2, bean.p2);
		assertEquals(rawBean.p3, bean.p3);
		assertNull(bean.p4);
		assertNull(bean.p5);
		assertNull(bean.p6);
		assertNull(bean.p7);
		assertNull(bean.p8);
		assertNull(bean.p9);
		assertEquals(0, bean.p10);
		assertEquals(0L, bean.p11);

		// copyProperties(SOURCE_VALUE_NOT_NULL, Object, Object, String[])
		bean = new BeanC();
		bean.p2 = new byte[0];
		bean.p3 = "123";

		BeanC bean2 = genBean();
		bean2.p2 = null;
		bean2.p3 = null;

		BeanUtil.copyProperties(BeanUtil.CopyPropModes.SOURCE_VALUE_NOT_NULL, bean2, bean, "p");

		assertNull(bean.getP());
		assertEquals(bean2.p1, bean.p1);
		assertArrayEquals(new byte[0], bean.p2); // no change
		assertEquals("123", bean.p3); // no change
		assertEquals(bean2.p4, bean.p4);
		assertEquals(bean2.p5, bean.p5);
		assertArrayEquals(bean2.p6, bean.p6);
		assertEquals(bean2.p7, bean.p7);
		assertEquals(bean2.p8, bean.p8);
		assertEquals(bean2.p9, bean.p9);
		assertEquals(bean2.p10, bean.p10);
		assertEquals(bean2.p11, bean.p11);

		// copyProperties(SOURCE_VALUE_NOT_NULL, Object, Object, boolean
		// String[])
		bean = new BeanC();
		bean.p2 = new byte[0];
		bean.p3 = "123";

		BeanUtil.copyProperties(BeanUtil.CopyPropModes.SOURCE_VALUE_NOT_NULL, bean2, bean, true, "p"); // excluded

		assertNull(bean.getP());
		assertEquals(bean2.p1, bean.p1);
		assertArrayEquals(new byte[0], bean.p2); // no change
		assertEquals("123", bean.p3); // no change
		assertEquals(bean2.p4, bean.p4);
		assertEquals(bean2.p5, bean.p5);
		assertArrayEquals(bean2.p6, bean.p6);
		assertEquals(bean2.p7, bean.p7);
		assertEquals(bean2.p8, bean.p8);
		assertEquals(bean2.p9, bean.p9);
		assertEquals(bean2.p10, bean.p10);
		assertEquals(bean2.p11, bean.p11);

		bean = new BeanC();
		bean.p2 = new byte[0];
		bean.p3 = "123";

		BeanUtil.copyProperties(BeanUtil.CopyPropModes.SOURCE_VALUE_NOT_NULL, bean2, bean, false, "p"); // included

		assertEquals(bean2.getP(), bean.getP());
		assertEquals(0, bean.p1);
		assertArrayEquals(new byte[0], bean.p2); // no change
		assertEquals("123", bean.p3); // no change
		assertNull(bean.p4);
		assertNull(bean.p5);
		assertNull(bean.p6);
		assertNull(bean.p7);
		assertNull(bean.p8);
		assertNull(bean.p9);
		assertEquals(0, bean.p10);
		assertEquals(0L, bean.p11);

		// copyProperties(TARGET_VALUE_NOT_SET, Object, Object, String[])
		bean = new BeanC();
		bean.p1 = 2;
		bean.p2 = new byte[0];
		bean.p3 = "abc";

		bean2.p2 = new byte[1];
		bean2.p3 = "cba";

		BeanUtil.copyProperties(BeanUtil.CopyPropModes.TARGET_VALUE_NOT_SET, bean2, bean, "p");

		assertNull(bean.getP());
		assertEquals(2, bean.p1); // no change
		assertArrayEquals(new byte[0], bean.p2); // no change
		assertEquals("abc", bean.p3); // no change
		assertEquals(bean2.p4, bean.p4);
		assertEquals(bean2.p5, bean.p5);
		assertArrayEquals(bean2.p6, bean.p6);
		assertEquals(bean2.p7, bean.p7);
		assertEquals(bean2.p8, bean.p8);
		assertEquals(bean2.p9, bean.p9);
		assertEquals(bean2.p10, bean.p10); // changed-when-default-value
		assertEquals(bean2.p11, bean.p11); // changed-when-default-value
	}

	@Test
	public void testCopyPropertiesWithAdjustedPropDesc() {
		// 1
		BoolBean bb1 = new BoolBean();
		BoolBean bb2 = new BoolBean();
		BeanUtil.copyProperties(bb1, bb2);
		assertFalse(bb2.isA());
		assertNull(bb2.isB());
		assertNull(bb2.isC());
		assertFalse(bb2.getD());
		assertFalse(bb2.isE());

		BeanUtil.copyProperties(BeanUtil.CopyPropModes.TARGET_VALUE_NOT_SET, bb1, bb2); // 无变化
		assertFalse(bb2.isA());
		assertNull(bb2.isB());
		assertNull(bb2.isC());
		assertFalse(bb2.getD());
		assertFalse(bb2.isE());

		// 2
		bb1.setA(true); // 错误读方法名和类型
		bb1.setB(true); // 错误读方法名
		bb1.setC(true); // 错误读方法名和写方法类型 (目前忽略写方法错误，不做修正)
		bb1.setD(true); // 错误读方法名
		bb1.setE(true); // 正常

		bb2 = new BoolBean();

		BeanUtil.copyProperties(bb1, bb2);
		assertTrue(bb2.isA());
		assertTrue(bb2.isB());
		assertNull(bb2.isC());
		assertTrue(bb2.getD());
		assertTrue(bb2.isE());

		BeanUtil.copyProperties(BeanUtil.CopyPropModes.TARGET_VALUE_NOT_SET, bb1, bb2); // 无变化
		assertTrue(bb2.isA());
		assertTrue(bb2.isB());
		assertNull(bb2.isC());
		assertTrue(bb2.getD());
		assertTrue(bb2.isE());

		// 3
		bb1 = new BoolBean();
		BeanUtil.copyProperties(BeanUtil.CopyPropModes.TARGET_VALUE_NOT_SET, bb1, bb2); // 无变化
		assertTrue(bb2.isA());
		assertTrue(bb2.isB());
		assertNull(bb2.isC());
		assertTrue(bb2.getD());
		assertTrue(bb2.isE());

		BeanUtil.copyProperties(bb1, bb2); // 覆盖成默认值
		assertFalse(bb2.isA());
		assertNull(bb2.isB());
		assertNull(bb2.isC());
		assertFalse(bb2.getD());
		assertFalse(bb2.isE());
	}

	@Test
	public void testGetRWMethods() throws Exception {
		BeanC beanC = new BeanC();
		beanC.setP3("123");
		beanC.setP(beanC);

		// getReadMethod
		Method c_p_rm = BeanUtil.getReadMethod(BeanC.class, "p");
		assertNotNull(c_p_rm);
		assertSame(beanC, c_p_rm.invoke(beanC));

		Method c_p3_rm = BeanUtil.getReadMethod(BeanC.class, "p3");
		assertNotNull(c_p3_rm);
		assertEquals(beanC.getP3(), c_p3_rm.invoke(beanC));

		Method c_p100_rm = BeanUtil.getReadMethod(BeanC.class, "p100");
		assertNull(c_p100_rm);

		// getWriteMethod
		Method c_p_wm = BeanUtil.getWriteMethod(BeanC.class, "p");
		assertNotNull(c_p_wm);

		c_p_wm.invoke(beanC, (BeanC) null);
		assertNull(beanC.getP());
		assertNull(c_p_rm.invoke(beanC));

		Method c_p3_wm = BeanUtil.getWriteMethod(BeanC.class, "p3");
		assertNotNull(c_p3_wm);

		c_p3_wm.invoke(beanC, "abc");
		assertEquals("abc", beanC.getP3());
		assertEquals("abc", c_p3_rm.invoke(beanC));

		Method c_p100_wm = BeanUtil.getWriteMethod(BeanC.class, "p100");
		assertNull(c_p100_wm);
	}

	@Test
	public void testGetRWMethodsWithAdjustedPropDesc() throws Exception {
		BoolBean bb = new BoolBean();
		bb.setA(true);
		bb.setB(true);
		bb.setC(true);
		bb.setD(true);
		bb.setE(true);

		Method arm = BeanUtil.getReadMethod(BoolBean.class, "a");
		Method awm = BeanUtil.getWriteMethod(BoolBean.class, "a");
		Method brm = BeanUtil.getReadMethod(BoolBean.class, "b");
		Method bwm = BeanUtil.getWriteMethod(BoolBean.class, "b");
		Method crm = BeanUtil.getReadMethod(BoolBean.class, "c");
		Method cwm = BeanUtil.getWriteMethod(BoolBean.class, "c");
		Method drm = BeanUtil.getReadMethod(BoolBean.class, "d");
		Method dwm = BeanUtil.getWriteMethod(BoolBean.class, "d");
		Method erm = BeanUtil.getReadMethod(BoolBean.class, "e");
		Method ewm = BeanUtil.getWriteMethod(BoolBean.class, "e");

		assertNotNull(arm);
		assertNotNull(awm);
		assertNotNull(brm);
		assertNotNull(bwm);
		assertNull(crm); // 未调整
		assertNotNull(cwm);
		assertNotNull(drm);
		assertNotNull(dwm);
		assertNotNull(erm);
		assertNotNull(ewm);

		assertTrue((Boolean) arm.invoke(bb));
		assertTrue((Boolean) brm.invoke(bb));
		assertTrue((Boolean) drm.invoke(bb));
		assertTrue((Boolean) erm.invoke(bb));

		awm.invoke(bb, (Boolean) null);
		bwm.invoke(bb, (Boolean) null);
		cwm.invoke(bb, Boolean.FALSE);
		dwm.invoke(bb, false);
		ewm.invoke(bb, false);

		assertFalse(bb.isA());
		assertNull(bb.isB());
		assertFalse(bb.isC());
		assertFalse(bb.getD());
		assertFalse(bb.isE());
	}

	@Test
	public void testGetMethod() {
		final BeanA beanA = new BeanA();
		final BeanB beanB = new BeanB();
		final BeanC beanC = new BeanC();

		assertEquals("m1", BeanUtil.getMethod(beanA, "m1").getName());
		assertNull(BeanUtil.getMethod(beanA, "m2"));
		assertNull(BeanUtil.getMethod(beanA, "m3"));

		assertEquals("m1", BeanUtil.getMethod(beanB, "m1").getName());
		assertEquals("m2", BeanUtil.getMethod(beanB, "m2").getName());
		assertNull(BeanUtil.getMethod(beanB, "m3"));

		assertEquals("m1", BeanUtil.getMethod(beanC, "m1").getName());
		assertNull(BeanUtil.getMethod(beanC, "m2"));
		assertEquals("m3", BeanUtil.getMethod(beanC, "m3").getName());

		assertEquals(0, BeanUtil.getMethod(beanC, "m1", new Class[0]).getParameterTypes().length);
		assertEquals(1, BeanUtil.getMethod(beanC, "m1", new Class[] { String.class }).getParameterTypes().length);
		assertNull(BeanUtil.getMethod(beanC, "m1", new Class[] { Integer.class }));

		assertNull(BeanUtil.getMethod(beanA, null));
		assertNull(BeanUtil.getMethod(null, null));
	}

	@Test
	public void testInvokeMethod() {
		final BeanA beanA = new BeanA();
		final BeanB beanB = new BeanB();
		final BeanC beanC = new BeanC();

		Method m = BeanUtil.getMethod(beanA, "m1");
		assertEquals("BeanA.m1", BeanUtil.invokeMethod(beanA, m));
		assertEquals("BeanA.m1", BeanUtil.invokeMethod(beanA, "m1"));
		try {
			BeanUtil.invokeMethod(beanA, "m2"); // no such method
			fail("Expect exception");
		} catch (Exception ex) {
		}

		assertNull(BeanUtil.invokeMethodQuietly(beanC, "m2")); // sallow-exception

		assertEquals("BeanA.m1", BeanUtil.invokeMethod(beanB, "m1"));
		assertEquals("BeanB.m2.test", BeanUtil.invokeMethod(beanB, "m2", "test"));

		m = BeanUtil.getMethod(beanC, "m1", new Class[0]);
		assertEquals("BeanA.m1", BeanUtil.invokeMethod(beanC, m));

		try {
			BeanUtil.invokeMethod(beanC, m, "1"); // error arg num
			fail("Expect exception");
		} catch (Exception ex) {
		}

		assertNull(BeanUtil.invokeMethodQuietly(beanC, m, "1")); // sallow-exception

		m = BeanUtil.getMethod(beanC, "m1", new Class[] { String.class });
		assertEquals("BeanC.m1.test", BeanUtil.invokeMethod(beanC, m, "test"));
		assertEquals("BeanC.m3.test", BeanUtil.invokeMethod(beanC, "m3", "test"));

		assertNull(BeanUtil.invokeMethod(beanA, (String) null));
		assertNull(BeanUtil.invokeMethod(null, (String) null));

		assertNull(BeanUtil.invokeMethod(beanA, (Method) null));
		assertNull(BeanUtil.invokeMethod(null, (Method) null));
	}

	@Test
	public void testGetField() {
		final BeanA beanA = new BeanA();
		final BeanB beanB = new BeanB();
		final BeanC beanC = new BeanC();

		assertEquals("p", BeanUtil.getField(beanA, "p").getName());
		assertNull(BeanUtil.getField(beanA, "p99"));

		assertEquals("p", BeanUtil.getField(beanB, "p").getName());
		assertEquals("p0", BeanUtil.getField(beanB, "p0").getName());
		assertNull(BeanUtil.getField(beanB, "p99"));

		assertEquals("p", BeanUtil.getField(beanC, "p").getName());
		assertEquals("p0", BeanUtil.getField(beanC, "p0").getName());
		assertEquals("p5", BeanUtil.getField(beanC, "p5").getName());
		assertNull(BeanUtil.getField(beanC, "p99"));

		assertNull(BeanUtil.getField(beanA, null));
		assertNull(BeanUtil.getField(null, null));
	}

	@Test
	public void testGetFieldValue() {
		final BeanA beanA = new BeanA();
		beanA.setP(beanA);
		beanA.setP0(true);

		Field f = BeanUtil.getField(beanA, "p");
		assertEquals(beanA, BeanUtil.getFieldValue(beanA, f));
		assertEquals(Boolean.TRUE, BeanUtil.getFieldValue(beanA, "p0"));

		try {
			BeanUtil.getFieldValue(beanA, "p99");
			fail("Expect exception");
		} catch (Exception ex) {
		}

		assertNull(BeanUtil.getFieldValueQuietly(beanA, "p99")); // sallow-exception

		assertNull(BeanUtil.getFieldValue(beanA, (String) null));
		assertNull(BeanUtil.getFieldValue(null, (String) null));

		assertNull(BeanUtil.getFieldValue(beanA, (Field) null));
		assertNull(BeanUtil.getFieldValue(null, (Field) null));
	}

	@Test
	public void testGetFields() {
		BeanB beanB = new BeanB();
		beanB.setP1(1);

		Map<String, Field> fieldMap = BeanUtil.getFields(BeanB.class);
		assertEquals(5, fieldMap.size());
		assertEquals(BeanUtil.getField(beanB, "p"), fieldMap.get("p"));
		assertEquals(BeanUtil.getField(beanB, "p0"), fieldMap.get("p0"));
		assertEquals(BeanUtil.getField(beanB, "p1"), fieldMap.get("p1"));
		assertEquals(BeanUtil.getField(beanB, "p2"), fieldMap.get("p2"));
		assertEquals(BeanUtil.getField(beanB, "p3"), fieldMap.get("p3"));

	//	assertEquals(1, BeanUtil.getFieldValue(beanB, fieldMap.get("p1")));

		fieldMap = BeanUtil.getFields(BeanB.class, "p");
		assertEquals(4, fieldMap.size());
		assertFalse(fieldMap.containsKey("p"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testGetFieldsWithAnnos() {
		Map<String, Field> fieldMap = BeanUtil.getFieldsWithAnnos(BeanA.class, AnnoA.class);
		assertEquals(2, fieldMap.size());
		assertEquals(BeanUtil.getField(BeanA.class, "p"), fieldMap.get("p"));
		assertEquals(BeanUtil.getField(BeanA.class, "p0"), fieldMap.get("p0"));

		fieldMap = BeanUtil.getFieldsWithAnnos(BeanA.class, AnnoB.class);
		assertEquals(0, fieldMap.size());

		fieldMap = BeanUtil.getFieldsWithAnnos(BeanB.class, AnnoA.class);
		assertEquals(1, fieldMap.size());
		assertEquals(BeanUtil.getField(BeanB.class, "p"), fieldMap.get("p"));

		fieldMap = BeanUtil.getFieldsWithAnnos(BeanB.class, AnnoA.class, AnnoB.class);
		assertEquals(5, fieldMap.size());
		assertEquals(BeanUtil.getField(BeanB.class, "p"), fieldMap.get("p"));
		assertEquals(BeanUtil.getField(BeanB.class, "p0"), fieldMap.get("p0"));
		assertEquals(BeanUtil.getField(BeanB.class, "p1"), fieldMap.get("p1"));
		assertEquals(BeanUtil.getField(BeanB.class, "p2"), fieldMap.get("p2"));
		assertEquals(BeanUtil.getField(BeanB.class, "p3"), fieldMap.get("p3"));

		fieldMap = BeanUtil.getFieldsWithAnnos(BeanB.class, AnnoC.class);
		assertEquals(0, fieldMap.size());

		fieldMap = BeanUtil.getFieldsWithAnnos(BeanC.class, AnnoA.class, AnnoB.class);
		assertEquals(1, fieldMap.size());
		assertEquals(BeanUtil.getField(BeanC.class, "p"), fieldMap.get("p"));

		assertEquals(0, BeanUtil.getFieldsWithAnnos(BeanC.class).size());
		assertNull(BeanUtil.getFieldsWithAnnos(null, AnnoA.class));
	}

	@Test
	public void testGetPropertyDescriptors() {
		BeanB beanB = new BeanB();
		beanB.setP1(1);

		Map<String, PropertyDescriptor> propDescMap = BeanUtil.getPropertyDescriptors(BeanB.class);
		assertEquals(5, propDescMap.size());
		assertEquals(BeanUtil.getPropertyDescriptor(BeanB.class, "p"), propDescMap.get("p"));
		assertEquals(BeanUtil.getPropertyDescriptor(BeanB.class, "p0"), propDescMap.get("p0"));
		assertEquals(BeanUtil.getPropertyDescriptor(BeanB.class, "p1"), propDescMap.get("p1"));
		assertEquals(BeanUtil.getPropertyDescriptor(BeanB.class, "p2"), propDescMap.get("p2"));
		assertEquals(BeanUtil.getPropertyDescriptor(BeanB.class, "p3"), propDescMap.get("p3"));

		//assertEquals(1, BeanUtil.invokeMethod(beanB, propDescMap.get("p1").getReadMethod()));

		propDescMap = BeanUtil.getPropertyDescriptors(BeanB.class, "p");
		assertEquals(4, propDescMap.size());
		assertFalse(propDescMap.containsKey("p"));
	}

	@Test
	public void testGetAdjustedPropertyDescs() {
		BeanB beanB = new BeanB();
		beanB.setP1(1);

		Map<String, BeanUtil.PropertyDesc> propDescMap = BeanUtil.getAdjustedPropertyDescs(BeanB.class);
		assertEquals(5, propDescMap.size());
		assertEquals(BeanUtil.getAdjustedPropertyDesc(BeanB.class, "p"), propDescMap.get("p"));
		assertEquals(BeanUtil.getAdjustedPropertyDesc(BeanB.class, "p0"), propDescMap.get("p0"));
		assertEquals(BeanUtil.getAdjustedPropertyDesc(BeanB.class, "p1"), propDescMap.get("p1"));
		assertEquals(BeanUtil.getAdjustedPropertyDesc(BeanB.class, "p2"), propDescMap.get("p2"));
		assertEquals(BeanUtil.getAdjustedPropertyDesc(BeanB.class, "p3"), propDescMap.get("p3"));

		BeanUtil.PropertyDesc beanB_p_pd = propDescMap.get("p");
		PropertyDescriptor beanB_p_pdr = BeanUtil.getPropertyDescriptor(BeanB.class, "p");
		assertEquals(beanB_p_pdr.getName(), beanB_p_pd.getName());
		assertEquals(beanB_p_pdr, beanB_p_pd.getPropDesc());
		assertEquals(beanB_p_pdr.getPropertyType(), beanB_p_pd.getPropertyType());
		assertEquals(beanB_p_pdr.getReadMethod(), beanB_p_pd.getReadMethod());
		assertEquals(beanB_p_pdr.getWriteMethod(), beanB_p_pd.getWriteMethod());
		assertFalse(beanB_p_pd.isAdjusted());

		assertFalse(propDescMap.get("p0").isAdjusted());
		assertFalse(propDescMap.get("p1").isAdjusted());
		assertFalse(propDescMap.get("p2").isAdjusted());
		assertFalse(propDescMap.get("p3").isAdjusted());

		//assertEquals(1, BeanUtil.invokeMethod(beanB, propDescMap.get("p1").getReadMethod()));

		propDescMap = BeanUtil.getAdjustedPropertyDescs(BeanB.class, "p");
		assertEquals(4, propDescMap.size());
		assertFalse(propDescMap.containsKey("p"));

		// BoolBean
		propDescMap = BeanUtil.getAdjustedPropertyDescs(BoolBean.class);
		assertEquals(5, propDescMap.size());
		assertEquals(BeanUtil.getAdjustedPropertyDesc(BoolBean.class, "a"), propDescMap.get("a"));
		assertEquals(BeanUtil.getAdjustedPropertyDesc(BoolBean.class, "b"), propDescMap.get("b"));
		assertEquals(BeanUtil.getAdjustedPropertyDesc(BoolBean.class, "c"), propDescMap.get("c"));
		assertEquals(BeanUtil.getAdjustedPropertyDesc(BoolBean.class, "d"), propDescMap.get("d"));
		assertEquals(BeanUtil.getAdjustedPropertyDesc(BoolBean.class, "e"), propDescMap.get("e"));

		BeanUtil.PropertyDesc boolBean_a_pd = propDescMap.get("a");
		PropertyDescriptor boolBean_a_pdr = BeanUtil.getPropertyDescriptor(BoolBean.class, "a");
		assertEquals(boolBean_a_pdr.getName(), boolBean_a_pd.getName());
		assertEquals(boolBean_a_pdr, boolBean_a_pd.getPropDesc());
		assertEquals(Boolean.TYPE, boolBean_a_pdr.getPropertyType());
		assertEquals(Boolean.class, boolBean_a_pd.getPropertyType());
		assertEquals(boolBean_a_pdr.getReadMethod(), boolBean_a_pd.getReadMethod());
		assertTrue(boolBean_a_pdr.getWriteMethod() != boolBean_a_pd.getWriteMethod()); // not
																						// same
		assertTrue(boolBean_a_pd.isAdjusted());

		assertTrue(propDescMap.get("b").isAdjusted());
		assertFalse(propDescMap.get("c").isAdjusted());
		assertFalse(propDescMap.get("d").isAdjusted());
		assertFalse(propDescMap.get("e").isAdjusted());

		assertNull(propDescMap.get("c").getReadMethod());
	}

	@Test
	public void testGetSetPropValue() {
		BeanC bean = new BeanC();
		BeanUtil.setPropValue(bean, "p0", true);
		assertEquals(true, BeanUtil.getPropValue(bean, "p0"));

		BeanUtil.setPropValue(bean, "p1", 9);
		assertEquals(9, BeanUtil.getPropValue(bean, "p1"));

		BeanUtil.setPropValue(bean, "p2", new byte[] { 1, 2, 3 });
		assertArrayEquals(new byte[] { 1, 2, 3 }, (byte[]) BeanUtil.getPropValue(bean, "p2"));

		BeanUtil.setPropValue(bean, "p3", "1");
		assertEquals("1", BeanUtil.getPropValue(bean, "p3"));

		BeanUtil.setPropValue(bean, "p4", new BigDecimal("1"));
		assertEquals(new BigDecimal("1"), BeanUtil.getPropValue(bean, "p4"));

		Date now = new Date();
		BeanUtil.setPropValue(bean, "p5", now);
		assertEquals(now, BeanUtil.getPropValue(bean, "p5"));
	}

	private BeanC genBean() {
		BeanC bean = new BeanC();
		bean.setP(bean);
		bean.p0 = true;
		bean.p1 = 1;
		bean.p2 = new byte[2];
		bean.p3 = "3";
		bean.p4 = new BigDecimal("4");
		bean.p5 = new Date();
		bean.p6 = new Integer[] { 1, 2 };
		bean.p7 = String.class;
		bean.p8 = new ArrayList<BeanC>();
		bean.p9 = new HashMap<BeanC, BeanC>();

		return bean;
	}

	static class BeanA {
		@AnnoA
		private BeanA p;

		@AnnoA
		private Boolean p0;

		public BeanA getP() {
			return p;
		}

		public void setP(BeanA p) {
			this.p = p;
		}

		public Boolean getP0() {
			return p0;
		}

		public void setP0(Boolean p0) {
			this.p0 = p0;
		}

		public String m1() {
			return "BeanA.m1";
		}
	}

	static class BeanB extends BeanA {
		@AnnoB
		private Boolean p0;

		@AnnoB
		private Integer p1;

		@AnnoB
		private Byte[] p2;

		@AnnoB
		private BeanA p3;

		public Boolean getP0() {
			return p0;
		}

		public void setP0(Boolean p0) {
			this.p0 = p0;
		}

		public Integer getP1() {
			return p1;
		}

		public void setP1(Integer p1) {
			this.p1 = p1;
		}

		public Byte[] getP2() {
			return p2;
		}

		public void setP2(Byte[] p2) {
			this.p2 = p2;
		}

		public BeanA getP3() {
			return p3;
		}

		public void setP3(BeanA p3) {
			this.p3 = p3;
		}

		public String m2(String m) {
			return "BeanB.m2." + m;
		}
	}

	static class BeanC extends BeanA {
		private boolean p0;

		private int p1;

		private byte[] p2;

		private String p3;

		private BigDecimal p4;

		private Date p5;

		private Integer[] p6;

		private Class<?> p7;

		private Collection<?> p8;

		private Map<?, ?> p9;

		private byte p10;

		private long p11;

		private Object p12;

		public boolean isP0() {
			return p0;
		}

		public void setP0(boolean p0) {
			this.p0 = p0;
		}

		public int getP1() {
			return p1;
		}

		public void setP1(int p1) {
			this.p1 = p1;
		}

		public byte[] getP2() {
			return p2;
		}

		public void setP2(byte[] p2) {
			this.p2 = p2;
		}

		public String getP3() {
			return p3;
		}

		public void setP3(String p3) {
			this.p3 = p3;
		}

		public BigDecimal getP4() {
			return p4;
		}

		public void setP4(BigDecimal p4) {
			this.p4 = p4;
		}

		public Date getP5() {
			return p5;
		}

		public void setP5(Date p5) {
			this.p5 = p5;
		}

		public Integer[] getP6() {
			return p6;
		}

		public void setP6(Integer[] p6) {
			this.p6 = p6;
		}

		public Class<?> getP7() {
			return p7;
		}

		public void setP7(Class<?> p7) {
			this.p7 = p7;
		}

		public Collection<?> getP8() {
			return p8;
		}

		public void setP8(Collection<?> p8) {
			this.p8 = p8;
		}

		public Map<?, ?> getP9() {
			return p9;
		}

		public void setP9(Map<?, ?> p9) {
			this.p9 = p9;
		}

		public byte getP10() {
			return p10;
		}

		public void setP10(byte p10) {
			this.p10 = p10;
		}

		public long getP11() {
			return p11;
		}

		public void setP11(long p11) {
			this.p11 = p11;
		}

		public Object getP12() {
			return p12;
		}

		public void setP12(Object p12) {
			this.p12 = p12;
		}

		public String m1(String m) {
			return "BeanC.m1." + m;
		}

		public String m3(String m) {
			return "BeanC.m3." + m;
		}
	}

	static class BoolBean {
		private Boolean a; // 错误读方法名和类型
		private Boolean b; // 错误读方法名
		private Boolean c; // 错误读方法名，错误写方法类型 (暂不处理直接忽略)
		private boolean d; // 错误读方法名
		private boolean e; // 正常

		public boolean isA() {
			if (a == null) {
				return false;
			}

			return a;
		}

		public void setA(Boolean a) {
			this.a = a;
		}

		public Boolean isB() {
			return b;
		}

		public void setB(Boolean b) {
			this.b = b;
		}

		public Boolean isC() {
			return c;
		}

		public void setC(boolean c) {
			this.c = c;
		}

		public boolean getD() {
			return d;
		}

		public void setD(boolean d) {
			this.d = d;
		}

		public boolean isE() {
			return e;
		}

		public void setE(boolean e) {
			this.e = e;
		}
	}

	@Target({ FIELD })
	@Retention(RUNTIME)
	@interface AnnoA {
	}

	@Target({ FIELD })
	@Retention(RUNTIME)
	@interface AnnoB {
	}

	@Target({ FIELD })
	@Retention(RUNTIME)
	@interface AnnoC {
	}
}
