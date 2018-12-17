package com.base.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * 字符串工具类。
 */
public class StringUtil {
    /**
     * 分割符 (默认为: , 或 ; 或 |)
     */
    private static final String SPLIT_DELIMITER = ",|;|\\|";

    /**
     * 连接分隔符
     */
    public static final String JOIN_SEPARATOR = ",";

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * 空格字符串
     */
    public static final String SPACE = " ";

    /**
     * 空格字符
     */
    public static final char SPACE_CHAR = ' ';

    /**
     * 字符串处理方式 - 左侧
     */
    private static final int LEFT = -1;

    /**
     * 字符串处理方式 - 右侧
     */
    private static final int RIGHT = 1;

    /**
     * 字符串处理方式 - 两侧
     */
    private static final int BOTH = 2;

    /**
     * 将字符串转换成字符数组，字符串为null时返回null
     *
     * @param str
     * @return
     */
    public static char[] toCharArray(String str) {
        return (str != null ? str.toCharArray() : null);
    }

    /**
     * 将字符数组转换为字符串，字符数组为null时返回null
     *
     * @param chars
     * @return
     */
    public static String toString(char[] chars) {
        return (chars != null ? new String(chars) : null);
    }

    /**
     * 将空字符串转换为null
     *
     * @param str
     * @return
     */
    public static String emptyAsNull(String str) {
        return ((str == null || EMPTY.equals(str.trim())) ? null : str);
    }

    /**
     * 将null转换为空字符串
     *
     * @param str
     * @return
     */
    public static String nullAsEmpty(String str) {
        return (str == null ? EMPTY : str);
    }

    /**
     * 格式化数字
     *
     * @param pattern
     * @param num
     * @return
     */
    public static String formatNumber(String pattern, Object num) {
        return ((num == null || pattern == null) ? EMPTY : new DecimalFormat(pattern).format(num));
    }

    /**
     * 格式化数字，前补0至digits长度
     *
     * @param digits
     * @param num
     * @return
     */
    public static String formatNumber(int digits, long num) {
        StringBuilder pattern = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            pattern.append("0");
        }

        return formatNumber(pattern.toString(), num);
    }

    /**
     * 格式化字符串，请使用{@link #formatString(String, Object[])}代替
     *
     * @param fmt
     * @param args
     * @return
     */
    @Deprecated
    public static String format(String fmt, Object... args) {
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        out.printf(fmt, args);
        out.flush();
        return writer.toString();
    }

    /**
     * 去除str字符串中首尾不可见字符, str为null返回null
     *
     * @param str 字符串
     * @return 更新后的字符串
     */
    public static String trim(String str) {
        return (str != null ? str.trim() : null);
    }

    /**
     * 去除str字符串中所有不可见字符, str为null返回null
     *
     * @param str 字符串
     * @return 更新后的字符串
     */
    public static String trimAll(String str) {
        return (str != null ? str.replaceAll("\\s+", "") : null);
    }

    /**
     * 去除str字符串中开头部分所有不可见字符, str为null返回null
     *
     * @param str 字符串
     * @return 更新后的字符串
     */
    public static String trimLeft(String str) {
        return (str != null ? str.replaceAll("^\\s+", "") : null);
    }

    /**
     * 去除str字符串中结尾部分所有不可见字符, str为null返回null
     *
     * @param str 字符串
     * @return 更新后的字符串
     */
    public static String trimRight(String str) {
        return (str != null ? str.replaceAll("\\s+$", "") : null);
    }

    /**
     * 去除str字符串左侧与tirmStr相同内容的字符串，如果tirmStr在左侧重复出现多次则全部去除。
     *
     * @param str
     * @param tirmStr
     * @return str为null或空、tirmStr为null或空、str的长度比tirmStr的长度小、未匹配到tirmStr时直接返回str
     */
    public static String trimLeft(String str, String tirmStr) {
        if (isEmpty(str) || isEmpty(tirmStr) || str.length() < tirmStr.length()) {
            return str;
        }

        int offset = 0;
        while (str.startsWith(tirmStr, offset)) {
            offset += tirmStr.length();
        }

        return (offset > 0 ? str.substring(offset) : str);
    }

    /**
     * 去除str字符串右侧与tirmStr相同内容的字符串，如果tirmStr在右侧重复出现多次则全部去除。
     *
     * @param str
     * @param tirmStr
     * @return str为null或空、tirmStr为null或空、str的长度比tirmStr的长度小、未匹配到tirmStr时直接返回str
     */
    public static String trimRight(String str, String tirmStr) {
        if (isEmpty(str) || isEmpty(tirmStr) || str.length() < tirmStr.length()) {
            return str;
        }

        int offset = str.length();
        while (str.startsWith(tirmStr, offset - tirmStr.length())) {
            offset -= tirmStr.length();
        }

        return (offset < str.length() ? str.substring(0, offset) : str);
    }

    /**
     * 去除str字符串两侧与tirmStr相同内容的字符串，如果tirmStr在两侧重复出现多次则全部去除。
     *
     * @param str
     * @param tirmStr
     * @return str为null或空、tirmStr为null或空、str的长度比tirmStr的长度小、未匹配到tirmStr时直接返回str
     */
    public static String trimBoth(String str, String tirmStr) {
        return trimRight(trimLeft(str, tirmStr), tirmStr);
    }

    /**
     * 颠倒字符串中字符的顺序
     *
     * @param str
     * @return
     */
    public static String reverse(String str) {
        if (isBlank(str)) {
            return str;
        }

        return new StringBuilder(str).reverse().toString();
    }

    /**
     * 获取字符串长度
     *
     * @param str
     * @return
     */
    public static int length(String str) {
        return (str == null ? 0 : str.length());
    }

    /**
     * 获取字符串集的总长度
     *
     * @param strs
     * @return
     */
    public static int length(String... strs) {
        int len = 0;
        if (strs != null) {
            for (String str : strs) {
                if (str != null) {
                    len += str.length();
                }
            }
        }

        return len;
    }

    /**
     * 获取字符串在平台默认字符集下的字节数
     *
     * @param str
     * @return
     */
    public static int lengthAsByte(String str) {
        return lengthAsByte(str, null);
    }

    /**
     * 获取字符串在指定字符集下的字节数
     *
     * @param str
     * @return
     */
    public static int lengthAsByte(String str, String charset) {
        try {
            if (str == null) {
                return 0;
            }

            if (charset == null) {
                charset = Charset.defaultCharset().name();
            }

            return str.getBytes(charset).length;
        } catch (UnsupportedEncodingException e) {
            return -1;
        }
    }

    /**
     * 字符串是否以prefix开头
     *
     * @param msg
     * @param prefix
     * @return
     */
    public static boolean startsWith(String msg, String prefix) {
        return (msg != null && prefix != null && msg.startsWith(prefix));
    }

    /**
     * 字符串是否以suffix结尾
     *
     * @param msg
     * @param suffix
     * @return
     */
    public static boolean endsWith(String msg, String suffix) {
        return (msg != null && suffix != null && msg.endsWith(suffix));
    }

    /**
     * 尝试对指定字符串进行右填充(padLen为负数时进行左填充)，填充padLen个padChar字符。
     *
     * @param msg
     * @param padChar
     * @param padLen
     * @return
     */
    public static String padding(String msg, char padChar, int padLen) {
        if (padLen == 0) {
            return msg;
        }

        StringBuilder buff = new StringBuilder(defaultString(msg));
        if (padLen > 0) {
            while (padLen-- > 0) {
                buff.append(padChar);
            }

        } else {
            while (padLen++ < 0) {
                buff.insert(0, padChar);
            }
        }

        return buff.toString();
    }

    /**
     * 尝试对指定字符串进行左填充，当遇到填充下一个padStr时超出totalLen长度时终止填充并返回当前填充结果
     *
     * @param msg
     * @param padStr
     * @param totalLen
     * @return
     */
    public static String leftPad(String msg, String padStr, int totalLen) {
        return pad(msg, padStr, totalLen, LEFT);
    }

    /**
     * 尝试对指定字符串进行右填充，当遇到填充下一个padStr时超出totalLen长度时终止填充并返回当前填充结果
     *
     * @param msg
     * @param padStr
     * @param totalLen
     * @return
     */
    public static String rightPad(String msg, String padStr, int totalLen) {
        return pad(msg, padStr, totalLen, RIGHT);
    }

    /**
     * 尝试对指定字符串进行左右填充，当遇到填充下两个padStr时超出totalLen长度时终止填充并返回当前填充结果
     *
     * @param msg
     * @param padStr
     * @param totalLen
     * @return
     */
    public static String bothPad(String msg, String padStr, int totalLen) {
        return pad(msg, padStr, totalLen, BOTH);
    }

    /**
     * 尝试对指定字符串进行指定方式的填充，当遇到填充padStr时超出len长度时终止填充并返回当前填充结果
     *
     * @param msg
     * @param padStr
     * @param totalLen
     * @param padMode
     * @return
     */
    private static String pad(String msg, String padStr, int totalLen, int padMode) {
        if (isEmpty(padStr) || totalLen <= 0 || (msg != null && msg.length() >= totalLen)) {
            return msg;
        }

        StringBuilder str = new StringBuilder(defaultString(msg));

        out:
        while (str.length() + padStr.length() * Math.abs(padMode) <= totalLen) {
            switch (padMode) {
                case LEFT: // 左填充
                    str.insert(0, padStr);
                    break;

                case RIGHT: // 右填充
                    str.append(padStr);
                    break;

                case BOTH: // 双向填充
                    str.insert(0, padStr).append(padStr);
                    break;

                default: // 未知填充方式
                    break out;
            }
        }

        return str.toString();
    }

    /**
     * 按照格式串格式化Date对象
     *
     * @param pattern 格式串
     * @param d       Date对象
     * @return 格式化后的字符串
     */
    public static String format(String pattern, java.util.Date d) {
        return ((d == null || pattern == null) ? EMPTY : new SimpleDateFormat(pattern).format(d));
    }

    /**
     * 按照格式串格式化Date对象
     *
     * @param pattern 格式串
     * @param d       Date对象
     * @param locale  场所
     * @return 格式化后的字符串
     */
    public static String format(String pattern, java.util.Date d, Locale locale) {
        return ((d == null || pattern == null) ? EMPTY : new SimpleDateFormat(pattern, locale).format(d));
    }

    /**
     * 按照格式串解析字符串到Date对象
     *
     * @param pattern 格式串
     * @param str     字符串
     * @return 解析出的Date对象
     */
    public static Date parseToDate(String pattern, String str) {
        try {
            return ((str == null || pattern == null) ? null : new SimpleDateFormat(pattern).parse(str));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按照格式串解析字符串到Date对象
     *
     * @param pattern
     * @param str
     * @return 返回null, 如果解析失败
     */
    public static Date parseToDateQuietly(String pattern, String str) {
        try {
            return parseToDate(pattern, str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 按照格式串解析字符串到SqlDate对象
     *
     * @param pattern
     * @param str
     * @return
     * @throws ParseException
     */
    public static java.sql.Date parseToSqlDate(String pattern, String str) {
        Date date = parseToDate(pattern, str);
        if (date == null) {
            return null;
        }

        return new java.sql.Date(date.getTime());
    }

    /**
     * 将字符串按制定字符集转换成16进制字符串
     *
     * @param str     字符串
     * @param charset 字符集. 如果为null则使用平台默认字符集.
     * @return 16进制字符串. 返回null, 如果str为null或转换出现异常.
     */
    public static String string2Hex(String str, String charset) {
        if (str == null) {
            return null;
        }

        try {
            byte[] bytes;
            if (charset != null) {
                bytes = str.getBytes(charset);

            } else {
                bytes = str.getBytes();
            }

            StringBuilder hex = new StringBuilder();
            for (byte b : bytes) {
                String hs = Integer.toHexString(b & 0xFF).toUpperCase();
                if (hs.length() == 1) {
                    hex.append("0");
                }
                hex.append(hs);
            }

            return hex.toString();
        } catch (Exception e1) {
            return null;
        }
    }

    /**
     * 将16进制字符串按照制定字符集转换成字符串
     *
     * @param hex     16进制字符串
     * @param charset 字符集. 如果为null则使用平台默认字符集.
     * @return 字符串. 返回null, 如果hex为空字符串或hex的长度不为偶数或转换出现异常.
     */
    public static String hex2String(String hex, String charset) {
        if (isBlank(hex) || hex.length() % 2 != 0) {
            return null;
        }

        try {
            byte[] bytes = new byte[hex.length() / 2];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (0xFF & Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16));
            }

            return (charset == null ? new String(bytes) : new String(bytes, charset));
        } catch (Exception e1) {
            return null;
        }
    }

    /**
     * 按照格式解析字符串, 调用{@link java.text.MessageFormat#parse(String)}
     *
     * @param pattern 格式
     * @param msg     字符串
     * @return 解析出的参数. 返回null, 如果解析出现异常
     */
    public static Object[] parseMessage(String pattern, String msg) {
        try {
            return new MessageFormat(pattern).parse(msg);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 格式化字符串, 调用{@link java.text.MessageFormat#format(String, Object[])}
     *
     * @param pattern 格式
     * @param args    参数
     * @return 格式化后的字符串. 返回null, 如果格式化出现异常
     */
    public static String formatMessage(String pattern, Object... args) {
        try {
            return MessageFormat.format(pattern, args);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 格式化字符串, 调用{@link java.lang.String#format(String, Object[])}
     *
     * @param format 格式
     * @param args   参数
     * @return 格式化后的字符串. 返回null, 如果格式化出现异常
     */
    public static String formatString(String format, Object... args) {
        try {
            return String.format(format, args);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 是否是空字符串
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 是否包含空字符串
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isHasEmpty(String... strings) {
        for (String str : strings) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否不是空字符串
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return (str != null && str.length() > 0);
    }

    /**
     * 去除两端空白字符后是否是空字符串
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 去除两端空白字符后是否不是空字符串
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNotBlank(String str) {
        return (str != null && str.trim().length() > 0);
    }

    /**
     * 默认字符串
     *
     * @param str 字符串
     * @return 如果str为null, 则返回空字符串
     */
    public static String defaultString(String str) {
        return defaultString(str, EMPTY);
    }

    /**
     * 默认字符串
     *
     * @param str    字符串
     * @param defVal 默认字符串
     * @return 如果str为null, 则返回defVal
     */
    public static String defaultString(String str, String defVal) {
        return (str == null ? defVal : str);
    }

    /**
     * 默认字符串
     *
     * @param str    字符串
     * @param defVal 默认字符串
     * @return 如果str为空字符串, 则返回defVal
     */
    public static String defaultStringIfEmpty(String str, String defVal) {
        return (isEmpty(str) ? defVal : str);
    }

    /**
     * 默认字符串
     *
     * @param str    字符串
     * @param defVal 默认字符串
     * @return 如果str去除两端空白字符后是空字符串, 则返回defVal
     */
    public static String defaultStringIfBlank(String str, String defVal) {
        return (isBlank(str) ? defVal : str);
    }

    /**
     * 源字符串是否包含目标字符串
     *
     * @param str    源字符串
     * @param target 目标字符串
     * @return
     */
    public static boolean contains(String str, String target) {
        if (str == null || target == null) {
            return false;
        }

        return str.contains(target);
    }

    /**
     * 源字符串是否包含任何一个目标字符串集中的字符串
     *
     * @param str
     * @param targets
     * @return
     */
    public static boolean containsAny(String str, String... targets) {
        if (ArrayUtil.isNotEmpty(targets)) {
            for (String target : targets) {
                if (contains(str, target)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 源字符串是否包含所有目标字符串集中的字符串
     *
     * @param str
     * @param targets
     * @return
     */
    public static boolean containsAll(String str, String... targets) {
        if (ArrayUtil.isEmpty(targets)) {
            return false;
        }

        for (String target : targets) {
            if (contains(str, target) == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * 统计子字符串在指定字符串中出现的次数
     *
     * @param str
     * @param subStr
     * @return
     */
    public static int count(String str, String subStr) {
        if (isEmpty(str) || isEmpty(subStr)) {
            return 0;
        }

        int cnt = 0, idx = 0, len = subStr.length();
        while ((idx = str.indexOf(subStr, idx)) != -1) {
            idx += len;
            cnt++;
        }

        return cnt;
    }

    /**
     * 将字符串重复指定次数
     *
     * @param str
     * @param times
     * @return
     */
    public static String repeat(String str, int times) {
        return repeat(str, null, times);
    }

    /**
     * 将字符串重复指定次数(使用指定分隔符)
     *
     * @param str
     * @param separator
     * @param times
     * @return
     */
    public static String repeat(String str, String separator, int times) {
        if (isEmpty(str) || times < 2) {
            return str;
        }

        StringBuilder buff = new StringBuilder();
        while (times-- > 0) {
            buff.append(str);

            if (separator != null && times > 0) {
                buff.append(separator);
            }
        }

        return buff.toString();
    }

    /**
     * 获取指定字符串中指定位置的字符，如果字符为null或idx超限，则返回defChar
     *
     * @param msg
     * @param idx
     * @param defChar
     * @return
     */
    public static char chatAt(String msg, int idx, char defChar) {
        if (msg == null || idx < 0 || idx >= msg.length()) {
            return defChar;
        }

        return msg.charAt(idx);
    }

    /**
     * 获取子字符串, 起始位置可以为负，不会出现下标越界
     *
     * @param msg
     * @param start 起始位置(包含), 取负代表从最后一个字符开始倒数，负无穷大时位置为0，正无穷大位置为length
     * @return
     */
    public static String subString(String msg, int start) {
        return subString(msg, start, 0);
    }

    /**
     * 获取子字符串, 起始和结束位置可以为负，不会出现下标越界
     *
     * @param msg
     * @param start 起始位置(包含), 取负代表从最后一个字符开始倒数，负无穷大和零时位置为0，正无穷大位置为length
     * @param end   结束位置(不包含), 取负代表从最后一个字符开始倒数，负无穷大时位置为0，零和正无穷大位置为length
     * @return
     */
    public static String subString(String msg, int start, int end) {
        if (isEmpty(msg)) {
            return msg;
        }

        if (start < 0) {
            start = Math.max(0, msg.length() + start); // 负无穷大取0
        }

        if (end <= 0) {
            end = msg.length() + end;
        }

        end = Math.min(end, msg.length()); // 正无穷大取length

        if (start >= msg.length() || end > msg.length() || start >= end) {
            return EMPTY;
        }

        return msg.substring(start, end);
    }

    /**
     * 在根据给定下标从字符串中取出最多指定长度字符
     *
     * @param str
     * @param offset 起始下标(偏移), 负数代表从最后一个元素开始倒数，负无穷大时位置为0
     * @param length 最大记录数，正数，当起始下标加length超过字符串长度时，length等于字符串长度减起始下标
     * @return
     */
    public static String getString(String str, int offset, int length) {
        if (isEmpty(str)) {
            return str;

        } else if (offset >= str.length() || length <= 0) {
            return EMPTY;
        }

        if (offset < 0) {
            offset = Math.max(0, str.length() + offset);
        }

        if (offset + length > str.length()) {
            length = str.length() - offset;
        }

        return str.substring(offset, offset + length);
    }

    /**
     * 查找目标字符串第1次出现的位置。如果找到则返回从起始到目标开始位置之间所有内容，否则返回null。
     *
     * @param src
     * @param target
     * @return
     */
    public static String find(String src, String target) {
        return find(src, target, 0, 1);
    }

    /**
     * 从偏移位置开始查找目标字符串第1次出现的位置。 如果找到则返回从偏移位置到目标开始位置之间所有内容，否则返回null。
     *
     * @param src
     * @param target
     * @param offset 起始下标(偏移), 负数代表从最后一个元素开始倒数，负无穷大时位置为0
     * @return
     */
    public static String find(String src, String target, int offset) {
        return find(src, target, offset, 1);
    }

    /**
     * 从偏移位置开始查找目标字符串第N次出现的位置。 如果找到则返回从偏移位置到目标开始位置之间所有内容。
     * 如果目标字符串未找到或出现次数未达到指定值，则返回null。
     *
     * @param src
     * @param target
     * @param offset     起始下标(偏移), 负数代表从最后一个元素开始倒数，负无穷大时位置为0
     * @param occurTimes
     * @return
     */
    public static String find(String src, String target, int offset, int occurTimes) {
        int end = indexOf(src, target, offset, occurTimes);
        if (end == -1) {
            return null;
        }

        if (offset < 0) {
            offset = Math.max(0, src.length() + offset);
        }

        return src.substring(offset, end);
    }

    /**
     * 查找目标字符串第1次出现的位置
     *
     * @param src
     * @param target
     * @return
     */
    public static int indexOf(String src, String target) {
        return indexOf(src, target, 0, 1);
    }

    /**
     * 从指定位置开始查找目标字符串第1次出现的位置
     *
     * @param src
     * @param target
     * @param offset 起始下标(偏移), 负数代表从最后一个元素开始倒数，负无穷大时位置为0
     * @return
     */
    public static int indexOf(String src, String target, int offset) {
        return indexOf(src, target, offset, 1);
    }

    /**
     * 从指定位置开始查找目标字符串出现第N次的位置
     *
     * @param src
     * @param target
     * @param offset     起始下标(偏移), 负数代表从最后一个元素开始倒数，负无穷大时位置为0
     * @param occurTimes
     * @return
     */
    public static int indexOf(String src, String target, int offset, int occurTimes) {
        if (isEmpty(src) || isEmpty(target) || occurTimes <= 0) {
            return -1;
        }

        if (offset < 0) {
            offset = Math.max(0, src.length() + offset);
        }

        if (target.length() + offset > src.length()) {
            return -1;
        }

        while (occurTimes-- > 0) {
            int pos = src.indexOf(target, offset);
            if (pos == -1) {
                return -1; // not found
            }

            offset = pos + 1; // found
        }

        return offset - 1; // last pos
    }

    /**
     * 反向查找目标字符串第1次出现的位置
     *
     * @param src
     * @param target
     * @return
     */
    public static int lastIndexOf(String src, String target) {
        return lastIndexOf(src, target, length(src), 1);
    }

    /**
     * 从指定位置开始反向查找目标字符串第1次出现的位置
     *
     * @param src
     * @param target
     * @param offset 起始下标(偏移), 负数代表从最后一个元素开始倒数，负无穷大时位置为0
     * @return
     */
    public static int lastIndexOf(String src, String target, int offset) {
        return lastIndexOf(src, target, offset, 1);
    }

    /**
     * 从指定位置开始反向查找目标字符串出现第N次的位置
     *
     * @param src
     * @param target
     * @param offset     起始下标(偏移), 负数代表从最后一个元素开始倒数，负无穷大时位置为0
     * @param occurTimes
     * @return
     */
    public static int lastIndexOf(String src, String target, int offset, int occurTimes) {
        if (isEmpty(src) || isEmpty(target) || occurTimes <= 0) {
            return -1;
        }

        if (offset < 0) {
            offset = Math.max(0, src.length() + offset);
        }

        if (target.length() > offset || offset > src.length()) {
            return -1;
        }

        while (occurTimes-- > 0) {
            int pos = src.lastIndexOf(target, offset);
            if (pos == -1) {
                return -1; // not found
            }

            offset = pos - 1; // found
        }

        return offset + 1; // last pos
    }

    /**
     * 字符串是否匹配指定模式
     *
     * @param str
     * @param pattern
     * @return
     */
    public static boolean matches(String str, String pattern) {
        return (str != null && pattern != null && str.matches(pattern));
    }

    /**
     * 替换给定字符串中指定范围的字符, 起始和结束位置可以为负，不会出现下标越界
     *
     * @param msg
     * @param flag
     * @param start 起始位置(包含), 取负代表从最后一个字符开始倒数，负无穷大和零时位置为0，正无穷大位置为length
     * @param end   结束位置(不包含), 取负代表从最后一个字符开始倒数，负无穷大时位置为0，零和正无穷大位置为length
     * @return
     */
    public static String mark(String msg, char flag, int start, int end) {
        if (isEmpty(msg)) {
            return msg;
        }

        if (start < 0) {
            start = Math.max(0, msg.length() + start); // 负无穷大取0
        }

        if (end <= 0) {
            end = msg.length() + end;
        }

        end = Math.min(end, msg.length()); // 正无穷大取length

        if (start >= msg.length() || end > msg.length() || start >= end) {
            return msg;
        }

        StringBuilder str = new StringBuilder(msg);
        for (int i = start; i < end; i++) {
            str.setCharAt(i, flag);
        }

        return str.toString();
    }

    /**
     * 将集合中各元素用默认连接符连接成字符串
     *
     * @param col
     * @return
     */
    public static String join(Collection<?> col) {
        return doJoin(col, null, false, false, null);
    }

    /**
     * 将集合中各元素用指定连接符连接成字符串
     *
     * @param col
     * @param separator
     * @return
     */
    public static String join(Collection<?> col, String separator) {
        return doJoin(col, separator, false, false, null);
    }

    /**
     * 将集合中各元素用默认连接符连接成字符串, 如果joinElements为true表示子元素如果是集合类型也需要连接
     *
     * @param col
     * @param separator
     * @param joinElements
     * @return
     */
    public static String join(Collection<?> col, boolean joinElements) {
        return doJoin(col, null, joinElements, false, null);
    }

    /**
     * 将集合中各元素用指定连接符连接成字符串, 如果joinElements为true表示子元素如果是集合类型也需要连接
     *
     * @param col
     * @param separator
     * @param joinElements
     * @return
     */
    public static String join(Collection<?> col, String separator, boolean joinElements) {
        return doJoin(col, separator, joinElements, false, null);
    }

    /**
     * 将集合中各元素用默认连接符连接成字符串，忽略null元素
     *
     * @param col
     * @return
     */
    public static String joinNoNull(Collection<?> col) {
        return doJoin(col, null, false, true, null);
    }

    /**
     * 将集合中各元素用指定连接符连接成字符串，忽略null元素
     *
     * @param col
     * @param separator
     * @return
     */
    public static String joinNoNull(Collection<?> col, String separator) {
        return doJoin(col, separator, false, true, null);
    }

    /**
     * 将集合中各元素用默认连接符连接成字符串, 如果joinElements为true表示子元素如果是集合类型也需要连接，忽略null元素
     *
     * @param col
     * @param separator
     * @param joinElements
     * @return
     */
    public static String joinNoNull(Collection<?> col, boolean joinElements) {
        return doJoin(col, null, joinElements, true, null);
    }

    /**
     * 将集合中各元素用指定连接符连接成字符串, 如果joinElements为true表示子元素如果是集合类型也需要连接，忽略null元素
     *
     * @param col
     * @param separator
     * @param joinElements
     * @return
     */
    public static String joinNoNull(Collection<?> col, String separator, boolean joinElements) {
        return doJoin(col, separator, joinElements, true, null);
    }

    /**
     * 将集合中各元素用指定连接符连接成字符串, 如果joinElements为true表示子元素如果是集合类型也需要连接
     *
     * @param col
     * @param separator
     * @param joinElements
     * @param ignoreNullElement
     * @param context
     * @return
     */
    private static String doJoin(Collection<?> col, String separator, boolean joinElements, boolean ignoreNullElement,
                                 Collection<Object> context) {
        if (col == null) {
            return null;

        } else if (col.isEmpty()) {
            return EMPTY;
        }

        if (separator == null) {
            separator = JOIN_SEPARATOR;
        }

        if (joinElements) {
            if (context == null) {
                // 不使用hashSet, 因为HashSet.add一个互相持有引用的集合对象时会出现死循环导致堆栈溢出
                context = new ArrayList<Object>();
            }

            context.add(col); // 防止循环引用
        }

        StringBuilder str = new StringBuilder();
        for (Object obj : col) {
            if (joinElements && obj instanceof Collection) {
                if (context.contains(obj) == false) {
                    if (str.length() > 0) {
                        str.append(separator);
                    }
                    // 连接子元素
                    str.append(doJoin((Collection<?>) obj, separator, joinElements, ignoreNullElement, context));
                }
            } else if (ignoreNullElement == false || obj != null) {
                if (str.length() > 0) {
                    str.append(separator);
                }
                str.append(ObjectUtil.toString(obj));
            }
        }

        return str.toString();
    }

    /**
     * 将数组中各元素用默认连接符连接成字符串，忽略null元素
     *
     * @param objs
     * @return
     */
    public static String joinNoNull(Object[] objs) {
        return doJoin(objs, null, false, true, null);
    }

    /**
     * 将数组中各元素用指定连接符连接成字符串，忽略null元素
     *
     * @param objs
     * @param separator
     * @return
     */
    public static String joinNoNull(Object[] objs, String separator) {
        return doJoin(objs, separator, false, true, null);
    }

    /**
     * 将数组中各元素用默认连接符连接成字符串, 如果joinElements为true表示子元素如果是对象数组类型也需要连接，忽略null元素
     *
     * @param objs
     * @param joinElements
     * @return
     */
    public static String joinNoNull(Object[] objs, boolean joinElements) {
        return doJoin(objs, null, joinElements, true, null);
    }

    /**
     * 将数组中各元素用指定连接符连接成字符串, 如果joinElements为true表示子元素如果是对象数组类型也需要连接，忽略null元素
     *
     * @param objs
     * @param separator
     * @param joinElements
     * @return
     */
    public static String joinNoNull(Object[] objs, String separator, boolean joinElements) {
        return doJoin(objs, separator, joinElements, true, null);
    }

    /**
     * 将数组中各元素用默认连接符连接成字符串
     *
     * @param objs
     * @return
     */
    public static String join(Object[] objs) {
        return doJoin(objs, null, false, false, null);
    }

    /**
     * 将数组中各元素用指定连接符连接成字符串
     *
     * @param objs
     * @param separator
     * @return
     */
    public static String join(Object[] objs, String separator) {
        return doJoin(objs, separator, false, false, null);
    }

    /**
     * 将数组中各元素用默认连接符连接成字符串, 如果joinElements为true表示子元素如果是对象数组类型也需要连接
     *
     * @param objs
     * @param joinElements
     * @return
     */
    public static String join(Object[] objs, boolean joinElements) {
        return doJoin(objs, null, joinElements, false, null);
    }

    /**
     * 将数组中各元素用指定连接符连接成字符串, 如果joinElements为true表示子元素如果是对象数组类型也需要连接
     *
     * @param objs
     * @param separator
     * @param joinElements
     * @return
     */
    public static String join(Object[] objs, String separator, boolean joinElements) {
        return doJoin(objs, separator, joinElements, false, null);
    }

    /**
     * 将数组中各元素用指定连接符连接成字符串, 如果joinElements为true表示子元素如果是对象数组类型也需要连接
     *
     * @param objs
     * @param separator
     * @param joinElements
     * @param ignoreNullElement
     * @param context
     * @return
     */
    private static String doJoin(Object[] objs, String separator, boolean joinElements, boolean ignoreNullElement,
                                 Collection<Object> context) {
        if (objs == null) {
            return null;

        } else if (objs.length == 0) {
            return EMPTY;
        }

        if (separator == null) {
            separator = JOIN_SEPARATOR;
        }

        if (joinElements) {
            if (context == null) {
                context = new HashSet<Object>();
            }

            context.add(objs); // 防止循环引用
        }

        StringBuilder str = new StringBuilder();
        for (Object obj : objs) {
            if (joinElements && obj instanceof Object[]) {
                if (context.contains(obj) == false) {
                    if (str.length() > 0) {
                        str.append(separator);
                    }
                    // 连接子元素
                    str.append(doJoin((Object[]) obj, separator, joinElements, ignoreNullElement, context));
                }
            } else if (ignoreNullElement == false || obj != null) {
                if (str.length() > 0) {
                    str.append(separator);
                }
                str.append(ObjectUtil.toString(obj));
            }
        }

        return str.toString();
    }

    /**
     * 将数组中各整数用指定连接符连接成字符串
     *
     * @param objs
     * @return
     */
    public static String join(int[] objs) {
        return join(objs, null);
    }

    /**
     * 将数组中各整数用指定连接符连接成字符串
     *
     * @param objs
     * @param separator
     * @return
     */
    public static String join(int[] objs, String separator) {
        if (objs == null) {
            return null;

        } else if (objs.length == 0) {
            return EMPTY;
        }

        if (separator == null) {
            separator = JOIN_SEPARATOR;
        }

        StringBuilder str = new StringBuilder();
        for (int obj : objs) {
            if (str.length() > 0) {
                str.append(separator);
            }
            str.append(obj);
        }

        return str.toString();
    }

    /**
     * 将字符串按分隔符分割后存入数组 (默认分隔符为 , 或 ; 或 |)
     *
     * @param msg 字符串
     * @return 数组. 返回null, 如果msg为null
     */
    public static String[] split(String msg) {
        return split(msg, SPLIT_DELIMITER);
    }

    /**
     * 将字符串按默认分隔符分割后存入数组 (默认分隔符为 , 或 ; 或 |)
     *
     * @param msg       字符串
     * @param delimiter 分隔符. 为null则使用默认分隔符(, 或 ; 或 |)
     * @return 数组. 返回null, 如果msg为null
     */
    public static String[] split(String msg, String delimiter) {
        if (msg == null) {
            return null;
        }

        // 如果delimiter为null则使用默认值
        delimiter = StringUtil.defaultString(delimiter, SPLIT_DELIMITER);
        return msg.split(delimiter);
    }

    /**
     * 将字符串按默认分隔符分割后存入集合 (默认分隔符为 , 或 ; 或 |)
     *
     * @param msg   字符串
     * @param clazz 集合类型
     * @return 集合. 返回null, 如果msg或clazz为null或者集合创建失败
     */
    public static <T extends Collection<String>> T split(String msg, Class<? extends T> clazz) {
        return split(msg, SPLIT_DELIMITER, clazz);
    }

    /**
     * 将字符串按分隔符分割后存入集合
     *
     * @param msg       字符串
     * @param delimiter 分隔符. 为null则使用默认分隔符(, 或 ; 或 |)
     * @param clazz     集合类型
     * @return 集合. 返回null, 如果msg或clazz为null或者集合创建失败
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends Collection<String>> T split(String msg, String delimiter,
                                                         Class<? extends Collection> clazz) {
        if (msg == null || clazz == null) {
            return null;
        }

        // 如果delimiter为null则使用默认值
        delimiter = StringUtil.defaultString(delimiter, SPLIT_DELIMITER);
        T collection = (T) CollectionUtil.newCollection(clazz);

        if (collection != null) {
            for (String part : msg.split(delimiter)) {
                collection.add(part);
            }
        }

        return collection;
    }

    /**
     * 将指定字符串str指定范围的内容替换为指定字符串replacement, 起始和结束位置可以为负，不会出现下标越界
     *
     * @param str
     * @param replacement
     * @param start       起始位置(包含), 取负代表从最后一个字符开始倒数，负无穷大和零时位置为0，正无穷大位置为length
     * @param end         结束位置(不包含), 取负代表从最后一个字符开始倒数，负无穷大时位置为0，零和正无穷大位置为length
     * @return
     */
    public static String replace(String str, String replacement, int start, int end) {
        if (isEmpty(str) || replacement == null) {
            return str;
        }

        if (start < 0) {
            start = Math.max(0, str.length() + start); // 负无穷大取0
        }

        if (end <= 0) {
            end = str.length() + end;
        }

        end = Math.min(end, str.length()); // 正无穷大取length

        if (start >= str.length() || end > str.length() || start >= end) {
            return str;
        }

        return new StringBuilder(str).replace(start, end, replacement).toString();
    }

    /**
     * 根据正则表达式对指定字符串进行替换，如果替换失败则返回指定字符串
     *
     * @param str         字符串
     * @param regex       正则表达式
     * @param replacement 替换内容，为null则用空字符串代替
     * @return 替换后的字符串
     */
    public static String replace(String str, String regex, String replacement) {
        try {
            if (str != null && regex != null) {
                return str.replaceAll(regex, defaultString(replacement));
            }
        } catch (Exception ex) {
            // Swallow the exception
        }

        return str;
    }

    /**
     * 获取给定字符串中所有在指定标记包含的内容(含标记)
     *
     * @param str 数据
     * @param tag 标记
     * @return 结果数据. str 或tag 有一个为null, 则返回空数组
     */
    public static String[] subString(String str, String tag) {
        return subStringBetween(str, tag, tag, true);
    }

    /**
     * 获取在给定字符串中所有在起始标记和结束标记之间的内容(含起始和结束标记)
     *
     * @param str   数据
     * @param open  起始字符串
     * @param close 结束字符串
     * @return 结果数据. str 或open 或close 有一个为null, 则返回空数组
     */
    public static String[] subString(String str, String open, String close) {
        return subStringBetween(str, open, close, true);
    }

    /**
     * 获取给定字符串中所有在指定标记包含的内容
     *
     * @param str 数据
     * @param tag 标记
     * @return 结果数据. str 或tag 有一个为null, 则返回空数组
     */
    public static String[] subStringBetween(String str, String tag) {
        return subStringBetween(str, tag, tag);
    }

    /**
     * 获取在给定字符串中所有在起始标记和结束标记之间的内容
     *
     * @param str   数据
     * @param open  起始字符串
     * @param close 结束字符串
     * @return 结果数据. str 或open 或close 有一个为null, 则返回空数组
     */
    public static String[] subStringBetween(String str, String open, String close) {
        return subStringBetween(str, open, close, false);
    }

    /**
     * 替换给定字符串中所有在起始标记和结束标记之间的内容(包含指定起始结束字符串).
     *
     * @param str         数据
     * @param target      标记
     * @param replacement 替换内容数组,下标对应找到内容的次数.当找到内容次数大于数组长度时,使用最后一个元素作为替换内容。
     * @return 替换后的字符串
     */
    public static String replaceString(String str, String target, String[] replacement) {
        return replaceStringBetween(str, target, target, replacement, true);
    }

    /**
     * 替换给定字符串中所有在起始标记和结束标记之间的内容(包含指定起始结束字符串).
     *
     * @param str         数据
     * @param open        起始字符串
     * @param close       结束字符串
     * @param replacement 替换内容数组,下标对应找到内容的次数.当找到内容次数大于数组长度时,使用最后一个元素作为替换内容。
     * @return 替换后的字符串
     */
    public static String replaceString(String str, String open, String close, String[] replacement) {
        return replaceStringBetween(str, open, close, replacement, true);
    }

    /**
     * 替换给定字符串中所有在起始标记和结束标记之间的内容(不包含指定起始结束字符串).
     *
     * @param str         数据
     * @param target      标记
     * @param replacement 替换内容数组,下标对应找到内容的次数.当找到内容次数大于数组长度时,使用最后一个元素作为替换内容。
     * @return 替换后的字符串
     */
    public static String replaceStringBetween(String str, String target, String[] replacement) {
        return replaceStringBetween(str, target, target, replacement, false);
    }

    /**
     * 替换给定字符串中所有在起始标记和结束标记之间的内容(不包含指定起始结束字符串).
     *
     * @param str         数据
     * @param open        起始字符串
     * @param close       结束字符串
     * @param replacement 替换内容数组,下标对应找到内容的次数.当找到内容次数大于数组长度时,使用最后一个元素作为替换内容。
     * @return 替换后的字符串
     */
    public static String replaceStringBetween(String str, String open, String close, String[] replacement) {
        return replaceStringBetween(str, open, close, replacement, false);
    }

    /**
     * 返回一个数组, 其元素是给定的XML中所有在指定无属性节点中包含内容(包含指定节点本身).
     *
     * @param xml      XML数据
     * @param nodeName 节点名称
     * @return 节点内容. xml 或nodeName 有一个为null, 则返回空数组
     */
    public static String[] subXML(String xml, String nodeName) {
        return subXML(xml, nodeName, true);
    }

    /**
     * 返回一个数组, 其元素是给定的XML中所有在指定无属性节点中包含内容(不包含指定节点本身).
     *
     * @param xml      XML数据
     * @param nodeName 节点名称
     * @return 节点内容. xml 或nodeName 有一个为null, 则返回空数组
     */
    public static String[] subXMLBetween(String xml, String nodeName) {
        return subXML(xml, nodeName, false);
    }

    /**
     * 替换给定XML字符串中所有指定无属性节点中包含内容(包含指定节点本身).
     *
     * @param str         数据
     * @param nodeName    标记
     * @param replacement 替换内容数组,下标对应找到内容的次数.当找到内容次数大于数组长度时,使用最后一个元素作为替换内容。
     * @return 替换后的字符串
     */
    public static String replaceXML(String str, String nodeName, String[] replacement) {
        return replaceXML(str, nodeName, replacement, true);
    }

    /**
     * 替换给定XML字符串中所有指定无属性节点中包含内容(不包含指定节点本身).
     *
     * @param str         数据
     * @param nodeName    标记
     * @param replacement 替换内容数组,下标对应找到内容的次数.当找到内容次数大于数组长度时,使用最后一个元素作为替换内容。
     * @return 替换后的字符串
     */
    public static String replaceXMLBetween(String str, String nodeName, String[] replacement) {
        return replaceXML(str, nodeName, replacement, false);
    }

    /**
     * 返回一个数组, 其元素是给定的XML中所有在指定无属性节点中包含内容.
     *
     * @param xml            XML数据
     * @param nodeName       节点名称
     * @param isNodeIncluded 是否在返回结果中包含节点内容
     * @return 结果数组. xml 或nodeName 有一个为null, 则返回空数组
     */
    static String[] subXML(String xml, String nodeName, boolean isNodeIncluded) {
        if (xml == null || nodeName == null) {
            return new String[0];
        }

        String startTag = new StringBuilder("<").append(nodeName).append(">").toString();
        String endTag = new StringBuilder("</").append(nodeName).append(">").toString();

        return subStringBetween(xml, startTag, endTag, isNodeIncluded);
    }

    /**
     * 替换给定XML字符串中所有在指定无属性节点之间的内容
     *
     * @param xml            XML数据
     * @param nodeName       节点名称
     * @param replacement    替换内容数组,下标对应找到内容的次数.当找到内容次数大于数组长度时,使用最后一个元素作为替换内容。
     * @param isNodeIncluded 是否在返回结果中包含节点内容
     * @return 替换后的XML
     */
    static String replaceXML(String xml, String nodeName, String[] replacement, boolean isNodeIncluded) {
        if (xml == null || nodeName == null || ArrayUtil.isEmpty(replacement)) {
            return xml;
        }

        String startTag = new StringBuilder("<").append(nodeName).append(">").toString();
        String endTag = new StringBuilder("</").append(nodeName).append(">").toString();

        return replaceStringBetween(xml, startTag, endTag, replacement, isNodeIncluded);
    }

    /**
     * 获取在给定字符串中所有在起始标记和结束标记之间的内容
     *
     * @param str           数据
     * @param open          起始字符串
     * @param close         结束字符串
     * @param isTagIncluded 是否在返回结果中包含标记内容
     * @return 结果数据. str 或open 或close 有一个为null, 则返回空数组
     */
    private static String[] subStringBetween(String str, String open, String close, boolean isTagIncluded) {
        if (str == null || open == null || close == null) {
            return new String[0];
        }

        List<String> results = new ArrayList<String>();
        int openStart = 0, openEnd = 0, closeStart = 0, closeEnd = 0;
        while (true) {
            // ... ^open ... close ...
            openStart = str.indexOf(open, closeEnd);
            if (openStart == -1) {
                break;
            }

            // ... open^ ... close ...
            openEnd = openStart + open.length();

            // ... open ... ^close ...
            closeStart = str.indexOf(close, openEnd);
            if (closeStart == -1) {
                break;
            }

            // ... open ... close^ ...
            closeEnd = closeStart + close.length();
            if (isTagIncluded) {
                // ... [open ... close] ...
                results.add(str.substring(openStart, closeEnd));

            } else {
                // ... open[ ... ]close ...
                results.add(str.substring(openEnd, closeStart));
            }
        }

        return results.toArray(new String[results.size()]);
    }

    /**
     * 替换给定字符串中所有在起始标记和结束标记之间的内容
     *
     * @param str           数据
     * @param open          起始字符串
     * @param close         结束字符串
     * @param replacement   替换内容数组,下标对应找到内容的次数.当找到内容次数大于数组长度时,使用最后一个元素作为替换内容。
     * @param isTagIncluded 是否在返回结果中包含标记内容
     * @return 替换后的字符串
     */
    private static String replaceStringBetween(String str, String open, String close, String[] replacement,
                                               boolean isTagIncluded) {
        if (str == null || open == null || close == null || ArrayUtil.isEmpty(replacement)) {
            return str;
        }

        StringBuilder result = new StringBuilder(str);
        String replaceStr = null;
        int maxRepLen = replacement.length - 1, lastRsLen = 0;
        int openStart = 0, openEnd = 0, closeStart = 0, closeEnd = 0, count = 0;
        while (true) {
            // ... ^open ... close ...
            openStart = result.indexOf(open, closeEnd);
            if (openStart == -1) {
                break;
            }

            // ... open^ ... close ...
            openEnd = openStart + open.length();

            // ... open ... ^close ...
            closeStart = result.indexOf(close, openEnd);
            if (closeStart == -1) {
                break;
            }

            // ... open ... close^ ...
            closeEnd = closeStart + close.length();

            lastRsLen = result.length();
            replaceStr = defaultString(replacement[(count > maxRepLen ? maxRepLen : count)]);

            if (isTagIncluded) {
                // ... [open ... close] ...
                result.replace(openStart, closeEnd, replaceStr);

            } else {
                // ... open[ ... ]close ...
                result.replace(openEnd, closeStart, replaceStr);
            }

            closeEnd += result.length() - lastRsLen;
            count++;
        }

        return result.toString();
    }

    /**
     * 字符串分割转list
     *
     * @param string
     * @param split
     * @param tail
     * @return
     */
    static public List<String> separateString(String string, String split, boolean tail) {
        List<String> fieldList = new ArrayList<String>();

        int len = string.length();
        int offset = 0;

        String field;
        char tag = split.trim().charAt(0);

        for (int i = 0; i < len; i++) {
            if (string.charAt(i) == tag) {
                field = string.substring(offset, i);

                if (field.length() == 0)
                    field = "";

                fieldList.add(field);
                offset = i + 1;
            }
        }

        if (tail == false) {
            field = string.substring(offset);

            if (field == null || field.length() == 0)
                field = "";

            fieldList.add(field);
        }

        return fieldList;
    }

    /**
     * 将字符串中指定位置的字符转换为大写字符
     *
     * @param msg
     * @param start 起始位置(包含), 取负代表从最后一个字符开始倒数，负无穷大和零时位置为0，正无穷大位置为length
     * @return
     */
    public static String toUpperCase(String msg, int start) {
        return toUpperCase(msg, start, 0);
    }

    /**
     * 将字符串中指定位置的字符转换为大写字符
     *
     * @param msg
     * @param start 起始位置(包含), 取负代表从最后一个字符开始倒数，负无穷大和零时位置为0，正无穷大位置为length
     * @param end   结束位置(不包含), 取负代表从最后一个字符开始倒数，负无穷大时位置为0，零和正无穷大位置为length
     * @return
     */
    public static String toUpperCase(String msg, int start, int end) {
        return changeCase(msg, start, end, true);
    }

    /**
     * 将字符串中指定位置的字符转换为小写字符
     *
     * @param msg
     * @param start 起始位置(包含), 取负代表从最后一个字符开始倒数，负无穷大和零时位置为0，正无穷大位置为length
     * @return
     */
    public static String toLowerCase(String msg, int start) {
        return toLowerCase(msg, start, 0);
    }

    /**
     * 将字符串中指定位置的字符转换为小写字符
     *
     * @param msg
     * @param start 起始位置(包含), 取负代表从最后一个字符开始倒数，负无穷大和零时位置为0，正无穷大位置为length
     * @param end   结束位置(不包含), 取负代表从最后一个字符开始倒数，负无穷大时位置为0，零和正无穷大位置为length
     * @return
     */
    public static String toLowerCase(String msg, int start, int end) {
        return changeCase(msg, start, end, false);
    }

    /**
     * 将字符串中指定位置的字符转换为小写或大写字符
     *
     * @param msg
     * @param start     起始位置(包含), 取负代表从最后一个字符开始倒数，负无穷大和零时位置为0，正无穷大位置为length
     * @param end       结束位置(不包含), 取负代表从最后一个字符开始倒数，负无穷大时位置为0，零和正无穷大位置为length
     * @param upperCase 是否转换为大写，true: 是, false: 否
     * @return
     */
    private static String changeCase(String msg, int start, int end, boolean upperCase) {
        if (isEmpty(msg)) {
            return msg;
        }

        if (start < 0) {
            start = Math.max(0, msg.length() + start); // 负无穷大取0
        }

        if (end <= 0) {
            end = msg.length() + end;
        }

        end = Math.min(end, msg.length()); // 正无穷大取length

        if (start >= msg.length() || end > msg.length() || start >= end) {
            return msg;
        }

        char[] chars = msg.toCharArray();
        for (int i = start; i < end; i++) {
            chars[i] = (upperCase ? Character.toUpperCase(chars[i]) : Character.toLowerCase(chars[i]));
        }

        return new String(chars);
    }

    /**
     * 将指定字符串连接起来，过滤为null的字符串
     *
     * @param objs
     * @return
     */
    public static String concat(Object... objs) {
        if (objs == null) {
            return null;
        }

        if (objs.length == 0) {
            return EMPTY;
        }

        StringBuilder buff = new StringBuilder();
        for (Object obj : objs) {
            if (obj != null) {
                buff.append(obj);
            }
        }

        return buff.toString();
    }

    /**
     * 将指定字符串连接起来，过滤为null的字符串
     *
     * @param col
     * @return
     */
    public static String concat(Collection<?> col) {
        if (col == null) {
            return null;
        }

        if (col.isEmpty()) {
            return EMPTY;
        }

        StringBuilder buff = new StringBuilder();
        for (Object obj : col) {
            if (obj != null) {
                buff.append(obj);
            }
        }

        return buff.toString();
    }

    private StringUtil() {
    }
}
