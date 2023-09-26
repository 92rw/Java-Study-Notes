package com.javaweb.ajax.servlet;

import com.google.gson.Gson;
import com.javaweb.ajax.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ajaxLogin1")
public class CheckUserServlet1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        //先定义向浏览器返回数据的格式
        response.setContentType("text/html;charset=utf-8");

        //如果用户名已被占用，以json格式返回该用户信息
        if ("king".equals(username)) {//用户名已被占用
            //后面这个king信息，是从DB中获取的
            User king = new User(1024, "king", "0xFFFF", "postmaster@outlook.com");
            //将king转成json字符串
            String strKing = new Gson().toJson(king);
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
