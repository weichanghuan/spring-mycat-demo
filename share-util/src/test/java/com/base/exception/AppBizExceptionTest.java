package com.base.exception;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 应用业务异常类单元测试类。
 * 
 *
 */
public class AppBizExceptionTest {

	@Test
	public void testAppBizExceptionString() {
		try {
			throw new AppBizException("001");
		} catch (AppBizException e) {
			assertEquals("001", e.getCode());
			assertEquals("AppBizException[001].", e.getMessage());
		}
	}

	@Test
	public void testAppBizExceptionStringString() {
		try {
			throw new AppBizException("001", "Error message.");
		} catch (AppBizException e) {
			assertEquals("001", e.getCode());
			assertEquals("AppBizException[001]: Error message.", e.getMessage());
		}
	}

	@Test
	public void testAppBizExceptionStringStringMapOfStringObject() {
		try {
			Map<String, String> ctx = new HashMap<String, String>();
			ctx.put("module", "tcp");
			throw new AppBizException("001", "Error message.", ctx);
		} catch (AppBizException e) {
			assertEquals("001", e.getCode());
			assertEquals("AppBizException[001]: Error message.", e.getMessage());
			assertEquals("tcp", e.getContext().get("module"));
		}
	}

}
