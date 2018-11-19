package com.base.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Test;

/**
 * IO工具测试类
 * 
 *
 */
public class IOUtilTest {
	/**
	 * 测试关闭方法
	 */
	@Test
	public void testCloseQuietly() {
		IOUtil.closeQuietly(null);
	}

	/**
	 * 拷贝数据
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCopy() throws IOException {
		// copy(File, File)
		File src1 = new File("pom.xml");
		File target1 = new File("target/io-test/test1/pom.xml");
		long count1 = IOUtil.copy(src1, target1); // autoCreate targetFile
		assertEquals(src1.length(), target1.length());
		assertEquals(src1.length(), count1);

		// copy(File, File, boolean)
		File src2 = new File("pom.xml");
		File target2 = new File("target/io-test/test2/pom.xml");
		long count2 = IOUtil.copy(src2, target2, true); // autoCreate targetFile
		assertEquals(src2.length(), target2.length());
		assertEquals(src2.length(), count2);

		try {
			File src3 = new File("pom.xml");
			File target3 = new File("target/io-test/test3/pom.xml");
			IOUtil.copy(src3, target3, false); // target3 is unexisted
			fail("Expect exception");
		} catch (Exception ex) {
		}

		// copy(File, OutputStream)
		File src4 = new File("pom.xml");
		File target4 = File.createTempFile("io-test4", null);
		try {
			// autoClose outStream
			long count4 = IOUtil.copy(src4, new FileOutputStream(target4));
			assertEquals(src4.length(), target4.length());
			assertEquals(src4.length(), count4);
		} finally {
			target4.delete();
		}

		// copy(File, OutputStream, boolean)
		File src5 = new File("pom.xml");
		File target5 = File.createTempFile("io-test5", null);
		try {
			long count5;
			OutputStream out = new FileOutputStream(target5);
			try {
				count5 = IOUtil.copy(src5, out, false); // manual close
														// outStream
			} finally {
				IOUtil.closeQuietly(out);
			}

			assertEquals(src5.length(), target5.length());
			assertEquals(src5.length(), count5);
		} finally {
			target5.delete();
		}

		// copy(InputStream, File)
		File src6 = new File("pom.xml");
		InputStream inStream6 = new FileInputStream(src6);
		File target6 = new File("target/io-test/test6/pom.xml");
		long count6 = IOUtil.copy(inStream6, target6); // autoClose outStream
		assertEquals(src6.length(), target6.length());
		assertEquals(src6.length(), count6);

		// copy(InputStream, File, boolean)
		File src7 = new File("pom.xml");
		InputStream inStream7 = new FileInputStream(src7);
		File target7 = new File("target/io-test/test7/pom.xml");
		try {
			IOUtil.copy(inStream7, target7, false); // target7 is unexisted
			fail("Expect exception");
		} catch (Exception ex) {
		}

		// copy(InputStream, File, boolean, boolean)
		File src8 = new File("pom.xml");
		InputStream inStream8 = new FileInputStream(src8);
		File target8 = new File("target/io-test/test8/pom.xml");
		// autoCreate parentDir, autoClose inStream
		long count8 = IOUtil.copy(inStream8, target8, true, true);
		assertEquals(src8.length(), target8.length());
		assertEquals(src8.length(), count8);

		// copy(InputStream, OutputStream)
		File src9 = new File("pom.xml");
		InputStream inStream9 = new FileInputStream(src9);
		File target9 = File.createTempFile("io-test9", null);
		try {
			OutputStream outStream9 = new FileOutputStream(target9);
			long count9 = IOUtil.copy(inStream9, outStream9);
			assertEquals(src9.length(), target9.length());
			assertEquals(src9.length(), count9);
		} finally {
			target9.delete();
		}

		// copy(InputStream, OutputStream, boolean)
		File src10 = new File("pom.xml");
		InputStream inStream10 = new FileInputStream(src10);
		File target10 = File.createTempFile("io-test10", null);
		try {
			OutputStream outStream10 = new FileOutputStream(target10);
			long count10 = IOUtil.copy(inStream10, outStream10, true);
			assertEquals(src10.length(), target10.length());
			assertEquals(src10.length(), count10);
		} finally {
			target10.delete();
		}
	}

	/**
	 * 测试toString方法
	 * 
	 * @throws IOException
	 */
	@Test
	public void testToString() throws IOException {
		final String encoding = "GBK";
		final String data = "测试字符串";

		// toString(in)
		ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
		assertEquals(data, IOUtil.toString(in));

		// toString(in, charset)
		in = new ByteArrayInputStream(data.getBytes(encoding));
		assertEquals(data, IOUtil.toString(in, encoding));

		in = new ByteArrayInputStream(data.getBytes(Charset.defaultCharset().name()));
		assertEquals(data, IOUtil.toString(in, null));

		assertNull(IOUtil.toString(null));
		assertNull(IOUtil.toString(null, null));
	}

	/**
	 * 测试toByteArray方法
	 * 
	 * @throws IOException
	 */
	@Test
	public void testToByteArray() throws IOException {
		final String data = "测试字符串";

		// toByteArray(in)
		ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
		assertArrayEquals(data.getBytes(), IOUtil.toByteArray(in));

		assertNull(IOUtil.toByteArray(null));
	}

	/**
	 * 测试readLines方法
	 * 
	 * @throws IOException
	 */
	@Test
	public void testReadLines() throws IOException {
		List<String> lines = IOUtil.readLines(new FileInputStream("pom.xml"));
		assertFalse(lines.isEmpty());

		lines = IOUtil.readLines(new FileInputStream("pom.xml"), Charsets.UTF_8);
		assertFalse(lines.isEmpty());

		lines = IOUtil.readLines(new FileInputStream("pom.xml"), null);
		assertFalse(lines.isEmpty());

		assertNull(IOUtil.readLines(null));
	}

	/**
	 * 测试writeLines方法
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWriteLines() throws IOException {
		File src = new File("pom.xml");
		File target = FileUtil.createTempFile();
		System.out.println(target.getAbsolutePath());
		List<String> srcLines = IOUtil.readLines(new FileInputStream(src));

		// writeLines(lines, stream)
		// system default config
		IOUtil.writeLines(srcLines, new FileOutputStream(target));

		String srcContent = IOUtil.toString(new FileInputStream(src));
		String targetContent = IOUtil.toString(new FileInputStream(target));
		assertEquals(srcContent, targetContent);

		IOUtil.writeLines(srcLines, null);
		IOUtil.writeLines(null, new ByteArrayOutputStream());

		// writeLines(lines, separator, stream, encoding)
		// MS_DOS config
		IOUtil.writeLines(srcLines, "\r\n", new FileOutputStream(target), Charsets.GBK);
		List<String> targetLines = IOUtil.readLines(new FileInputStream(target));
		assertArrayEquals(srcLines.toArray(), targetLines.toArray());

		targetLines = IOUtil.readLines(new FileInputStream(target));
		assertArrayEquals(srcLines.toArray(), targetLines.toArray());

		IOUtil.writeLines(srcLines, null, null, null);
		IOUtil.writeLines(null, null, new ByteArrayOutputStream(), null);

		target.delete();
	}
}
