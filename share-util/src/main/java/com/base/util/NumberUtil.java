package com.base.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

/**
 * Number工具类
 */
public final class NumberUtil {
    /**
     * 获取Byte值, 如果number为null, 则返回0
     *
     * @param number Byte
     * @return 数值
     */
    public static Byte defaultNumber(Byte number) {
        return defaultNumber(number, Byte.valueOf((byte) 0));
    }

    /**
     * 获取Short值, 如果number为null, 则返回0
     *
     * @param number Short
     * @return 数值
     */
    public static Short defaultNumber(Short number) {
        return defaultNumber(number, Short.valueOf((short) 0));
    }

    /**
     * 获取Integer值, 如果number为null, 则返回0
     *
     * @param number Integer
     * @return 数值
     */
    public static Integer defaultNumber(Integer number) {
        return defaultNumber(number, Integer.valueOf(0));
    }

    /**
     * 获取Long值, 如果number为null, 则返回0
     *
     * @param number Long
     * @return 数值
     */
    public static Long defaultNumber(Long number) {
        return defaultNumber(number, Long.valueOf(0));
    }

    /**
     * 获取Float值, 如果number为null, 则返回0
     *
     * @param number Float
     * @return 数值
     */
    public static Float defaultNumber(Float number) {
        return defaultNumber(number, Float.valueOf(0));
    }

    /**
     * 获取Double值, 如果number为null, 则返回0
     *
     * @param number Double
     * @return 数值
     */
    public static Double defaultNumber(Double number) {
        return defaultNumber(number, Double.valueOf(0));
    }

    /**
     * 获取BigDecimal值, 如果number为null, 则返回0
     *
     * @param number BigDecimal
     * @return 数值
     */
    public static BigDecimal defaultNumber(BigDecimal number) {
        return defaultNumber(number, BigDecimal.ZERO);
    }

    /**
     * 获取BigInteger值, 如果number为null, 则返回0
     *
     * @param number BigInteger
     * @return 数值
     */
    public static BigInteger defaultNumber(BigInteger number) {
        return defaultNumber(number, BigInteger.valueOf(0));
    }

    /**
     * 获取Number值, 如果number为null, 则返回defNumber
     *
     * @param number Number
     * @return 数值
     */
    public static <T extends Number> T defaultNumber(T number, T defNumber) {
        return (number != null ? number : defNumber);
    }

    /**
     * 对numbers中所有数字求和
     *
     * @param numbers
     * @return
     */
    public static int sum(int[] numbers) {
        int rst = 0;
        if (numbers != null) {
            for (int number : numbers) {
                rst += number;
            }
        }

        return rst;
    }

    /**
     * 对numbers中所有数字求和
     *
     * @param numbers
     * @return
     */
    public static long sum(long[] numbers) {
        long rst = 0;
        if (numbers != null) {
            for (long number : numbers) {
                rst += number;
            }
        }

        return rst;
    }

    /**
     * 对numbers中所有不为null的数字求和
     *
     * @param numbers
     * @return
     */
    public static BigDecimal sum(Number... numbers) {
        BigDecimal rst = BigDecimal.ZERO;
        if (numbers != null) {
            for (Number num : numbers) {
                if (num != null) {
                    rst = rst.add(asBigDecimal(num));
                }
            }
        }

        return rst;
    }

    /**
     * 对numbers中所有不为null的数字求和
     *
     * @param numbers
     * @return
     */
    public static BigDecimal sum(Collection<? extends Number> numbers) {
        BigDecimal rst = BigDecimal.ZERO;
        if (numbers != null) {
            for (Number num : numbers) {
                if (num != null) {
                    rst = rst.add(asBigDecimal(num));
                }
            }
        }

        return rst;
    }

    /**
     * 是否是纯数字，可以包含负号
     *
     * @param number
     * @return
     */
    public static boolean isDigits(String number) {
        return (number != null && number.matches("-?\\d+"));
    }

    /**
     * 将字符串转换成Long
     *
     * @param number
     * @param defNum
     * @return
     */
    public static Long parseLong(String number, Long defNum) {
        try {
            return (number != null ? Long.valueOf(number) : defNum);
        } catch (Exception ex) {
            return defNum;
        }
    }

    /**
     * 将字符串转换成Integer
     *
     * @param number
     * @param defNum
     * @return
     */
    public static Integer parseInteger(String number, Integer defNum) {
        try {
            return (number != null ? Integer.valueOf(number) : defNum);
        } catch (Exception ex) {
            return defNum;
        }
    }

    /**
     * 将字符串转换成BigDecimal
     *
     * @param number
     * @param defNum
     * @return
     */
    public static BigDecimal parseBigDecimal(String number, BigDecimal defNum) {
        try {
            return (number != null ? new BigDecimal(number) : defNum);
        } catch (Exception ex) {
            return defNum;
        }
    }

    /**
     * 将数字转换为Integer
     *
     * @param number
     * @return
     */
    public static Integer asInteger(Number number) {
        return asInteger(number, null);
    }

    /**
     * 将数字转换为Integer
     *
     * @param number
     * @param defValIfNull
     * @return
     */
    public static Integer asInteger(Number number, Integer defValIfNull) {
        if (number == null) {
            return defValIfNull;

        } else if (number instanceof Integer) {
            return (Integer) number;
        }

        return Integer.valueOf(number.intValue());
    }

    /**
     * 将数字转换为Long
     *
     * @param number
     * @return
     */
    public static Long asLong(Number number) {
        return asLong(number, null);
    }

    /**
     * 将数字转换为Long
     *
     * @param number
     * @param defValIfNull
     * @return
     */
    public static Long asLong(Number number, Long defValIfNull) {
        if (number == null) {
            return defValIfNull;

        } else if (number instanceof Long) {
            return (Long) number;
        }

        return Long.valueOf(number.longValue());
    }

    /**
     * 将数字转换为BigDecimal
     *
     * @param number
     * @return
     */
    public static BigDecimal asBigDecimal(Number number) {
        return asBigDecimal(number, null);
    }

    /**
     * 将数字转换为BigDecimal
     *
     * @param number
     * @param defValIfNull
     * @return
     */
    public static BigDecimal asBigDecimal(Number number, BigDecimal defValIfNull) {
        if (number == null) {
            return defValIfNull;

        } else if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        }

        return new BigDecimal(number.toString());
    }

    /**
     * num1是否大于num2, 如果任何一个为null则返回false
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean greaterThan(BigDecimal num1, BigDecimal num2) {
        return (num1 != null && num2 != null && num1.compareTo(num2) > 0);
    }

    /**
     * num1是否大于num2, 如果任何一个为null则返回false
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean greaterThan(Integer num1, Integer num2) {
        return (num1 != null && num2 != null && num1.compareTo(num2) > 0);
    }

    /**
     * num1是否大于num2, 如果任何一个为null则返回false
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean greaterThan(Number num1, Number num2) {
        return (num1 != null && num2 != null && asBigDecimal(num1).compareTo(asBigDecimal(num2)) > 0);
    }

    /**
     * num1是否小于num2, 如果任何一个为null则返回false
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean lessThan(BigDecimal num1, BigDecimal num2) {
        return (num1 != null && num2 != null && num1.compareTo(num2) < 0);
    }

    /**
     * num1是否小于num2, 如果任何一个为null则返回false
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean lessThan(Integer num1, Integer num2) {
        return (num1 != null && num2 != null && num1.compareTo(num2) < 0);
    }

    /**
     * num1是否小于num2, 如果任何一个为null则返回false
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean lessThan(Number num1, Number num2) {
        return (num1 != null && num2 != null && asBigDecimal(num1).compareTo(asBigDecimal(num2)) < 0);
    }

    /**
     * 获取nums最大值
     *
     * @param nums
     * @return
     */
    public static BigDecimal max(BigDecimal... nums) {
        if (ArrayUtil.isEmpty(nums)) {
            return null;
        }

        BigDecimal max = null;
        for (BigDecimal num : nums) {
            if (max == null || (num != null && num.compareTo(max) > 0)) {
                max = num;
            }
        }

        return max;
    }

    /**
     * 获取nums最小值
     *
     * @param nums
     * @return
     */
    public static BigDecimal min(BigDecimal... nums) {
        if (ArrayUtil.isEmpty(nums)) {
            return null;
        }

        BigDecimal min = null;
        for (BigDecimal num : nums) {
            if (min == null || (num != null && num.compareTo(min) < 0)) {
                min = num;
            }
        }

        return min;
    }

    /**
     * 取负数
     *
     * @param num
     * @return
     */
    public static BigDecimal negate(BigDecimal num) {
        return (num != null ? num.negate() : null);
    }

    /**
     * 取负数
     *
     * @param num
     * @return
     */
    public static Integer negate(Integer num) {
        return (num != null ? Integer.valueOf(-num.intValue()) : null);
    }

    /**
     * 取负数
     *
     * @param num
     * @return
     */
    public static Long negate(Long num) {
        return (num != null ? Long.valueOf(-num.longValue()) : null);
    }

    /**
     * 构造函数
     */
    private NumberUtil() {
    }
}
