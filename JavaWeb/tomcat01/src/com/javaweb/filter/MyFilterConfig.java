package com.javaweb.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = "/myFilter/*",initParams = {@WebInitParam(name = "ip",value = "11.45.1.4"),
        @WebInitParam(name = "port",value = "6980")})
public class MyFilterConfig implements Filter {
    private String ip;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //先获取到访问来源的ip，封杀要过滤的网段
        String remoteAddr = req.getRemoteAddr();
        if (remoteAddr.contains(ip)) {
            System.out.println("过滤掉来自 " +  remoteAddr + " 的访问请求");
            req.getRequestDispatcher("/filter/login.jsp").forward(req, resp);
            return;//如果不写return，那么会继续执行下方的代码
        }
        //继续访问目标资源
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("init()方法被调用");
        //通过FilterConfig获取相关的参数
        String filterName = config.getFilterName();
        ip = config.getInitParameter("ip");//用于后续过滤功能
        ServletContext servletContext = config.getServletContext();

        System.out.println("当前过滤器名：" + filterName);
        System.out.println("过滤掉的网段：" + ip);
        System.out.println("========");
        Enumeration<String> initParameterNames = config.getInitParameterNames();

        //枚举的遍历
        while (initParameterNames.hasMoreElements()) {
            System.out.println("参数名：" + initParameterNames.nextElement());
        }
    }
}
