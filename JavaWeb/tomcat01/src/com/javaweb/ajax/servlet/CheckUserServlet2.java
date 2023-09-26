package com.javaweb.ajax.servlet;

import com.google.gson.Gson;
import com.javaweb.ajax.entity.User;
import com.javaweb.ajax.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ajaxLogin2")
public class CheckUserServlet2 extends HttpServlet {
    //定义一个UserService属性
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        //先定义向浏览器返回数据的格式
        response.setContentType("text/html;charset=utf-8");

        //到数据库查询
        User userByName = userService.getUserByName(username);

        //如果用户名已被占用，以json格式返回该用户信息
        if (userByName != null) {//用户已存在
            //将User对象转成json字符串并返回
            String strKing = new Gson().toJson(userByName);
            //向前端返回信息
            response.getWriter().write(strKing);
        } else {
            response.getWriter().write("");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
