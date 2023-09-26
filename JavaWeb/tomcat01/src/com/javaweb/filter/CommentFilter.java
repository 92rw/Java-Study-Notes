package com.javaweb.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = "/filter/comment/*",initParams = {@WebInitParam(name = "forbiddenword",value = "大地震八级，查学历，悉尼的弟弟，见识大高玩的厉害"),
        @WebInitParam(name = "port",value = "6980")})
public class CommentFilter implements Filter {
    private String[] forbiddenword;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //判断是否包含禁用词
        String content = req.getParameter("content");
        for (String word : forbiddenword) {
            if (content.contains(word)) {
                req.setAttribute("errorInfo","检测到禁用词 " + word);
                req.getRequestDispatcher("/filter/comment.jsp").forward(req, resp);
                return;//如果不写return，那么会继续执行下方的代码
            }
        }

        //继续访问目标资源
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        //获取禁用词
        forbiddenword = config.getInitParameter("forbiddenword").split("，");
    }
}
