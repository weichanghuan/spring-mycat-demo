package com.base.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Charsets工具测试类
 * 
 *
 */
public class CharsetsTest {
	/**
	 * 测试isSupported方法
	 */
	@Test
	public void testIsSupported() {
		Assert.assertTrue(Charsets.isSupported("utf-8"));
		Assert.assertTrue(Charsets.isSupported(Charsets.UTF_8));
		Assert.assertFalse(Charsets.isSupported(null));
		Assert.assertFalse(Charsets.isSupported("UTF-888"));
	}

	/**
	 * 测试defaultCharset方法
	 */
	@Test
	public void testDefaultCharset() {
		Assert.assertNotNull(Charsets.defaultCharset());
	}
}
