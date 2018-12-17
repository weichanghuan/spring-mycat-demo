package com.base.util;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/**
 * MD5工具测试类
 */
public class MD5Test {
    @Test
    public void testHex() throws Exception {
        String msg = "1";
        String md5Hex = "C4CA4238A0B923820DCC509A6F75849B";

        Assert.assertEquals(md5Hex, MD5.md5Hex32(msg, Charsets.UTF_8));
        Assert.assertEquals(StringUtil.subString(md5Hex, 8, 24), MD5.md5Hex16(msg, Charsets.UTF_8));

        msg = "中国";
        String md5HexGBK = "CF0832DEDF7457BBCBFA00BBD87B300A";
        String md5HexUTF8 = "C13DCEABCB143ACD6C9298265D618A9F";

        Assert.assertEquals(md5HexGBK, MD5.md5Hex32(msg, Charsets.GBK));
        Assert.assertEquals(StringUtil.subString(md5HexGBK, 8, 24), MD5.md5Hex16(msg, Charsets.GBK));

        Assert.assertEquals(md5HexUTF8, MD5.md5Hex32(msg, Charsets.UTF_8));
        Assert.assertEquals(StringUtil.subString(md5HexUTF8, 8, 24), MD5.md5Hex16(msg, Charsets.UTF_8));

        try {
            MD5.md5Hex16(null, Charsets.GBK);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            MD5.md5Hex32(null, Charsets.GBK);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }
    }

    @Test
    public void test() throws Exception {
        String msg = "1";
        String md5Hex = "C4CA4238A0B923820DCC509A6F75849B";
        byte[] md5 = ByteUtil.fromHex(md5Hex);

        Assert.assertArrayEquals(md5, MD5.md5Byte16(msg.getBytes(Charsets.UTF_8)));
        Assert.assertArrayEquals(ByteUtil.subBytes(md5, 4, 12), MD5.md5Byte8(msg.getBytes(Charsets.UTF_8)));

        msg = "中国";
        String md5HexGBK = "CF0832DEDF7457BBCBFA00BBD87B300A";
        String md5HexUTF8 = "C13DCEABCB143ACD6C9298265D618A9F";
        byte[] md5GBK = ByteUtil.fromHex(md5HexGBK);
        byte[] md5UTF8 = ByteUtil.fromHex(md5HexUTF8);

        Assert.assertArrayEquals(md5GBK, MD5.md5Byte16(msg.getBytes(Charsets.GBK)));
        Assert.assertArrayEquals(ByteUtil.subBytes(md5GBK, 4, 12), MD5.md5Byte8(msg.getBytes(Charsets.GBK)));

        Assert.assertArrayEquals(md5UTF8, MD5.md5Byte16(msg.getBytes(Charsets.UTF_8)));
        Assert.assertArrayEquals(ByteUtil.subBytes(md5UTF8, 4, 12), MD5.md5Byte8(msg.getBytes(Charsets.UTF_8)));

        try {
            MD5.md5Byte16((byte[]) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            MD5.md5Byte8((byte[]) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }
    }

    @Test
    public void testFile() throws Exception {
        String msg = "中国";
        String md5HexGBK = "CF0832DEDF7457BBCBFA00BBD87B300A";
        byte[] md5GBK = ByteUtil.fromHex(md5HexGBK);

        File file = FileUtil.createTempFile();
        FileUtil.writeFile(file, msg.getBytes(Charsets.GBK));

        Assert.assertEquals(md5HexGBK, MD5.md5Hex32(file));
        Assert.assertEquals(StringUtil.subString(md5HexGBK, 8, 24), MD5.md5Hex16(file));

        Assert.assertArrayEquals(md5GBK, MD5.md5Byte16(file));
        Assert.assertArrayEquals(ByteUtil.subBytes(md5GBK, 4, 12), MD5.md5Byte8(file));

        try {
            MD5.md5Hex32((File) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }
    }
}
