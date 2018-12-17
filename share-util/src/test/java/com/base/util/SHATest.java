package com.base.util;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/**
 * SHA工具测试类
 */
public class SHATest {
    @Test
    public void testHex() throws Exception {
        String msg = "1";
        String sha1Hex = "356A192B7913B04C54574D18C28D46E6395428AB";
        String sha256Hex = "6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B";
        String sha512Hex = "4DFF4EA340F0A823F15D3F4F01AB62EAE0E5DA579CCB851F8DB9DFE84C58B2B37B89903A740E1EE172DA793A6E79D560E5F7F9BD058A12A280433ED6FA46510A";

        Assert.assertEquals(sha1Hex, SHA.sha1Hex(msg, Charsets.UTF_8));
        Assert.assertEquals(sha256Hex, SHA.sha256Hex(msg, Charsets.UTF_8));
        Assert.assertEquals(sha512Hex, SHA.sha512Hex(msg, Charsets.UTF_8));

        msg = "中国";
        String sha1HexGBK = "AFE49B0708A2735778D679F56A8F8CEF993D702D";
        String sha256HexGBK = "F2856104A1EF1761186F3FA28AC3A2ED13B44C94708AF9D65D76E709EF10FE61";
        String sha512HexGBK = "301826A7CF50368A20EE6F57A0372246D78D16CB0A0926E239C6F6A1C57744FA413C0BA7E360323A481A492F4621D0D8D78CAC9CE76DF6841D547E9029B0051C";
        String sha1HexUTF8 = "101806F57C322FB403A9788C4C24B79650D02E77";
        String sha256HexUTF8 = "F0E9521611BB290D7B09B8CD14A63C3FE7CBF9A2F4E0090D8238D22403D35182";
        String sha512HexUTF8 = "6A169E7D5B7526651086D0D37D6E7686C7E75FF7039D063AD100AEFAB1057A4C1DB1F1E5D088C9585DB1D7531A461AB3F4490CC63809C08CC074574B3FFF759A";

        Assert.assertEquals(sha1HexGBK, SHA.sha1Hex(msg, Charsets.GBK));
        Assert.assertEquals(sha256HexGBK, SHA.sha256Hex(msg, Charsets.GBK));
        Assert.assertEquals(sha512HexGBK, SHA.sha512Hex(msg, Charsets.GBK));
        Assert.assertEquals(sha1HexUTF8, SHA.sha1Hex(msg, Charsets.UTF_8));
        Assert.assertEquals(sha256HexUTF8, SHA.sha256Hex(msg, Charsets.UTF_8));
        Assert.assertEquals(sha512HexUTF8, SHA.sha512Hex(msg, Charsets.UTF_8));

        try {
            SHA.sha1Hex((String) null, Charsets.UTF_8);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            SHA.sha256Hex((String) null, Charsets.UTF_8);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            SHA.sha512Hex((String) null, Charsets.UTF_8);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }
    }

    @Test
    public void testByte() throws Exception {
        String msg = "1";
        String sha1Hex = "356A192B7913B04C54574D18C28D46E6395428AB";
        String sha256Hex = "6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B";
        String sha512Hex = "4DFF4EA340F0A823F15D3F4F01AB62EAE0E5DA579CCB851F8DB9DFE84C58B2B37B89903A740E1EE172DA793A6E79D560E5F7F9BD058A12A280433ED6FA46510A";
        byte[] sha1 = ByteUtil.fromHex(sha1Hex);
        byte[] sha256 = ByteUtil.fromHex(sha256Hex);
        byte[] sha512 = ByteUtil.fromHex(sha512Hex);

        Assert.assertArrayEquals(sha1, SHA.sha1(msg.getBytes(Charsets.UTF_8)));
        Assert.assertArrayEquals(sha256, SHA.sha256(msg.getBytes(Charsets.UTF_8)));
        Assert.assertArrayEquals(sha512, SHA.sha512(msg.getBytes(Charsets.UTF_8)));

        msg = "中国";
        String sha1HexGBK = "AFE49B0708A2735778D679F56A8F8CEF993D702D";
        String sha256HexGBK = "F2856104A1EF1761186F3FA28AC3A2ED13B44C94708AF9D65D76E709EF10FE61";
        String sha512HexGBK = "301826A7CF50368A20EE6F57A0372246D78D16CB0A0926E239C6F6A1C57744FA413C0BA7E360323A481A492F4621D0D8D78CAC9CE76DF6841D547E9029B0051C";
        String sha1HexUTF8 = "101806F57C322FB403A9788C4C24B79650D02E77";
        String sha256HexUTF8 = "F0E9521611BB290D7B09B8CD14A63C3FE7CBF9A2F4E0090D8238D22403D35182";
        String sha512HexUTF8 = "6A169E7D5B7526651086D0D37D6E7686C7E75FF7039D063AD100AEFAB1057A4C1DB1F1E5D088C9585DB1D7531A461AB3F4490CC63809C08CC074574B3FFF759A";
        byte[] sha1GBK = ByteUtil.fromHex(sha1HexGBK);
        byte[] sha256GBK = ByteUtil.fromHex(sha256HexGBK);
        byte[] sha512GBK = ByteUtil.fromHex(sha512HexGBK);
        byte[] sha1UTF8 = ByteUtil.fromHex(sha1HexUTF8);
        byte[] sha256UTF8 = ByteUtil.fromHex(sha256HexUTF8);
        byte[] sha512UTF8 = ByteUtil.fromHex(sha512HexUTF8);

        Assert.assertArrayEquals(sha1GBK, SHA.sha1(msg.getBytes(Charsets.GBK)));
        Assert.assertArrayEquals(sha256GBK, SHA.sha256(msg.getBytes(Charsets.GBK)));
        Assert.assertArrayEquals(sha512GBK, SHA.sha512(msg.getBytes(Charsets.GBK)));
        Assert.assertArrayEquals(sha1UTF8, SHA.sha1(msg.getBytes(Charsets.UTF_8)));
        Assert.assertArrayEquals(sha256UTF8, SHA.sha256(msg.getBytes(Charsets.UTF_8)));
        Assert.assertArrayEquals(sha512UTF8, SHA.sha512(msg.getBytes(Charsets.UTF_8)));

        try {
            SHA.sha1((byte[]) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            SHA.sha256((byte[]) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            SHA.sha512((byte[]) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }
    }

    @Test
    public void testFile() throws Exception {
        String msg = "中国";
        String sha1HexGBK = "AFE49B0708A2735778D679F56A8F8CEF993D702D";
        String sha256HexGBK = "F2856104A1EF1761186F3FA28AC3A2ED13B44C94708AF9D65D76E709EF10FE61";
        String sha512HexGBK = "301826A7CF50368A20EE6F57A0372246D78D16CB0A0926E239C6F6A1C57744FA413C0BA7E360323A481A492F4621D0D8D78CAC9CE76DF6841D547E9029B0051C";
        byte[] sha1GBK = ByteUtil.fromHex(sha1HexGBK);
        byte[] sha256GBK = ByteUtil.fromHex(sha256HexGBK);
        byte[] sha512GBK = ByteUtil.fromHex(sha512HexGBK);

        File file = FileUtil.createTempFile();
        FileUtil.writeFile(file, msg.getBytes(Charsets.GBK));

        Assert.assertEquals(sha1HexGBK, SHA.sha1Hex(file));
        Assert.assertEquals(sha256HexGBK, SHA.sha256Hex(file));
        Assert.assertEquals(sha512HexGBK, SHA.sha512Hex(file));

        Assert.assertArrayEquals(sha1GBK, SHA.sha1(file));
        Assert.assertArrayEquals(sha256GBK, SHA.sha256(file));
        Assert.assertArrayEquals(sha512GBK, SHA.sha512(file));

        try {
            SHA.sha1Hex((File) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            SHA.sha256Hex((File) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            SHA.sha512Hex((File) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            SHA.sha1((File) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            SHA.sha256((File) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }

        try {
            SHA.sha512((File) null);
            Assert.fail("Expect exception");
        } catch (Exception ex) {
        }
    }
}
