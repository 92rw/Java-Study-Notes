package com.javaweb.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns ="/CreateSession")
public class CreateSession extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("createSession被调用");

        //获取Session，同时可能创建Session
        HttpSession session = request.getSession();
        //获取当前Session的id
        System.out.println(session.getId());

        //在Session中存放数据
        session.setAttribute("Version","1.1.4");
        //设置生命周期（单位为秒）
        session.setMaxInactiveInterval(200);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("<h1>创建Session成功，生命周期200秒</h1>");
        writer.flush();//将缓存的数据进行刷新
        writer.close();//及时释放资源
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}