package com.base.util;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

/**
 * Base58测试类
 * 
 *
 */
public class Base58Test {
	private Charset charset = Charset.forName("UTF-8");
	
	@Test
	public void testEncode() {
		String str = "Base58测试";
		byte[] bytes = str.getBytes(charset);
		String base58EncRes = "2Fex4vaSLM7Zvrzck";
		byte[] base58EncResBytes = base58EncRes.getBytes(charset);

		// encodeString(String)
		String base58Str1 = Base58.encodeString(str);

		// encodeString(byte[])
		String base58Str2 = Base58.encodeString(bytes);
		Assert.assertEquals(base58EncRes, base58Str1);
		Assert.assertEquals(base58EncRes, base58Str2);

		// encode(String)
		byte[] base58Bytes1 = Base58.encode(str);

		// encode(byte[])
		byte[] base58Bytes2 = Base58.encode(bytes);
		Assert.assertArrayEquals(base58EncResBytes, base58Bytes1);
		Assert.assertArrayEquals(base58EncResBytes, base58Bytes2);
	}

	@Test
	public void testDecode() {
		String base58EncRes = "2Fex4vaSLM7Zvrzck";
		byte[] base58EncResBytes = base58EncRes.getBytes(charset);

		String str = "Base58测试";
		byte[] bytes = str.getBytes(charset);

		// decode(String)
		byte[] bytes1 = Base58.decode(base58EncRes);

		// decode(byte[])
		byte[] bytes2 = Base58.decode(base58EncResBytes);
		Assert.assertArrayEquals(bytes, bytes1);
		Assert.assertArrayEquals(bytes, bytes2);
	}
}
