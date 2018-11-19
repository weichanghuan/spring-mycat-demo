package com.base.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * 私有访问器测试类。
 * 
 *
 */
public class PrivateAccessorTest {
	private static class TestBeanA {
		private String a;

		@SuppressWarnings("unused")
		private String getValue() {
			return a;
		}
	}

	private static class TestBeanB extends TestBeanA {
		private String b;

		@SuppressWarnings("unused")
		private String getValue() {
			return b;
		}
	}

	@Test
	public void test() {
		// 1
		TestBeanA beanA = new TestBeanA();
		PrivateAccessor.setField(beanA, "a", "123");
		String valueA = (String) PrivateAccessor.getField(beanA, "a");
		assertEquals("123", valueA);

		valueA = (String) PrivateAccessor.invokeMethod(beanA, PrivateAccessor.newMethod("getValue"));
		assertEquals("123", valueA);

		// 2
		TestBeanB beanB = new TestBeanB();
		try {
			PrivateAccessor.setField(beanB, "a", "123");
			fail("Expect exception");
		} catch (Exception ex) {
		}

		try {
			PrivateAccessor.getField(beanB, "a");
			fail("Expect exception");
		} catch (Exception ex) {
		}

		PrivateAccessor.setField(TestBeanA.class, beanB, "a", "123");

		valueA = (String) PrivateAccessor.getField(TestBeanA.class, beanB, "a");
		assertEquals("123", valueA);

		valueA = (String) PrivateAccessor.invokeMethod(TestBeanA.class, beanB, PrivateAccessor.newMethod("getValue"));
		assertEquals("123", valueA);

		String valueB = (String) PrivateAccessor.getField(beanB, "b");
		assertNull(valueB);
	}

}
