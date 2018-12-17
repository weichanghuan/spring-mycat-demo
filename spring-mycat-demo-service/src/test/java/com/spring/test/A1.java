package com.spring.test;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class A1 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8};

        int min = IntStream.of(nums).min().getAsInt();
        System.out.println(min);


        new Thread(() -> System.out.println("running")).start();


        String s = "17621053900".replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        System.out.println(s);

        String s1 = getPinYinHeadChar("马同学");
        System.out.println(s1.charAt(0));


        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        List<String> list1 = list.subList(0, 3);


        Timestamp a = Timestamp.valueOf("2018-05-18 09:32:32");
        Timestamp b = Timestamp.valueOf("2018-05-11 09:32:32");
        if (b.before(a)) {
            System.out.println("b时间比a时间早");
        }

        System.out.println("end");
    }


    /**
     * 得到中文首字母
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

}
