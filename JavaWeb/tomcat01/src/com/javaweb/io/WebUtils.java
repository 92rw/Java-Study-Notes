package com.javaweb.io;

import java.time.LocalDateTime;

public class WebUtils {
    public static String getYearMonthDay() {
        LocalDateTime time = LocalDateTime.now();
        return String.format("/%d/%d/%d",time.getYear(),time.getMonthValue(),time.getDayOfMonth());
    }
      //用于测试是否可以正常输出
//    public static void main(String[] args) {
//        System.out.println(WebUtils.getYearMonthDay());
//    }
}
