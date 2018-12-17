package com.base.util;

import com.base.exception.InterruptedRuntimeException;

/**
 * Sleep工具类。
 */
public class SleepUtil {
    public static void sleep(long ts) {
        try {
            Thread.sleep(ts);
        } catch (InterruptedException e) {
            throw new InterruptedRuntimeException(e.getMessage(), e);
        }
    }
}
