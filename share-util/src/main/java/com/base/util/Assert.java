package com.base.util;

import java.util.Map;

import com.base.exception.AssertFailError;

/**
 * 断言类。
 */
public class Assert {
    public static void assertNotNull(String name, Object value) {
        if (value == null) {
            throw new NotNullException(name);
        }
    }

    public static void assertNotNull(Map<String, ?> map, String name) {
        assertNotNull(name, map.get(name));
    }

    /**
     * 断言为null
     *
     * @param actual
     */
    public static void assertNull(Object actual) {
        if (actual != null) {
            failWhenNotNull(actual);
        }
    }

    /**
     * 断言不为null
     *
     * @param actual
     */
    public static void assertNotNull(Object actual) {
        if (actual == null) {
            failWhenNull();
        }
    }

    /**
     * 断言成功
     *
     * @param condition
     */
    public static void assertTrue(boolean condition) {
        if (condition == false) {
            fail();
        }
    }

    /**
     * 断言失败
     *
     * @param condition
     */
    public static void assertFalse(boolean condition) {
        if (condition) {
            fail();
        }
    }

    /**
     * 断言相等
     *
     * @param expect
     * @param actual
     */
    public static void assertEquals(Object expect, Object actual) {
        if (ObjectUtil.isNotEqual(expect, actual)) {
            failWhenNotEquals(expect, actual);
        }
    }

    /**
     * 断言不相等
     *
     * @param expect
     * @param actual
     */
    public static void assertNotEquals(Object expect, Object actual) {
        if (ObjectUtil.isEqual(expect, actual)) {
            failWhenEquals(actual);
        }
    }

    /**
     * 断言相同
     *
     * @param expect
     * @param actual
     */
    public static void assertSame(Object expect, Object actual) {
        if (expect != actual) {
            failWhenNotSame(expect, actual);
        }
    }

    /**
     * 断言不相同
     *
     * @param expect
     * @param actual
     */
    public static void assertNotSame(Object expect, Object actual) {
        if (expect == actual) {
            failWhenSame(actual);
        }
    }

    /**
     * 断言失败
     */
    public static void fail() {
        throw new AssertFailError();
    }

    /**
     * 断言失败
     *
     * @param msg
     */
    public static void fail(String msg) {
        throw new AssertFailError(msg);
    }

    /**
     * 断言失败
     *
     * @param pattern
     * @param args
     */
    public static void fail(String pattern, Object... args) {
        throw new AssertFailError(String.format(pattern, args));
    }

    /**
     * 失败当不为null
     *
     * @param actual
     */
    private static void failWhenNotNull(Object actual) {
        fail("Value should be null, but was [%s]", actual);
    }

    /**
     * 失败当为null
     */
    private static void failWhenNull() {
        fail("Value should not be null");
    }

    /**
     * 失败当对象相等
     *
     * @param actual
     */
    private static void failWhenEquals(Object actual) {
        fail("Values should be different, actual=[%s]", actual);
    }

    /**
     * 失败当对象不相等
     *
     * @param expect
     * @param actual
     */
    private static void failWhenNotEquals(Object expect, Object actual) {
        fail("Values should equals, expect=[%s], but actual=[%s]", expect, actual);
    }

    /**
     * 失败当对象相同
     *
     * @param actual
     */
    private static void failWhenSame(Object actual) {
        fail("Values should not be same, actual=[%s]", actual);
    }

    /**
     * 失败当对象不相同
     *
     * @param expect
     * @param actual
     */
    private static void failWhenNotSame(Object expect, Object actual) {
        fail("Values should be same, expect=[%s], but actual=[%s]", expect, actual);
    }
}
