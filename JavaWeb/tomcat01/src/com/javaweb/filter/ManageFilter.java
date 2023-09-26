package com.javaweb.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ManageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //当Tomcat创建Filter后，就会调用该方法，进行初始化

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //每次访问filter过滤的链接时，都会调用此方法
        //如果这里没有指定下一步请求的链接，那么将无法继续访问
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        String username = (String)session.getAttribute("username");

        if (username != null) { //用户登录成功过
            //继续访问目标资源
            filterChain.doFilter(servletRequest,servletResponse);
            System.out.println("=====过滤器登入日志======");
            System.out.println("servletRequest：" + servletRequest);
            System.out.println("登录用户：" + username);
            System.out.println("IP地址：" + httpServletRequest.getRemoteAddr());
            System.out.println("访问页面：" + httpServletRequest.getRequestURL());
        } else{ //用户未登录成功过
            //返回到登录页面
            servletRequest.getRequestDispatcher("/filter/login.jsp").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        //当filter被销毁时，调用该方法
    }
}
