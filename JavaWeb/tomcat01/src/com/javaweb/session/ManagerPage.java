package com.javaweb.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns ="/ManagerPage")
public class ManagerPage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object acceptedUser = session.getAttribute("AcceptedUser");
        if (acceptedUser == null) {
            //用户没有登录过，将请求重定向
            response.sendRedirect(request.getContextPath() + "/test/SessionLogin.html");
            return;//目前下方的代码都在括号内，不需要继续执行。但是考虑到后续增加功能，故而此处中断执行
        } else {
            System.out.println("用户" + acceptedUser +"成功登录");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.println("<h1>登录成功</h1>");
            writer.println("欢迎你，管理员" + acceptedUser.toString());
            writer.flush();
            writer.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}