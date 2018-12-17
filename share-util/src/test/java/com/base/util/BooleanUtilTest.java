package com.base.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 布尔值工具测试类
 */
public class BooleanUtilTest {

    @Test
    public void testParseBoolean() {
        assertTrue(BooleanUtil.parseBoolean("1", false));
        assertTrue(BooleanUtil.parseBoolean("true", false));
        assertTrue(BooleanUtil.parseBoolean("True", false));
        assertTrue(BooleanUtil.parseBoolean("TRUE", false));
        assertTrue(BooleanUtil.parseBoolean("1", true));
        assertTrue(BooleanUtil.parseBoolean("true", true));
        assertFalse(BooleanUtil.parseBoolean("0", true));
        assertFalse(BooleanUtil.parseBoolean("-1", true));
        assertFalse(BooleanUtil.parseBoolean("false", true));
        assertFalse(BooleanUtil.parseBoolean("abc", true));
        assertFalse(BooleanUtil.parseBoolean(" ", true));
        assertFalse(BooleanUtil.parseBoolean("", true));
        assertTrue(BooleanUtil.parseBoolean(null, true));
        assertFalse(BooleanUtil.parseBoolean(null, false));
    }
}
