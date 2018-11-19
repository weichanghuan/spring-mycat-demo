package com.base.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Frame结构字节输出流。
 * 
 *
 */
public class FrameOutputStream {
	/**
	 * 输出流
	 */
	private OutputStream out;

	public FrameOutputStream(OutputStream out) {
		this.out = out;
	}

	private void writeFrameBytesImpl(byte[] data, int off, int len) throws IOException {
		byte[] lenData = FrameLen.toBytes(len);
		out.write(lenData);
		out.write(data, off, len);
	}

	public void writeFrameBytes(byte[] data, int off, int len) {
		try {
			writeFrameBytesImpl(data, off, len);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void writeFrameBytes(byte[] data) {
		writeFrameBytes(data, 0, data.length);
	}

	public void flush() {
		try {
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void close() {
		try {
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
