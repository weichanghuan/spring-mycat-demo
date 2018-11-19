package com.base.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Frame结构字节输入流。
 * 
 *
 */
public class FrameInputStream {
	/**
	 * 缺省最大帧尺寸8MBytes
	 */
	public static final int DEFAULT_MAX_FRAME_SIZE = 1024 * 1024 * 8;

	/**
	 * 输入流
	 */
	private InputStream in;

	/**
	 * 最大帧尺寸（字节数）
	 */
	private int maxFrameSize = DEFAULT_MAX_FRAME_SIZE;

	public FrameInputStream(InputStream in, int maxFrameSize) {
		this.in = in;
		this.maxFrameSize = maxFrameSize;
	}
	
	public FrameInputStream(InputStream in) {
		this.in = in;
	}

	private byte[] readFrameBytesImpl() throws IOException {
		byte[] lenData = new byte[FrameLen.FRAME_LEN_SIZE];
		int i = in.read(lenData, 0, FrameLen.FRAME_LEN_SIZE);
		if (i == -1) {
			// EOF
			return null;
		}

		if (i != FrameLen.FRAME_LEN_SIZE) {
			throw new CorruptedFrameInputStreamException("Can't read the frame len.");
		}

		int len = FrameLen.fromBytes(lenData);
		if (len > maxFrameSize) {
			throw new CorruptedFrameInputStreamException("Too big frame, frame_len=[" + len + "].");
		}

		byte[] data = new byte[len];
		i = in.read(data, 0, len);
		if (i != len) {
			throw new CorruptedFrameInputStreamException("Can't read the frame body, excepted=[" + len + "], actual=["
					+ i + "].");
		}

		return data;
	}

	/**
	 * 读取Frame字节块。
	 * 
	 * @return null表示达到字节流尾部。
	 * @throws IOException
	 */
	public byte[] readFrameBytes() {
		try {
			return readFrameBytesImpl();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void close() {
		try {
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
