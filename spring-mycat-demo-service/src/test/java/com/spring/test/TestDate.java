package com.spring.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {
    public static void main(String[] args) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (date.equals("2018-11-01")) {
            System.out.println("111");
        }
    }
}
