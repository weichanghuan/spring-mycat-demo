package com.easy;

/**
 * 给定一个 32 位有符号整数，将整数中的数字进行反转。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 123
 * 输出: 321
 * 示例 2:
 * <p>
 * 输入: -123
 * 输出: -321
 * 示例 3:
 * <p>
 * 输入: 120
 * 输出: 21
 * 注意:
 * <p>
 * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231,  231 − 1]。
 * 根据这个假设，如果反转后的整数溢出，则返回 0。
 */
public class First {

    public static Integer reverse(Integer x) {
        if (x == 0) {
            return 0;
        }

        long result = 0;
        if (x > 0) {
            while (true) {
                int n = x % 10;//取出最后一个数
                //判断是否是最后一位
                x = x / 10;//降位
                result = result * 10 + n;
                if (result > Integer.MAX_VALUE) {
                    return 0;
                }
                if (x == 0) {
                    return (int) result;
                }
            }
        }

        if (x < 0) {
            while (true) {
                int n = x % 10;//取出最后一个数
                //判断是否是最后一位
                x = x / 10;//降位
                result = result * 10 + n;

                if (result < Integer.MIN_VALUE) {
                    return 0;
                }
                if (x == 0) {
                    return (int) result;
                }


            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int reverse = First.reverse(-2147483648);
        System.out.println(reverse);
    }
}