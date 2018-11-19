package com.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * SHA工具类
 * 
 *
 */
public final class SHA {
	/**
	 * 算法名称 - SHA1
	 */
	private static final String SHA1 = "SHA-1";

	/**
	 * 算法名称 - SHA256
	 */
	private static final String SHA256 = "SHA-256";

	/**
	 * 算法名称 - SHA512
	 */
	private static final String SHA512 = "SHA-512";

	/**
	 * 计算字符串的SHA1，返回Hex字符结果
	 * 
	 * @param msgStr
	 * @param charset
	 * @return
	 */
	public static String sha1Hex(String msgStr, String charset) {
		return shaAsHex(SHA1, msgStr, charset);
	}

	/**
	 * 计算字符串的SHA256，返回Hex字符结果
	 * 
	 * @param msgStr
	 * @param charset
	 * @return
	 */
	public static String sha256Hex(String msgStr, String charset) {
		return shaAsHex(SHA256, msgStr, charset);
	}

	/**
	 * 计算字符串的SHA512，返回Hex字符结果
	 * 
	 * @param msgStr
	 * @param charset
	 * @return
	 */
	public static String sha512Hex(String msgStr, String charset) {
		return shaAsHex(SHA512, msgStr, charset);
	}

	/**
	 * 计算文件SHA1，返回Hex字符结果
	 * 
	 * @param file
	 * @return
	 */
	public static String sha1Hex(File file) {
		return shaAsHex(SHA1, file);
	}

	/**
	 * 计算文件SHA256，返回Hex字符结果
	 * 
	 * @param file
	 * @return
	 */
	public static String sha256Hex(File file) {
		return shaAsHex(SHA256, file);
	}

	/**
	 * 计算文件SHA512，返回Hex字符结果
	 * 
	 * @param file
	 * @return
	 */
	public static String sha512Hex(File file) {
		return shaAsHex(SHA512, file);
	}

	/**
	 * 计算SHA1
	 * 
	 * @param msg
	 * @return
	 */
	public static byte[] sha1(byte[] msg) {
		return sha(SHA1, msg);
	}

	/**
	 * 计算SHA256
	 * 
	 * @param msg
	 * @return
	 */
	public static byte[] sha256(byte[] msg) {
		return sha(SHA256, msg);
	}

	/**
	 * 计算SHA512
	 * 
	 * @param msg
	 * @return
	 */
	public static byte[] sha512(byte[] msg) {
		return sha(SHA512, msg);
	}

	/**
	 * 计算文件SHA1
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] sha1(File file) {
		return sha(SHA1, file);
	}

	/**
	 * 计算文件SHA256
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] sha256(File file) {
		return sha(SHA256, file);
	}

	/**
	 * 计算文件SHA512
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] sha512(File file) {
		return sha(SHA512, file);
	}

	/**
	 * 计算SHA - 文件
	 * 
	 * @param algorithm
	 * @param file
	 * @return
	 */
	private static byte[] sha(String algorithm, File file) {
		InputStream in = null;
		try {
			in = new FileInputStream(file);

			MessageDigest md = MessageDigest.getInstance(algorithm);

			byte[] buffer = new byte[8192];
			int len;
			while ((len = in.read(buffer)) != -1) {
				md.update(buffer, 0, len);
			}

			return md.digest();
		} catch (Exception e) {
			throw new RuntimeException(String.format("Calc stream %s error", algorithm), e);

		} finally {
			IOUtil.closeQuietly(in);
		}
	}

	/**
	 * 计算SHA - 字节数组
	 * 
	 * @param algorithm
	 * @param msg
	 * @return
	 */
	private static byte[] sha(String algorithm, byte[] msg) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			return md.digest(msg);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Calc %s error", algorithm), e);
		}
	}

	/**
	 * 计算文件SHA，返回Hex字符结果
	 * 
	 * @param algorithm
	 * @param file
	 * @return
	 */
	private static String shaAsHex(String algorithm, File file) {
		return ByteUtil.asHex(sha(algorithm, file));
	}

	/**
	 * 计算SHA，返回Hex字符结果
	 * 
	 * @param algorithm
	 * @param msgStr
	 * @param charset
	 * @return
	 */
	private static String shaAsHex(String algorithm, String msgStr, String charset) {
		byte[] msg = msgStr.getBytes(Charset.forName(charset));

		return ByteUtil.asHex(sha(algorithm, msg));
	}

	private SHA() {
	}
}
