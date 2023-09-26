package com.javaweb.jsp;

public class WebUtils {
    public static int parseInt(String strNum, int defaultVal) {
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println(strNum + "不能转成整数");
        }
        return defaultVal;
    }
}
