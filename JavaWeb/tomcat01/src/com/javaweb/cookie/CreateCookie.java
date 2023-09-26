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
 * 演示如何创建cookie，并保存到浏览器
 */
@WebServlet(urlPatterns ="/Newcookie")
public class CreateCookie extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("CreateCookie 方法被调用");
        //1.创建一个Cookie对象
        //第一个字符串是key（唯一），第二个字符串是value
        //此时cookie在服务器端，还没发给浏览器
        Cookie cookie = new Cookie("carriage", "sleeper");

        response.setContentType("text/html;charset=utf-8");
        //2.将cookie发送给浏览器
        response.addCookie(cookie);
        PrintWriter writer = response.getWriter();
        writer.println("<h1>Cookie创建成功</h1>");

        writer.flush();
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}
