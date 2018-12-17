package com.spring.demo.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * cookie 相关操作类
 */
public class CookieUtil {
    public static Logger logger = LoggerFactory.getLogger("CookieUtils");

    /**
     * 写cookie
     *
     * @param type     1:erp;2:site&app
     * @param name     cookie 名称
     * @param value    明文值
     * @param age      过期时间 秒
     * @param path     路径
     * @param response
     */
    public static void setCookie(int type, String name, String value, int age, String path, HttpServletResponse response) {

        try {

            Cookie cookie = new Cookie(name, URLEncoder.encode(value, "utf-8"));

            cookie.setMaxAge(age);
            cookie.setPath(path);
            if (type == 2) {
                cookie.setDomain("51offer.com");
            }
            response.addCookie(cookie);
        } catch (Exception e) {
            logger.error("set cookie error {},{}", value, e);
        }
    }

    /**
     * 通过cookie name 获得值
     *
     * @param name
     */
    public static String getCookie(String name, HttpServletRequest request) {
        try {
			/*if(StringUtils.equals(name, "uid")){
				name="back_uid";
			}*/
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals(name)) {
						/*if(c.getName().equals("uid")){
							if(c.getDomain().equals("admin.51offer.com")){
								return URLDecoder.decode(c.getValue(),"utf-8");
							}else{
								return null;
							}
						}*/
                        return URLDecoder.decode(c.getValue(), "utf-8");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("get cookie error {},{}", name, e);
        }
        return null;
    }

    /**
     * 清除cookie值
     *
     * @param type 1:erp;2:site&app
     * @param name
     */
    public static String clearCookies(int type, HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c != null) {
                        c.setMaxAge(0);

                        c.setPath("/");

                        if (type == 2) {
                            c.setDomain("51offer.com");
                        }

                        response.addCookie(c);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("clear cookie error {}", e);
        }
        return null;
    }

}
