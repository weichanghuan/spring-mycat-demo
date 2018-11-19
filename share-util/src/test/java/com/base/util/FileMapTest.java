package com.base.util;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * FileMap测试类
 * 
 *
 */
public class FileMapTest {

	@Test
	public void testPut() {
		File file = FileUtil.createTempFile();
		FileMap<String> map = new FileMap<String>();

		Assert.assertNull(map.put("test", file));
		Assert.assertEquals(1, map.size());
		Assert.assertTrue(file.exists());

		File oldFile = map.put("test", new File("/tmp/testPut"));
		Assert.assertEquals(1, map.size());
		Assert.assertSame(file, oldFile);
		Assert.assertFalse(file.exists());

		Assert.assertNull(map.put("test1", null));
		Assert.assertEquals(2, map.size());

		Assert.assertNull(map.put(null, null));
		Assert.assertEquals(3, map.size());
	}

	@Test
	public void testPutAll() {
		Map<String, File> files = new HashMap<String, File>();
		File file1 = FileUtil.createTempFile();
		File file2 = FileUtil.createTempFile();
		files.put("1", file1);
		files.put("2", file2);

		File file3 = FileUtil.createTempFile();
		FileMap<String> map1 = new FileMap<String>();
		map1.put("1", file3);

		map1.putAll(files);
		Assert.assertEquals(2, map1.size());
		Assert.assertFalse(file3.exists());
		Assert.assertTrue(file1.exists());
		Assert.assertTrue(file2.exists());

		FileMap<String> map2 = new FileMap<String>();
		File file4 = map2.createTempFile("2");

		map1.putAll(map2);
		Assert.assertEquals(2, map1.size());
		Assert.assertFalse(file2.exists());
		Assert.assertTrue(file4.exists());

		map1.putAll(null);
		Assert.assertEquals(2, map1.size());
	}

	@Test
	public void testCreateTempFile() {
		FileMap<String> map = new FileMap<String>();
		File temp1 = map.createTempFile("1");
		Assert.assertEquals(1, map.size());
		Assert.assertTrue(temp1.exists());

		File temp2 = map.createTempFile(null);
		Assert.assertEquals(2, map.size());
		Assert.assertTrue(temp2.exists());
	}

	@Test
	public void testCreateFile() {
		FileMap<String> map = new FileMap<String>();
		File file1 = map.createFile("1", "/tmp/testCreateFile");
		Assert.assertEquals(1, map.size());
		Assert.assertTrue(file1.exists());

		File file2 = map.createFile(null, null);
		Assert.assertEquals(2, map.size());
		Assert.assertNull(file2);
	}

	@Test
	public void testGet() {
		FileMap<String> map = new FileMap<String>();
		File temp = map.createTempFile("1");

		Assert.assertSame(temp, map.get("1"));
		Assert.assertNull(map.get(null));
	}

	@Test
	public void testClear() {
		FileMap<String> map = new FileMap<String>();
		File file1 = map.createTempFile("1");
		File file2 = map.createFile("2", "/tmp/testClear");
		File file3 = FileUtil.createTempFile();
		map.put("3", file3);

		Assert.assertEquals(3, map.size());
		map.clear();
		Assert.assertEquals(0, map.size());

		Assert.assertFalse(file1.exists());
		Assert.assertFalse(file2.exists());
		Assert.assertFalse(file3.exists());
	}

	@Test
	public void testEntryMap() {
		FileMap<String> map = new FileMap<String>();
		File file1 = map.createTempFile("1");
		File file2 = map.createFile("2", "/tmp/testEntryMap");

		Map<String, File> entryMap = map.entryMap();
		Assert.assertEquals(2, entryMap.size());
		Assert.assertEquals(file1, entryMap.get("1"));
		Assert.assertEquals(file2, entryMap.get("2"));

		entryMap.clear();
		Assert.assertEquals(0, entryMap.size());
		Assert.assertEquals(2, map.size());
	}

	@Test
	public void testRemove() {
		FileMap<String> map = new FileMap<String>();
		File file = map.createTempFile("1");

		File oldFile = map.remove("1");
		Assert.assertSame(file, oldFile);
		Assert.assertFalse(oldFile.exists());

		Assert.assertNull(map.remove(null));
	}

	@Test
	public void testContainsKey() {
		FileMap<String> map = new FileMap<String>();
		map.createTempFile("1");
		Assert.assertTrue(map.containsKey("1"));
		Assert.assertFalse(map.containsKey(null));
	}

	@Test
	public void testContainsValue() {
		FileMap<String> map = new FileMap<String>();
		File file = map.createTempFile("1");
		Assert.assertTrue(map.containsValue(file));
		Assert.assertFalse(map.containsValue(null));
	}

	@Test
	public void testValues() {
		FileMap<String> map = new FileMap<String>();
		File file1 = map.createTempFile("1");
		File file2 = map.createTempFile("2");

		Collection<File> files = map.values();
		Assert.assertEquals(2, files.size());
		Assert.assertTrue(files.contains(file1));
		Assert.assertTrue(files.contains(file2));
	}

	@Test
	public void testKeySet() {
		FileMap<String> map = new FileMap<String>();
		map.createTempFile("1");
		map.createTempFile("2");

		Set<String> keys = map.keySet();
		Assert.assertEquals(2, keys.size());
		Assert.assertTrue(keys.contains("1"));
		Assert.assertTrue(keys.contains("2"));
	}

	@Test
	public void testEntrySet() {
		FileMap<String> map = new FileMap<String>();
		map.createTempFile("1");
		map.createTempFile("2");

		Set<Map.Entry<String, File>> entries = map.entrySet();
		Assert.assertEquals(2, entries.size());
		Iterator<Map.Entry<String, File>> i = entries.iterator();
		Assert.assertEquals("1", i.next().getKey());
		Assert.assertEquals("2", i.next().getKey());
	}

	@Test
	public void testIsEmpty() {
		FileMap<String> map = new FileMap<String>();
		Assert.assertTrue(map.isEmpty());

		map.createTempFile("1");
		Assert.assertFalse(map.isEmpty());
	}

	@Test
	public void testSize() {
		FileMap<String> map = new FileMap<String>();
		Assert.assertEquals(0, map.size());

		map.createTempFile("1");
		Assert.assertEquals(1, map.size());
	}
}
