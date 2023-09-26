package com.javaweb.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.HashMap;

/**
 * 模拟Tomcat通过@WebServlet注解装载实例的过程
 */
public class TestAnnotationServlet {
    private static final HashMap<String, Object> hm = new HashMap<>();

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //扫描包的路径，得到类的全路径（IO操作）
        String classAllPath = "com.javaweb.servlet.AnnotationServlet";
        //得到 AnnotationServlet 的Class对象
        Class<?> aClass = Class.forName(classAllPath);
        //得到注解类实例，获取和url相关的注解
        WebServlet annotation = aClass.getAnnotation(WebServlet.class);
        String[] urlPatterns = annotation.urlPatterns();
        for (String urlPattern : urlPatterns) {
            System.out.println("url= "+ urlPattern);
        }

        //如果url匹配，且第一次加载，Tomcat会创建一个 AnnotationServlet 实例到HashMap
        Object instance = aClass.newInstance();
        System.out.println("instance= " + instance);//运行类型是AnnotationServlet

        //将对象实例加入到HashMap
        hm.put("AnnotationServlet", instance);
    }
}
