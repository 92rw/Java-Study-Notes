package com.javaweb.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns ="/CookieAccept")
public class CookieAccept extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("监听到访问请求");
        //1.接收浏览器提交的表单数据
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String resMsg = "";

        //2.判断是否合法
        if ("QDK".equalsIgnoreCase(username) && "18089".equals(pwd)) {
            resMsg = "<h1>登录成功！</h1>";
            //将登陆成功的用户名，以cookie的形式保存到浏览器
            Cookie userCookie = new Cookie("AcceptedUser", username);
            //设置Cookie的生命周期
            userCookie.setMaxAge(3600 * 24 * 3);
            //向浏览器返回Cookie数据
            response.addCookie(userCookie);
        } else {
            resMsg = "<h1>登录失败！</h1>";
        }
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(resMsg);
        writer.flush();//将缓存的数据进行刷新
        writer.close();//及时释放资源
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
