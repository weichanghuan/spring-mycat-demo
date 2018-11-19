package com.base.util;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

/**
 * 监测HashMap类测试类。
 *
 * 
 */
public class CheckHashMapTest {
	public static class NotFoundException extends RuntimeException {
		private static final long serialVersionUID = -1585640581327871042L;

		public NotFoundException(String msg) {
			super(msg);
		}

		public NotFoundException() {
		}
	}

	@Test
	public void test() {
		Map<String, String> map = new CheckHashMap<String, String>(NotFoundException.class, "value");
		map.put("1", "a");
		assertEquals("a", map.get("1"));

		try {
			map.get("2");
		} catch (NotFoundException e) {
			assertEquals("Not found the value, key=[2].", e.getMessage());
		}
		
		map = new CheckHashMap<String, String>(NotFoundException.class);
		map.put("1", "a");
		assertEquals("a", map.get("1"));

		try {
			map.get("2");
		} catch (NotFoundException e) {
			assertEquals("2", e.getMessage());
		}
	}

}
