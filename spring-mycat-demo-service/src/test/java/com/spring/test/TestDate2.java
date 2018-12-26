package com.spring.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate2 {


    public static void main(String[] args) {
        System.out.println(timeStamp2Date("1545793553","YYYY-mm-dd HH:mm:ss"));
    }

    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }

}
