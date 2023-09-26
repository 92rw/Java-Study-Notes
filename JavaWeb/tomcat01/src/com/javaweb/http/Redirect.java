package com.javaweb.http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Redirect", urlPatterns ="/course")
public class Redirect extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将请求重定向到Curriculum.html
        //运行原理：返回302状态码，在响应头中指定新的Location

        //如果是转发，地址是：主机地址+Tomcat地址+forward()中的字符串
        //浏览器解析重定向时，会被重定向到：主机地址+sendRedirect()中的字符串

        //Tomcat地址的获取
        String contextPath = getServletContext().getContextPath();
        System.out.println("application context=" + contextPath);
        //重定向时要带上Tomcat地址
        response.sendRedirect(contextPath + "/test/Curriculum.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}
