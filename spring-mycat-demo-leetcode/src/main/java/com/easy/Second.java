package com.easy;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * 输入: 121
 * 输出: true
 * <p>
 * 示例 2:
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * <p>
 * 示例 3:
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 */
public class Second {

    public static void main(String[] args) {
        int count = getCount(11110);
        System.out.println(count);
        System.out.println(getResult(11111));
    }


    public static boolean getResult(int l) {
        if (l < 0) {
            return Boolean.FALSE;
        }
        //得到位数
        int count = getCount(l);
        //判断循环次数
        int i = count / 2;
        for (int m = 1; m <= i; ) {
            long first = getInt(m, l);
            long second = getInt(count, l);
            if (first == second) {
                m++;
                count--;
                continue;
            }
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }


    public static int getCount(int l) {
        int i = 0;
        do {
            l /= 10;
            i++;
            if (l <= 0) {
                return i;
            }
        } while (true);

    }

    /**
     * 得到整数任意位的数字
     *
     * @param i 第几位
     * @param l 数字
     * @return
     */
    public static long getInt(int i, int l) {
        long temp = 1;
        for (int m = 1; m < i; m++) {
            temp = temp * 10;
        }

        long num_hundred = l / temp % 10;

        return num_hundred;
    }
}
