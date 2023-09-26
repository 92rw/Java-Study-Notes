package com.javaweb.http;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns ="/check")
public class CheckServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("CheckServlet 被调用");
        //根据用户名确定该用户是什么身份
        String user = request.getParameter("username");
        //如果是同一个request对象（请求转发），那么可以在不同的Servlet中使用getAttribute()方法获取数据
        if("tom".equals(user)) {
            //设置域数据
            request.setAttribute("role","管理员");
        } else {
            request.setAttribute("role", "普通用户");
        }

        //获取分发器，说明："/manager"是下一个Servlet的uri，"/"被解析成tomcat的地址
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/manager");
        //利用分发器的forward()方法把当前Servlet的request和response传递给下一个Servlet
        requestDispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
