package com.base.util;

/**
 * 系统工具类。
 */
public class SystemUtil {
    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        if (os.toUpperCase().indexOf("WINDOWS") >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将当前线程的堆栈输出为字符串
     *
     * @return
     */
    public static String getStackTraceAsString() {
        return getStackTraceAsString(Thread.currentThread(), null);
    }

    /**
     * 将当前线程的堆栈输出为字符串，可指定连接符
     *
     * @param separator
     * @return
     */
    public static String getStackTraceAsString(String separator) {
        return getStackTraceAsString(Thread.currentThread(), separator);
    }

    /**
     * 将指定线程的堆栈输出为字符串
     *
     * @param thread
     * @return
     */
    public static String getStackTraceAsString(Thread thread) {
        return getStackTraceAsString(thread, null);
    }

    /**
     * 将指定线程的堆栈输出为字符串，可指定连接符
     *
     * @param thread
     * @param separator
     * @return
     */
    public static String getStackTraceAsString(Thread thread, String separator) {
        if (thread == null) {
            return null;
        }

        return StringUtil.join(thread.getStackTrace(), StringUtil.defaultString(separator, "\n"), false);
    }
}
