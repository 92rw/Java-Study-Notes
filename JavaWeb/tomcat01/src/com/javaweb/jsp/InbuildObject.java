package com.javaweb.jsp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns ="/Inbuild")
public class InbuildObject extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("InbuildObject类被调用");

        //梳理Servlet中可以使用的对象
        PrintWriter writer = response.getWriter();
        writer.println("haha");
        request.getParameter("age");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        session.setAttribute("job", "java工程师90000");
        //域对象ServletContext
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("count", 666);
        ServletConfig servletConfig = getServletConfig();
        servletConfig.getInitParameter("pwd");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}