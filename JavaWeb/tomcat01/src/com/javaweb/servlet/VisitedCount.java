package com.javaweb.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/count"})
public class VisitedCount extends HttpServlet {
    /**
     * 统计网页被访问次数
     * 可将计数相关的代码块封装到static修饰的方法中，传入参数ServletContext，返回Integer，以便整个网站进行调用
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取到ServletContext对象
        ServletContext servletContext = getServletContext();

         //从servletContext获取 visit_count 属性 key-value
         Object visit_count = servletContext.getAttribute("visit_count");
         //判断visit_count是否为null
         if (visit_count == null) {//说明是第1次访问网站
             servletContext.setAttribute("visit_count", 1);
             visit_count = 1;//如果这里不赋值，那么浏览器第一次访问时得到的是null
         } else { //是第二次或以后
             //取出visit_count属性的值+1
             visit_count = Integer.parseInt(visit_count + "") + 1;
             //放回到servletContext
             servletContext.setAttribute("visit_count", visit_count);
         }


        //输出显示
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.printf("<h1>网站被访问的次数是%s</h1>",visit_count);
        writer.flush();
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //演示ServletContext的使用
        //得到 ServletContext 对象
        ServletContext servletContext = getServletContext();
        //获取到web.xml的<context-param>信息
        String website = servletContext.getInitParameter("website");
        String user = servletContext.getInitParameter("user");
        System.out.println("website= " + website);
        System.out.println("user= " + user);
        //获取项目的域名路径
        String contextPath = servletContext.getContextPath();
        System.out.println("域名路径= " + contextPath);
        //获取项目发布后的真实工作路径
        //这里的斜杠“/”表示项目发布后的根路径
        String realPath = servletContext.getRealPath("/");
        System.out.println("真实路径= " + realPath);

        //上面的方法都是在控制台中显示，下面调用doPost方法，向浏览器返回当前网页访问次数
        doPost(request,response);
    }
}

