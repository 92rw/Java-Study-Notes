package com.javaweb.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 演示Servlet开发过程，需要重写5个接口方法
 */
public class ServletInterface implements Servlet {

    private int count = 0;//用于计算程序被调用次数
    /**
     * 初始化servlet：当Tomcat创建此类实例时，会调用init方法（仅调用一次）
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("ServletInterface的init()方法被调用");
    }

    /**
     * 返回Servlet的配置
     * @return
     */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 处理浏览器的请求，包括get/post。浏览器每次请求Servlet时，都会调用此方法。
     * 当Tomcat调用此方法时，会把http请求的数据封装成实现了 ServletRequest 接口的servletRequest对象，由此得到用户提交的数据
     * servletResponse对象返回数据给Tomcat，Tomcat解析后返回数据给浏览器
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        count++;
        //count的数值不停累计，说明此程序是单例的
        System.out.println("当前count的值为" + count);
        //Tomcat每处理一个http线程，就生成一个新的线程
        System.out.println("当前线程id" + Thread.currentThread().getId());

        //从servletRequest对象获取请求方式：因为ServletRequest类没有得到提交方式的方法，
        // 因此向下转型为子接口HttpServletRequest，调用其getMethod() 方法
        String method = ((HttpServletRequest) servletRequest).getMethod();
        if ("GET".equals(method)) {
            doGet();
        } else if ("POST".equals(method)) {
            doPost();
        }

    }

    /**
     * 返回Servlet信息，使用较少
     * @return
     */
    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     * 在Servlet销毁时调用，仅调用一次
     */
    @Override
    public void destroy() {
        System.out.println("ServletInterface的destroy()方法被调用");
    }

    /**
     * 用于响应get请求
     */
    public void doGet() {
        System.out.println("ServletInterface的doGet()方法被调用");
    }

    /**
     * 用于响应post请求
     */
    public void doPost() {
        System.out.println("ServletInterface的doPost()方法被调用");
    }
}
