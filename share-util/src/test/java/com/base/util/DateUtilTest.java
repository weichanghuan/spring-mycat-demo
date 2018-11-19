package com.base.util;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 日期工具测试类
 * 
 *
 */
public class DateUtilTest {
	/**
	 * 测试getSQLDate
	 */
	@Test
	public void testGetSQLDate() {
		// getSQLDate(Date)
		Date date1 = DateUtil.getDate(2012, 1, 3);
		assertEquals(date1.getTime(), DateUtil.getSQLDate(date1).getTime());
		assertNull(DateUtil.getSQLDate(null));

		// 时分秒毫秒不同
		Date date2 = new Date();
		assertFalse(date2.getTime() == DateUtil.getSQLDate(date2).getTime());

		// getSQLDate(year, month, day)
		assertEquals(DateUtil.getDate(2012, 11, 11).getTime(), DateUtil.getSQLDate(2012, 11, 11).getTime());
		assertNull(DateUtil.getSQLDate(-1, -2, -3));
	}

	/**
	 * 测试getDate
	 */
	@Test
	public void testGetDate() {
		// 1
		Calendar cdr = Calendar.getInstance();
		cdr.set(2009, 9, 9, 8, 7, 6); // 2009/10/09 08:07:06
		cdr.clear(Calendar.MILLISECOND);
		Date date = cdr.getTime();

		assertEquals(date.getTime(), DateUtil.getDate(2009, 10, 9, 8, 7, 6).getTime());

		// 2
		cdr.clear();
		cdr.set(2009, 9, 9);
		date = cdr.getTime();

		assertEquals(date.getTime(), DateUtil.getDate(2009, 10, 9).getTime());

		assertNull(DateUtil.getDate(0, 0, 0));
		assertNull(DateUtil.getDate(2009, 2, 29));
		assertNull(DateUtil.getDate(2009, 10, 9, 8, 7, 61));

		// 3
		date = new Date();
		assertEquals(date.getTime(), DateUtil.getDate(date).getTime());
		assertNull(DateUtil.getDate(null));
	}

	/**
	 * 测试daysBetween方法
	 */
	@Test
	public void testDaysBetween() {
		final Date date1 = DateUtil.getDate(2002, 5, 18, 12, 0, 0);
		final Date date2 = DateUtil.getDate(2002, 5, 1, 12, 0, 0);
		final Date date3 = DateUtil.getDate(2002, 5, 1, 5, 0, 0);
		final Date date4 = DateUtil.getDate(2002, 4, 1, 12, 0, 0);
		final Date date5 = DateUtil.getDate(2000, 1, 1, 0, 0, 0);

		assertEquals(17, DateUtil.daysBetween(date1, date2)); // 比较时精确到日
		assertEquals(-17, DateUtil.daysBetween(date2, date1));
		assertEquals(17, DateUtil.daysBetween(date1, date3));
		assertEquals(-17, DateUtil.daysBetween(date3, date1));
		assertEquals(47, DateUtil.daysBetween(date1, date4));
		assertEquals(-47, DateUtil.daysBetween(date4, date1));
		assertEquals(868, DateUtil.daysBetween(date1, date5));
		assertEquals(-868, DateUtil.daysBetween(date5, date1));

		try {
			DateUtil.daysBetween(date1, null);
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}

		try {
			DateUtil.daysBetween(null, date1);
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}

		try {
			DateUtil.daysBetween(null, null);
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}
	}

	/**
	 * 测试monthsBetween方法
	 */
	@Test
	public void testMonthsBetween() {
		final Date date1 = DateUtil.getDate(2002, 5, 5, 5, 5, 5);
		final Date date2 = DateUtil.getDate(2002, 2, 2, 2, 2, 2);
		final Date date3 = DateUtil.getDate(2001, 1, 1, 1, 1, 1);
		final Date date4 = DateUtil.getDate(2001, 9, 9, 9, 9, 9);

		assertEquals(3, DateUtil.monthsBetween(date1, date2)); // 比较时精确到月
		assertEquals(-3, DateUtil.monthsBetween(date2, date1));
		assertEquals(16, DateUtil.monthsBetween(date1, date3));
		assertEquals(-16, DateUtil.monthsBetween(date3, date1));
		assertEquals(8, DateUtil.monthsBetween(date1, date4));
		assertEquals(-8, DateUtil.monthsBetween(date4, date1));

		try {
			DateUtil.monthsBetween(date1, null);
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}

		try {
			DateUtil.monthsBetween(null, date1);
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}

		try {
			DateUtil.monthsBetween(null, null);
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}
	}

	/**
	 * 测试yearsBetween方法
	 */
	@Test
	public void testYearsBetween() {
		final Date date1 = DateUtil.getDate(2012, 12, 12, 12, 12, 12);
		final Date date2 = DateUtil.getDate(2012, 5, 5, 5, 5, 5);
		final Date date3 = DateUtil.getDate(2001, 1, 1, 1, 1, 1);

		assertEquals(0, DateUtil.yearsBetween(date1, date2)); // 比较时精确到年
		assertEquals(0, DateUtil.yearsBetween(date2, date1));
		assertEquals(11, DateUtil.yearsBetween(date1, date3));
		assertEquals(-11, DateUtil.yearsBetween(date3, date1));

		try {
			DateUtil.yearsBetween(date1, null);
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}

		try {
			DateUtil.yearsBetween(null, date1);
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}

		try {
			DateUtil.yearsBetween(null, null);
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}
	}

	/**
	 * 测试rollDate方法
	 */
	@Test
	public void testRollDate() {
		Date date1 = DateUtil.getDate(2012, 12, 1, 0, 0, 0);
		Date date2 = DateUtil.getDate(2012, 12, 2);
		java.sql.Date date3 = DateUtil.getSQLDate(2012, 12, 1);

		assertEquals(date2, DateUtil.rollDate(date1, Calendar.DATE, 1));
		assertEquals(date3, DateUtil.rollDate(date2, Calendar.DATE, -1));

		assertNull(DateUtil.rollDate(null, 0, 0));

		date1 = DateUtil.getDate(2016, 7, 17, 1, 0, 0);
		date2 = DateUtil.getDate(2016, 7, 18, 1, 0, 0);
		assertEquals(date2, DateUtil.rollDate(date1, "1D", true));
		date2 = DateUtil.getDate(2016, 7, 16, 1, 0, 0);
		assertEquals(date2, DateUtil.rollDate(date1, "1D", false));
	}

	/**
	 * 测试parseDateTerm方法
	 */
	@Test
	public void testParseDateTerm() {
		String[] terms = DateUtil.parseDateTerm("0s");
		assertEquals("0", terms[0]);
		assertEquals("s", terms[1]);

		terms = DateUtil.parseDateTerm("60m");
		assertEquals("60", terms[0]);
		assertEquals("m", terms[1]);

		terms = DateUtil.parseDateTerm("1h");
		assertEquals("1", terms[0]);
		assertEquals("h", terms[1]);

		terms = DateUtil.parseDateTerm(" 10 D");
		assertEquals("10", terms[0]);
		assertEquals("D", terms[1]);

		terms = DateUtil.parseDateTerm(" 2W ");
		assertEquals("2", terms[0]);
		assertEquals("W", terms[1]);

		terms = DateUtil.parseDateTerm(" 3M ");
		assertEquals("3", terms[0]);
		assertEquals("M", terms[1]);

		terms = DateUtil.parseDateTerm("1Y");
		assertEquals("1", terms[0]);
		assertEquals("Y", terms[1]);

		try {
			terms = DateUtil.parseDateTerm("1.0Y");
			fail("Expect error");
		} catch (Exception ex) {
		}
		
		try {
			terms = DateUtil.parseDateTerm("1Q");
			fail("Expect error");
		} catch (Exception ex) {
		}
		
		try {
			terms = DateUtil.parseDateTerm("1");
			fail("Expect error");
		} catch (Exception ex) {
		}
		
		try {
			terms = DateUtil.parseDateTerm("h");
			fail("Expect error");
		} catch (Exception ex) {
		}
		
		try {
			terms = DateUtil.parseDateTerm(null);
			fail("Expect error");
		} catch (Exception ex) {
		}
	}

	/**
	 * 测试setField方法
	 */
	@Test
	public void testSetField() {
		Date date1 = DateUtil.getDate(2012, 12, 1, 0, 0, 0);
		Date date2 = DateUtil.getDate(2012, 12, 1, 12, 0, 0);
		java.sql.Date date3 = DateUtil.getSQLDate(2012, 12, 2);
		assertEquals(date2, DateUtil.setDateField(date1, Calendar.HOUR_OF_DAY, 12));
		assertEquals(date3, DateUtil.setDateField(date1, Calendar.DATE, 2));

		assertNull(DateUtil.setDateField(null, 0, 0));
	}

	@Test
	public void testQuarter() {
		Date date = DateUtil.parse("yyyy-MM-dd", "2013-01-05");
		assertEquals(0, DateUtil.getCurrentQuarter(date));
		date = DateUtil.parse("yyyy-MM-dd", "2013-03-05");
		assertEquals(0, DateUtil.getCurrentQuarter(date));
		date = DateUtil.parse("yyyy-MM-dd", "2013-04-05");
		assertEquals(1, DateUtil.getCurrentQuarter(date));
		date = DateUtil.parse("yyyy-MM-dd", "2013-06-05");
		assertEquals(1, DateUtil.getCurrentQuarter(date));
		date = DateUtil.parse("yyyy-MM-dd", "2013-07-05");
		assertEquals(2, DateUtil.getCurrentQuarter(date));
		date = DateUtil.parse("yyyy-MM-dd", "2013-12-05");
		assertEquals(3, DateUtil.getCurrentQuarter(date));

		date = DateUtil.parse("yyyy-MM-dd", "2013-01-05");
		date = DateUtil.nextQuarterFirstMonth(date);
		assertEquals("2013-04-05", DateUtil.format("yyyy-MM-dd", date));

		date = DateUtil.parse("yyyy-MM-dd", "2013-03-05");
		date = DateUtil.nextQuarterFirstMonth(date);
		assertEquals("2013-04-05", DateUtil.format("yyyy-MM-dd", date));

		date = DateUtil.parse("yyyy-MM-dd", "2013-04-05");
		date = DateUtil.nextQuarterFirstMonth(date);
		assertEquals("2013-07-05", DateUtil.format("yyyy-MM-dd", date));

		date = DateUtil.parse("yyyy-MM-dd", "2013-12-05");
		date = DateUtil.nextQuarterFirstMonth(date);
		assertEquals("2014-01-05", DateUtil.format("yyyy-MM-dd", date));
	}

	@Test
	public void testInTerm() {
		java.util.Date startTime = new java.util.Date();
		java.util.Date nowTime = DateUtil.rollDate(startTime, Calendar.DAY_OF_MONTH, 2);
		assertTrue(DateUtil.inTerm("1Y", startTime, nowTime));
		assertTrue(DateUtil.inTerm("1M", startTime, nowTime));
		assertTrue(DateUtil.inTerm("1W", startTime, nowTime));
		assertTrue(DateUtil.inTerm("10D", startTime, nowTime));
		assertFalse(DateUtil.inTerm("1D", startTime, nowTime));
		assertFalse(DateUtil.inTerm("1h", startTime, nowTime));
		assertFalse(DateUtil.inTerm("1m", startTime, nowTime));
		assertFalse(DateUtil.inTerm("1s", startTime, nowTime));
	}

	@Test
	public void testIsSameDay() {
		assertTrue(DateUtil.isSameDay(new java.util.Date(), new java.util.Date()));
		assertFalse(
				DateUtil.isSameDay(new java.util.Date(), DateUtil.rollDate(new java.util.Date(), Calendar.DATE, -1)));
	}

	@Test
	public void testDateFromString() {
		assertEquals(DateUtil.getDate(2014, 1, 24), DateUtil.dateFromString("2014-01-24"));
	}

	@Test
	public void testDateToString() {
		assertEquals("2014-01-24", DateUtil.dateToString(DateUtil.getDate(2014, 1, 24)));
	}

	@Test
	public void testMaxDate() {
		Date d1 = DateUtil.getDate(2016, 8, 14);
		Date d2 = null;
		Date d3 = DateUtil.getDate(2016, 8, 14, 23, 00, 00);
		Date d4 = DateUtil.getDate(2000, 1, 1);

		assertEquals(d3, DateUtil.max(d1, d2, d3, d4));
		assertEquals(d3, DateUtil.max(d1, d2, d3));
		assertEquals(d1, DateUtil.max(d1, d2));
		assertEquals(d1, DateUtil.max(d1, d4));
		assertNull(DateUtil.max(d2));
		assertNull(DateUtil.max());
		assertNull(DateUtil.max((Date[]) null));
	}

	@Test
	public void testMinDate() {
		Date d1 = DateUtil.getDate(2016, 8, 14);
		Date d2 = null;
		Date d3 = DateUtil.getDate(2016, 8, 14, 23, 00, 00);
		Date d4 = DateUtil.getDate(2000, 1, 1);

		assertEquals(d4, DateUtil.min(d1, d2, d3, d4));
		assertEquals(d1, DateUtil.min(d1, d2, d3));
		assertEquals(d1, DateUtil.min(d1, d2));
		assertEquals(d4, DateUtil.min(d1, d4));
		assertNull(DateUtil.min(d2));
		assertNull(DateUtil.min());
		assertNull(DateUtil.min((Date[]) null));
	}
}
