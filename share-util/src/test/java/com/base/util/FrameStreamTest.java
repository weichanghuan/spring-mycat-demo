package com.base.util;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

/**
 * Frame结构字节流测试类。
 * 
 *
 */
public class FrameStreamTest {

	@Test
	public void test() {
		System.out.println("frame-len-size=" + FrameLen.FRAME_LEN_SIZE);
		
		byte[] lenBuf = FrameLen.toBytes(1024*1024*20);
		System.out.println(FrameLen.fromBytes(lenBuf));
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		FrameOutputStream out = new FrameOutputStream(bout);
		
		String str1 = "1234567";
		String str2 = "12345678";
		
		byte[] data = str1.getBytes();
		out.writeFrameBytes(data);
		data = str2.getBytes();
		out.writeFrameBytes(data);
		
		ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		FrameInputStream in = new FrameInputStream(bin);
		data = in.readFrameBytes();
		assertEquals(str1, new String(data));
		data = in.readFrameBytes();
		assertEquals(str2, new String(data));
		
		data = bout.toByteArray();
		data[0] = 0x7f;
		data[1] = 0x7f;
		data[2] = 0x7f;
		data[3] = 0x7f;
		bin = new ByteArrayInputStream(data);
		in = new FrameInputStream(bin);
		try {
			data = in.readFrameBytes();
			fail("Fail to throw e");
		} catch(CorruptedFrameInputStreamException e) {
		}
	}

}
