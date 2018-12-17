package com.base.util;

import static com.base.util.ByteUtil.find;
import static com.base.util.ByteUtil.fromHex;
import static com.base.util.ByteUtil.indexOf;
import static com.base.util.ByteUtil.length;
import static com.base.util.ByteUtil.parseBytes;
import static com.base.util.ByteUtil.subBytes;
import static com.base.util.ByteUtil.xor;

import org.junit.Assert;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * Byte工具测试类
 */
public class ByteUtilTest {
    /**
     * 测试length方法
     */
    @Test
    public void testLength() {
        // length(Object[])
        assertEquals(1, ByteUtil.length(new byte[]{0x01}));
        assertEquals(2, ByteUtil.length(new byte[]{0x01, 0x02}));
        assertEquals(0, ByteUtil.length(new byte[0]));
        assertEquals(0, ByteUtil.length((byte[]) null));

        // length(Object[][])
        assertEquals(3, ByteUtil.length((byte[]) null, new byte[]{0x01}, new byte[]{0x01, 0x02}));
        assertEquals(0, ByteUtil.length((byte[][]) null));
        assertEquals(0, ByteUtil.length());
    }

    /**
     * 测试concat方法
     */
    @Test
    public void testConcat() {
        final byte[] bytes1 = {0x01, (byte) 0xff};
        final byte[] bytes2 = {0x01, 0x02};
        final byte[] bytes3 = {0x03, 0x04};

        byte[] concatBytes = ByteUtil.concat((byte[]) null, bytes1, bytes2, bytes3);
        assertEquals(bytes1[0], concatBytes[0]);
        assertEquals(bytes1[1], concatBytes[1]);
        assertEquals(bytes2[0], concatBytes[2]);
        assertEquals(bytes2[1], concatBytes[3]);
        assertEquals(bytes3[0], concatBytes[4]);
        assertEquals(bytes3[1], concatBytes[5]);

        assertEquals(0, ByteUtil.concat((byte[]) null, (byte[]) null).length);
        assertNull(ByteUtil.concat((byte[][]) null));
    }

    /**
     * 测试inverse方法
     */
    @Test
    public void testInverse() {
        final byte[] bytes = {0x01, 0x02, 0x03, 0x04};

        byte[] inversedBytes = ByteUtil.inverse(bytes);
        assertEquals((byte) 0xfe, inversedBytes[0]);
        assertEquals((byte) 0xfd, inversedBytes[1]);
        assertEquals((byte) 0xfc, inversedBytes[2]);
        assertEquals((byte) 0xfb, inversedBytes[3]);
        assertEquals((byte) 0x01, bytes[0]);
        assertEquals((byte) 0x02, bytes[1]);
        assertEquals((byte) 0x03, bytes[2]);
        assertEquals((byte) 0x04, bytes[3]);

        assertNull(ByteUtil.inverse(null));
        assertEquals(0, ByteUtil.inverse(new byte[0]).length);
    }

    /**
     * 测试GetBytes方法
     */
    @Test
    public void testGetBytes() {
        // 1 getBytes(offset, length)
        final byte[] bArr = {0x00, 0x01, 0x02, (byte) 0xff};
        byte[] subArr = ByteUtil.getBytes(bArr, 0, 4);

        assertEquals(4, subArr.length);
        assertEquals(bArr[0], subArr[0]);
        assertEquals(bArr[3], subArr[3]);

        // 2
        subArr = ByteUtil.getBytes(bArr, 0, 10);
        assertEquals(4, subArr.length);
        assertEquals(bArr[0], subArr[0]);
        assertEquals(bArr[3], subArr[3]);

        // 3
        subArr = ByteUtil.getBytes(bArr, -999, 2);
        assertEquals(2, subArr.length);
        assertEquals(bArr[0], subArr[0]);
        assertEquals(bArr[1], subArr[1]);

        // 4
        subArr = ByteUtil.getBytes(bArr, -2, 1);
        assertEquals(1, subArr.length);
        assertEquals(bArr[2], subArr[0]);

        // 5
        subArr = ByteUtil.getBytes(bArr, -2, 999);
        assertEquals(2, subArr.length);
        assertEquals(bArr[2], subArr[0]);
        assertEquals(bArr[3], subArr[1]);

        // 6
        subArr = ByteUtil.getBytes(bArr, 0, 0);
        assertEquals(0, subArr.length);

        // 7
        subArr = ByteUtil.getBytes(bArr, -1, 0);
        assertEquals(0, subArr.length);

        // 8
        subArr = ByteUtil.getBytes(bArr, 4, 1);
        assertEquals(0, subArr.length);

        // 9
        subArr = ByteUtil.getBytes(bArr, 0, -1);
        assertEquals(0, subArr.length);

        // 10
        subArr = ByteUtil.getBytes(null, 0, 4);
        assertNull(subArr);

        // 11
        subArr = ByteUtil.getBytes(null, -1, 10);
        assertNull(subArr);
    }

    /**
     * 测试SubBytes方法
     */
    @Test
    public void testSubBytes() {
        final byte[] bArr = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};

        // subBytes(arr, start, end)
        // 1
        byte[] subArr = ByteUtil.subBytes(bArr, 1, 3);
        assertEquals(2, subArr.length);
        assertEquals(bArr[1], subArr[0]);
        assertEquals(bArr[2], subArr[1]);

        // 2
        subArr = ByteUtil.subBytes(bArr, 1, 999);
        assertEquals(9, subArr.length);
        assertEquals(bArr[1], subArr[0]);
        assertEquals(bArr[9], subArr[8]);

        // 3
        subArr = ByteUtil.subBytes(bArr, -3, 9);
        assertEquals(2, subArr.length);
        assertEquals(bArr[7], subArr[0]);
        assertEquals(bArr[8], subArr[1]);

        // 4
        subArr = ByteUtil.subBytes(bArr, -999, 1);
        assertEquals(1, subArr.length);
        assertEquals(bArr[0], subArr[0]);

        // 5
        subArr = ByteUtil.subBytes(bArr, -999, 999);
        assertEquals(10, subArr.length);
        assertEquals(bArr[0], subArr[0]);
        assertEquals(bArr[9], subArr[9]);

        // 6
        subArr = ByteUtil.subBytes(bArr, 5, -2);
        assertEquals(3, subArr.length);
        assertEquals(bArr[5], subArr[0]);
        assertEquals(bArr[7], subArr[2]);

        // 7
        subArr = ByteUtil.subBytes(bArr, -4, -2);
        assertEquals(2, subArr.length);
        assertEquals(bArr[6], subArr[0]);
        assertEquals(bArr[7], subArr[1]);

        // 8
        subArr = ByteUtil.subBytes(bArr, 0, 0);
        assertArrayEquals(bArr, subArr);

        // 9
        subArr = ByteUtil.subBytes(bArr, 1, 0);
        assertEquals(9, subArr.length);
        assertEquals(bArr[1], subArr[0]);
        assertEquals(bArr[9], subArr[8]);

        // 10
        subArr = ByteUtil.subBytes(bArr, -1, 0);
        assertEquals(1, subArr.length);
        assertEquals(bArr[9], subArr[0]);

        // 11
        subArr = ByteUtil.subBytes(bArr, 4, 2);
        assertEquals(0, subArr.length);

        // 12
        subArr = ByteUtil.subBytes(bArr, 100, 102);
        assertEquals(0, subArr.length);

        // 13
        subArr = ByteUtil.subBytes(bArr, -1, -2);
        assertEquals(0, subArr.length);

        // 14
        subArr = ByteUtil.subBytes(bArr, 0, -999);
        assertEquals(0, subArr.length);

        // 15
        subArr = ByteUtil.subBytes(new byte[0], 0, 1);
        assertEquals(0, subArr.length);

        // 16
        assertNull(ByteUtil.subBytes(null, 0, 1));

        // subBytes(arr, start)
        // 1
        subArr = ByteUtil.subBytes(bArr, 1);
        assertEquals(9, subArr.length);
        assertEquals(bArr[1], subArr[0]);
        assertEquals(bArr[9], subArr[8]);

        // 2
        subArr = ByteUtil.subBytes(bArr, 10);
        assertEquals(0, subArr.length);

        // 3
        subArr = ByteUtil.subBytes(bArr, 999);
        assertEquals(0, subArr.length);

        // 4
        subArr = ByteUtil.subBytes(bArr, -9);
        assertEquals(9, subArr.length);
        assertEquals(bArr[1], subArr[0]);
        assertEquals(bArr[9], subArr[8]);

        // 5
        subArr = ByteUtil.subBytes(bArr, -10);
        assertEquals(10, subArr.length);
        assertEquals(bArr[0], subArr[0]);
        assertEquals(bArr[9], subArr[9]);

        // 6
        subArr = ByteUtil.subBytes(bArr, -999);
        assertEquals(10, subArr.length);
        assertEquals(bArr[0], subArr[0]);
        assertEquals(bArr[9], subArr[9]);

        // 7
        assertNull(ByteUtil.subBytes(null, 0));
    }

    /**
     * 测试xor方法
     */
    @Test
    public void testXor() {
        byte[] byteArr1 = {0x01, 0x0f, 0x10, (byte) 0xff};
        byte[] byteArr2 = {0x00, 0x00, 0x00, 0x00};
        byte[] byteArr3 = {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        byte[] byteArr4 = {(byte) 0xfe, (byte) 0xf0, (byte) 0xef, (byte) 0x00};

        // xor (byte[][])
        assertArrayEquals(byteArr2, ByteUtil.xor(byteArr1, byteArr1));
        assertArrayEquals(byteArr1, ByteUtil.xor(byteArr1, byteArr2));
        assertArrayEquals(byteArr4, ByteUtil.xor(byteArr1, byteArr3));
        assertArrayEquals(byteArr3, ByteUtil.xor(byteArr1, byteArr4));
        assertArrayEquals(byteArr2, ByteUtil.xor(byteArr1, byteArr2, byteArr3, byteArr4));

        assertArrayEquals(byteArr1, ByteUtil.xor(byteArr1));
        assertArrayEquals(byteArr1, ByteUtil.xor(byteArr1, null, null));
        assertArrayEquals(byteArr2, ByteUtil.xor(byteArr1, null, byteArr1));

        assertNull(ByteUtil.xor((byte[]) null));
        assertNull(ByteUtil.xor((byte[][]) null));

        try {
            ByteUtil.xor(byteArr1, new byte[1]); // diff length
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        // xor (byte[], byte)
        assertArrayEquals(byteArr1, ByteUtil.xor(byteArr1, (byte) 0x00));
        assertArrayEquals(byteArr4, ByteUtil.xor(byteArr1, (byte) 0xff));
        assertArrayEquals(byteArr3, ByteUtil.xor(byteArr2, (byte) 0xff));

        assertNull(ByteUtil.xor((byte[]) null, (byte) 0x00));
    }

    /**
     * 测试rightPad方法
     */
    @Test
    public void testRightPad() {
        final byte[] bytes1 = {0x01, 0x02};
        final byte[] bytes2 = {0x01, 0x02, 0x00, 0x00};
        final byte[] bytes3 = {0x01, 0x02, (byte) 0xff, (byte) 0xff};
        final byte[] bytes4 = {0x00, 0x00};

        assertArrayEquals(bytes2, ByteUtil.rightPad(bytes1, 4, (byte) 0x00));
        assertArrayEquals(bytes3, ByteUtil.rightPad(bytes1, 4, (byte) 0xff));

        assertArrayEquals(bytes1, ByteUtil.rightPad(bytes1, 2, (byte) 0xff));
        assertArrayEquals(bytes1, ByteUtil.rightPad(bytes1, 0, (byte) 0xff));
        assertArrayEquals(bytes1, ByteUtil.rightPad(bytes1, -2, (byte) 0xff));

        assertArrayEquals(bytes4, ByteUtil.rightPad(new byte[0], 2, (byte) 0x00));
        assertNull(ByteUtil.rightPad(null, 1, (byte) 0x00));
    }

    /**
     * 测试parseBytes方法
     */
    @Test
    public void testParseBytes() {
        // parseBytes(long)
        assertArrayEquals(new byte[]{0x00}, ByteUtil.parseBytes(0L));
        assertArrayEquals(new byte[]{0x01}, ByteUtil.parseBytes(1L));
        assertArrayEquals(new byte[]{0x63}, ByteUtil.parseBytes(99L));
        assertArrayEquals(new byte[]{(byte) 0xff}, ByteUtil.parseBytes(255L));
        assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff}, ByteUtil.parseBytes(65535L));
        assertArrayEquals(new byte[]{0x01, 0x00, 0x00,}, ByteUtil.parseBytes(65536L));
        assertEquals(8, ByteUtil.parseBytes(Long.MAX_VALUE).length);

        assertNull(ByteUtil.parseBytes(-1L));
        assertNull(ByteUtil.parseBytes(Long.MIN_VALUE));

        // parseBytes(long, length)
        assertArrayEquals(new byte[]{0x00, 0x00}, ByteUtil.parseBytes(0L, 2));
        assertArrayEquals(new byte[]{0x00, 0x63}, ByteUtil.parseBytes(99L, 2));
        assertArrayEquals(new byte[]{0x01, 0x00}, ByteUtil.parseBytes(256L, 2));
        assertArrayEquals(new byte[]{0x00, 0x01, 0x02}, ByteUtil.parseBytes(258L, 3));
        assertArrayEquals(new byte[]{0x02}, ByteUtil.parseBytes(258L, 1)); // 只处理一个字节

        assertNull(ByteUtil.parseBytes(-1L, 2));
        assertNull(ByteUtil.parseBytes(1L, 0));
        assertNull(ByteUtil.parseBytes(1L, -1));
    }

    /**
     * 测试toLong方法
     */
    @Test
    public void testToLong() {
        assertEquals(0L, ByteUtil.toLong(new byte[]{0x00}));
        assertEquals(1L, ByteUtil.toLong(new byte[]{0x01}));
        assertEquals(1L, ByteUtil.toLong(new byte[]{0x00, 0x00, 0x00, 0x01}));
        assertEquals(255L, ByteUtil.toLong(new byte[]{(byte) 0xff}));
        assertEquals(256L, ByteUtil.toLong(new byte[]{0x01, 0x00}));
        assertEquals(256L, ByteUtil.toLong(new byte[]{0x00, 0x00, 0x01, 0x00}));
        assertEquals(65535L, ByteUtil.toLong(new byte[]{(byte) 0xff, (byte) 0xff}));

        assertEquals(123456L, ByteUtil.toLong(ByteUtil.parseBytes(123456L)));
        assertEquals(Long.MAX_VALUE, ByteUtil.toLong(ByteUtil.parseBytes(Long.MAX_VALUE)));

        assertEquals(0L, ByteUtil.toLong(null));
        assertEquals(0L, ByteUtil.toLong(new byte[0]));
    }

    /**
     * 测试reverse方法
     */
    @Test
    public void testReverse() {
        byte[] bytes = {0x00, 0x01};
        ByteUtil.reverse(bytes);
        assertArrayEquals(new byte[]{0x01, 0x00}, bytes);

        bytes = new byte[]{(byte) 0x9A, 0x78, 0x56, 0x34, 0x12};
        ByteUtil.reverse(bytes);
        assertArrayEquals(new byte[]{0x12, 0x34, 0x56, 0x78, (byte) 0x9A}, bytes);

        bytes = new byte[]{0x01};
        ByteUtil.reverse(bytes);
        assertArrayEquals(new byte[]{0x01}, bytes);

        bytes = new byte[0];
        ByteUtil.reverse(bytes);
        assertArrayEquals(new byte[0], bytes);

        ByteUtil.reverse(null); // Nothing happened
    }

    /**
     * 测试startsWith
     */
    @Test
    public void testStartsWith() {
        byte[] bytes = {0x00, 0x01, 0x02, 0x03};

        assertTrue(ByteUtil.startsWith(bytes, 0, new byte[]{0x00, 0x01}));
        assertTrue(ByteUtil.startsWith(bytes, 2, (byte) 0x02, (byte) 0x03));
        assertTrue(ByteUtil.startsWith(bytes, 0, new byte[]{0x00}));
        assertTrue(ByteUtil.startsWith(bytes, 0, (byte) 0x00));
        assertFalse(ByteUtil.startsWith(bytes, 1, (byte) 0x00));
        assertFalse(ByteUtil.startsWith(bytes, -1, (byte) 0x00));
        assertFalse(ByteUtil.startsWith(bytes, 100, (byte) 0x00));
        assertFalse(ByteUtil.startsWith(bytes, 0, new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05}));
        assertFalse(ByteUtil.startsWith(bytes, 0, null));
        assertFalse(ByteUtil.startsWith(null, 0, new byte[]{0x00}));
        assertFalse(ByteUtil.startsWith(bytes, 0, new byte[0]));
    }

    /**
     * 测试tirmLeft方法
     */
    @Test
    public void testTirmLeft() {
        byte[] bytes = {0x01, 0x02, 0x01, 0x02, 0x03, 0x03, 0x01, 0x02, 0x01, 0x02};
        assertArrayEquals(new byte[]{0x02, 0x01, 0x02, 0x03, 0x03, 0x01, 0x02, 0x01, 0x02},
                ByteUtil.tirmLeft(bytes, new byte[]{0x01})); // bytes
        assertArrayEquals(new byte[]{0x02, 0x01, 0x02, 0x03, 0x03, 0x01, 0x02, 0x01, 0x02},
                ByteUtil.tirmLeft(bytes, (byte) 0x01)); // byte
        assertArrayEquals(new byte[]{0x03, 0x03, 0x01, 0x02, 0x01, 0x02},
                ByteUtil.tirmLeft(bytes, new byte[]{0x01, 0x02}));
        assertArrayEquals(new byte[]{0x03, 0x03, 0x01, 0x02, 0x01, 0x02},
                ByteUtil.tirmLeft(bytes, new byte[]{0x01, 0x02, 0x01, 0x02}));

        assertArrayEquals(bytes, ByteUtil.tirmLeft(bytes, (byte) 0x00));
        assertArrayEquals(bytes, ByteUtil.tirmLeft(bytes, new byte[]{0x00}));
        assertArrayEquals(bytes, ByteUtil.tirmLeft(bytes, null));
        assertArrayEquals(bytes, ByteUtil.tirmLeft(bytes));
        assertArrayEquals(new byte[0], ByteUtil.tirmLeft(new byte[]{0x01, 0x01}, (byte) 0x01));
        assertArrayEquals(new byte[]{0x02}, ByteUtil.tirmLeft(new byte[]{0x01, 0x01, 0x02}, (byte) 0x01));
        assertArrayEquals(new byte[]{0x01}, ByteUtil.tirmLeft(new byte[]{0x01}, new byte[]{0x01, 0x01}));
        assertNull(ByteUtil.tirmLeft(null, (byte) 0x01));
    }

    /**
     * 测试tirmRight方法
     */
    @Test
    public void testTirmRight() {
        byte[] bytes = {0x01, 0x02, 0x01, 0x02, 0x03, 0x03, 0x01, 0x02, 0x01, 0x02};
        assertArrayEquals(new byte[]{0x01, 0x02, 0x01, 0x02, 0x03, 0x03, 0x01, 0x02, 0x01},
                ByteUtil.tirmRight(bytes, new byte[]{0x02})); // bytes
        assertArrayEquals(new byte[]{0x01, 0x02, 0x01, 0x02, 0x03, 0x03, 0x01, 0x02, 0x01},
                ByteUtil.tirmRight(bytes, (byte) 0x02)); // byte
        assertArrayEquals(new byte[]{0x01, 0x02, 0x01, 0x02, 0x03, 0x03},
                ByteUtil.tirmRight(bytes, new byte[]{0x01, 0x02}));
        assertArrayEquals(new byte[]{0x01, 0x02, 0x01, 0x02, 0x03, 0x03},
                ByteUtil.tirmRight(bytes, new byte[]{0x01, 0x02, 0x01, 0x02}));

        assertArrayEquals(bytes, ByteUtil.tirmRight(bytes, (byte) 0x00));
        assertArrayEquals(bytes, ByteUtil.tirmRight(bytes, new byte[]{0x00}));
        assertArrayEquals(bytes, ByteUtil.tirmRight(bytes, null));
        assertArrayEquals(bytes, ByteUtil.tirmRight(bytes));
        assertArrayEquals(new byte[0], ByteUtil.tirmRight(new byte[]{0x01, 0x01}, (byte) 0x01));
        assertArrayEquals(new byte[]{0x01}, ByteUtil.tirmRight(new byte[]{0x01, 0x02, 0x02}, (byte) 0x02));
        assertArrayEquals(new byte[]{0x01}, ByteUtil.tirmRight(new byte[]{0x01}, new byte[]{0x01, 0x01}));
        assertNull(ByteUtil.tirmRight(null, (byte) 0x01));
    }

    /**
     * 测试isEmpty方法
     */
    @Test
    public void testIsEmpty() {
        assertTrue(ByteUtil.isEmpty(null));
        assertTrue(ByteUtil.isEmpty(new byte[0]));
        assertFalse(ByteUtil.isEmpty(new byte[]{1}));
    }

    /**
     * 测试isNotEmpty方法
     */
    @Test
    public void testIsNotEmpty() {
        assertFalse(ByteUtil.isNotEmpty(null));
        assertFalse(ByteUtil.isNotEmpty(new byte[0]));
        assertTrue(ByteUtil.isNotEmpty(new byte[]{1}));
    }

    /**
     * 测试byteAt方法
     */
    @Test
    public void testByteAt() {
        final byte[] bArr = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};

        assertEquals(0x00, ByteUtil.byteAt(bArr, 0, (byte) 0xFF));
        assertEquals(0x02, ByteUtil.byteAt(bArr, 2, (byte) 0xFF));
        assertEquals(0x09, ByteUtil.byteAt(bArr, 9, (byte) 0xFF));
        assertEquals((byte) 0xFF, ByteUtil.byteAt(bArr, -1, (byte) 0xFF));
        assertEquals((byte) 0xFF, ByteUtil.byteAt(bArr, 10, (byte) 0xFF));
        assertEquals((byte) 0xFF, ByteUtil.byteAt(null, 0, (byte) 0xFF));
    }

    /**
     * 测试indexOf方法
     */
    @Test
    public void testIndexOf() {
        final byte[] bytes = {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x01, 0x23,
                0x01, 0x23};

        // indexOf(src, target)
        assertEquals(0, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}));
        assertEquals(2, ByteUtil.indexOf(bytes, new byte[]{0x45}));
        assertEquals(9, ByteUtil.indexOf(bytes, new byte[]{0x23, 0x01}));
        assertEquals(-1, ByteUtil.indexOf(bytes, new byte[]{(byte) 0xFF}));
        assertEquals(-1, ByteUtil.indexOf(bytes, new byte[0]));
        assertEquals(-1, ByteUtil.indexOf(bytes, null));
        assertEquals(-1, ByteUtil.indexOf(new byte[0], new byte[]{0x01}));
        assertEquals(-1, ByteUtil.indexOf(null, new byte[]{0x01}));

        // indexOf(src, target, offset)
        assertEquals(0, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, 0));
        assertEquals(0, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, -999));
        assertEquals(8, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, 1));
        assertEquals(8, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, -11));
        assertEquals(10, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, 10));
        assertEquals(10, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, -2));
        assertEquals(-1, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, 12));
        assertEquals(2, ByteUtil.indexOf(bytes, new byte[]{0x45}, 0));
        assertEquals(-1, ByteUtil.indexOf(bytes, new byte[]{0x45}, 3));
        assertEquals(-1, ByteUtil.indexOf(bytes, new byte[0], 0));
        assertEquals(-1, ByteUtil.indexOf(bytes, null, 0));
        assertEquals(-1, ByteUtil.indexOf(new byte[0], new byte[]{0x01}, 0));
        assertEquals(-1, ByteUtil.indexOf(null, new byte[]{0x01}, 0));

        // indexOf(src, target, offset, occurTimes)
        assertEquals(0, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, 0, 1));
        assertEquals(0, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, -999, 1));
        assertEquals(8, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, 1, 1));
        assertEquals(8, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, 0, 2));
        assertEquals(10, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, -11, 2));
        assertEquals(10, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, 0, 3));
        assertEquals(-1, ByteUtil.indexOf(bytes, new byte[]{0x01, 0x23}, 0, 4));
        assertEquals(2, ByteUtil.indexOf(bytes, new byte[]{0x45}, 0, 1));
        assertEquals(-1, ByteUtil.indexOf(bytes, new byte[]{0x45}, 0, 2));
        assertEquals(-1, ByteUtil.indexOf(bytes, new byte[]{0x01}, 0, -1));
        assertEquals(-1, ByteUtil.indexOf(bytes, new byte[0], 0, 1));
        assertEquals(-1, ByteUtil.indexOf(bytes, null, 0, 1));
        assertEquals(-1, ByteUtil.indexOf(new byte[0], new byte[]{0x01}, 0, 1));
        assertEquals(-1, ByteUtil.indexOf(null, new byte[]{0x01}, 0, 1));
    }

    /**
     * 测试find方法
     */
    @Test
    public void testFind() {
        final byte[] bytes = {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x01, 0x23,
                0x01, 0x23};

        // find(src, target)
        assertArrayEquals(new byte[0], ByteUtil.find(bytes, new byte[]{0x01}));
        assertArrayEquals(new byte[]{0x01, 0x23}, ByteUtil.find(bytes, new byte[]{0x45}));
        assertArrayEquals(
                new byte[]{0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x01},
                ByteUtil.find(bytes, new byte[]{0x23, 0x01}));
        assertNull(ByteUtil.find(bytes, new byte[]{(byte) 0xFF}));
        assertNull(ByteUtil.find(bytes, new byte[0]));
        assertNull(ByteUtil.find(bytes, null));
        assertNull(ByteUtil.find(new byte[0], new byte[]{0x01}));
        assertNull(ByteUtil.find(null, new byte[]{0x01}));

        // find(src, target, offset)
        assertArrayEquals(new byte[0], ByteUtil.find(bytes, new byte[]{0x01}, 0));
        assertArrayEquals(new byte[0], ByteUtil.find(bytes, new byte[]{0x01}, -999));
        assertArrayEquals(new byte[]{0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x01},
                ByteUtil.find(bytes, new byte[]{0x23, 0x01}, 1));
        assertArrayEquals(new byte[]{0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x01},
                ByteUtil.find(bytes, new byte[]{0x23, 0x01}, -11));
        assertArrayEquals(new byte[]{0x01}, ByteUtil.find(bytes, new byte[]{0x23, 0x01}, 8));
        assertArrayEquals(new byte[]{0x01}, ByteUtil.find(bytes, new byte[]{0x23, 0x01}, -4));
        assertNull(ByteUtil.find(bytes, new byte[]{0x23, 0x01}, 10));
        assertNull(ByteUtil.find(bytes, new byte[]{0x23, 0x01}, -2));
        assertArrayEquals(new byte[]{0x01, 0x23}, ByteUtil.find(bytes, new byte[]{0x45}, 0));
        assertNull(ByteUtil.find(bytes, new byte[]{0x45}, 3));
        assertNull(ByteUtil.find(bytes, new byte[0], 0));
        assertNull(ByteUtil.find(bytes, null, 0));
        assertNull(ByteUtil.find(new byte[0], new byte[]{0x01}, 0));
        assertNull(ByteUtil.find(null, new byte[]{0x01}, 0));

        // find(src, target, offset, occurTimes)
        assertArrayEquals(new byte[0], ByteUtil.find(bytes, new byte[]{0x01, 0x23}, 0, 1));
        assertArrayEquals(new byte[0], ByteUtil.find(bytes, new byte[]{0x01, 0x23}, -999, 1));
        assertArrayEquals(new byte[]{0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF},
                ByteUtil.find(bytes, new byte[]{0x01, 0x23}, 1, 1));
        assertArrayEquals(new byte[]{0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF},
                ByteUtil.find(bytes, new byte[]{0x01, 0x23}, 0, 2));
        assertArrayEquals(new byte[]{0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF},
                ByteUtil.find(bytes, new byte[]{0x01, 0x23}, -12, 2));
        assertArrayEquals(
                new byte[]{0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x01, 0x23},
                ByteUtil.find(bytes, new byte[]{0x01, 0x23}, 0, 3));

        assertNull(ByteUtil.find(bytes, new byte[]{0x01, 0x23}, 0, 4));
        assertArrayEquals(new byte[]{0x01, 0x23}, ByteUtil.find(bytes, new byte[]{0x45}, 0, 1));
        assertNull(ByteUtil.find(bytes, new byte[]{0x45}, 0, 2));
        assertNull(ByteUtil.find(bytes, new byte[]{0x12}, 0, -1));
        assertNull(ByteUtil.find(bytes, new byte[0], 0, 1));
        assertNull(ByteUtil.find(bytes, null, 0, 1));
        assertNull(ByteUtil.find(new byte[0], new byte[]{0x12}, 0, 1));
        assertNull(ByteUtil.find(null, new byte[]{0x12}, 0, 1));
    }

    /**
     * 测试fromHex方法
     */
    @Test
    public void testFromHex() {
        assertArrayEquals(new byte[]{0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF},
                ByteUtil.fromHex("0123456789ABCDEF"));
        assertArrayEquals(new byte[]{(byte) 0xAB, (byte) 0xCD, (byte) 0xEF}, ByteUtil.fromHex("abcdef"));
        assertArrayEquals(new byte[1], ByteUtil.fromHex("00"));
        assertArrayEquals(new byte[]{0x12, 0x34}, ByteUtil.fromHex("123", '4')); // odd-pad
        assertArrayEquals(new byte[]{0x12, 0x34}, ByteUtil.fromHex("1234", '5')); // even-no-pad
        assertArrayEquals(new byte[0], ByteUtil.fromHex(""));
        assertNull(ByteUtil.fromHex(null));

        try {
            ByteUtil.fromHex("   "); // space
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            ByteUtil.fromHex("你好"); // invalid hex
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }
    }

    /**
     * 测试asHex方法
     */
    @Test
    public void testAsHex() {
        assertEquals("0123456789ABCDEF",
                ByteUtil.asHex(new byte[]{0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF}));
        assertEquals("ABCDEF", ByteUtil.asHex(new byte[]{(byte) 0xab, (byte) 0xcd, (byte) 0xef}));
        assertEquals("abcdef", ByteUtil.asHex(new byte[]{(byte) 0xab, (byte) 0xcd, (byte) 0xef}, true)); // lower-case
        assertEquals("00", ByteUtil.asHex(new byte[1]));
        assertEquals("00", ByteUtil.asHex(new byte[1], true)); // lower-case
        assertEquals("", ByteUtil.asHex(new byte[0]));
        assertNull(ByteUtil.asHex(null));
    }

    /**
     * 测试genRandomByteArray方法
     */
    @Test
    public void testGenRandomByteArray() {
        int length = 1000;
        byte[] randByteArray1 = ByteUtil.genRandomByteArray(length);
        assertEquals(length, randByteArray1.length);
        assertFalse(Arrays.equals(new byte[length], randByteArray1));

        byte[] randByteArray2 = ByteUtil.genRandomByteArray(length);
        assertEquals(length, randByteArray2.length);
        assertFalse(Arrays.equals(new byte[length], randByteArray2));

        assertFalse(Arrays.equals(randByteArray1, randByteArray2));

        try {
            ByteUtil.genRandomByteArray(0);
            fail("Expect error");
        } catch (Exception ex) {
        }

        try {
            ByteUtil.genRandomByteArray(-1);
            fail("Expect error");
        } catch (Exception ex) {
        }
    }
}
