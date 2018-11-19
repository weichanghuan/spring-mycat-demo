package com.base.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 交集检查器测试类
 * 
 *
 */
public class IntersectionCheckerTest {

	@Test
	public void test() {
		IntersectionChecker checker = new IntersectionChecker();
		Assert.assertFalse(checker.hasIntersection());

		// 1
		checker.addRange(0, 0); // 0
		checker.addRange(0, 0);
		Assert.assertTrue(checker.hasIntersection());
		checker.reset();

		// 2
		checker.addRange(-10, 10); // [-10, 10]
		checker.addRange(-9, 9);
		Assert.assertTrue(checker.hasIntersection());
		checker.reset();

		// 3
		checker.addRange(-10, 10);
		checker.addRange(0, 20);
		Assert.assertTrue(checker.hasIntersection());
		checker.reset();

		// 4
		checker.addRange(-10, 10);
		checker.addRange(-10, -10);
		Assert.assertTrue(checker.hasIntersection());
		checker.reset();

		// 5
		checker.addRange(-10, 10);
		checker.addRange(10, 10);
		Assert.assertTrue(checker.hasIntersection());
		checker.reset();

		// 6
		checker.addRange(-10, 10);
		checker.addRange(0, 0);
		Assert.assertTrue(checker.hasIntersection());
		checker.reset();

		// 7
		checker.addRange(-100, -100);
		checker.addRange(-99, -1);
		checker.addRange(0, 1);
		checker.addRange(2, 2);
		checker.addRange(3, 5);
		checker.addRange(6, 99);
		checker.addRange(100, 100);
		Assert.assertFalse(checker.hasIntersection());

		checker.addRange(100, 101);
		Assert.assertTrue(checker.hasIntersection());
		checker.reset();

		// 8
		try {
			checker.addRange(100, 1);
			Assert.fail("Expect exception");
		} catch (Exception ex) {
		}
	}
}
