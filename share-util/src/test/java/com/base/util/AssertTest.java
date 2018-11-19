package com.base.util;

import org.junit.Test;

import com.base.exception.AssertFailError;

/**
 * Assert测试类
 *
 *
 */
public class AssertTest {
	@Test
	public void test() {
		// assertTrue
		Assert.assertTrue(true);
		try {
			Assert.assertTrue(false);
			org.junit.Assert.fail();
		} catch (AssertFailError ex) {
		}

		// assertFalse
		Assert.assertFalse(false);
		try {
			Assert.assertFalse(true);
			org.junit.Assert.fail();
		} catch (AssertFailError ex) {
		}

		// assertNull
		Assert.assertNull(null);
		try {
			Assert.assertNull(1);
			org.junit.Assert.fail();
		} catch (AssertFailError ex) {
		}

		// assertNotNull
		Assert.assertNotNull(1);

		try {
			Assert.assertNotNull(null);
			org.junit.Assert.fail();
		} catch (AssertFailError ex) {
		}

		// assertSame
		Assert.assertSame(Void.class, Void.class);

		try {
			Assert.assertSame(Void.class, Object.class);
			org.junit.Assert.fail();
		} catch (AssertFailError ex) {
		}

		// assertNotSame
		Assert.assertNotSame(Void.class, Object.class);

		try {
			Assert.assertNotSame(Void.class, Void.class);
			org.junit.Assert.fail();
		} catch (AssertFailError ex) {
		}

		// assertEquals
		Assert.assertEquals(1, 1);

		try {
			Assert.assertEquals(1, 2);
			org.junit.Assert.fail();
		} catch (AssertFailError ex) {
		}

		// assertNotEquals
		Assert.assertNotEquals(1, 2);

		try {
			Assert.assertNotEquals(1, 1);
			org.junit.Assert.fail();
		} catch (AssertFailError ex) {
		}
	}
}
