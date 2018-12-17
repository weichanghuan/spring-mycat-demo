package com.base.util;

import org.junit.Test;
import org.junit.Assert;

/**
 * 系统工具测试类
 */
public class SystemUtilTest {
    @Test
    public void testGetStackTraceAsString() {
        String trace = SystemUtil.getStackTraceAsString();
        Assert.assertTrue(trace.contains("SystemUtil.getStackTraceAsString"));
        Assert.assertTrue(trace.contains("SystemUtilTest.testGetStackTraceAsString"));

        trace = SystemUtil.getStackTraceAsString(";");
        Assert.assertTrue(trace.contains(";SystemUtil.getStackTraceAsString"));
        Assert.assertTrue(trace.contains(";SystemUtilTest.testGetStackTraceAsString"));

        Thread thread = new Thread() {
            public void run() {
                testSleep();
            }

            private void testSleep() {
                SleepUtil.sleep(500L);
            }
        };
        Assert.assertEquals("", SystemUtil.getStackTraceAsString(thread)); // thread-not-start

        thread.start();
        SleepUtil.sleep(200L);

        trace = SystemUtil.getStackTraceAsString(thread);
        Assert.assertTrue(trace.contains("SleepUtil.sleep"));
        Assert.assertTrue(trace.contains("SystemUtilTest$1.testSleep"));

        trace = SystemUtil.getStackTraceAsString(thread, ";");
        Assert.assertTrue(trace.contains(";SleepUtil.sleep"));
        Assert.assertTrue(trace.contains(";SystemUtilTest$1.testSleep"));

        SleepUtil.sleep(500L);

        Assert.assertEquals("", SystemUtil.getStackTraceAsString(thread)); // thread-terminated

        Assert.assertNull(SystemUtil.getStackTraceAsString((Thread) null));
    }
}
