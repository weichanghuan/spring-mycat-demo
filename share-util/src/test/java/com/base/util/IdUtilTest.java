package com.base.util;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * 身份证工具测试类
 * 
 *
 */
public class IdUtilTest {

	@Test
	public void testConvertId15To18() {
		assertEquals("310106197703250814", IdUtil.convertId15To18("310106770325081"));
		assertEquals("123456197890123454", IdUtil.convertId15To18("123456789012345"));

		try {
			IdUtil.convertId15To18("310106197703250814");
			Assert.fail("Expect error");
		} catch (Exception ex) {
		}

		try {
			IdUtil.convertId15To18("123456789ABCDEF");
			Assert.fail("Expect error");
		} catch (Exception ex) {
		}

		try {
			IdUtil.convertId15To18("123456");
			Assert.fail("Expect error");
		} catch (Exception ex) {
		}

		try {
			IdUtil.convertId15To18(null);
			Assert.fail("Expect error");
		} catch (Exception ex) {
		}
	}

	@Test
	public void testCalcCheckValue() {
		assertEquals("6", IdUtil.calcCheckValue("31010119840526101"));
		assertEquals("X", IdUtil.calcCheckValue("31010119840526003"));

		try {
			IdUtil.calcCheckValue("123456");
			Assert.fail("Expect error");
		} catch (Exception ex) {
		}

		try {
			IdUtil.calcCheckValue(null);
			Assert.fail("Expect error");
		} catch (Exception ex) {
		}
	}

	@Test
	public void testIsValidIdCardNo() {
		// 1
		assertTrue(IdUtil.isValidIdCardNo("310101198405261016"));
		assertTrue(IdUtil.isValidIdCardNo("31010119840526003X"));
		assertTrue(IdUtil.isValidIdCardNo("31010119840526003x"));
		assertTrue(IdUtil.isValidIdCardNo("31010119840526003*"));

		assertFalse(IdUtil.isValidIdCardNo("310101198405260030")); // error-cv
		assertFalse(IdUtil.isValidIdCardNo("31010119840526003F")); // error-cv

		// 2
		assertTrue(IdUtil.isValidIdCardNo("310101198405261"));
		assertFalse(IdUtil.isValidIdCardNo("31010119840526X")); // num-only

		// 3
		assertFalse(IdUtil.isValidIdCardNo("31010119840526117")); // 17
		assertFalse(IdUtil.isValidIdCardNo("abc"));
		assertFalse(IdUtil.isValidIdCardNo("   "));
		assertFalse(IdUtil.isValidIdCardNo(null));
	}

	@Test
	public void testGetProvinceInfo() {
		// getProvinceCode
		assertEquals("31", IdUtil.getProvinceCode("310101198405261016"));
		assertNull(IdUtil.getProvinceCode("3"));
		assertNull(IdUtil.getProvinceCode(null));

		// getProvinceName
		assertEquals("上海", IdUtil.getProvinceName("310101198405261016"));
		assertEquals("北京", IdUtil.getProvinceName("110101198405261019"));
		assertNull(IdUtil.getProvinceName("888888888888888888"));
		assertNull(IdUtil.getProvinceName("8"));
		assertNull(IdUtil.getProvinceName(null));
	}

	@Test
	public void testFormat() {
		assertEquals("31010119840526X", IdUtil.format("31010119840526*"));
		assertEquals("31010119840526X", IdUtil.format("31010119840526x"));
		assertEquals("31010119840526117", IdUtil.format("31010119840526117"));
		assertEquals("", IdUtil.format(""));
		assertNull(IdUtil.format(null));
	}
}
