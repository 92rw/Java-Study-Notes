package com.javaweb.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 读取从浏览器发来的cookie信息
 */
@WebServlet(urlPatterns ="/Readcookie")
public class ReadCookies extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ReadCookies 方法被调用");
        //1.通过 HttpServletRequest 对象读取所有cookie
        Cookie[] cookies = request.getCookies();
        //2.遍历cookie
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                System.out.printf("\ncookie名：%s，值：%s",cookie.getName(),cookie.getValue());
            }
        }
        //3.向浏览器返回信息
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("<h1>Cookie读取成功</h1>");

        writer.flush();
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}