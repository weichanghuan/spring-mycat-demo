package com.base.util;

/**
 * 布尔值工具类
 */
public final class BooleanUtil {
    /**
     * 将字符串解析为布尔值，如果boolVal不为null且值为1或true(忽略大小写)，返回true；如果boolVal为null，
     * 则返回defVal；否则返回false
     *
     * @param boolVal
     * @param defVal
     * @return
     */
    public static boolean parseBoolean(String boolVal, boolean defVal) {
        return (boolVal != null ? boolVal.equalsIgnoreCase("true") || boolVal.equals("1") : defVal);
    }

    private BooleanUtil() {
    }
}
