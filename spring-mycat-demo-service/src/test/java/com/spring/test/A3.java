package com.spring.test;

import com.spring.demo.utils.JSONUtil;

import java.util.HashMap;
import java.util.Map;

public class A3 {


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        temp(map);
        System.out.println(JSONUtil.toJSonString(map));


    }


    public static void temp(Map<String, String> map) {
        map.put("1", "1");
    }
}
