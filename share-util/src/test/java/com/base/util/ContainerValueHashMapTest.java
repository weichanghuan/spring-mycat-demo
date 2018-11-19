package com.base.util;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * 容器数值HashMap类测试类。
 * 
 *
 */
public class ContainerValueHashMapTest {

	@Test
	public void test() {
		Map<String, Set<String>> map = new ContainerValueHashMap<String, Set<String>>(HashSet.class);
		Set<String> set = map.get("a");
		set.add("a");
		set = map.get("a");
		assertTrue(set.contains("a"));
	}

}
