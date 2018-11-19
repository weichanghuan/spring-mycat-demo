package com.base.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Random;

import org.junit.Test;

/**
 * Zip工具单元测试类。
 * 
 *
 */
public class ZipUtilTest {

	@Test
	public void test() {
		StringBuffer sb = new StringBuffer();
		Random rd = new Random(System.currentTimeMillis());
		for ( int i=0; i < 4096; i++ ) {
			sb.append(String.valueOf(rd.nextInt()%10));
		}
		
		byte[] cdata = ZipUtil.zipBytes(sb.toString().getBytes());
		System.out.println(cdata.length);
		byte[] pdata = ZipUtil.unzipBytes(cdata);
		String str = new String(pdata);
		
		assertEquals(sb.length(), str.length());
		assertEquals(sb.toString(), str);
		
		String fileName = ZipUtilTest.class.getResource("/test-data/a.txt").getPath();
		File file = new File(fileName);
		File zipFile = ZipUtil.zipFile(file);
		
		file.delete();
		
		ZipUtil.unzipFile(zipFile, new String[]{"a.txt"});
	}

}
