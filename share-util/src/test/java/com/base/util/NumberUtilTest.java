package com.base.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Number工具测试类
 */
public class NumberUtilTest {
    /**
     * 测试defaultNumber方法
     */
    @Test
    public void testDefaultNumber() {
        assertEquals(Byte.valueOf("1"), NumberUtil.defaultNumber(Byte.valueOf("1")));
        assertEquals(Byte.valueOf("0"), NumberUtil.defaultNumber((Byte) null));

        assertEquals(Short.valueOf("1"), NumberUtil.defaultNumber(Short.valueOf("1")));
        assertEquals(Short.valueOf("0"), NumberUtil.defaultNumber((Short) null));

        assertEquals(Integer.valueOf("1"), NumberUtil.defaultNumber(Integer.valueOf("1")));
        assertEquals(Integer.valueOf("0"), NumberUtil.defaultNumber((Integer) null));

        assertEquals(Long.valueOf("1"), NumberUtil.defaultNumber(Long.valueOf("1")));
        assertEquals(Long.valueOf("0"), NumberUtil.defaultNumber((Long) null));

        assertEquals(Float.valueOf("1.5"), NumberUtil.defaultNumber(Float.valueOf("1.5")));
        assertEquals(Float.valueOf("0"), NumberUtil.defaultNumber((Float) null));

        assertEquals(Double.valueOf("1.5"), NumberUtil.defaultNumber(Double.valueOf("1.5")));
        assertEquals(Double.valueOf("0"), NumberUtil.defaultNumber((Double) null));

        assertEquals(BigInteger.valueOf(1), NumberUtil.defaultNumber(BigInteger.valueOf(1)));
        assertEquals(BigInteger.valueOf(0), NumberUtil.defaultNumber((BigInteger) null));

        assertEquals(BigDecimal.valueOf(1.05D), NumberUtil.defaultNumber(BigDecimal.valueOf(1.05D)));
        assertEquals(BigDecimal.valueOf(0), NumberUtil.defaultNumber((BigDecimal) null));
    }

    /**
     * 测试sum方法
     */
    @Test
    public void testSum() {
        int[] intArr = {-1, 2, 4};
        long[] longArr = {-1L, 2L, 4L};
        Short[] shorts = new Short[]{(short) -1, (short) 2, null, (short) 4};
        Integer[] integers = new Integer[]{-1, 2, null, 4};
        Long[] longs = new Long[]{-1L, 2L, null, 4L};
        Float[] floats = new Float[]{-1.1F, 2.2F, null, 4.4F};
        Double[] doubles = new Double[]{-1.1D, 2.2D, null, 4.4D};
        BigInteger[] bigIntegers = new BigInteger[]{BigInteger.valueOf(-1), BigInteger.valueOf(2), null,
                BigInteger.valueOf(4)};
        BigDecimal[] bigDecimals = new BigDecimal[]{new BigDecimal("-1.1"), new BigDecimal("2.2"), null,
                new BigDecimal("4.4")};
        Number[] nums = new Number[]{-1, (short) 2, 3L, 4.111F, 5.222D, null, BigInteger.valueOf(7),
                new BigDecimal("8.333")};

        // sum(int[])
        assertEquals((Integer) 5, (Integer) NumberUtil.sum(intArr));
        assertEquals((Integer) 0, (Integer) NumberUtil.sum((int[]) null));

        // sum(long[])
        assertEquals((Long) 5L, (Long) NumberUtil.sum(longArr));
        assertEquals((Long) 0L, (Long) NumberUtil.sum((long[]) null));

        // sum(Number[])
        assertEquals(new BigDecimal("5"), NumberUtil.sum(shorts));
        assertEquals(new BigDecimal("5"), NumberUtil.sum(integers));
        assertEquals(new BigDecimal("5"), NumberUtil.sum(longs));
        assertEquals(new BigDecimal("5.5"), NumberUtil.sum(floats));
        assertEquals(new BigDecimal("5.5"), NumberUtil.sum(doubles));
        assertEquals(new BigDecimal("5"), NumberUtil.sum(bigIntegers));
        assertEquals(new BigDecimal("5.5"), NumberUtil.sum(bigDecimals));
        assertEquals(new BigDecimal("28.666"), NumberUtil.sum(nums));

        assertEquals(BigDecimal.ZERO, NumberUtil.sum(new Number[0]));
        assertEquals(BigDecimal.ZERO, NumberUtil.sum((Number[]) null));

        // sum(Collection<Number>)
        assertEquals(new BigDecimal("5"), NumberUtil.sum(ArrayUtil.asList(shorts)));
        assertEquals(new BigDecimal("5"), NumberUtil.sum(ArrayUtil.asList(integers)));
        assertEquals(new BigDecimal("5"), NumberUtil.sum(ArrayUtil.asList(longs)));
        assertEquals(new BigDecimal("5.5"), NumberUtil.sum(ArrayUtil.asList(floats)));
        assertEquals(new BigDecimal("5.5"), NumberUtil.sum(ArrayUtil.asSet(doubles)));
        assertEquals(new BigDecimal("5"), NumberUtil.sum(ArrayUtil.asSet(bigIntegers)));
        assertEquals(new BigDecimal("5.5"), NumberUtil.sum(ArrayUtil.asSet(bigDecimals)));
        assertEquals(new BigDecimal("28.666"), NumberUtil.sum(ArrayUtil.asSet(nums)));

        assertEquals(BigDecimal.ZERO, NumberUtil.sum(new ArrayList<Number>()));
        assertEquals(BigDecimal.ZERO, NumberUtil.sum((Collection<Number>) null));
    }

    /**
     * 测试isDigits方法
     */
    @Test
    public void testIsDigits() {
        assertTrue(NumberUtil.isDigits("1"));
        assertTrue(NumberUtil.isDigits("-1"));
        assertTrue(NumberUtil.isDigits("1000000"));
        assertTrue(NumberUtil.isDigits("-1000000"));
        assertFalse(NumberUtil.isDigits("100.00"));
        assertFalse(NumberUtil.isDigits("abc"));
        assertFalse(NumberUtil.isDigits("   "));
        assertFalse(NumberUtil.isDigits(""));
        assertFalse(NumberUtil.isDigits(null));
    }

    /**
     * 测试parseLong方法
     */
    @Test
    public void testParseLong() {
        assertEquals((Long) 1L, NumberUtil.parseLong("1", null));
        assertEquals((Long) (-1L), NumberUtil.parseLong("-1", null));
        assertEquals((Long) 10000L, NumberUtil.parseLong("10000", 2L));

        assertEquals((Long) 1L, NumberUtil.parseLong("a", 1L));
        assertNull(NumberUtil.parseLong("a", null));

        assertEquals((Long) 1L, NumberUtil.parseLong(null, 1L));
    }

    /**
     * 测试parseInteger方法
     */
    @Test
    public void testParseInteger() {
        assertEquals((Integer) 1, NumberUtil.parseInteger("1", null));
        assertEquals((Integer) (-1), NumberUtil.parseInteger("-1", null));
        assertEquals((Integer) 10000, NumberUtil.parseInteger("10000", 2));

        assertEquals((Integer) 1, NumberUtil.parseInteger("a", 1));
        assertNull(NumberUtil.parseInteger("a", null));

        assertEquals((Integer) 1, NumberUtil.parseInteger(null, 1));
    }

    /**
     * 测试asInteger方法
     */
    @Test
    public void testAsInteger() {
        assertEquals((Integer) 1, NumberUtil.asInteger(1));
        assertEquals((Integer) 1, NumberUtil.asInteger(1L));
        assertEquals((Integer) 1, NumberUtil.asInteger(1.1D));
        assertEquals((Integer) 1, NumberUtil.asInteger(1.2F));
        assertEquals((Integer) 1, NumberUtil.asInteger((short) 1));
        assertEquals((Integer) 1, NumberUtil.asInteger(new BigDecimal("1.1")));
        assertNull(NumberUtil.asInteger(null));

        assertEquals((Integer) 1, NumberUtil.asInteger(1, 2));
        assertEquals((Integer) 1, NumberUtil.asInteger(null, 1));
        assertNull(NumberUtil.asInteger(null, null));
    }

    /**
     * 测试asLong方法
     */
    @Test
    public void testAsLong() {
        assertEquals((Long) 1L, NumberUtil.asLong(1));
        assertEquals((Long) 1L, NumberUtil.asLong(1L));
        assertEquals((Long) 1L, NumberUtil.asLong(1.1D));
        assertEquals((Long) 1L, NumberUtil.asLong(1.2F));
        assertEquals((Long) 1L, NumberUtil.asLong((short) 1));
        assertEquals((Long) 1L, NumberUtil.asLong(new BigDecimal("1.1")));
        assertNull(NumberUtil.asLong(null));

        assertEquals((Long) 1L, NumberUtil.asLong(1, 2L));
        assertEquals((Long) 1L, NumberUtil.asLong(null, 1L));
        assertNull(NumberUtil.asLong(null, null));
    }

    /**
     * 测试asBigDecimal方法
     */
    @Test
    public void testAsBigDecimal() {
        assertEquals(new BigDecimal("1"), NumberUtil.asBigDecimal(1));
        assertEquals(new BigDecimal("1"), NumberUtil.asBigDecimal(1L));
        assertEquals(new BigDecimal("1.1"), NumberUtil.asBigDecimal(1.1D));
        assertEquals(new BigDecimal("1.2"), NumberUtil.asBigDecimal(1.2F));
        assertEquals(new BigDecimal("1"), NumberUtil.asBigDecimal((short) 1));
        assertEquals(new BigDecimal("1.1"), NumberUtil.asBigDecimal(new BigDecimal("1.1")));
        assertNull(NumberUtil.asBigDecimal(null));

        assertEquals(new BigDecimal("1"), NumberUtil.asBigDecimal(1, new BigDecimal("2")));
        assertEquals(new BigDecimal("1"), NumberUtil.asBigDecimal(null, new BigDecimal("1")));
        assertNull(NumberUtil.asBigDecimal(null, null));
    }

    /**
     * 测试greaterThan方法
     */
    @Test
    public void testGreaterThan() {
        // 1 greaterThan(BigDecimal, BigDecimal)
        assertTrue(NumberUtil.greaterThan(new BigDecimal("1"), new BigDecimal("-1")));
        assertTrue(NumberUtil.greaterThan(new BigDecimal("0.02"), new BigDecimal("0.01")));

        assertFalse(NumberUtil.greaterThan(new BigDecimal("1"), new BigDecimal("1")));
        assertFalse(NumberUtil.greaterThan(new BigDecimal("1"), null));
        assertFalse(NumberUtil.greaterThan(null, new BigDecimal("1")));
        assertFalse(NumberUtil.greaterThan((BigDecimal) null, null));

        // 2 greaterThan(Integer, Integer)
        assertTrue(NumberUtil.greaterThan(1, -1));
        assertTrue(NumberUtil.greaterThan(100, 1));

        assertFalse(NumberUtil.greaterThan(1, 1));
        assertFalse(NumberUtil.greaterThan(1, null));
        assertFalse(NumberUtil.greaterThan(null, 1));
        assertFalse(NumberUtil.greaterThan((Integer) null, null));

        // 3 greaterThan(Number, Number)
        assertTrue(NumberUtil.greaterThan(new BigDecimal("1e10"), 1L));
        assertTrue(NumberUtil.greaterThan(new BigInteger("1"), -1));
        assertTrue(NumberUtil.greaterThan(0.02D, 0.01F));
        assertTrue(NumberUtil.greaterThan(2L, 0.01F));
        assertTrue(NumberUtil.greaterThan(2, 0.01D));
        assertTrue(NumberUtil.greaterThan((short) 2, 0.01F));
        assertTrue(NumberUtil.greaterThan((byte) 2, 0.01D));

        assertFalse(NumberUtil.greaterThan((byte) 1, 1));
        assertFalse(NumberUtil.greaterThan(1L, 1D));
        assertFalse(NumberUtil.greaterThan(1L, null));
        assertFalse(NumberUtil.greaterThan(null, 1F));
        assertFalse(NumberUtil.greaterThan((Number) null, null));
    }

    /**
     * 测试lessThan方法
     */
    @Test
    public void testLessThan() {
        // 1 lessThan(BigDecimal, BigDecimal)
        assertTrue(NumberUtil.lessThan(new BigDecimal("-1"), new BigDecimal("1")));
        assertTrue(NumberUtil.lessThan(new BigDecimal("0.01"), new BigDecimal("0.02")));

        assertFalse(NumberUtil.lessThan(new BigDecimal("1"), new BigDecimal("1")));
        assertFalse(NumberUtil.lessThan(new BigDecimal("1"), null));
        assertFalse(NumberUtil.lessThan(null, new BigDecimal("1")));
        assertFalse(NumberUtil.lessThan((BigDecimal) null, null));

        // 2 lessThan(Integer, Integer)
        assertTrue(NumberUtil.lessThan(-1, 1));
        assertTrue(NumberUtil.lessThan(1, 100));

        assertFalse(NumberUtil.lessThan(1, 1));
        assertFalse(NumberUtil.lessThan(1, null));
        assertFalse(NumberUtil.lessThan(null, 1));
        assertFalse(NumberUtil.lessThan((Integer) null, null));

        // 3 lessThan(Number, Number)
        assertTrue(NumberUtil.lessThan(1L, new BigDecimal("1e10")));
        assertTrue(NumberUtil.lessThan(-1, new BigInteger("1")));
        assertTrue(NumberUtil.lessThan(0.01F, 0.02D));
        assertTrue(NumberUtil.lessThan(0.01F, 2L));
        assertTrue(NumberUtil.lessThan(0.01D, 2));
        assertTrue(NumberUtil.lessThan(0.01F, (short) 2));
        assertTrue(NumberUtil.lessThan(0.01D, (byte) 2));

        assertFalse(NumberUtil.lessThan((byte) 1, 1));
        assertFalse(NumberUtil.lessThan(1L, 1D));
        assertFalse(NumberUtil.lessThan(1L, null));
        assertFalse(NumberUtil.lessThan(null, 1F));
        assertFalse(NumberUtil.lessThan((Number) null, null));
    }

    @Test
    public void parse() {
        // parseInteger
        assertEquals(new Integer("1"), NumberUtil.parseInteger("1", null));
        assertEquals(new Integer("0"), NumberUtil.parseInteger("a", 0));
        assertEquals(new Integer("0"), NumberUtil.parseInteger(null, 0));

        // parseLong
        assertEquals(new Long("1"), NumberUtil.parseLong("1", null));
        assertEquals(new Long("0"), NumberUtil.parseLong("a", 0L));
        assertEquals(new Long("0"), NumberUtil.parseLong(null, 0L));

        // parseBigDecimal
        assertEquals(new BigDecimal("1.1"), NumberUtil.parseBigDecimal("1.1", null));
        assertEquals(new BigDecimal("0"), NumberUtil.parseBigDecimal("a", BigDecimal.ZERO));
        assertEquals(new BigDecimal("0"), NumberUtil.parseBigDecimal(null, BigDecimal.ZERO));
    }

    @Test
    public void testMax() {
        assertEquals(new BigDecimal("1000.1"),
                NumberUtil.max(new BigDecimal("0"), new BigDecimal("1000.1"), null, new BigDecimal("-1000")));
        assertEquals(new BigDecimal("0"), NumberUtil.max(new BigDecimal("0"), null, new BigDecimal("-1000")));
        assertEquals(new BigDecimal("0"), NumberUtil.max(new BigDecimal("0")));
        assertNull(NumberUtil.max((BigDecimal[]) null));
        assertNull(NumberUtil.max());
    }

    @Test
    public void testMin() {
        assertEquals(new BigDecimal("-1000"),
                NumberUtil.min(new BigDecimal("0"), new BigDecimal("1000.1"), null, new BigDecimal("-1000")));
        assertEquals(new BigDecimal("-1000"), NumberUtil.min(new BigDecimal("0"), null, new BigDecimal("-1000")));
        assertEquals(new BigDecimal("0"), NumberUtil.min(new BigDecimal("0")));
        assertNull(NumberUtil.min((BigDecimal[]) null));
        assertNull(NumberUtil.min());
    }

    @Test
    public void testNegate() {
        assertEquals(new BigDecimal("-1000"), NumberUtil.negate(new BigDecimal("1000")));
        assertEquals(new BigDecimal("1000.12"), NumberUtil.negate(new BigDecimal("-1000.12")));
        assertEquals(Integer.valueOf(-1), NumberUtil.negate(Integer.valueOf(1)));
        assertEquals(Integer.valueOf(1), NumberUtil.negate(Integer.valueOf(-1)));
        assertEquals(Long.valueOf(-1), NumberUtil.negate(Long.valueOf(1)));
        assertEquals(Long.valueOf(1), NumberUtil.negate(Long.valueOf(-1)));
        assertNull(NumberUtil.negate((BigDecimal) null));
        assertNull(NumberUtil.negate((Integer) null));
        assertNull(NumberUtil.negate((Long) null));
    }
}
