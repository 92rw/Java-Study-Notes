package com.javaweb.cookie;

import javax.servlet.http.Cookie;

public class CookieUtils {

    //编写一个方法，返回指定名字的cookie值
    public static Cookie readCookieByName(String name, Cookie[] cookies) {

        //判断传入的参数是否正确
        if (name == null || "".equals(name) || cookies == null || cookies.length == 0) {
            return null;
        }
        //遍历cookies
        for (Cookie cookie : cookies) {
            if(name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
}
