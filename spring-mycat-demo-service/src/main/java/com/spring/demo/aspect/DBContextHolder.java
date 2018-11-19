package com.spring.demo.aspect;

/**
 * Created by Administrator on 2018/8/3.
 */
public class DBContextHolder {
    public static final String DATA_SOURCE_MASTER = "Write";
    public static final String DATA_SOURCE_SLAVER = "Read";

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>();

    public static void setDBType(String dbType) {
        THREAD_LOCAL.set(dbType);
    }

    public static String getDBType() {
        return THREAD_LOCAL.get();
    }

    public static void clearDBType() {
        THREAD_LOCAL.remove();
    }
}
