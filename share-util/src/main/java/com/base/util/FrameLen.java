package com.base.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Frame结构长度类，采用BIG_ENDIAN字节顺序，用于网络传输。
 */
public class FrameLen {
    public static int FRAME_LEN_SIZE;

    static {
        ByteBuffer bb = ByteBuffer.allocate(128);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(1024 * 1024);
        FRAME_LEN_SIZE = bb.position();
    }

    public static int fromBytes(byte[] lenData) {
        ByteBuffer bb = ByteBuffer.wrap(lenData);
        bb.order(ByteOrder.BIG_ENDIAN);
        return bb.getInt();
    }

    public static byte[] toBytes(int len) {
        ByteBuffer bb = ByteBuffer.allocate(FRAME_LEN_SIZE);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(len);
        return bb.array();
    }
}
