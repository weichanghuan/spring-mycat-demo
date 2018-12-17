package com.base.util;

import java.nio.charset.Charset;

/**
 * 常用字符集常量类
 */
public final class Charsets {
    /**
     * US-ASCII
     */
    public static final String US_ASCII = "US-ASCII";

    /**
     * ISO-8859-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * UTF-8
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * UTF-16
     */
    public static final String UTF_16 = "UTF-16";

    /**
     * GB2312
     */
    public static final String GB2312 = "GB2312";

    /**
     * GBK
     */
    public static final String GBK = "GBK";

    /**
     * GB18030
     */
    public static final String GB18030 = "GB18030";

    /**
     * 是否是支持的字符集
     *
     * @param charsetName
     * @return
     */
    public static boolean isSupported(String charsetName) {
        try {
            Charset.forName(charsetName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 返回默认字符集名称
     *
     * @return
     */
    public static String defaultCharset() {
        return Charset.defaultCharset().name();
    }

    private Charsets() {
    }
}
