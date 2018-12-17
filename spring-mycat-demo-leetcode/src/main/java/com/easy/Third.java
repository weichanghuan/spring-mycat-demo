package com.easy;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * 示例 1:
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 * <p>
 * 示例 2:
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 * 说明:
 * <p>
 * 所有输入只包含小写字母 a-z 。
 */
public class Third {

    public static void main(String[] args) {
        System.out.println("a".length());
    }


    public String longestCommonPrefix(String[] strs) {
        if (null == strs || strs.length <= 0) {
            return "";
        }

        String str1 = strs[0];
        int length = str1.length();
        if (strs.length == 1) {
            return str1;
        }
        String[] temp = new String[strs.length - 1];
        System.arraycopy(strs, 1, temp, 0, temp.length);

        for (int i = 0; i < temp.length; i++) {
            for (int m = 0; m < length; m++) {
                char c = str1.charAt(m);

                int lengthtemp = temp[i].length();
                char c2;
                if (m < lengthtemp) {
                    c2 = temp[i].charAt(m);
                } else {
                    return str1.substring(0, m);
                }


                if (c == c2) {
                    continue;
                }
                if (i == 0) {
                    return "";
                }

                return str1.substring(0, m);


            }
        }


        return "";
    }
}
