package com.javaweb.servlet;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用注解方式配置Servlet
 * 注解方式和web.xml配置Servlet的流程机制一样
 *
 * @WebServlet 是一个注解，可完成name，url-pattern,load-on-startup等配置
 */
@WebServlet(urlPatterns = {"/ok1", "/ok2"})
public class AnnotationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("注解配置Servlet的doPost()方法被调用");
        System.out.println("访问的浏览器ip= " + request.getRemoteAddr());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("注解配置Servlet的doGet()方法被调用");
        System.out.println("访问的浏览器ip= " + request.getRemoteAddr());
    }
}
