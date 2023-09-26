package com.javaweb.filter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns ="/Author")
public class AuthorizationCheck extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //假设密码123456即可通过
        if ("123456".equals(password)) {//用户信息合法

            request.getSession().setAttribute("username", username);
            //请求转发到admin.jsp
            request.getRequestDispatcher("/filter/manage/admin.jsp").forward(request,response);
        } else {//用户信息不合法
            //返回登录页面
            request.getRequestDispatcher("/filter/login.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}