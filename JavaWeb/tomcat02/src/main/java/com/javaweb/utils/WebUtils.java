package com.javaweb.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WebUtils {
    /**
     * 将传入的字符串，转成int形式
     * @param strNum
     * @param defaultVal 如果转换失败，返回这个值
     * @return
     */
    public static int parseInt(String strNum, int defaultVal) {
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println(strNum + "无法转换为数字");
        }

        return defaultVal;
    }

    //判断uri是不是html文件
    public static boolean isHtml(String uri) {
        return uri.endsWith(".html");
    }

    //根据文件名读取文件
    public static String readHtml(String filename) {
        String path = WebUtils.class.getResource("/").getPath();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path + filename));
            String context = "";
            while ((context = bufferedReader.readLine()) != null) {
                stringBuilder.append(context);
                System.out.println(context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
