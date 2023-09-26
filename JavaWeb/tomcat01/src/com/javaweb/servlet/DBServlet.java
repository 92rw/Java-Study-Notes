package com.javaweb.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DBServlet extends HttpServlet {
    /**
     * 查看这个init方法的源码了解到运行底层机制：
     * 1、当DBServlet对象初始化时，Tomcat会同时创建一个ServletConfig对象
     * 2、此处super.init(config)方法会调用父类GenericServlet的方法，把Tomcat创建的对象赋给父类
     * 3、如果将 super.init(config); 语句注销，那么将无法调用 getServletConfig()方法获取 config属性
     * 4、如果重写init()方法，如果要在其他方法调用getServletConfig()方法获取 ServletConfig类对象，那么
     * 必须要保留super.init(config)语句，否则得到的是null
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init=" + config);
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //要求：获取到配置的用户名或密码
        //解决方式：利用从父类GenericServlet继承来的方法getServletConfig()
        //说明：返回的servletConfig对象是 private transient ServletConfig config
        //transient关键字修饰的属性保存在内存中，不会被串行化（不会被保存到文件）
        ServletConfig servletConfig = getServletConfig();
        String username = servletConfig.getInitParameter("username");
        String pwd = servletConfig.getInitParameter("pwd");
        System.out.printf("初始化得到参数 username=%s, 密码=%s\n",username,pwd);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
