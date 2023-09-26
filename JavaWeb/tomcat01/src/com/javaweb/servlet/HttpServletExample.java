package com.javaweb.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpServletExample extends HttpServlet {
    //重写doGet和doPost方法

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HttpServlet子类的doGet()方法被调用");
        //得到工程发布后的实际磁盘路径
        String realPath = getServletContext().getRealPath("/");

        //得到Tomcat的域名路径
        String contextPath = getServletContext().getContextPath();

        System.out.println("realPath= " + realPath);
        System.out.println("contextPath= " + contextPath);
        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HttpServlet子类的doPost()方法被调用");
    }
}
