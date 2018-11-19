package com.base.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * 文件工具测试类。
 * 
 *
 */
public class FileUtilTest {

	@Test
	public void test() {
		assertEquals("", FileUtil.getFileNameExtension(".abc"));
		assertEquals("", FileUtil.getFileNameExtension("/.abc/abc"));
		assertEquals("pdf", FileUtil.getFileNameExtension("/.abc/abc.pdf"));

		assertEquals("/.abc/abc.txt", FileUtil.changeFileNameExtension("/.abc/abc.pdf", "txt"));
		assertEquals("/.abc/abc.txt", FileUtil.changeFileNameExtension("/.abc/abc", "txt"));
	}

	/**
	 * 测试mkParentDirs方法
	 */
	@Test
	public void testMkParentDirs() {
		// mkParentDirs(String)
		assertTrue(FileUtil.mkParentDirs("target/file-test/1/1/1"));
		assertFalse(FileUtil.mkParentDirs((String) null));

		// mkParentDirs(File)
		assertTrue(FileUtil.mkParentDirs(new File("target/file-test/2/2/2")));
		assertFalse(FileUtil.mkParentDirs((File) null));
	}

	/**
	 * 测试createNewFile方法
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateNewFile() {
		// createNewFile(String)
		File file = FileUtil.createNewFile("target/file-test/3/3/3.txt");
		assertNotNull(file);
		assertTrue(file.exists());
		file.delete();

		assertNull(FileUtil.createNewFile((String) null));

		// createNewFile(File)
		File file2 = new File("target/file-test/4/4/4.txt");
		assertTrue(FileUtil.createNewFile(file2));
		file2.delete();

		assertFalse(FileUtil.createNewFile((File) null));
	}

	/**
	 * 测试CreateTempFile方法
	 */
	@Test
	public void testCreateTempFile() {
		File tempFile = FileUtil.createTempFile();
		try {
			assertNotNull(tempFile);
			assertTrue(tempFile.exists());
		} finally {
			tempFile.delete();
		}

		final String suffix = ".txt";
		tempFile = FileUtil.createTempFile(suffix);
		try {
			assertNotNull(tempFile);
			assertTrue(tempFile.exists());
			assertTrue(tempFile.getName().endsWith(suffix));
		} finally {
			tempFile.delete();
		}

		final String prefix = "abc";
		tempFile = FileUtil.createTempFile(prefix, suffix);
		try {
			assertNotNull(tempFile);
			assertTrue(tempFile.exists());
			assertTrue(tempFile.getName().startsWith(prefix));
			assertTrue(tempFile.getName().endsWith(suffix));
		} finally {
			tempFile.delete();
		}
	}

	@Test
	public void testListFiles() {
		String path = FileUtilTest.class.getResource("/test-data").getFile();
		File file = new File(path);
		File[] fs = FileUtil.listFiles(file, "[.]*.txt");
		for (File f : fs) {
			System.out.println(f.getName());
		}
	}

	@Test
	public void testReadLines() {
		List<String> lines = FileUtil.readLines(new File("pom.xml"));
		assertFalse(lines.isEmpty());
	}

	@Test
	public void testReadLine() {
		String line = FileUtil.readLine(new File("pom.xml"));
		assertNotNull(line);
	}

	@Test
	public void testRename() throws IOException {
		File file = new File("target/rename.txt");
		FileUtil.writeFile(file, "测试");

		File newFile = FileUtil.rename(file, "txt.rename");
		Assert.assertFalse(file.exists());
		Assert.assertTrue(newFile.exists());
		Assert.assertEquals(file.getParent(), newFile.getParent());
		Assert.assertEquals("txt.rename", newFile.getName());

		FileUtil.writeFile(file, "test");
		Assert.assertNotNull(FileUtil.rename(newFile, file.getName()));

		try {
			FileUtil.rename(file, "."); // invalid name
			Assert.fail("Expect exception");
		} catch (Exception ex) {
		}

		try {
			FileUtil.rename(new File("@#$%1234"), "."); // inexisted file
			Assert.fail("Expect exception");
		} catch (Exception ex) {
		}

		try {
			FileUtil.rename(null, "abc");
			Assert.fail("Expect exception");
		} catch (Exception ex) {
		}

		try {
			FileUtil.rename(file, null);
			Assert.fail("Expect exception");
		} catch (Exception ex) {
		}
	}

	@Test
	public void testGetFileName() {
		Assert.assertEquals("ABC.txt", FileUtil.getFileName("/abc/123/ABC.txt"));
		Assert.assertEquals("ABC", FileUtil.getFileName("/abc/123/ABC"));
		Assert.assertEquals("ABC", FileUtil.getFileName("abc/123/ABC"));
		Assert.assertEquals("abc", FileUtil.getFileName("abc"));
		Assert.assertEquals("", FileUtil.getFileName(""));
		Assert.assertNull(FileUtil.getFileName(null));
	}

	@Test
	public void testGetSystemTempDir() {
		String tmpDir = FileUtil.getSystemTempDir();
		Assert.assertNotNull(tmpDir);
		Assert.assertTrue(new File(tmpDir).isDirectory());
	}

	@Test
	public void testCreateTempDir() {
		Set<String> allDirs = new HashSet<String>();
		int i = 100;
		while (i-- > 0) {
			File tmpDir = FileUtil.createTempDir(); // random dir name
			Assert.assertNotNull(tmpDir);
			Assert.assertTrue(tmpDir.isDirectory());
			Assert.assertTrue(allDirs.add(tmpDir.getAbsolutePath()));
		}
	}
}
