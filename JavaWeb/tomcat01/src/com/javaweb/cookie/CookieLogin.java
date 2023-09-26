package com.javaweb.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns ="/CookieLogin")
public class CookieLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回给用户的用户名
        String username = "";
        //判断浏览器发来的请求是否包含需要的Cookie
        Cookie[] cookies = request.getCookies();
        Cookie acceptedUser = CookieUtils.readCookieByName("AcceptedUser", cookies);
        if (acceptedUser != null) {
            username = acceptedUser.getValue();
        }

        response.setContentType("text/html;charset=utf-8");

        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>登录页面</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>用户登录界面</h1>\n" +
                "<form action=\"CookieAccept\" method=\"post\">\n" +
                "    电报码：<input type=\"text\" name=\"username\" value=\"" + username+ "\"><br/>\n" +
                "    车站码：<input type=\"password\" name=\"pwd\"><br/>\n" +
                "    <input type=\"submit\" value=\"登录\">\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>");

        //为了确保数据正确返回，可以使用这两个方法
        writer.flush();//将缓存的数据进行刷新
        writer.close();//及时释放资源
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
