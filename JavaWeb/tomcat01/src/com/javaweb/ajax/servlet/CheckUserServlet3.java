package com.javaweb.ajax.servlet;

import com.google.gson.Gson;
import com.javaweb.ajax.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ajaxLogin3")
public class CheckUserServlet3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");//和前面两个servlet不一样，原因是前端发来的参数名称不一样

        Gson gson = new Gson();

        response.setContentType("text/json;charset=UTF-8");//如果不设置text格式，火狐浏览器会提示“XML解析错误：格式不佳"

        if ("king".equals(username)) {//用户名已被占用
            //后面这个king信息，是从DB中获取的
            User king = new User(1024, "king", "0xFFFF", "postmaster@outlook.com");
            //向前端返回king字符串
            response.getWriter().write(gson.toJson(king));
        } else {
            //这样向前端返回的数据都是User类，不再需要解析null
            User voidUser = new User(-1, "", "", "");
            response.getWriter().write(gson.toJson(voidUser));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
