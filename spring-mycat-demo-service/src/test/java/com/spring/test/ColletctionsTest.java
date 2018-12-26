package com.spring.test;

import java.net.URLEncoder;
import java.util.*;

/**
 * 测试Colletctions
 */
public class ColletctionsTest {

    public static void main(String[] args)throws Exception {
//        List<String> list = new ArrayList<String>();
//
//
//        list.add("3");
//        list.add("4");
//        list.add("1");
//        list.add("2");
//        System.out.println("de");
//        Collections.sort(list, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return 0;
//            }
//        });
//
//
//        Collections.sort(list);
//        System.out.println("end");

        String filePath = "用户信息" ;
        String encode = URLEncoder.encode(filePath, "utf-8");
        System.out.println(encode);
    }
}
