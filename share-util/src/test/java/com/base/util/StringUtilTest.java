package com.base.util;

import static com.base.util.StringUtil.bothPad;
import static com.base.util.StringUtil.chatAt;
import static com.base.util.StringUtil.concat;
import static com.base.util.StringUtil.contains;
import static com.base.util.StringUtil.containsAll;
import static com.base.util.StringUtil.containsAny;
import static com.base.util.StringUtil.count;
import static com.base.util.StringUtil.defaultString;
import static com.base.util.StringUtil.defaultStringIfBlank;
import static com.base.util.StringUtil.defaultStringIfEmpty;
import static com.base.util.StringUtil.emptyAsNull;
import static com.base.util.StringUtil.endsWith;
import static com.base.util.StringUtil.find;
import static com.base.util.StringUtil.formatMessage;
import static com.base.util.StringUtil.formatNumber;
import static com.base.util.StringUtil.formatString;
import static com.base.util.StringUtil.getString;
import static com.base.util.StringUtil.hex2String;
import static com.base.util.StringUtil.indexOf;
import static com.base.util.StringUtil.isBlank;
import static com.base.util.StringUtil.isEmpty;
import static com.base.util.StringUtil.isNotBlank;
import static com.base.util.StringUtil.isNotEmpty;
import static com.base.util.StringUtil.join;
import static com.base.util.StringUtil.joinNoNull;
import static com.base.util.StringUtil.lastIndexOf;
import static com.base.util.StringUtil.leftPad;
import static com.base.util.StringUtil.length;
import static com.base.util.StringUtil.lengthAsByte;
import static com.base.util.StringUtil.mark;
import static com.base.util.StringUtil.matches;
import static com.base.util.StringUtil.nullAsEmpty;
import static com.base.util.StringUtil.padding;
import static com.base.util.StringUtil.parseMessage;
import static com.base.util.StringUtil.parseToDateQuietly;
import static com.base.util.StringUtil.repeat;
import static com.base.util.StringUtil.replace;
import static com.base.util.StringUtil.replaceString;
import static com.base.util.StringUtil.replaceStringBetween;
import static com.base.util.StringUtil.replaceXML;
import static com.base.util.StringUtil.replaceXMLBetween;
import static com.base.util.StringUtil.reverse;
import static com.base.util.StringUtil.rightPad;
import static com.base.util.StringUtil.split;
import static com.base.util.StringUtil.startsWith;
import static com.base.util.StringUtil.string2Hex;
import static com.base.util.StringUtil.subString;
import static com.base.util.StringUtil.subStringBetween;
import static com.base.util.StringUtil.subXML;
import static com.base.util.StringUtil.subXMLBetween;
import static com.base.util.StringUtil.toLowerCase;
import static com.base.util.StringUtil.toUpperCase;
import static com.base.util.StringUtil.trim;
import static com.base.util.StringUtil.trimAll;
import static com.base.util.StringUtil.trimBoth;
import static com.base.util.StringUtil.trimLeft;
import static com.base.util.StringUtil.trimRight;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * 字符串工具测试类
 * 
 *
 */
public class StringUtilTest {
	/**
	 * 测试toString方法
	 */
	@Test
	public void testToString() {
		char[] chars = { 'a', 'b', 'c', '1', '2', '3', '测', '试', ' ' };
		Assert.assertEquals("abc123测试 ", StringUtil.toString(chars));
		Assert.assertEquals("", StringUtil.toString(new char[0]));
		Assert.assertNull(StringUtil.toString(null));
	}

	/**
	 * 测试toCharArray方法
	 */
	@Test
	public void testToCharArray() {
		String str = "abc123测试 ";
		char[] chars = { 'a', 'b', 'c', '1', '2', '3', '测', '试', ' ' };
		Assert.assertArrayEquals(chars, StringUtil.toCharArray(str));
		Assert.assertArrayEquals(new char[0], StringUtil.toCharArray(""));
		Assert.assertNull(StringUtil.toCharArray(null));
	}

	/**
	 * 测试trim方法
	 */
	@Test
	public void testTrim() {
		Assert.assertEquals("abc123", trim(" \t\r\nabc123\t\r\n "));
		Assert.assertEquals("abc 123", trim(" \nabc 123\r "));
		Assert.assertNull(trim(null));
	}

	/**
	 * 测试trimAll方法
	 */
	@Test
	public void testTrimAll() {
		Assert.assertEquals("abc123", trimAll(" abc123 "));
		Assert.assertEquals("abc123", trimAll(" a b c  1 2 3 "));
		Assert.assertNull(trim(null));
	}

	/**
	 * 测试tirmLeft方法
	 */
	@Test
	public void testTirmLeft() {
		Assert.assertEquals("a b c \t\r\n", trimLeft("\t\r\n a b c \t\r\n"));
		Assert.assertEquals("abc   ", trimLeft("   abc   "));
		Assert.assertEquals("", trimLeft(" \t \r \n "));

		Assert.assertEquals("bcabc123123", trimLeft("abcabc123123", "a"));
		Assert.assertEquals("cabc123123", trimLeft("abcabc123123", "ab"));
		Assert.assertEquals("123123", trimLeft("abcabc123123", "abc"));
		Assert.assertEquals("abcabc123123", trimLeft("abcabc123123", "1"));
		Assert.assertEquals("abcabc123123", trimLeft("abcabc123123", ""));
		Assert.assertEquals("abcabc123123", trimLeft("abcabc123123", " "));
		Assert.assertEquals("abcabc123123", trimLeft("abcabc123123", null));
		Assert.assertEquals("", trimLeft("abcabc", "abc"));
		Assert.assertEquals("1", trimLeft("abc1", "abc"));
		Assert.assertEquals("aaa", trimLeft("aaa", "aaaa"));
		Assert.assertNull(trimLeft(null, "a"));
	}

	/**
	 * 测试tirmRight方法
	 */
	@Test
	public void testTirmRight() {
		Assert.assertEquals("\t\r\n a b c", trimRight("\t\r\n a b c \t\r\n"));
		Assert.assertEquals("   abc", trimRight("   abc"));
		Assert.assertEquals("", trimRight(" \t \r \n "));

		Assert.assertEquals("abcabc12312", trimRight("abcabc123123", "3"));
		Assert.assertEquals("abcabc1231", trimRight("abcabc123123", "23"));
		Assert.assertEquals("abcabc", trimRight("abcabc123123", "123"));
		Assert.assertEquals("abcabc123123", trimRight("abcabc123123", "a"));
		Assert.assertEquals("abcabc123123", trimRight("abcabc123123", ""));
		Assert.assertEquals("abcabc123123", trimRight("abcabc123123", " "));
		Assert.assertEquals("abcabc123123", trimRight("abcabc123123", null));
		Assert.assertEquals("", trimRight("abcabc", "abc"));
		Assert.assertEquals("1", trimRight("1abc", "abc"));
		Assert.assertEquals("aaa", trimRight("aaa", "aaaa"));
		Assert.assertNull(trimRight(null, "a"));
	}

	/**
	 * 测试tirmBoth方法
	 */
	@Test
	public void testTirmBoth() {
		Assert.assertEquals("bc123abc", trimBoth("abc123abc", "a"));
		Assert.assertEquals("c123abc", trimBoth("abc123abc", "ab"));
		Assert.assertEquals("123", trimBoth("abc123abc", "abc"));
		Assert.assertEquals("abc123a", trimBoth("abc123abc", "bc"));
		Assert.assertEquals("abc123ab", trimBoth("abc123abc", "c"));
		Assert.assertEquals("abc123abc", trimBoth("abc123abc", ""));
		Assert.assertEquals("abc123abc", trimBoth("abc123abc", " "));
		Assert.assertEquals("abc123abc", trimBoth("abc123abc", null));
		Assert.assertEquals("", trimBoth("abcabc", "abc"));
		Assert.assertEquals("1", trimBoth("1abc", "abc"));
		Assert.assertEquals("1", trimBoth("abc1", "abc"));
		Assert.assertEquals("aaa", trimBoth("aaa", "aaaa"));
		Assert.assertNull(trimBoth(null, "a"));
	}

	/**
	 * 测试replace方法
	 */
	@Test
	public void testReplace() {
		// replace(str, regex, replacement)
		Assert.assertEquals("abc123", replace(" abc 123 ", "\\s+", ""));
		Assert.assertEquals("abc123", replace(" abc 123 ", "\\s+", null));
		Assert.assertEquals(" abc 123 ", replace(" abc 123 ", null, ""));
		Assert.assertEquals(" abc 123 ", replace(" abc 123 ", "[a]{1,b}", "")); // 错误的正则
		Assert.assertNull(replace(null, "\\s+", ""));
		Assert.assertNull(replace(null, null, ""));
		Assert.assertNull(replace(null, null, null));

		// replace(str, replacement, start, end)
		final String pan = "123456789ABCDEFGHIJ";

		Assert.assertEquals("*23456789ABCDEFGHIJ", replace(pan, "*", 0, 1));
		Assert.assertEquals("1*3456789ABCDEFGHIJ", replace(pan, "*", 1, 2));
		Assert.assertEquals("123456*GHIJ", replace(pan, "*", 6, 15));
		Assert.assertEquals("123456*GHIJ", replace(pan, "*", 6, -4));
		Assert.assertEquals("123456*GHIJ", replace(pan, "*", -13, -4));
		Assert.assertEquals("*", replace(pan, "*", 0, pan.length()));
		Assert.assertEquals("1*", replace(pan, "*", 1, 99999));
		Assert.assertEquals("*", replace(pan, "*", -99999, 99999));
		Assert.assertEquals("*23456789ABCDEFGHIJ", replace(pan, "*", -99999, 1));
		Assert.assertEquals("ABC456789ABCDEFGHIJ", replace(pan, "ABC", 0, 3));
		Assert.assertEquals("*", replace(pan, "*", 0, 0));
		Assert.assertEquals("1*", replace(pan, "*", 1, 0));
		Assert.assertEquals("123456789ABCDEFGHI*", replace(pan, "*", -1, 0));
		Assert.assertEquals(pan, replace(pan, "*", -1, -2));
		Assert.assertEquals(pan, replace(pan, "*", -1, 1));
		Assert.assertEquals("", replace("", "*", 0, 1));
		Assert.assertNull(replace(null, "*", 0, 1));
	}

	/**
	 * 测试split (return String[])
	 */
	@Test
	public void testSplit2Array() {
		String msg = "a,b;c|d";

		// 1
		String[] arr = split(msg);
		Assert.assertEquals(4, arr.length);
		Assert.assertEquals("d", arr[3]);

		// 2
		arr = split(msg, ";");
		Assert.assertEquals(2, arr.length);
		Assert.assertEquals("c|d", arr[1]);

		// 3
		arr = split(msg, (String) null);
		Assert.assertEquals(4, arr.length);
		Assert.assertEquals("d", arr[3]);

		// 4
		arr = split(null);
		Assert.assertNull(arr);

		// 5
		arr = split(null, ";");
		Assert.assertNull(arr);
	}

	/**
	 * 测试split (return ? extends Collection&lt;String&gt;)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testSplit2Collection() {
		String msg = "a,b;c|d";

		// 1
		List<String> list = split(msg, List.class);
		Assert.assertEquals(4, list.size());
		Assert.assertEquals("d", list.get(3));

		// 2
		list = split(msg, ";", List.class);
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("c|d", list.get(1));

		// 3
		list = split(msg, null, List.class);
		Assert.assertEquals(4, list.size());
		Assert.assertEquals("d", list.get(3));

		// 4
		list = split(null, List.class);
		Assert.assertNull(list);

		// 5
		list = split(msg, (Class<List<String>>) null);
		Assert.assertNull(list);

		// 6
		list = split(null, ";", List.class);
		Assert.assertNull(list);

		// 7
		list = split(msg, ";", (Class<List<String>>) null);
		Assert.assertNull(list);

		// 8
		list = split(msg, "\\|", List.class);
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("d", list.get(1));
	}

	/**
	 * 测试convertXMLToMapBetween方法
	 */
	@Test
	public void testSubStringBetween() {
		final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>"
				+ "<head><name>head</name><value>head</value></head>"
				+ "<body><name>body</name><value>body</value></body>" + "</root>";

		// subStringBetween(String, String, String)
		String[] results = subStringBetween(xml, "<name>", "</name>");
		Assert.assertArrayEquals(new String[] { "head", "body" }, results);

		results = subStringBetween(xml, "<head>", "</head>");
		Assert.assertArrayEquals(new String[] { "<name>head</name><value>head</value>" }, results);

		results = subStringBetween(xml, "</root>", "<root>");
		Assert.assertEquals(0, results.length);

		results = subStringBetween(xml, "<root>", null);
		Assert.assertEquals(0, results.length);

		results = subStringBetween(xml, null, null);
		Assert.assertEquals(0, results.length);

		results = subStringBetween(null, "<root>", "</root>");
		Assert.assertEquals(0, results.length);

		// subStringBetween(String, String)
		results = subStringBetween(xml, "head");
		Assert.assertArrayEquals(new String[] { "><name>", "</value></" }, results);

		results = subStringBetween(xml, "/body");
		Assert.assertEquals(0, results.length);

		results = subStringBetween(xml, null);
		Assert.assertEquals(0, results.length);

		results = subStringBetween(null, "root");
		Assert.assertEquals(0, results.length);
	}

	/**
	 * 测试subXML
	 */
	@Test
	public void testSubXML() {
		final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<head><ext>head</ext></head>"
				+ "<body><ext>123</ext></body>" + "</root>";

		String[] results = subXML(xml, "head");
		Assert.assertArrayEquals(new String[] { "<head><ext>head</ext></head>" }, results);

		results = subXML(xml, "ext");
		Assert.assertArrayEquals(new String[] { "<ext>head</ext>", "<ext>123</ext>" }, results);

		results = subXML(xml, "123");
		Assert.assertEquals(0, results.length);

		results = subXML(xml, null);
		Assert.assertEquals(0, results.length);

		results = subXML(null, "ext");
		Assert.assertEquals(0, results.length);
	}

	/**
	 * 测试subXMLBetween
	 */
	@Test
	public void testSubXMLBetween() {
		final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<head><ext>head</ext></head>"
				+ "<body><ext>123</ext></body>" + "</root>";

		String[] results = subXMLBetween(xml, "head");
		Assert.assertArrayEquals(new String[] { "<ext>head</ext>" }, results);

		results = subXMLBetween(xml, "ext");
		Assert.assertArrayEquals(new String[] { "head", "123" }, results);

		results = subXMLBetween(xml, "123");
		Assert.assertEquals(0, results.length);

		results = subXMLBetween(xml, null);
		Assert.assertEquals(0, results.length);

		results = subXMLBetween(null, "ext");
		Assert.assertEquals(0, results.length);
	}

	/**
	 * 测试replaceString
	 */
	@Test
	public void testReplaceString() {
		String data = "Hello (Boy)! Today is (Monday).";
		String expected = "Hello Girl! Today is Tuesday.";
		String actual = replaceString(data, "(", ")", new String[] { "Girl", "Tuesday" });
		Assert.assertEquals(expected, actual);

		data = "(1)(2)(3)[4]( 5 )";
		expected = "abc[4]c";
		actual = replaceString(data, "(", ")", new String[] { "a", "b", "c" });
		Assert.assertEquals(expected, actual);

		data = "No replacement";
		expected = data;
		actual = replaceString(data, "[", "]", new String[] {});
		Assert.assertEquals(expected, actual);

		data = "No start and close";
		expected = data;
		actual = replaceString(data, null, null, new String[] {});
		Assert.assertEquals(expected, actual);

		Assert.assertNull(replaceString(null, null, null, null));
	}

	/**
	 * 测试replaceStringBetween
	 */
	@Test
	public void testReplaceStringBetween() {
		String data = "Hello (Boy)! Today is (Monday).";
		String expected = "Hello (Girl)! Today is (Tuesday).";
		String actual = replaceStringBetween(data, "(", ")", new String[] { "Girl", "Tuesday" });
		Assert.assertEquals(expected, actual);

		data = "(1)(2)(3)[4]( 5 )";
		expected = "(a)(b)(c)[4](c)";
		actual = replaceStringBetween(data, "(", ")", new String[] { "a", "b", "c" });
		Assert.assertEquals(expected, actual);

		data = "No replacement";
		expected = data;
		actual = replaceStringBetween(data, "[", "]", new String[] {});
		Assert.assertEquals(expected, actual);

		data = "No start and close";
		expected = data;
		actual = replaceStringBetween(data, null, null, new String[] {});
		Assert.assertEquals(expected, actual);

		Assert.assertNull(replaceStringBetween(null, null, null, null));
	}

	/**
	 * 测试replaceXML
	 */
	@Test
	public void testReplaceXML() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<head><ext>head</ext></head>"
				+ "<body><ext>123</ext></body>" + "</root>";
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<body><ext>123</ext></body>"
				+ "</root>";
		String actual = replaceXML(xml, "head", new String[] { "" });
		Assert.assertEquals(expected, actual);

		expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<head>head</head>" + "<body>body</body>"
				+ "</root>";
		actual = replaceXML(xml, "ext", new String[] { "head", "body" });
		Assert.assertEquals(expected, actual);

		expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<head>abc</head>" + "<body>abc</body>"
				+ "</root>";
		actual = replaceXML(xml, "ext", new String[] { "abc" });
		Assert.assertEquals(expected, actual);

		expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test />";
		actual = replaceXML(xml, "root", new String[] { "<test />" });
		Assert.assertEquals(expected, actual);

		xml = "No replacement";
		expected = xml;
		actual = replaceXML(xml, "head", new String[] {});
		Assert.assertEquals(expected, actual);

		xml = "No start and close";
		expected = xml;
		actual = replaceXML(xml, null, new String[] {});
		Assert.assertEquals(expected, actual);

		Assert.assertNull(replaceXML(null, null, null));
	}

	/**
	 * 测试replaceXMLBetween
	 */
	@Test
	public void testReplaceXMLBetween() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<head><ext>head</ext></head>"
				+ "<body><ext></ext></body>" + "</root>";
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<head></head>"
				+ "<body><ext></ext></body>" + "</root>";
		String actual = replaceXMLBetween(xml, "head", new String[] { "" });
		Assert.assertEquals(expected, actual);

		expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<head><ext>head</ext></head>"
				+ "<body><ext>body</ext></body>" + "</root>";
		actual = replaceXMLBetween(xml, "ext", new String[] { "head", "body" });
		Assert.assertEquals(expected, actual);

		expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<head><ext>abc</ext></head>"
				+ "<body><ext>abc</ext></body>" + "</root>";
		actual = replaceXMLBetween(xml, "ext", new String[] { "abc" });
		Assert.assertEquals(expected, actual);

		expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>" + "<test />" + "</root>";
		actual = replaceXMLBetween(xml, "root", new String[] { "<test />" });
		Assert.assertEquals(expected, actual);

		xml = "No replacement";
		expected = xml;
		actual = replaceXMLBetween(xml, "head", new String[] {});
		Assert.assertEquals(expected, actual);

		xml = "No start and close";
		expected = xml;
		actual = replaceXMLBetween(xml, null, new String[] {});
		Assert.assertEquals(expected, actual);

		Assert.assertNull(replaceXMLBetween(null, null, null));
	}

	/**
	 * 测试formatMessage
	 */
	@Test
	public void testFormatMessage() {
		Assert.assertEquals("1,2", formatMessage("{0},{1}", 1, "2"));
		Assert.assertNull(formatMessage(null));
		Assert.assertNull(formatMessage("{x}", 1));
	}

	/**
	 * 测试formatString
	 */
	@Test
	public void testFormatString() {
		Assert.assertEquals("1,2", formatString("%d,%s", 1, "2"));
		Assert.assertNull(formatString(null));
		Assert.assertNull(formatString("%M", 1));
	}

	/**
	 * 测试parseMessage
	 */
	@Test
	public void testParseMessage() {
		Object[] objs = parseMessage("{0}, {1}", "abc, bcd");
		Assert.assertEquals("abc", objs[0]);
		Assert.assertEquals("bcd", objs[1]);
		Assert.assertNull(parseMessage(null, "abc, bcd"));
		Assert.assertNull(parseMessage("{0}, {1}", null));
		Assert.assertNull(parseMessage(null, null));
		Assert.assertNull(parseMessage("{X}", "1"));
	}

	/**
	 * 测试parseToDateQuietly
	 */
	@Test
	public void testParseToDateQuietly() {
		Assert.assertTrue(parseToDateQuietly("yyyyMMdd", "20100304").compareTo(DateUtil.getDate(2010, 3, 4)) == 0);
		Assert.assertNull(parseToDateQuietly(null, null));
	}

	/**
	 * 测试isEmpty
	 */
	@Test
	public void testIsEmpty() {
		Assert.assertTrue(isEmpty(null));
		Assert.assertTrue(isEmpty(""));
		Assert.assertFalse(isEmpty("  "));
		Assert.assertFalse(isEmpty("a"));
	}

	/**
	 * 测试isNotEmpty
	 */
	@Test
	public void testIsNotEmpty() {
		Assert.assertFalse(isNotEmpty(null));
		Assert.assertFalse(isNotEmpty(""));
		Assert.assertTrue(isNotEmpty("  "));
		Assert.assertTrue(isNotEmpty("a"));
	}

	/**
	 * 测试isBlank
	 */
	@Test
	public void testIsBlank() {
		Assert.assertTrue(isBlank(null));
		Assert.assertTrue(isBlank(""));
		Assert.assertTrue(isBlank("  "));
		Assert.assertFalse(isBlank("a"));
	}

	/**
	 * 测试isNotBlank
	 */
	@Test
	public void testIsNotBlank() {
		Assert.assertFalse(isNotBlank(null));
		Assert.assertFalse(isNotBlank(""));
		Assert.assertFalse(isNotBlank("  "));
		Assert.assertTrue(isNotBlank("a"));
	}

	/**
	 * 测试defaultString
	 */
	@Test
	public void testDefaultString() {
		// defaultString(str)
		Assert.assertEquals("", defaultString(null));
		Assert.assertEquals("", defaultString(""));
		Assert.assertEquals(" ", defaultString(" "));
		Assert.assertEquals("a", defaultString("a"));

		// defaultString(str, defVal)
		Assert.assertEquals("dv", defaultString(null, "dv"));
		Assert.assertEquals("", defaultString("", "dv"));
		Assert.assertEquals(" ", defaultString(" ", "dv"));
		Assert.assertEquals("a", defaultString("a", "dv"));
	}

	/**
	 * 测试defaultStringIfEmpty
	 */
	@Test
	public void testDefaultStringIfEmpty() {
		Assert.assertEquals("dv", defaultStringIfEmpty(null, "dv"));
		Assert.assertEquals("dv", defaultStringIfEmpty("", "dv"));
		Assert.assertEquals(" ", defaultStringIfEmpty(" ", "dv"));
		Assert.assertEquals("a", defaultStringIfEmpty("a", "dv"));
	}

	/**
	 * 测试defaultStringIfBlank
	 */
	@Test
	public void testDefaultStringIfBlank() {
		Assert.assertEquals("dv", defaultStringIfBlank(null, "dv"));
		Assert.assertEquals("dv", defaultStringIfBlank("", "dv"));
		Assert.assertEquals("dv", defaultStringIfBlank(" ", "dv"));
		Assert.assertEquals("a", defaultStringIfBlank("a", "dv"));
	}

	/**
	 * 测试string2Hex
	 */
	@Test
	public void testString2Hex() {
		Assert.assertEquals("B4CBB1A3B5A5B3ACB9FDBDC9B7D1D3D0D0A7C6DA", string2Hex("此保单超过缴费有效期", "GB2312"));
		Assert.assertEquals("41", string2Hex("A", null));
		Assert.assertNull(string2Hex(null, "GBA"));
		Assert.assertNull(string2Hex(null, null));
	}

	/**
	 * 测试hex2String
	 */
	@Test
	public void testHex2String() {
		Assert.assertEquals("此保单超过缴费有效期", hex2String("B4CBB1A3B5A5B3ACB9FDBDC9B7D1D3D0D0A7C6DA", "GB2312"));
		Assert.assertEquals("A", hex2String("41", null));
		Assert.assertNull(hex2String("4", "GB2312"));
		Assert.assertNull(hex2String(null, "GBA"));
		Assert.assertNull(hex2String(null, null));
	}

	/**
	 * 测试contains方法
	 */
	@Test
	public void testContains() {
		Assert.assertTrue(contains("abc", "b"));
		Assert.assertFalse(contains("abc", "d"));
		Assert.assertFalse(contains("abc", null));
		Assert.assertFalse(contains(null, "d"));
		Assert.assertFalse(contains(null, null));
	}

	/**
	 * 测试containsAny方法
	 */
	@Test
	public void testContainsAny() {
		Assert.assertTrue(containsAny("abc", "d", "e", null, "b"));
		Assert.assertFalse(containsAny("abc", "d", null));
		Assert.assertFalse(containsAny("abc", (String) null));
		Assert.assertFalse(containsAny("abc"));
		Assert.assertFalse(containsAny(null));
		Assert.assertFalse(containsAny(null, "d"));
		Assert.assertFalse(containsAny(null, "a", (String) null, "1"));
	}

	/**
	 * 测试containsAll方法
	 */
	@Test
	public void testContainsAll() {
		Assert.assertTrue(containsAll("abc", "a", "b", "c"));
		Assert.assertFalse(containsAll("abc", "a", "d"));
		Assert.assertFalse(containsAll("abc", "a", (String) null));
		Assert.assertFalse(containsAll("abc"));
		Assert.assertFalse(containsAll(null));
		Assert.assertFalse(containsAll(null, "d"));
		Assert.assertFalse(containsAll(null, (String) null, (String) null));
	}

	/**
	 * 测试count方法
	 */
	@Test
	public void testCount() {
		final String str = "112233321ABC";
		Assert.assertEquals(3, count(str, "1"));
		Assert.assertEquals(3, count(str, "2"));
		Assert.assertEquals(3, count(str, "3"));
		Assert.assertEquals(0, count(str, "4"));
		Assert.assertEquals(1, count(str, "11"));
		Assert.assertEquals(1, count(str, "12"));
		Assert.assertEquals(0, count(str, "13"));
		Assert.assertEquals(1, count(str, "33"));
		Assert.assertEquals(1, count(str, str));
		Assert.assertEquals(0, count(str, str + " "));
		Assert.assertEquals(0, count(str, ""));
		Assert.assertEquals(0, count(str, null));
		Assert.assertEquals(0, count(null, "1"));
		Assert.assertEquals(0, count(null, null));
	}

	/**
	 * 测试repeat方法
	 */
	@Test
	public void testRepeat() {
		// repeat(str, times)
		Assert.assertEquals("a", repeat("a", 1));
		Assert.assertEquals("aaa", repeat("a", 3));
		Assert.assertEquals("abab", repeat("ab", 2));
		Assert.assertEquals("  ", repeat(" ", 2));
		Assert.assertEquals("", repeat("", 10));
		Assert.assertNull(repeat(null, 10));
		Assert.assertEquals("a", repeat("a", -1));

		// repeat(str, separator, times)
		Assert.assertEquals("a", repeat("a", ",", 1));
		Assert.assertEquals("a,a,a", repeat("a", ",", 3));
		Assert.assertEquals("aaa", repeat("a", null, 3));
		Assert.assertEquals("aaa", repeat("a", "", 3));
		Assert.assertEquals("ab,ab", repeat("ab", ",", 2));
		Assert.assertEquals(" , ", repeat(" ", ",", 2));
		Assert.assertEquals("", repeat("", ",", 10));
		Assert.assertNull(repeat(null, ",", 10));
		Assert.assertEquals("a", repeat("a", ",", -1));
	}

	/**
	 * 测试join方法
	 */
	@Test
	@SuppressWarnings({ "unchecked" })
	public void testJoin() {
		// join(Object[], [String])
		final Object[] arr = { new BigDecimal("1"), 'a', "你", "", " ", null, '\n' };

		Assert.assertEquals("1,a,你,, ,null,\n", join(arr));
		Assert.assertEquals("1,a,你,, ,null,\n", join(arr, ","));
		Assert.assertEquals("1,a,你,, ,null,\n", join(arr, null));
		Assert.assertEquals("1@@a@@你@@@@ @@null@@\n", join(arr, "@@"));
		Assert.assertEquals("", join(new Object[0]));
		Assert.assertNull(join((Object[]) null));
		Assert.assertNull(join((Object[]) null, ","));
		Assert.assertNull(join((Object[]) null, null));

		// joinNoNull(Object[], [String])
		Assert.assertEquals("1,a,你,, ,\n", joinNoNull(arr));
		Assert.assertEquals("1,a,你,, ,\n", joinNoNull(arr, ","));
		Assert.assertEquals("1,a,你,, ,\n", joinNoNull(arr, null));
		Assert.assertEquals("1@@a@@你@@@@ @@\n", joinNoNull(arr, "@@"));
		Assert.assertEquals("", joinNoNull(new Object[0]));
		Assert.assertNull(joinNoNull((Object[]) null));
		Assert.assertNull(joinNoNull((Object[]) null, ","));
		Assert.assertNull(joinNoNull((Object[]) null, null));

		// join(Object[], [String], boolean)
		final Object[] arrs = { 0.1, arr, new String[] { "a", "2" } };

		Assert.assertEquals("0.1,1,a,你,, ,null,\n,a,2", join(arrs, true));
		Assert.assertEquals("0.1,1,a,你,, ,null,\n,a,2", join(arrs, ",", true));
		Assert.assertEquals("0.1,1,a,你,, ,null,\n,a,2", join(arrs, null, true));
		Assert.assertEquals("0.1@@1@@a@@你@@@@ @@null@@\n@@a@@2", join(arrs, "@@", true));
		Assert.assertNull(join((Object[]) null, true));
		Assert.assertNull(join((Object[]) null, ",", true));
		Assert.assertNull(join((Object[]) null, null, true));

		// joinNoNull(Object[], [String], boolean)
		Assert.assertEquals("0.1,1,a,你,, ,\n,a,2", joinNoNull(arrs, true));
		Assert.assertEquals("0.1,1,a,你,, ,\n,a,2", joinNoNull(arrs, ",", true));
		Assert.assertEquals("0.1,1,a,你,, ,\n,a,2", joinNoNull(arrs, null, true));
		Assert.assertEquals("0.1@@1@@a@@你@@@@ @@\n@@a@@2", joinNoNull(arrs, "@@", true));
		Assert.assertNull(joinNoNull((Object[]) null, true));
		Assert.assertNull(joinNoNull((Object[]) null, ",", true));
		Assert.assertNull(joinNoNull((Object[]) null, null, true));

		// 互相持有引用
		final Object[] arr1 = { 0, null, 2, 3 };
		final Object[] arr2 = { "a", "b", arr1, "d" };
		arr1[1] = arr2;

		// join(Object[], boolean)
		Assert.assertEquals("0,a,b,d,2,3", join(arr1, true));
		Assert.assertEquals("a,b,0,2,3,d", join(arr2, true));

		// joinNoNull(Object[], boolean)
		Assert.assertEquals("0,a,b,d,2,3", joinNoNull(arr1, true));
		Assert.assertEquals("a,b,0,2,3,d", joinNoNull(arr2, true));

		// join(Collection<?>, [String])
		final Collection<?> col = ArrayUtil.asList(new BigDecimal("1"), 'a', "你", "", " ", null, '\n');

		Assert.assertEquals("1,a,你,, ,null,\n", join(col));
		Assert.assertEquals("1,a,你,, ,null,\n", join(col, ","));
		Assert.assertEquals("1,a,你,, ,null,\n", join(col, null));
		Assert.assertEquals("1@@a@@你@@@@ @@null@@\n", join(col, "@@"));
		Assert.assertNull(join((Collection<?>) null));
		Assert.assertNull(join((Collection<?>) null, ","));
		Assert.assertNull(join((Collection<?>) null, null));

		// joinNoNull(Collection<?>, [String])
		Assert.assertEquals("1,a,你,, ,\n", joinNoNull(col));
		Assert.assertEquals("1,a,你,, ,\n", joinNoNull(col, ","));
		Assert.assertEquals("1,a,你,, ,\n", joinNoNull(col, null));
		Assert.assertEquals("1@@a@@你@@@@ @@\n", joinNoNull(col, "@@"));
		Assert.assertNull(joinNoNull((Collection<?>) null));
		Assert.assertNull(joinNoNull((Collection<?>) null, ","));
		Assert.assertNull(joinNoNull((Collection<?>) null, null));

		// join(Collection<?>, [String], boolean)
		final Collection<?> cols = ArrayUtil.asList(0.1,
				ArrayUtil.asList(new BigDecimal("1"), 'a', "你", "", " ", null, '\n'), ArrayUtil.asList("a", "2"));

		Assert.assertEquals("0.1,1,a,你,, ,null,\n,a,2", join(cols, true));
		Assert.assertEquals("0.1,1,a,你,, ,null,\n,a,2", join(cols, ",", true));
		Assert.assertEquals("0.1,1,a,你,, ,null,\n,a,2", join(cols, null, true));
		Assert.assertEquals("0.1@@1@@a@@你@@@@ @@null@@\n@@a@@2", join(cols, "@@", true));
		Assert.assertEquals("", join(new ArrayList<Object>()));
		Assert.assertNull(join((Collection<?>) null, true));
		Assert.assertNull(join((Collection<?>) null, ",", true));
		Assert.assertNull(join((Collection<?>) null, null, true));

		// joinNoNull(Collection<?>, [String], boolean)
		Assert.assertEquals("0.1,1,a,你,, ,\n,a,2", joinNoNull(cols, true));
		Assert.assertEquals("0.1,1,a,你,, ,\n,a,2", joinNoNull(cols, ",", true));
		Assert.assertEquals("0.1,1,a,你,, ,\n,a,2", joinNoNull(cols, null, true));
		Assert.assertEquals("0.1@@1@@a@@你@@@@ @@\n@@a@@2", joinNoNull(cols, "@@", true));
		Assert.assertEquals("", joinNoNull(new ArrayList<Object>()));
		Assert.assertNull(joinNoNull((Collection<?>) null, true));
		Assert.assertNull(joinNoNull((Collection<?>) null, ",", true));
		Assert.assertNull(joinNoNull((Collection<?>) null, null, true));

		// 互相持有引用
		final List<Object> list1 = ArrayUtil.asList(0, (Object) null, 2, 3);
		final List<?> list2 = ArrayUtil.asList("a", "b", list1, "d");
		list1.set(1, list2);

		// join(Collection<?>, boolean)
		Assert.assertEquals("a,b,0,2,3,d", join(list2, true));
		Assert.assertEquals("0,a,b,d,2,3", join(list1, true));

		// joinNoNull(Collection<?>, boolean)
		Assert.assertEquals("a,b,0,2,3,d", joinNoNull(list2, true));
		Assert.assertEquals("0,a,b,d,2,3", joinNoNull(list1, true));

		// join(int[])
		final int[] iArr1 = { -2, -1, 0, 1, 2 };
		Assert.assertEquals("-2,-1,0,1,2", join(iArr1));
		Assert.assertEquals("", join(new int[0]));
		Assert.assertNull(join((int[]) null));

		// join(int[], [String])
		Assert.assertEquals("-2;-1;0;1;2", join(iArr1, ";"));
		Assert.assertEquals("", join(new int[0], ";"));
		Assert.assertNull(join((int[]) null, ";"));
	}

	/**
	 * 测试SubString方法
	 */
	@Test
	public void testSubString() {
		final String pan = "123456789ABCDEFGHIJ";

		// subString(String, int, int)
		Assert.assertEquals("1", subString(pan, 0, 1));
		Assert.assertEquals("2", subString(pan, 1, 2));
		Assert.assertEquals("789ABCDEF", subString(pan, 6, 15));
		Assert.assertEquals("789ABCDEF", subString(pan, 6, -4));
		Assert.assertEquals("789ABCDEF", subString(pan, -13, -4));
		Assert.assertEquals("123456789ABCDEFGHIJ", subString(pan, 0, pan.length()));
		Assert.assertEquals("23456789ABCDEFGHIJ", subString(pan, 1, 99999));
		Assert.assertEquals("123456789ABCDEFGHIJ", subString(pan, -99999, 99999));
		Assert.assertEquals("1", subString(pan, -99999, 1));
		Assert.assertEquals("123456789ABCDEFGHIJ", subString(pan, 0, 0));
		Assert.assertEquals("23456789ABCDEFGHIJ", subString(pan, 1, 0));
		Assert.assertEquals("J", subString(pan, -1, 0));
		Assert.assertEquals("", subString(pan, -1, -2));
		Assert.assertEquals("", subString(pan, -1, 1));
		Assert.assertEquals("", subString("", 0, 1));
		Assert.assertNull(subString(null, 0, 1));

		// subString(String, int)
		Assert.assertEquals(pan, subString(pan, 0));
		Assert.assertEquals("789ABCDEFGHIJ", subString(pan, 6));
		Assert.assertEquals("EFGHIJ", subString(pan, -6));
		Assert.assertEquals(pan, subString(pan, -99999));
		Assert.assertEquals("", subString(pan, 99999));
		Assert.assertEquals("", subString("", 0));
		Assert.assertNull(subString(null, 0));

		final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<root>"
				+ "<head><name>head</name><value>head</value></head>"
				+ "<body><name>body</name><value>body</value></body>" + "</root>";

		// subString(String, String, String)
		String[] results = subString(xml, "<name>", "</name>");
		Assert.assertArrayEquals(new String[] { "<name>head</name>", "<name>body</name>" }, results);

		results = subString(xml, "<head>", "</head>");
		Assert.assertArrayEquals(new String[] { "<head><name>head</name><value>head</value></head>" }, results);

		results = subString(xml, "</root>", "<root>");
		Assert.assertEquals(0, results.length);

		results = subString(xml, "<root>", null);
		Assert.assertEquals(0, results.length);

		results = subString(xml, null, null);
		Assert.assertEquals(0, results.length);

		results = subString(null, "<root>", "</root>");
		Assert.assertEquals(0, results.length);

		// subString(String, String)
		results = subString(xml, "head");
		Assert.assertArrayEquals(new String[] { "head><name>head", "head</value></head" }, results);

		results = subString(xml, "/body");
		Assert.assertEquals(0, results.length);

		results = subString(xml, null);
		Assert.assertEquals(0, results.length);

		results = subString(null, "root");
		Assert.assertEquals(0, results.length);
	}

	/**
	 * 测试GetString方法
	 */
	@Test
	public void testGetString() {
		final String pan = "123456789ABCDEFGHIJ";

		// getString(String, int, int)
		Assert.assertEquals("1", getString(pan, 0, 1));
		Assert.assertEquals("23", getString(pan, 1, 2));
		Assert.assertEquals("789ABCDEFGHIJ", getString(pan, 6, 15));
		Assert.assertEquals("789A", getString(pan, 6, 4));
		Assert.assertEquals("789A", getString(pan, -13, 4));
		Assert.assertEquals("123456789ABCDEFGHIJ", getString(pan, 0, pan.length()));
		Assert.assertEquals("23456789ABCDEFGHIJ", getString(pan, 1, 99999));
		Assert.assertEquals("123456789ABCDEFGHIJ", getString(pan, -99999, 99999));
		Assert.assertEquals("1", getString(pan, -99999, 1));
		Assert.assertEquals("", getString(pan, 0, 0));
		Assert.assertEquals("", getString(pan, 1, 0));
		Assert.assertEquals("", getString(pan, -1, -2));
		Assert.assertEquals("", getString(pan, 99999, 1));
		Assert.assertEquals("", getString("", 0, 1));
		Assert.assertNull(getString(null, 0, 1));
	}

	/**
	 * 测试GetString方法
	 */
	@Test
	public void testMatches() {
		Assert.assertTrue(matches("123", "\\d{3}"));
		Assert.assertFalse(matches("123", "\\d{2}"));
		Assert.assertFalse(matches("123", null));
		Assert.assertFalse(matches(null, "\\d{3}"));
		Assert.assertFalse(matches(null, null));
	}

	/**
	 * 测试Mark方法
	 */
	@Test
	public void testMark() {
		final String pan = "123456789ABCDEFGHIJ";

		Assert.assertEquals("*23456789ABCDEFGHIJ", mark(pan, '*', 0, 1));
		Assert.assertEquals("1*3456789ABCDEFGHIJ", mark(pan, '*', 1, 2));
		Assert.assertEquals("123456*********GHIJ", mark(pan, '*', 6, 15));
		Assert.assertEquals("123456*********GHIJ", mark(pan, '*', 6, -4));
		Assert.assertEquals("123456*********GHIJ", mark(pan, '*', -13, -4));
		Assert.assertEquals("*******************", mark(pan, '*', 0, pan.length()));
		Assert.assertEquals("1******************", mark(pan, '*', 1, 99999));
		Assert.assertEquals("*******************", mark(pan, '*', -99999, 99999));
		Assert.assertEquals("*23456789ABCDEFGHIJ", mark(pan, '*', -99999, 1));
		Assert.assertEquals("*******************", mark(pan, '*', 0, 0));
		Assert.assertEquals("1******************", mark(pan, '*', 1, 0));
		Assert.assertEquals("123456789ABCDEFGHI*", mark(pan, '*', -1, 0));
		Assert.assertEquals(pan, mark(pan, '*', -1, -2));
		Assert.assertEquals(pan, mark(pan, '*', -1, 1));
		Assert.assertEquals("", mark("", '*', 0, 1));
		Assert.assertNull(mark(null, '*', 0, 1));
	}

	/**
	 * 测试formatNumber方法
	 */
	@Test
	public void testFormatNumber() {
		// formatNumber(int, long)
		Assert.assertEquals("0001", formatNumber(4, 1));
		Assert.assertEquals("-1", formatNumber(0, -1));
		Assert.assertEquals("-3", formatNumber(-999, -3));

		// formatNumber(String, Object)
		Assert.assertEquals("0001", formatNumber("0000", 1));

		Assert.assertEquals("", formatNumber(null, 123));
		Assert.assertEquals("", formatNumber("0000", null));

		try {
			formatNumber("0000", "abc");
			Assert.fail("Expect exception");
		} catch (Exception ex) {
		}
	}

	/**
	 * 测试nullAsEmpty方法
	 */
	@Test
	public void testNullAsEmpty() {
		Assert.assertEquals("123", nullAsEmpty("123"));
		Assert.assertEquals("   ", nullAsEmpty("   "));
		Assert.assertEquals("", nullAsEmpty(""));
		Assert.assertEquals("", nullAsEmpty(null));
	}

	/**
	 * 测试nullAsEmpty方法
	 */
	@Test
	public void testEmptyAsNull() {
		Assert.assertEquals("123", emptyAsNull("123"));
		Assert.assertNull(emptyAsNull("   "));
		Assert.assertNull(emptyAsNull(""));
		Assert.assertNull(emptyAsNull(null));
	}

	/**
	 * 测试length方法
	 */
	@Test
	public void testLength() {
		// length(String)
		Assert.assertEquals(9, length("123abc 测试"));
		Assert.assertEquals(0, length((String) null));

		// length(String[])
		Assert.assertEquals(0, length((String[]) null));
		Assert.assertEquals(9, length((String) null, "123", "abc", " 测试"));
		Assert.assertEquals(0, length());
	}

	/**
	 * 测试lengthAsByte方法
	 */
	@Test
	public void testLengthAsByte() {
		// lengthAsByte(string)
		Assert.assertEquals(Charsets.UTF_8.equals(Charsets.defaultCharset()) ? 13 : 11, lengthAsByte("123abc 测试"));
		Assert.assertEquals(0, lengthAsByte(null));

		// lengthAsByte(string, charset)
		Assert.assertEquals(11, lengthAsByte("123abc 测试", "GBK"));
		Assert.assertEquals(Charsets.UTF_8.equals(Charsets.defaultCharset()) ? 13 : 11,
				lengthAsByte("123abc 测试", null));
		Assert.assertEquals(-1, lengthAsByte("1", "UTF-888"));
		Assert.assertEquals(0, lengthAsByte(null, null));
	}

	/**
	 * 测试length方法
	 */
	@Test
	public void testReverse() {
		Assert.assertEquals("试测 cba321", reverse("123abc 测试"));
		Assert.assertEquals("   ", reverse("   "));
		Assert.assertEquals("", reverse(""));
		Assert.assertNull(reverse(null));
	}

	/**
	 * 测试testPad方法
	 */
	@Test
	public void testPad() {
		// leftPad
		Assert.assertEquals("   abc", leftPad("abc", " ", 6));
		Assert.assertEquals("**abc", leftPad("abc", "**", 6));
		Assert.assertEquals("****abc", leftPad("abc", "**", 7));
		Assert.assertEquals("测试abc", leftPad("abc", "测试", 6));
		Assert.assertEquals("abc", leftPad("abc", "", 6));
		Assert.assertEquals("abc", leftPad("abc", null, 6));
		Assert.assertEquals("******", leftPad("", "*", 6));
		Assert.assertEquals("******", leftPad(null, "*", 6));
		Assert.assertEquals("abc", leftPad("abc", "*", 2));
		Assert.assertEquals("abc", leftPad("abc", "*", -1));

		// rightPad
		Assert.assertEquals("abc   ", rightPad("abc", " ", 6));
		Assert.assertEquals("abc**", rightPad("abc", "**", 6));
		Assert.assertEquals("abc****", rightPad("abc", "**", 7));
		Assert.assertEquals("abc测试", rightPad("abc", "测试", 6));
		Assert.assertEquals("abc", rightPad("abc", "", 6));
		Assert.assertEquals("abc", rightPad("abc", null, 6));
		Assert.assertEquals("******", rightPad("", "*", 6));
		Assert.assertEquals("******", rightPad(null, "*", 6));
		Assert.assertEquals("abc", rightPad("abc", "*", 2));
		Assert.assertEquals("abc", rightPad("abc", "*", -1));

		// bothPad
		Assert.assertEquals("  abc  ", bothPad("abc", " ", 7));
		Assert.assertEquals("**abc**", bothPad("abc", "**", 7));
		Assert.assertEquals("**abc**", bothPad("abc", "**", 10));
		Assert.assertEquals("测试abc测试", bothPad("abc", "测试", 8));
		Assert.assertEquals("abc", bothPad("abc", "", 8));
		Assert.assertEquals("abc", bothPad("abc", null, 8));
		Assert.assertEquals("********", bothPad("", "*", 8));
		Assert.assertEquals("******", bothPad(null, "*", 6));
		Assert.assertEquals("abc", bothPad("abc", "*", 2));
		Assert.assertEquals("abc", bothPad("abc", "*", -1));

		// padding
		Assert.assertEquals("abc1", padding("abc", '1', 1)); // 右填充
		Assert.assertEquals("abc2222222222", padding("abc", '2', 10));
		Assert.assertEquals("3abc", padding("abc", '3', -1)); // 左填充
		Assert.assertEquals("4444444444abc", padding("abc", '4', -10));
		Assert.assertEquals("abc", padding("abc", '5', 0));
		Assert.assertEquals("******", padding(null, '*', 6));
	}

	/**
	 * 测试startsWith方法
	 */
	@Test
	public void testStartsWith() {
		Assert.assertTrue(startsWith("abc", "a"));
		Assert.assertTrue(startsWith("abc", "ab"));
		Assert.assertFalse(startsWith("abc", "c"));
		Assert.assertFalse(startsWith("abc", null));
		Assert.assertFalse(startsWith(null, "a"));
	}

	/**
	 * 测试endsWith方法
	 */
	@Test
	public void testEndsWith() {
		Assert.assertTrue(endsWith("abc", "c"));
		Assert.assertTrue(endsWith("abc", "bc"));
		Assert.assertFalse(endsWith("abc", "a"));
		Assert.assertFalse(endsWith("abc", null));
		Assert.assertFalse(endsWith(null, "c"));
	}

	/**
	 * 测试charAt方法
	 */
	@Test
	public void testCharAt() {
		Assert.assertEquals('a', chatAt("abc", 0, '\n'));
		Assert.assertEquals('c', chatAt("abc", 2, '\n'));
		Assert.assertEquals('\n', chatAt("abc", -1, '\n'));
		Assert.assertEquals('\n', chatAt("abc", 3, '\n'));
		Assert.assertEquals('\n', chatAt(null, 0, '\n'));
	}

	/**
	 * 测试indexOf方法
	 */
	@Test
	public void testIndexOf() {
		final String str = "1234567890123123";

		// indexOf(src, target)
		Assert.assertEquals(0, indexOf(str, "123"));
		Assert.assertEquals(3, indexOf(str, "45"));
		Assert.assertEquals(10, indexOf(str, "1231"));
		Assert.assertEquals(-1, indexOf(str, "A"));
		Assert.assertEquals(-1, indexOf(str, ""));
		Assert.assertEquals(-1, indexOf(str, null));
		Assert.assertEquals(-1, indexOf("", "1"));
		Assert.assertEquals(-1, indexOf(null, "1"));

		// indexOf(src, target, offset)
		Assert.assertEquals(0, indexOf(str, "123", 0));
		Assert.assertEquals(0, indexOf(str, "123", -999));
		Assert.assertEquals(10, indexOf(str, "123", 1));
		Assert.assertEquals(10, indexOf(str, "123", -15));
		Assert.assertEquals(13, indexOf(str, "123", 11));
		Assert.assertEquals(13, indexOf(str, "123", -5));
		Assert.assertEquals(-1, indexOf(str, "123", 14));
		Assert.assertEquals(3, indexOf(str, "45", 0));
		Assert.assertEquals(-1, indexOf(str, "45", 5));
		Assert.assertEquals(-1, indexOf(str, "", 0));
		Assert.assertEquals(-1, indexOf(str, null, 0));
		Assert.assertEquals(-1, indexOf("", "1", 0));
		Assert.assertEquals(-1, indexOf(null, "1", 0));

		// indexOf(src, target, offset, occurTimes)
		Assert.assertEquals(0, indexOf(str, "123", 0, 1));
		Assert.assertEquals(0, indexOf(str, "123", -999, 1));
		Assert.assertEquals(10, indexOf(str, "123", 1, 1));
		Assert.assertEquals(10, indexOf(str, "123", 0, 2));
		Assert.assertEquals(13, indexOf(str, "123", -15, 2));
		Assert.assertEquals(13, indexOf(str, "123", 0, 3));
		Assert.assertEquals(-1, indexOf(str, "123", 0, 4));
		Assert.assertEquals(3, indexOf(str, "45", 0, 1));
		Assert.assertEquals(-1, indexOf(str, "45", 0, 2));
		Assert.assertEquals(-1, indexOf(str, "1", 0, -1));
		Assert.assertEquals(-1, indexOf(str, "", 0, 1));
		Assert.assertEquals(-1, indexOf(str, null, 0, 1));
		Assert.assertEquals(-1, indexOf("", "1", 0, 1));
		Assert.assertEquals(-1, indexOf(null, "1", 0, 1));
		Assert.assertEquals(-1, indexOf(str, "123", 0, 0));
		Assert.assertEquals(-1, indexOf(str, "123", 0, -1));
	}

	/**
	 * 测试lastIndexOf方法
	 */
	@Test
	public void testLastIndexOf() {
		final String str = "1234567890123123";

		// lastIndexOf(src, target)
		Assert.assertEquals(13, lastIndexOf(str, "123"));
		Assert.assertEquals(3, lastIndexOf(str, "45"));
		Assert.assertEquals(10, lastIndexOf(str, "1231"));
		Assert.assertEquals(-1, lastIndexOf(str, "A"));
		Assert.assertEquals(-1, lastIndexOf(str, ""));
		Assert.assertEquals(-1, lastIndexOf(str, null));
		Assert.assertEquals(-1, lastIndexOf("", "1"));
		Assert.assertEquals(-1, lastIndexOf(null, "1"));

		// lastIndexOf(src, target, offset)
		Assert.assertEquals(13, lastIndexOf(str, "123", str.length()));
		Assert.assertEquals(13, lastIndexOf(str, "123", str.length() - 1));
		Assert.assertEquals(13, lastIndexOf(str, "123", -1));
		Assert.assertEquals(10, lastIndexOf(str, "123", str.length() - 4));
		Assert.assertEquals(10, lastIndexOf(str, "123", -4));
		Assert.assertEquals(0, lastIndexOf(str, "123", str.length() - 7));
		Assert.assertEquals(0, lastIndexOf(str, "123", -7));
		Assert.assertEquals(-1, lastIndexOf(str, "123", str.length() + 1));
		Assert.assertEquals(-1, lastIndexOf(str, "123", 0));
		Assert.assertEquals(3, lastIndexOf(str, "45", str.length()));
		Assert.assertEquals(-1, lastIndexOf(str, "45", 2));
		Assert.assertEquals(-1, lastIndexOf(str, "123", 999));
		Assert.assertEquals(-1, lastIndexOf(str, "123", -999));
		Assert.assertEquals(-1, lastIndexOf(str, "", 0));
		Assert.assertEquals(-1, lastIndexOf(str, null, 0));
		Assert.assertEquals(-1, lastIndexOf("", "1", 0));
		Assert.assertEquals(-1, lastIndexOf(null, "1", 0));

		// lastIndexOf(src, target, offset, occurTimes)
		Assert.assertEquals(13, lastIndexOf(str, "123", str.length(), 1));
		Assert.assertEquals(10, lastIndexOf(str, "123", str.length(), 2));
		Assert.assertEquals(0, lastIndexOf(str, "123", str.length(), 3));
		Assert.assertEquals(-1, lastIndexOf(str, "123", str.length(), 4));
		Assert.assertEquals(13, lastIndexOf(str, "123", str.length() - 1, 1));
		Assert.assertEquals(10, lastIndexOf(str, "123", str.length() - 1, 2));
		Assert.assertEquals(0, lastIndexOf(str, "123", str.length() - 1, 3));
		Assert.assertEquals(-1, lastIndexOf(str, "123", str.length() - 1, 4));
		Assert.assertEquals(10, lastIndexOf(str, "123", str.length() - 4, 1));
		Assert.assertEquals(0, lastIndexOf(str, "123", str.length() - 4, 2));
		Assert.assertEquals(-1, lastIndexOf(str, "123", str.length() - 4, 3));
		Assert.assertEquals(0, lastIndexOf(str, "123", str.length() - 7, 1));
		Assert.assertEquals(-1, lastIndexOf(str, "123", str.length() - 7, 2));
		Assert.assertEquals(3, lastIndexOf(str, "45", str.length(), 1));
		Assert.assertEquals(-1, lastIndexOf(str, "45", str.length(), 2));
		Assert.assertEquals(-1, lastIndexOf(str, "", str.length(), 1));
		Assert.assertEquals(-1, lastIndexOf(str, null, str.length(), 1));
		Assert.assertEquals(-1, lastIndexOf("", "1", str.length(), 1));
		Assert.assertEquals(-1, lastIndexOf(null, "1", str.length(), 1));
		Assert.assertEquals(-1, lastIndexOf(str, "123", str.length(), 0));
		Assert.assertEquals(-1, lastIndexOf(str, "123", str.length(), -1));
	}

	/**
	 * 测试find方法
	 */
	@Test
	public void testFind() {
		final String str = "1234567890123123";

		// find(src, target)
		Assert.assertEquals("", find(str, "123"));
		Assert.assertEquals("123", find(str, "45"));
		Assert.assertEquals("1234567890", find(str, "1231"));
		Assert.assertNull(find(str, "A"));
		Assert.assertNull(find(str, ""));
		Assert.assertNull(find(str, null));
		Assert.assertNull(find("", "1"));
		Assert.assertNull(find(null, "1"));

		// find(src, target, offset)
		Assert.assertEquals("", find(str, "123", 0));
		Assert.assertEquals("", find(str, "123", -999));
		Assert.assertEquals("234567890", find(str, "123", 1));
		Assert.assertEquals("234567890", find(str, "123", -15));
		Assert.assertEquals("23", find(str, "123", 11));
		Assert.assertEquals("23", find(str, "123", -5));
		Assert.assertNull(find(str, "123", 14));
		Assert.assertEquals("123", find(str, "45", 0));
		Assert.assertNull(find(str, "45", 5));
		Assert.assertNull(find(str, "", 0));
		Assert.assertNull(find(str, null, 0));
		Assert.assertNull(find("", "1", 0));
		Assert.assertNull(find(null, "1", 0));

		// find(src, target, offset, occurTimes)
		Assert.assertEquals("", find(str, "123", 0, 1));
		Assert.assertEquals("", find(str, "123", -999, 1));
		Assert.assertEquals("234567890", find(str, "123", 1, 1));
		Assert.assertEquals("1234567890", find(str, "123", 0, 2));
		Assert.assertEquals("234567890123", find(str, "123", -15, 2));
		Assert.assertEquals("1234567890123", find(str, "123", 0, 3));
		Assert.assertNull(find(str, "123", 0, 4));
		Assert.assertEquals("123", find(str, "45", 0, 1));
		Assert.assertNull(find(str, "45", 0, 2));
		Assert.assertNull(find(str, "1", 0, -1));
		Assert.assertNull(find(str, "", 0, 1));
		Assert.assertNull(find(str, null, 0, 1));
		Assert.assertNull(find("", "1", 0, 1));
		Assert.assertNull(find(null, "1", 0, 1));
		Assert.assertNull(find(str, "123", 0, 0));
		Assert.assertNull(find(str, "123", 0, -1));
	}

	/**
	 * 测试toUpperOrLowerCase方法
	 */
	@Test
	public void testToUpperOrLowerCase() {
		final String str = "abcdeABCDEfghFGH12345测试";
		Assert.assertEquals("AbcdeABCDEfghFGH12345测试", toUpperCase(str, 0, 1));
		Assert.assertEquals("ABCDEABCDEfghFGH12345测试", toUpperCase(str, 0, 6));
		Assert.assertEquals("ABCDEABCDEFGHFGH12345测试", toUpperCase(str, 0, 0));
		Assert.assertEquals("abcdeABCDEfGHFGH12345测试", toUpperCase(str, -12, 0));
		Assert.assertEquals("ABCDEABCDEFGHFGH12345测试", toUpperCase(str, -999, 999));
		Assert.assertEquals(str, toUpperCase(str, 1000, 2000));
		Assert.assertEquals("", toUpperCase("", -999, 999));
		Assert.assertNull(toUpperCase(null, 0, 0));

		Assert.assertEquals("ABCDEABCDEFGHFGH12345测试", toUpperCase(str, 0));
		Assert.assertEquals("abcdeABCDEfGHFGH12345测试", toUpperCase(str, 11));
		Assert.assertEquals(str, toUpperCase(str, 1000));
		Assert.assertEquals("", toUpperCase("", 1000));
		Assert.assertNull(toUpperCase(null, 0));

		Assert.assertEquals("abcdeaBCDEfghFGH12345测试", toLowerCase(str, 5, 6));
		Assert.assertEquals("abcdeabcdefghFGH12345测试", toLowerCase(str, 5, 11));
		Assert.assertEquals("abcdeabcdefghfgh12345测试", toLowerCase(str, 0, 0));
		Assert.assertEquals("abcdeABCDEfghfgh12345测试", toLowerCase(str, -12, 0));
		Assert.assertEquals("abcdeabcdefghfgh12345测试", toLowerCase(str, -999, 999));
		Assert.assertEquals(str, toLowerCase(str, 1000, 2000));
		Assert.assertEquals("", toLowerCase("", -999, 999));
		Assert.assertNull(toLowerCase(null, -999, 999));

		Assert.assertEquals("abcdeabcdefghfgh12345测试", toLowerCase(str, 0));
		Assert.assertEquals("abcdeABCDEfghfgh12345测试", toLowerCase(str, 11));
		Assert.assertEquals(str, toLowerCase(str, 1000));
		Assert.assertEquals("", toLowerCase("", 1000));
		Assert.assertNull(toLowerCase(null, 0));
	}

	/**
	 * 测试concat方法
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testConcat() {
		// concat(Object[])
		Assert.assertEquals("1,2", concat("1", ",", 2));
		Assert.assertEquals("1,2,3 ", concat(null, "1", ",", null, 2, "", ",", 3L, " "));
		Assert.assertEquals("1.2=false", concat(1.2D, "=", false));
		Assert.assertEquals("1.2=false", concat(new Object[] { 1.2D, "=", false }));

		Assert.assertEquals("", concat(null, null));
		Assert.assertEquals("", concat(new Object[0]));
		Assert.assertEquals("", concat());
		Assert.assertNull(concat((Object[]) null));

		// concat(Collection<?>)
		Assert.assertEquals("1,2", concat(ArrayUtil.asList("1", ",", 2)));
		Assert.assertEquals("1,2,3 ", concat(ArrayUtil.asList(null, "1", ",", null, 2, "", ",", 3L, " ")));
		Assert.assertEquals("1.2=false", concat(ArrayUtil.asList(1.2D, "=", false)));
		Assert.assertEquals("1.2=false", concat(ArrayUtil.asList(1.2D, "=", false)));

		Assert.assertEquals("", concat(ArrayUtil.asList(null, null)));
		Assert.assertEquals("", concat(new ArrayList<Object>()));
		Assert.assertNull(concat((Collection<?>) null));
	}
}
