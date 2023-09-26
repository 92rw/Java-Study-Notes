package com.javaweb.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns ="/SessionCheck")
public class LoginCheck extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //得到提交的用户名和密码
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        if ("95306".equals(pwd)) {
            //将用户名保存到Session
            HttpSession session = request.getSession();
            session.setAttribute("AcceptedUser", username);

            //请求转发到ManagerPage
            request.getRequestDispatcher("ManagerPage").forward(request,response);
        } else {
            //请求转发进入到LoginError.html，（服务器端的地址）
            request.getRequestDispatcher("test/LoginError.html").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}